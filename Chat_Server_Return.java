package ALL;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class Chat_Server_Return implements Runnable {
    int j;
    char getgrpinfo;
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE="";

    public Chat_Server_Return(Socket X) {
        this.SOCK=X;
    }

    public void CheckConnection() throws IOException {
        if (!SOCK.isConnected()) {
            for (int i = 0; i < Chat_Server.ConnectionArray.size(); i++) {
                if (Chat_Server.ConnectionArray.get(i) == SOCK) {
                    Chat_Server.ConnectionArray.remove(i);
                    getgrpinfo=Chat_Server.grpArray.get(i);
                    System.out.println("grpinfo=="+getgrpinfo);
                    Chat_Server.grpArray.remove(i);


                }
            }

            for (int i = 0; i <Chat_Server.ConnectionArray.size(); i++) {
                Socket TEMP_SOCK = (Socket) Chat_Server.ConnectionArray.get(i);
                char tmp_grp=Chat_Server.grpArray.get(i);
                if(tmp_grp==getgrpinfo) {
                    PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());

                    //TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + "Disconnected");
                    TEMP_OUT.println("Disconnected");
                    TEMP_OUT.flush();
                    System.out.println("Disconnected");
                }
            }
        }
    }

    @Override
    public void run() {
        try{
            try{
                INPUT=new Scanner(SOCK.getInputStream());
                OUT=new PrintWriter(SOCK.getOutputStream());
                while(true)
                {
                    CheckConnection();
                    if(!INPUT.hasNext()){
                        return;
                    }

                    MESSAGE=INPUT.nextLine();
                    System.out.println(MESSAGE);

                    System.out.println("client said:" + MESSAGE);
                    for (int i = 0; i < Chat_Server.ConnectionArray.size(); i++) {
                        if(SOCK==Chat_Server.ConnectionArray.get(i))
                        {
                            j=i;
                            break;
                        }
                    }
                    getgrpinfo=Chat_Server.grpArray.get(j);




                        for (int i = 0; i < Chat_Server.ConnectionArray.size(); i++) {
                        Socket TEMP_SOCK = (Socket) Chat_Server.ConnectionArray.get(i);
                        char tmp_grp=Chat_Server.grpArray.get(i);
                        if(tmp_grp==getgrpinfo) {
                            PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                            TEMP_OUT.println(MESSAGE);
                            TEMP_OUT.flush();
                            System.out.println("sent to:" + TEMP_SOCK.getLocalAddress().getHostName());
                        }
                    }
                }
            }
            finally {
                System.out.println("client said:" + MESSAGE);
                for (int i = 0; i < Chat_Server.ConnectionArray.size(); i++) {
                    if(SOCK==Chat_Server.ConnectionArray.get(i))
                    {
                        j=i;
                        break;
                    }
                }
                getgrpinfo=Chat_Server.grpArray.get(j);
                if(getgrpinfo=='A') {
                    Chat_Server.grpA--;
                    System.out.println(getgrpinfo +" " + Chat_Server.grpA);
                }

                if(getgrpinfo=='B') {
                    Chat_Server.grpB--;
                    System.out.println(getgrpinfo +" " + Chat_Server.grpB);
                }
                if(getgrpinfo=='C') {
                    Chat_Server.grpC--;
                    System.out.println(getgrpinfo +" " + Chat_Server.grpC);

                }
                System.out.println("grpinfo="+getgrpinfo);
                SOCK.close();
            }
        }
        catch (Exception X) {
            System.out.println(X);
        }

    }
}
