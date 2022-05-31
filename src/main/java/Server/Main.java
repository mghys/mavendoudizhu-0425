package Server;

import Server.Model.Player;
import Server.Model.Poker;
import Server.Model.Tools;
import Server.Thread.LisenThread;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static List<Player>players = new ArrayList<>();//玩家列表
    public static List<Poker>pokers = new ArrayList<>();//全部扑克牌
    public static List<Poker>dzpokers = new ArrayList<>();//地主三张牌
    public static boolean dzisfound = false;//地主确定标志
    public static int dznum;
    public static int nownum;//记录当前该谁出牌
    public static int bignum;//记录当前回合谁出的牌
    public static List<Poker>nowpokers = new ArrayList<>();//记录当前的牌
    private static boolean isgameover = false;
    private static String testmsg;
    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(8888);

        //准备pokers
        preparePoker();


        while (true){
            Socket socket = serverSocket.accept();
            Player player = new Player(socket);
            players.add(player);
            //人数够时下一步
            if (players.size()==3){
                break;
            }
        }

        //分配id
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getOut().println(i);
        }

        //测试阶段不洗牌
        sendExPoker();

        //正常发牌
//        sendPoker();


        //选地主
        while (true){
            //记录不叫地主人数
            int num = 0;
            String msg = "叫地主不？";
            while (num<3){
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).getOut().println(msg);
                    if ((testmsg = players.get(i).getIn().readLine()).equals("叫"))
                    {
                        players.get(i).getOut().println("你是地主！");
                        System.out.println("地主确定");
                        dznum = i;
                        players.get(i).setLastnum(20);
                        //发放地主牌
                        players.get(i).getOut().println(JSON.toJSONString(dzpokers));
                        for (int j = 1; j < 3; j++) {
                            //公开地主牌
                            players.get((i+j)%3).getOut().println(i + "是地主，地主的牌是："+JSON.toJSONString(dzpokers));
                            System.out.println("地主的牌："+JSON.toJSONString(dzpokers));
                        }
                        dzisfound = true;
                        break;
                    }
                    System.out.println(testmsg);
                    if (dzisfound){break;}
                    num++;
                }
                if (dzisfound){break;}
            }
            //没人叫地主0号为地主
            if(!dzisfound){
                players.get(0).getOut().println("你是地主！");
                System.out.println("地主确定");
                //发放地主牌
                players.get(0).getOut().println(JSON.toJSONString(dzpokers));
                for (int j = 1; j < 3; j++) {
                    //公开地主牌
                    players.get(j%3).getOut().println(0 + "是地主，地主的牌是："+JSON.toJSONString(dzpokers));
                    System.out.println("地主的牌："+JSON.toJSONString(dzpokers));
                }
                dzisfound = true;
                dznum = 0;
                players.get(0).setLastnum(20);
            }

            //选完地主下一阶段
            break;
        }

        //游戏开始
        nownum = dznum;
        while (!isgameover){
            if (nowpokers.size()==0){
                players.get(nownum%3).getOut().println("请出牌");
                String msg = players.get(nownum%3).getIn().readLine();
                System.out.println(msg);
                //修改剩余牌数
                players.get(nownum%3).setLastnum(players.get(nownum%3).getLastnum()-Tools.turntoPokersNum(msg));
                nowpokers = Tools.turntoPokers(msg);
                for (int i = 1; i < 3; i++) {
                    players.get((nownum+i)%3).getOut().println(nownum%3+"-"+msg);
                }
                bignum = nownum;
                nownum++;
            }

            //又轮到自己
            else if (bignum%3==nownum%3){
                nowpokers.clear();
            }

            else {
                players.get(nownum%3).getOut().println("请压牌");
                String msg = players.get(nownum%3).getIn().readLine();
                System.out.println(msg);
                if (msg.equals("不出")) nownum++;
                else {
                    for (int i = 1; i < 3; i++) {
                        players.get((nownum+i)%3).getOut().println(nownum%3+"-"+msg);
                    }
                    //修改剩余牌数
                    players.get(nownum%3).setLastnum(players.get(nownum%3).getLastnum()-Tools.turntoPokersNum(msg));
                    nowpokers = Tools.turntoPokers(msg);
                    bignum=nownum;
                    nownum++;
                }
            }

        isGameover();
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).getOut().println("游戏结束");
        }
        while (true){
            if((nownum-1)%3==dznum){
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).getOut().println("地主赢了");
                }
                break;
            }
            else{
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).getOut().println("农民赢了");
                }
                break;
            }

        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getSocket().close();
        }
    }

    private static void preparePoker(){
        Poker dw = new Poker();
        dw.setId(0);
        dw.setNum(17);
        pokers.add(dw);
        Poker xw = new Poker();
        xw.setId(1);
        xw.setNum(16);
        pokers.add(xw);
        for (int i = 2; i < 54; i++) {
            Poker poker = new Poker();
            poker.setId(i);
            pokers.add(poker);
        }
        int num = 15;
        int idex = 2;
        while (num>2){
            for (int i = 0; i <4; i++) {
                pokers.get(idex).setNum(num);
                idex++;
            }
            num--;
        }
//        Collections.shuffle(pokers);//测试不洗牌
    }

    //发牌方法
    private static void sendPoker(){
        //循环发牌
        for (int i = 0; i < 51; i++) {
            players.get(i%3).getPokers().add(pokers.get(i));
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getOut().println(JSON.toJSONString(players.get(i).getPokers()));
        }
        for (int i = 51; i < 54; i++) {
            dzpokers.add(pokers.get(i));
        }
    }

    private static void sendExPoker(){
        //循环发牌
        for (int i = 0; i < 17; i++) {
            players.get(0).getPokers().add(pokers.get(i));
        }
        for (int i = 17; i < 34; i++) {
            players.get(1).getPokers().add(pokers.get(i));
        }
        for (int i = 34; i < 51; i++) {
            players.get(2).getPokers().add(pokers.get(i));
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getOut().println(JSON.toJSONString(players.get(i).getPokers()));
        }
        for (int i = 51; i < 54; i++) {
            dzpokers.add(pokers.get(i));
        }
    }

    private static void isGameover(){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getLastnum()==0) isgameover = true;
        }
    }

}
