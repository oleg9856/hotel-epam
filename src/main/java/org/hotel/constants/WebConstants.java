package org.hotel.constants;

public class WebConstants {
    public static final String CONTROLLER_PATH = "/controller?command=";
    public static final String COMMAND_PARAMETER = "command";
    public static final String ID_PARAMETER = "id";


    public static final String SIG_IN_INVALID_DATA = "sig_in.invalid_data";

    public static final String COMMAND_HOME = "home";
    public static final String COMMAND_SIG_IN = "sign_in";
    public static final String COMMAND_SIG_OUT = "sign_out";
    public static final String COMMAND_LOGIN = "login";
    public static final String COMMAND_ROOMS = "rooms";

    public static final String LOGIN_PATH = CONTROLLER_PATH +
            COMMAND_HOME;
    public static final String HOME_PATH = CONTROLLER_PATH +
            COMMAND_HOME;

    public static final String HOME_PAGE_JSP_PATH = "/WEB-INF/jsp/home.jsp";
    public static final String ROOMS_PAGE_JSP_PATH = "/WEB-INF/jsp/rooms.jsp";
    public static final String SIG_IN_PAGE_JSP_PATH = "/WEB-INF/jsp/signin.jsp";
    public static final String LOGIN_PAGE_JSP_PATH = "/WEB-INF/jsp/login.jsp";
    public static final String VIEW_503_ERROR = "/WEB-INF/jsp/503-error.jsp";
    public static final String ROOM_PAGE_JSP_PATH = "/WEB-INF/jsp/rooms.jsp";



}
