package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
		//init the port
    static final int PORT = 3443;
		// client list
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {

        Socket clientSocket = null;
			
        ServerSocket serverSocket = null;
        try {
						
            serverSocket = new ServerSocket(PORT);
            System.out.println("server started");
				
            while (true) {
								// waiting for connection
                clientSocket = serverSocket.accept();
								//create handler
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
								// each new connection create new thread
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
								// close connection
                clientSocket.close();
                System.out.println("server stopped");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
		
		// messege to all clients
    public void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }

    }

		// delete client from collection
    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
