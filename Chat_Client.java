package ALL;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class Chat_Client implements Runnable {
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND=new Scanner(System.in);
    PrintWriter OUT;

    public Chat_Client(Socket X)
    {
        this.SOCK=X;
    }
    @Override
    public void run() {
        try{
            try{
                INPUT=new Scanner(SOCK.getInputStream());
                OUT=new PrintWriter(SOCK.getOutputStream());
                OUT.flush() ;
                CheckStream();

            }finally {
                SOCK.close();
            }
        }catch(Exception X) {
            System.out.println(X);
        }
    }
    public void DISCONNECT() throws IOException {
        OUT.println(Chat_Client_GUI.Username + " disconnected");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null,"You disconnected");
        System.exit(0);
    }

    private void CheckStream() {
        while(true) {
            RECEIVE();
        }
    }

    private void RECEIVE() {
        if(INPUT.hasNext()) {
            String MESSAGE=INPUT.nextLine();
            if(MESSAGE.contains("#?!")) {
                String TEMP1=MESSAGE.substring(3);
                TEMP1=TEMP1.replace("[","");
                TEMP1=TEMP1.replace("]","");
                String[] CurrentUsers=TEMP1.split(", ");

            }
            else {
                Chat_Client_GUI.TA_CONVERSATION.append(MESSAGE + "\n");
            }
        }
    }

    //......................................................
    public  void  SEND(String X) {
        OUT.println(Chat_Client_GUI.Username+": "+X);
        OUT.flush();
        Chat_Client_GUI.TF_Message.setText("");
    }
}
