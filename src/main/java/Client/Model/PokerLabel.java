package Client.Model;

import Client.Thread.LisenTread;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PokerLabel extends JLabel {
    public String imgAdress;
    public int id;
    public boolean ischoose = false;

    public PokerLabel(int id){
        this.id = id;
        this.imgAdress = "D:\\FINDW\\mavendoudizhu - 0425\\src\\main\\resources\\pokersimg\\"+id+".jpg";
        this.setSize(105,160);
        this.setIcon(new ImageIcon(this.imgAdress));
        this.setVisible(true);

    }


}
