package org.hotel.command.factory;

import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.impl.HomeCommand;
import org.hotel.command.impl.LoginCommand;
import org.hotel.command.impl.LogoutCommand;
import org.hotel.command.impl.SignInCommand;
import org.hotel.command.impl.reservation.room.ShowRoomsCommand;
import org.hotel.dao.DAOHelper;
import org.hotel.service.api.ReservationService;
import org.hotel.service.api.RoomClassService;
import org.hotel.service.api.RoomService;
import org.hotel.service.api.UserService;
import org.hotel.service.impl.ReservationServiceImpl;
import org.hotel.service.impl.RoomClassServiceImpl;
import org.hotel.service.impl.RoomServiceImpl;
import org.hotel.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.hotel.constants.WebConstants.*;

@Log4j2
public class CommandFactoryImpl implements CommandFactory {

    private final DAOHelper daoHelper;
    private final Map<String, Command> commands = new HashMap<>();

    public CommandFactoryImpl(DAOHelper daoHelper) {
        this.daoHelper = daoHelper;
        init();
    }


    private void init(){
        commands.put(COMMAND_HOME, new HomeCommand());
        commands.put(COMMAND_SIG_IN, new SignInCommand(getUserService()));
        commands.put(COMMAND_LOGIN, new LoginCommand(getUserService()));
        commands.put(COMMAND_SIG_OUT, new LogoutCommand());
        commands.put(COMMAND_ROOMS, new ShowRoomsCommand(getReservationService(),
                getRoomService(), getRoomClassService()));
    }

    @Override
    public Command getCommand(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.warn("Command not found, commandName --> {}", commandName);
            return commands.get(COMMAND_HOME);
        }

        return commands.get(commandName);
    }

    private  UserService getUserService() {
        return new UserServiceImpl(daoHelper);
    }

    private  RoomService getRoomService() {
        return new RoomServiceImpl(daoHelper);
    }

    private  RoomClassService getRoomClassService() {
        return new RoomClassServiceImpl(daoHelper);
    }

    private  ReservationService getReservationService() {
        return new ReservationServiceImpl(daoHelper);
    }
}
