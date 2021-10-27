//package client;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientWindow extends JFrame {
    
    private static final String SERVER_HOST = "172.20.10.14";
    
    private static final int SERVER_PORT = 5500;
    
    private Socket clientSocket;
    
    private Scanner inMessage;
    
    private PrintWriter outMessage;
    
    private JTextField jtfMessage;
    private JLabel jlName;
    private JTextArea jtaTextAreaMessage;
    // client name
    private String clientName = "";

    public String getClientName() {
        return this.clientName;
    }

    public ClientWindow(String userID) {
        try {
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBounds(600, 300, 600, 500);
        setTitle("Пдсм");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jtaTextAreaMessage);
        add(jsp, BorderLayout.CENTER);
        JLabel jlNumberOfClients = new JLabel("");
        add(jlNumberOfClients, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSendMessage = new JButton("Отправить");
        bottomPanel.add(jbSendMessage, BorderLayout.EAST);
        jtfMessage = new JTextField("Введите ваше сообщение: ");
        bottomPanel.add(jtfMessage, BorderLayout.CENTER);
        
        jlName = new JLabel(userID + "  |");
        //jlName = userID;
        bottomPanel.add(jlName, BorderLayout.WEST);
        jbSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtfMessage.getText().trim().isEmpty() && !jlName.getText().trim().isEmpty()) {
                    clientName = jlName.getText();
                    sendMsg();
                    jtfMessage.grabFocus();
                }
            }
        });
        //when focused textbox cleared
        jtfMessage.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfMessage.setText("");
            }
        });
        // when focused namebox cleared
        jlName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jlName.setText("");
            }
        });
        // in new thread start connection with server
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (inMessage.hasNext()) {
                            String inMes = inMessage.nextLine();
                            String clientsInChat = "Клиентов в чате = ";
                            if (inMes.indexOf(clientsInChat) == 0) {
                                jlNumberOfClients.setText(inMes);
                            } else {
                                jtaTextAreaMessage.append(inMes);
                                jtaTextAreaMessage.append("\n");
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
        //close event handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    if (!clientName.isEmpty() && clientName != "Введите ваше имя: ") {
                        outMessage.println(userID + " вышел из чата!");
                    } else {
                        outMessage.println("Участник вышел из чата, так и не представившись!");
                    }
                    outMessage.println("##");
                    outMessage.flush();
                    outMessage.close();
                    inMessage.close();
                    clientSocket.close();
                } catch (IOException exc) {

                }
            }
        });
        setVisible(true);
    }

    public void sendMsg() {
        String messageStr = jlName.getText() + ": " + jtfMessage.getText();
        outMessage.println(messageStr);
        outMessage.flush();
        jtfMessage.setText("");
    }
}

