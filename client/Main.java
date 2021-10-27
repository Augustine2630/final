//package client;

public class Main {
    public static void main(String[] args) {
      //  ClientWindow clientWindow = new ClientWindow();

        LoginSystem logsys = new LoginSystem();

        LoginPage loginPage = new LoginPage(logsys.getLoginInfo());

    }
}
