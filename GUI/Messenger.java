import java.rmi.server.LogStream;

public class Messenger {
    public static void main(String[] args){
        LoginSystem logsys = new LoginSystem();

        LoginPage loginPage = new LoginPage(logsys.getLoginInfo());
    }   
    
}
    