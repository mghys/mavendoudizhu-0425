package Client;

import Client.Model.Player;
import Client.Model.Tools;
import Client.Thread.LisenTread;
import Client.Thread.SendThread;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Player player = new Player();
        //Tools.preparePoker();
        new LisenTread(player).start();
        new SendThread(player).start();
    }
}
