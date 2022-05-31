package Client.Thread;

import Client.Model.*;
import Client.Windows.MainFrame;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LisenTread extends Thread {
    private Player player;
    private BufferedReader bf;
    public static int dznum;
    public static List<Poker> nowPokers = new ArrayList<>();
    public static List<Poker> dzPokers = new ArrayList<>();
    public static List<Integer> choosedPokers = new ArrayList<>();
    public static boolean gameover = false;
    public static String nowMsg;


    public LisenTread(Player player) {
        this.player = player;
        this.bf = player.getIn();
    }

    @Override
    public void run() {
        String msg = null;

        //分配id
        while (true){
            try {
                if (!((msg = bf.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.setId(Tools.turntoInt(msg));
            if (player.getId()==0){
                player.setLeftnum(2);
                player.setRightnum(1);
            }
            else if (player.getId()==1){
                player.setLeftnum(0);
                player.setRightnum(2);
            }
            else {
                player.setLeftnum(1);
                player.setRightnum(0);
            }

            System.out.println(player.getId());
            break;
        }
        msg = null;

        //解析牌
        while (true) {
            try {
                if ((msg = bf.readLine()) != null) {
                    System.out.println(msg);
                    JSONArray pokersJsonArray = JSONArray.parseArray(msg);
                    for (int i = 0; i < pokersJsonArray.size(); i++) {
                        JSONObject jsonObject = (JSONObject) pokersJsonArray.get(i);
                        int pid = jsonObject.getInteger("id");
                        int pnum = jsonObject.getInteger("num");
                        Poker poker = new Poker(pid, pnum);
                        player.getPokers().add(poker);
                        Collections.sort(player.getPokers());
                    }
                    //显示手牌
                    System.out.println(JSON.toJSONString(player.getPokers()));

                    //窗体显示手牌
                    showPokers(player.getPokers());
                    MainFrame.mainPanel.repaint();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg = null;



        //叫地主环节
        while (true){
            try {
                if((msg = bf.readLine())!=null&&msg.equals("叫地主不？")){
                    //显示是否叫地主信息
                    System.out.println(msg);

                    //窗体显示按钮
                    //叫地主按钮
                    JButton buttonJiaodizhu = new JButton("叫地主");
                    buttonJiaodizhu.setSize(80,40);
                    buttonJiaodizhu.setLocation(305,400);
                    buttonJiaodizhu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            player.getOut().println("叫");
                            MainFrame.mainPanel.removeAll();
                            MainFrame.mainPanel.repaint();

                        }
                    });

                    //不叫按钮
                    JButton buttonBujiao = new JButton("不叫");
                    buttonBujiao.setSize(80,40);
                    buttonBujiao.setLocation(395,400);
                    buttonBujiao.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            player.getOut().println("不叫");
                            MainFrame.mainPanel.removeAll();
                            showPokers(player.getPokers());
                            MainFrame.mainPanel.repaint();

                        }
                    });

                    MainFrame.mainPanel.add(buttonBujiao);
                    MainFrame.mainPanel.add(buttonJiaodizhu);
                    MainFrame.mainPanel.repaint();

                    msg = null;
                    while (true){
                        if ((msg = bf.readLine())!=null){
                            if (msg.equals("你是地主！")){
                                msg = bf.readLine();
                                System.out.println("你是地主！");
                                JSONArray pokersJsonArray = JSONArray.parseArray(msg);
                                for (int i = 0; i < pokersJsonArray.size(); i++) {
                                    JSONObject jsonObject = (JSONObject) pokersJsonArray.get(i);
                                    int pid = jsonObject.getInteger("id");
                                    int pnum = jsonObject.getInteger("num");
                                    Poker poker = new Poker(pid, pnum);
                                    player.getPokers().add(poker);
                                    dzPokers.add(poker);
                                    Collections.sort(player.getPokers());
                                }

                                System.out.println(JSON.toJSONString(player.getPokers()));

                                showPokers(player.getPokers());
                                showDZPokers(dzPokers);

                                break;
                            }
                            else{
                                System.out.println(msg+"前");
                                break;
                            }
                        }
                    }
                    break;
                }
                //别人是地主
                else {
                    System.out.println(msg+"后");
                    //顶部显示地主牌
                    String[] msgs = msg.split("：");
                    dzPokers = Tools.turntoPokers(msgs[1]);
                    showDZPokers(dzPokers);
                    MainFrame.mainPanel.repaint();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        msg = null;


        //游戏开始
        while (!gameover){
            try {
                if ((msg= bf. readLine())!=null){
                    if (msg.equals("请压牌")){

                        int preType = PokerCombo.whatPC(nowPokers);
                        int preNmu = PokerCombo.pcNum(nowPokers);

                        //加入按钮
                        JButton buttonYapai = new JButton("出牌");
                        buttonYapai.setSize(80,40);
                        buttonYapai.setLocation(305,400);
                        buttonYapai.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                //获取选中的牌
                                List<Poker>choosePokers = Tools.creatPC(choosedPokers);
                                int chooseType = PokerCombo.whatPC(choosePokers);
                                int chooseNum = PokerCombo.pcNum(choosePokers);

                                //不合规矩，牌型不同且不是炸弹
                                //重置选择
                                if (chooseType<0||((chooseType!=preType)&&chooseType!=9&&chooseType!=99)){

                                    choosedPokers.clear();
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showQTChupai(nowMsg);
                                    JButton buttonBuchu = new JButton("不出");
                                    buttonBuchu.setSize(80,40);
                                    buttonBuchu.setLocation(395,400);
                                    buttonBuchu.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            player.getOut().println("不出");
                                            MainFrame.mainPanel.remove(buttonBuchu);
                                            MainFrame.mainPanel.remove(buttonYapai);
                                            MainFrame.mainPanel.repaint();
                                        }
                                    });
                                    MainFrame.mainPanel.add(buttonYapai);
                                    MainFrame.mainPanel.add(buttonBuchu);
                                    MainFrame.mainPanel.repaint();

                                }
                                //当前不是炸，炸弹随便出
                                else if ((preType!=9&&preType!=99)&&(chooseType==9)){
                                    //出牌扑克列表更新
                                    Tools.removeOut(choosedPokers,player.getPokers());
                                    choosedPokers.clear();
                                    //显示出完后的手牌
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showZJChupai(choosePokers);
                                    MainFrame.mainPanel.repaint();
                                    nowPokers = choosePokers;

                                    String msgO = JSON.toJSONString(choosePokers);
                                    player.getOut().println(msgO);
                                }

                                //出的同类且数值大
                                else if (preType==chooseType&&chooseNum>preNmu&&nowPokers.size()==choosePokers.size()){
                                    //出牌扑克列表更新
                                    Tools.removeOut(choosedPokers,player.getPokers());
                                    choosedPokers.clear();
                                    //显示出完后的手牌
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showZJChupai(choosePokers);
                                    MainFrame.mainPanel.repaint();
                                    nowPokers = choosePokers;

                                    String msgO = JSON.toJSONString(choosePokers);
                                    player.getOut().println(msgO);
                                }
                                //王炸随便出
                                else if (chooseType==99){
                                    //出牌扑克列表更新
                                    Tools.removeOut(choosedPokers,player.getPokers());
                                    choosedPokers.clear();
                                    //显示出完后的手牌
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showZJChupai(choosePokers);
                                    MainFrame.mainPanel.repaint();
                                    nowPokers = choosePokers;

                                    String msgO = JSON.toJSONString(choosePokers);
                                    player.getOut().println(msgO);
                                }
                                //当前王炸啥也不能出
                                else if (preType==99){
                                    player.getOut().println("不出");
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showQTChupai(nowMsg);
                                    MainFrame.mainPanel.repaint();
                                    player.getOut().println("不出");
                                }
                                //其他都不能出
                                else {
                                    choosedPokers.clear();
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showQTChupai(nowMsg);
                                    JButton buttonBuchu = new JButton("不出");
                                    buttonBuchu.setSize(80,40);
                                    buttonBuchu.setLocation(395,400);
                                    buttonBuchu.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            player.getOut().println("不出");
                                            MainFrame.mainPanel.remove(buttonBuchu);
                                            MainFrame.mainPanel.remove(buttonYapai);
                                            MainFrame.mainPanel.repaint();
                                        }
                                    });
                                    MainFrame.mainPanel.add(buttonYapai);
                                    MainFrame.mainPanel.add(buttonBuchu);
                                    MainFrame.mainPanel.repaint();
                                }
                            }
                        });

                        JButton buttonBuchu = new JButton("不出");
                        buttonBuchu.setSize(80,40);
                        buttonBuchu.setLocation(395,400);
                        buttonBuchu.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                player.getOut().println("不出");
                                MainFrame.mainPanel.remove(buttonBuchu);
                                MainFrame.mainPanel.remove(buttonYapai);
                                MainFrame.mainPanel.repaint();
                            }
                        });

                        MainFrame.mainPanel.add(buttonYapai);
                        MainFrame.mainPanel.add(buttonBuchu);
                        MainFrame.mainPanel.repaint();


                        System.out.println(msg);
                    }
                    else if (msg.equals("请出牌")){
                        //清空当前牌
                        nowPokers.clear();

                        //加入按钮
                        JButton buttonChupai = new JButton("出牌");
                        buttonChupai.setSize(80,40);
                        buttonChupai.setLocation(305,400);
                        buttonChupai.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                List<Poker>choosePokers = Tools.creatPC(choosedPokers);
                                int chooseType = PokerCombo.whatPC(choosePokers);
                                //不合规矩
                                if (chooseType<0){
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    MainFrame.mainPanel.add(buttonChupai);
                                    MainFrame.mainPanel.repaint();
                                    System.out.println(chooseType);
                                    choosedPokers.clear();
                                }
                                else{
                                    //出牌扑克列表更新
                                    Tools.removeOut(choosedPokers,player.getPokers());
                                    choosedPokers.clear();
                                    //显示出完后的手牌
                                    MainFrame.mainPanel.removeAll();
                                    showPokers(player.getPokers());
                                    showDZPokers(dzPokers);
                                    showZJChupai(choosePokers);
                                    MainFrame.mainPanel.repaint();
                                    nowPokers = choosePokers;

                                    String msgO = JSON.toJSONString(choosePokers);
                                    player.getOut().println(msgO);
                                }
                            }
                        });
                        MainFrame.mainPanel.add(buttonChupai);
                        MainFrame.mainPanel.repaint();

                        System.out.println(msg);
                    }
                    else if (msg.equals("游戏结束")){
                        System.out.println("收到"+msg);
                        gameover = true;
                        break;
                    }

                    //显示别人出的牌
                    else{

                        MainFrame.mainPanel.removeAll();
                        showPokers(player.getPokers());
                        showDZPokers(dzPokers);

                        showQTChupai(msg);
                        MainFrame.mainPanel.repaint();

                        msg = msg.split("-")[1];
                        nowPokers = Tools.turntoPokers(msg);

                        System.out.println(msg);
                    }
                    msg = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        msg = null;
        //游戏结束
        while (true){
            try {
                if ((msg = bf.readLine())!=null){
                    JLabel jLabelforgo = new JLabel(msg);
                    jLabelforgo.setVisible(true);
                    jLabelforgo.setSize(400,200);
                    jLabelforgo.setLocation(500,200);
                    jLabelforgo.setFont(new Font("宋体",Font.BOLD, 16));
                    MainFrame.mainPanel.removeAll();
                    MainFrame.mainPanel.add(jLabelforgo);
                    MainFrame.mainPanel.repaint();
                    System.out.println(msg);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        player.getOut().close();
        try {
            player.getIn().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showPokers(List<Poker> pokers){

        for (int i = 0; i < pokers.size(); i++) {
            PokerLabel pokerLabel = new PokerLabel(pokers.get(i).getId());
            player.getPokerLabels().add(pokerLabel);
            pokerLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (pokerLabel.ischoose==false){
                        pokerLabel.ischoose=true;
                        choosedPokers.add(pokerLabel.id);
                        pokerLabel.setLocation(pokerLabel.getX(),pokerLabel.getY()-10);
                    }
                    else {
                        pokerLabel.ischoose=false;
                        pokerLabel.setLocation(pokerLabel.getX(),pokerLabel.getY()+10);
                        for (int j = 0; j < choosedPokers.size(); j++) {
                            if (pokerLabel.id==choosedPokers.get(j)){
                                choosedPokers.remove(j);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            player.getPokerLabels().add(pokerLabel);
            MainFrame.mainPanel.add(pokerLabel);
            MainFrame.mainPanel.setComponentZOrder(pokerLabel,0);
            Tools.move(pokerLabel,300+30*i,450);
        }
    }

    public void showDZPokers(List<Poker> pokers){
//        for (int i = 0; i < pokers.size(); i++) {
//            PokerLabel pokerLabel = new PokerLabel(pokers.get(i).getId());
//            Tools.move(pokerLabel,520+30*i,0);
//            MainFrame.mainPanel.add(pokerLabel);
//        }
//        pokers = Tools.daoxuSort(pokers);
        for (int i = pokers.size()-1; i >= 0; i--) {
            PokerLabel pokerLabel = new PokerLabel(pokers.get(i).getId());
            Tools.move(pokerLabel,520+30*pokers.size()-30*(pokers.size()-i),0);
            MainFrame.mainPanel.add(pokerLabel);
        }
    }

    public void showZJChupai(List<Poker> pokers){
//        for (int i = 0; i < pokers.size(); i++) {
//            PokerLabel pokerLabel = new PokerLabel(pokers.get(i).getId());
//            MainFrame.mainPanel.add(pokerLabel);
//            Tools.move(pokerLabel,300+30*i,280);
//        }
        for (int i = pokers.size()-1; i >= 0 ; i--) {
            PokerLabel pokerLabel = new PokerLabel(pokers.get(i).getId());
            Tools.move(pokerLabel,300+30*pokers.size()-30*(pokers.size()-i),280);
            MainFrame.mainPanel.add(pokerLabel);
        }

    }

    public void showQTChupai(String msg){
        nowMsg = msg;
        String[] msgs = msg.split("-");
        int iid = Tools.turntoInt(msgs[0]);
        List<Poker> ipokers = Tools.turntoPokers(msgs[1]);
        if (iid == player.getLeftnum()){
            for (int i = ipokers.size()-1; i >= 0; i--) {
                PokerLabel pokerLabel = new PokerLabel(ipokers.get(i).getId());
                Tools.move(pokerLabel,0+30*i,200);
                MainFrame.mainPanel.add(pokerLabel);
            }
//            for (int i = 0; i < ipokers.size(); i++) {
//                PokerLabel pokerLabel = new PokerLabel(ipokers.get(i).getId());
//                Tools.move(pokerLabel,0+30*i,200);
//                MainFrame.mainPanel.add(pokerLabel);
//            }
        }
        else {
//            for (int i = 0; i < ipokers.size(); i++) {
//                PokerLabel pokerLabel = new PokerLabel(ipokers.get(i).getId());
//                Tools.move(pokerLabel,1050-30*(ipokers.size()-i),200);
//                MainFrame.mainPanel.add(pokerLabel);
//            }
            ipokers = Tools.daoxuSort(ipokers);
            for (int i = ipokers.size()-1; i >= 0; i--) {
                PokerLabel pokerLabel = new PokerLabel(ipokers.get(ipokers.size()-i-1).getId());
                Tools.move(pokerLabel,1050-30*(ipokers.size()-i-1),200);
                MainFrame.mainPanel.add(pokerLabel);
            }
        }
    }

}