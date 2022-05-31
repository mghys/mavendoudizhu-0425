package Client.Windows;


import Client.Model.Player;

import java.io.IOException;

public class MainWindow {
    public static void main(String[] args) throws IOException {
        Player player = new Player();
        new MainFrame(player);
    }

}
