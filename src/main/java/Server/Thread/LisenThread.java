package Server.Thread;

import Server.Main;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class LisenThread extends Thread{

    private Socket socket;
    private BufferedReader bf;
    private PrintStream ps;

    public LisenThread(Socket socket) throws IOException {
        this.socket = socket;
        this.bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.ps = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        String msg = null;
        while (true){
            try {
                msg = this.bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (msg != null){
                System.out.println(msg);
            }
            if(msg.equals("叫")){

            }
        }
    }
}
