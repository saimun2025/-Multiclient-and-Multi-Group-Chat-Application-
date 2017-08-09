package ALL;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Chat_Server {
    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();
    public static ArrayList<Character> grpArray = new ArrayList<Character>();
    public static Integer grpA,grpB,grpC;



    public static void main(String[] args) throws IOException {
        grpA=0;grpB=0;grpC=0;
        try {
            final int PORT = 9000;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("waiting for clients ::");
            while (true) {
                Socket SOCK = SERVER.accept();
                ConnectionArray.add(SOCK);
                System.out.println("Client connected from :" + SOCK.getLocalAddress().getHostName());
                AddUsername(SOCK);

                PrintWriter TEMP_OUT = new PrintWriter(SOCK.getOutputStream());
                TEMP_OUT.println("A: " +grpA + "/5   "+"B: "+grpB+"/5   "+"C: "+grpC+"/5");
                TEMP_OUT.flush();

                Scanner INPUT=new Scanner(SOCK.getInputStream());
                String MESSAGE=INPUT.nextLine();
                char[] m=MESSAGE.toCharArray();
                //System.out.println(m[0]);
                grpArray.add(m[0]);

                if(m[0]=='A')
                    grpA++;
                if(m[0]=='B')
                    grpB++;
                if(m[0]=='C')
                    grpC++;


                Chat_Server_Return CHAT = new Chat_Server_Return(SOCK);
                Thread X = new Thread(CHAT);
                X.start();
            }
        } catch (Exception X) {
            System.out.println(X);
        }

    }

    public static void AddUsername(Socket X) throws IOException {
        Scanner INPUT = new Scanner(X.getInputStream());
        String Username = INPUT.nextLine();
        CurrentUsers.add(Username);



    }
}

