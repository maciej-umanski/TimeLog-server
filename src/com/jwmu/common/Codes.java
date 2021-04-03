package com.jwmu.common;

public interface Codes {
    //VERY BAD CODE
    String INTERNAL_ERROR = "-500";

    //error codes
    String WRONG_CREDENTIALS = "-1";
    String CLIENT_NOT_LOGGED = "-2";
    String CLIENT_IS_LOGGED = "-3";
    String WRONG_COMMAND = "-4";
    String DATABASE_DISCONNECTED = "-5";
    String LOGIN_EXIST = "-6";
    Object NO_PERMISSION = "-7";

    //good codes
    String CLIENT_CONNECTED = "0";
    String SUCCESSFUL_LOGIN = "1";
    String SUCCESSFUL_LOGOFF = "2";
    String DATABASE_CONNECTED = "3";
    String SUCCESSFUL_REGISTER = "4";


}
