package org.hotel;

import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.command.factory.CommandFactory;
import org.hotel.command.factory.CommandFactoryImpl;
import org.hotel.connection.ConnectionPool;
import org.hotel.dao.DAOHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import static org.hotel.constants.WebConstants.COMMAND_PARAMETER;

@Log4j2
@WebServlet(name = "Controller", value = "/controller")
public class FrontController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            DAOHelper daoHelper = new DAOHelper(connection);
            CommandFactory commandFactory = new CommandFactoryImpl(daoHelper);
            String commandName = request.getParameter(COMMAND_PARAMETER);
            Command command = commandFactory.getCommand(commandName);
            CommandResult commandResult = command.execute(request, response);
            dispatch(request, response, commandResult);
        } catch (Exception e) {
            log.error("Process method failed {}", e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult)
            throws ServletException, IOException {
        String page = commandResult.getPage();
        if (commandResult.isRedirect()) {
            String contextPath = getServletContext().getContextPath();
            response.sendRedirect(contextPath + page);
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        }
    }

}
