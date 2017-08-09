package ALL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Chat_Client_GUI {
    public static Integer availgrpA,availgrpB,availgrpC;
    private static Chat_Client ChatClient;
    public static String Username = "Unkhown";
    //GUI.......................
    public static JFrame MainWindow = new JFrame();
    private static JButton B_CONNECT = new JButton();
    private static JButton B_DISCONNECT = new JButton();
    private static JButton B_SEND = new JButton();
    private static JLabel L_Message = new JLabel("Message :");
    public static JTextField TF_Message = new JTextField(20);
    private static JLabel L_Conversation = new JLabel();
    public static JTextArea TA_CONVERSATION = new JTextArea();
    private static JScrollPane SP_CONVERSATION = new JScrollPane();
    private static JLabel L_LoggedInAs = new JLabel();
    private static JLabel L_LoggedInAsBox = new JLabel();
    private static JLabel grpas = new JLabel();
    private static JLabel grpbox = new JLabel();
    // GUI GLOBALS -LogIn Window
    public static JFrame LogInWindow = new JFrame();
    public static JTextField TF_UserNameBox = new JTextField(20);
    private static JButton B_Enter = new JButton("Enter");
    private static JLabel L_EnterUserName = new JLabel("Enter UserName:");
    private static JPanel P_logIn = new JPanel();
    public static JFrame grpWindow = new JFrame();
    private static JLabel grpBox = new JLabel();
    public static JTextField grpName = new JTextField(1);
    private static JButton Enter = new JButton("Enter");
    private static JLabel EnterUserName = new JLabel("Enter Group Name:");
    //....................................................
    public static void main(String[] args) {
        BuildMainWindow();
        Initialize();
    }
    //...................................................
    public static void connect()
    {
        try{
            final int PORT=9000;
            final String HOST="localhost";
            Socket SOCK=new Socket(HOST,PORT);
            System.out.println("You Connected To :" + HOST);
             ChatClient=new Chat_Client(SOCK);
            //............SEnd name to add to online list
            PrintWriter OUT=new PrintWriter(SOCK.getOutputStream());
            OUT.println(Username);
            OUT.flush();
            Scanner INPUT=new Scanner(SOCK.getInputStream());
            String MESSAGE=INPUT.nextLine();
            grpWindow.setTitle("Group info :");
            grpWindow.setSize(400,200);
            grpWindow.setLocation(250,200);
            grpWindow.setResizable(false);
            grpWindow.getContentPane().setLayout(null);
            grpWindow.getContentPane().add(grpBox);
            grpBox.setBounds(80,10,300,25);
            grpBox.setFont(new Font("Tahoma",0,16));
            grpBox.setText(MESSAGE);
            grpWindow.getContentPane().add(EnterUserName);
            EnterUserName.setBounds(80,30,150,25);
            grpWindow.getContentPane().add(grpName);
            grpName.setBounds(190,30,25,25);
            grpWindow.getContentPane().add(Enter);
            Enter.setBounds(160,60,100,25);
            grpWindow.setVisible(true);
            char[] tmp=MESSAGE.toCharArray();
            availgrpA=tmp[3]-'0';
            availgrpB=tmp[12]-'0';
            availgrpC=tmp[21]-'0';
            Enter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!grpName.getText().equals("") && ((grpName.getText().equals("A") && availgrpA==5) || (grpName.getText().equals("B") && availgrpB==5) || (grpName.getText().equals("C") && availgrpC==5) ))
                    {
                        JOptionPane.showMessageDialog(null,"Group is Full!!");
                        grpName.setText("");
                    }
                    else if(!grpName.getText().equals("") && (grpName.getText().equals("A") || grpName.getText().equals("B") || grpName.getText().equals("C")  )) {
                        String getgrp =grpName.getText();//.trim();

                        OUT.println(getgrp);
                        OUT.flush();
                        grpbox.setText(getgrp);


                        grpWindow.setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Enter a valid group name");
                        grpName.setText("");
                    }
                }
            });
            Thread X = new Thread(ChatClient);
            X.start();

        }catch (Exception X){
            System.out.print(X);
            JOptionPane.showMessageDialog(null,"Server not responding");
            System.exit(0);
        }
    }
    //....................................................
    public static void Initialize() {
        B_SEND.setEnabled(false);
        B_DISCONNECT.setEnabled(false);
        B_CONNECT.setEnabled(true);
    }

    //................................................
    public static void BuildLogWindow() {
        LogInWindow.setTitle("What Is Your Name :");
        LogInWindow.setSize(400,100);
        LogInWindow.setLocation(250,200);
        LogInWindow.setResizable(false);
        P_logIn=new JPanel();
        P_logIn.add(L_EnterUserName);
        P_logIn.add(TF_UserNameBox);
        P_logIn.add(B_Enter);
        LogInWindow.add(P_logIn);
        LoginAction();
        LogInWindow.setVisible(true);
    }
    //....................................................
    private static void BuildMainWindow() {

        MainWindow.setTitle(Username +"'s Chat Box");
        MainWindow.setSize(450,500);
        MainWindow.setLocation(220,180);
        MainWindow.setResizable(false);
        ConfigueMainWindow();
        MainWindow_Action();
        MainWindow.setVisible(true);
    }
    //................................................................
    private static void ConfigueMainWindow() {

        MainWindow.setBackground(new Color(255,255,255));
        MainWindow.setSize(500,320);
        MainWindow.getContentPane().setLayout(null);
        B_SEND.setBackground(new Color(117, 83,255));
        B_SEND.setForeground(new Color(253, 253, 250));
        B_SEND.setFont(new Font("Times New Roman",1,18));
        B_SEND.setText("send");
        MainWindow.getContentPane().add(B_SEND);
        B_SEND.setBounds(350,235,100,30);
        B_DISCONNECT.setBackground(new Color(255, 199, 250));
        B_DISCONNECT.setForeground(new Color(0,0,0));
        B_DISCONNECT.setFont(new Font("Times New Roman",1,18));
        B_DISCONNECT.setText("Disconncet");
        MainWindow.getContentPane().add(B_DISCONNECT);
        B_DISCONNECT.setBounds(170,15,130,30);
        B_CONNECT.setBackground(new Color(37, 130, 255));
        B_CONNECT.setForeground(new Color(248, 255,255));
        B_CONNECT.setFont(new Font("Times New Roman",1,18));
        B_CONNECT.setText("Connect");
        B_CONNECT.setToolTipText("");
        MainWindow.getContentPane().add(B_CONNECT);
        B_CONNECT.setBounds(10,15,130,30);
        L_Message.setFont(new Font("Tahoma",1,13));
        L_Message.setText("Message");
        MainWindow.getContentPane().add(L_Message);
        L_Message.setBounds(10,240,60,20);
        TF_Message.setForeground((new Color(0,0,255)));
        TF_Message.requestFocus();
        MainWindow.getContentPane().add(TF_Message);
        TF_Message.setBounds(70,235,260,30);
        L_Conversation.setHorizontalAlignment(SwingConstants.CENTER);
        L_Conversation.setFont(new Font("Tahoma",1,14));
        L_Conversation.setText("Conversation");
        MainWindow.getContentPane().add(L_Conversation);
        L_Conversation.setBounds(100,50,140,16);
        TA_CONVERSATION.setColumns(20);
        TA_CONVERSATION.setFont(new Font("Tahoma",0,12));
        TA_CONVERSATION.setForeground(new Color(0,0,255));
        TA_CONVERSATION.setLineWrap(true);
        TA_CONVERSATION.setRows(5);
        TA_CONVERSATION.setEditable(false);
        SP_CONVERSATION.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_CONVERSATION.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_CONVERSATION.setViewportView(TA_CONVERSATION);
        MainWindow.getContentPane().add(SP_CONVERSATION);
        SP_CONVERSATION.setBounds(10,75,480,150);
        L_LoggedInAs.setFont(new Font("Tahoma",0,12));
        L_LoggedInAs.setText("Currently logged In As:");
        MainWindow.getContentPane().add(L_LoggedInAs);
        L_LoggedInAs.setBounds(335,5,140,20);
        L_LoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_LoggedInAsBox.setFont(new Font("Tahoma",0,14));
        L_LoggedInAsBox.setForeground(new Color(28, 107, 255));
        L_LoggedInAsBox.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
        MainWindow.getContentPane().add(L_LoggedInAsBox);
        L_LoggedInAsBox.setBounds(335,25,150,20);
//group info box
        grpas.setFont(new Font("Tahoma",1,12));
        grpas.setText("Group:");
        MainWindow.getContentPane().add(grpas);
        grpas.setBounds(335,50,140,20);
        grpbox.setHorizontalAlignment(SwingConstants.CENTER);
        grpbox.setFont(new Font("Tahoma",1,14));
        grpbox.setForeground(new Color(125, 46, 255));
        grpbox.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
        MainWindow.getContentPane().add(grpbox);
        grpbox.setBounds(380,50,50,20);
    }
    //............................................
    public static  void LoginAction() {
        B_Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_ENTER();
            }
        });
    }
    //.................................................

    public static  void ACTION_B_ENTER() {
        if(!TF_UserNameBox.getText().equals("")) {
            Username=TF_UserNameBox.getText().trim();
            L_LoggedInAsBox.setText(Username);
            Chat_Server.CurrentUsers.add(Username);
            MainWindow.setTitle(Username +"'s CHAT BOX");
            LogInWindow.setVisible(true);
            B_SEND.setEnabled(true);
            B_DISCONNECT.setEnabled(true);
            B_CONNECT.setEnabled(false);
            connect();
            LogInWindow.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null,"Enter a name");
        }
    }
    //.................................................
    public static void MainWindow_Action() {
        B_SEND.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_SEND();
            }
        });

        B_DISCONNECT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_DISCONNECT();
            }
        });

        B_CONNECT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildLogWindow();
            }
        });
    }
    //..................................................
    public static void ACTION_B_SEND()
    {
        if(!TF_Message.getText().equals("")) {
            ChatClient.SEND(TF_Message.getText());
            TF_Message.requestFocus();
        }
    }
    //.............................................
    public static void ACTION_B_DISCONNECT()
    {
        try{
            ChatClient.DISCONNECT();

        }catch (Exception Y) {
            Y.printStackTrace();
        }
    }
}