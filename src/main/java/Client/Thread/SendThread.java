package Client.Thread;

import Client.Model.Player;
import Client.Model.Poker;
import Client.Model.PokerCombo;
import Client.Model.Tools;
import com.alibaba.fastjson.JSON;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class SendThread extends Thread{
    private Player player;
    private PrintStream ps;
    private Scanner scanner = new Scanner(System.in);

    public SendThread(Player player){
        this.player = player;
        this.ps = player.getOut();

    }
    @Override
    public void run() {


        String msg=null;
        while (true){
            if ((msg = scanner.nextLine())!=null){
                if (msg.equals("叫")||msg.equals("不叫")||msg.equals("不出")){
                    ps.println(msg);
                    msg = null;
                }
                else{
                    //当前需要压牌
                    if (LisenTread.nowPokers.size()>0){
                        int nowPNum = PokerCombo.pcNum(LisenTread.nowPokers);
                        int nowType = PokerCombo.whatPC(LisenTread.nowPokers);

                        String[] pokerOSId = msg.split(",");
                        int[] pokerOId = new int[pokerOSId.length];
                        for (int i = 0; i <pokerOSId.length; i++) {
                            pokerOId[i] = Tools.turntoInt(pokerOSId[i]);
                        }
                        List<Poker>pokerO = Tools.creatPC(pokerOId);

                        int preType = PokerCombo.whatPC(pokerO);
                        int prePNum = PokerCombo.pcNum(pokerO);

                        if (preType<0||LisenTread.nowPokers.size()!=pokerO.size()) System.out.println("牛马牌型不准出");
                        else if (preType==9||prePNum==99){
                            if (preType!=9&&preType!=99){
                                //出完牌列表更新
                                Tools.removeOut(pokerOId,player.getPokers());
                                System.out.println(JSON.toJSONString(player.getPokers()));

                                String msgO = JSON.toJSONString(pokerO);
                                ps.println(msgO);
                            }
                            else if ((preType==9||preType==99)&&prePNum>nowPNum){
                                //出完牌列表更新
                                Tools.removeOut(pokerOId,player.getPokers());
                                System.out.println(JSON.toJSONString(player.getPokers()));

                                String msgO = JSON.toJSONString(pokerO);
                                ps.println(msgO);
                            }

                            else System.out.println("炸弹太小不能出");
                        }
                        else if (preType!=nowType) System.out.println("不一样牌型不能出");
                        else if (prePNum<nowPNum) System.out.println("太小不能出");
                        else {
                            //出完牌列表更新
                            Tools.removeOut(pokerOId,player.getPokers());
                            System.out.println(JSON.toJSONString(player.getPokers()));
                            String msgO = JSON.toJSONString(pokerO);
                            ps.println(msgO);
                        }

                    }
                    //当前需要出牌
                    else{
                        String[] pokerOSId = msg.split(",");
                        int[] pokerOId = new int[pokerOSId.length];
                        for (int i = 0; i <pokerOSId.length; i++) {
                            pokerOId[i] = Tools.turntoInt(pokerOSId[i]);
                        }
                        List<Poker>pokerO = Tools.creatPC(pokerOId);

                        int preType = PokerCombo.whatPC(pokerO);

                        if (preType<0) System.out.println("牛马牌型不准出");
                        else {
                            //出牌扑克列表更新
                            Tools.removeOut(pokerOId,player.getPokers());
                            System.out.println(JSON.toJSONString(player.getPokers()));

                            String msgO = JSON.toJSONString(pokerO);
                            ps.println(msgO);

                        }
                    }

                }

            }
        }
    }
}
