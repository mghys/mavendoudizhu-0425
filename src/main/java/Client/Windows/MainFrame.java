package Client.Windows;

import Client.Model.Player;
import Client.Thread.LisenTread;
import Client.Thread.SendThread;

import javax.swing.*;

public class MainFrame extends JFrame {
    public Player player;
    public static JPanel mainPanel = new JPanel();
    public static JPanel dzmainPanel = new JPanel();



    public MainFrame(Player player){

        this.player = player;
        this.setSize(1200,700);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setBounds(0,0,1200,700);
        mainPanel.setVisible(true);
        this.add(mainPanel);

        new LisenTread(this.player).start();
        new SendThread(this.player).start();
    }
}
