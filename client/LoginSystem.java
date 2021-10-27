//package client;

import java.util.HashMap;

public class LoginSystem {
    
    HashMap<String, String> loginsys = new HashMap<String, String>();

    LoginSystem(){
        loginsys.put("Augustine","aboba");
		loginsys.put("123","PASSWORD");
		loginsys.put("kak","sahar");
    }

    public HashMap getLoginInfo(){
        return loginsys;
    }
}
