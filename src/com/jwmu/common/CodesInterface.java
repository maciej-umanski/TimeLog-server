package com.jwmu.common;

public interface CodesInterface {
    //error codes
    String WRONG_CREDENTIALS = "-1";
    String CLIENT_NOT_LOGGED = "-2";
    String CLIENT_IS_LOGGED = "-3";
    String WRONG_COMMAND = "-4";
    String CLIENT_DISCONNECTED = "-5";

    //good codes
    String CLIENT_CONNECTED = "0";
    String SUCCESSFUL_LOGIN = "1";
    String SUCCESSFUL_LOGOFF = "2";

}
