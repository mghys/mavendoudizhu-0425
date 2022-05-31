package Server.Model;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private int id;
    private PrintStream out;
    private BufferedReader in;
    private List<Poker> pokers = new ArrayList<>();
    private Socket socket;
    private int lastnum = 17;

    public Player(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintStream(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

}
