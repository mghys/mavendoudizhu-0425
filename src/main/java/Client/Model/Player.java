package Client.Model;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private int id;
    private PrintStream out;
    private BufferedReader in;
    private List<Poker> pokers = new ArrayList<>();
    private List<PokerLabel> pokerLabels = new ArrayList<>();
    private int leftnum;
    private int rightnum;

    public Player() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(),8888);
        this.out = new PrintStream(socket.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.getPokerLabels().clear();
    }
}
