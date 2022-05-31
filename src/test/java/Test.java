import Client.Model.Poker;
import Client.Model.PokerCombo;
import Client.Model.Tools;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static Client.Model.PokerCombo.*;

public class Test {
    public static List<Poker>pokers = new ArrayList<>();

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
    }

    //数组构造牌型
    public static List<Poker> creatPC(int [] nums){
        List<Poker>pokerList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            pokerList.add(pokers.get(nums[i]));
        }
        Collections.sort(pokerList);
        return pokerList;
    }

    @org.junit.Test
    public void comTest(){
        preparePoker();
        System.out.println(JSON.toJSONString(pokers));

        //测试王炸
//        List<Poker>wangzha = pokers.subList(0,2);
//        Collections.sort(wangzha);
//        System.out.println(JSON.toJSONString(wangzha));
//        System.out.println(isWangzha(wangzha));

        //测试四带二
//        List<Poker>sidaier = pokers.subList(0,6);
//        Collections.sort(sidaier);
//        System.out.println(isSidaier(sidaier));

        //测试飞机,三不带
        List<Poker>feiji;
        List<Poker>feiji2;
//        int[]feijinm = new int[]{14,15,16,18,19,20,3,2,6,7};
        int[]feijinm = new int[]{52,49,44,43,42,41,40,39,34,35,36,37};
//        int[]feijinm2 = new int[]{14,15,16,6,7,8,1,2};
        feiji = creatPC(feijinm);
        System.out.println(JSON.toJSONString(feiji));
//        feiji2= creatPC(feijinm2);
        Collections.sort(feiji);
        feiji = PokerCombo.reSort(feiji);
        System.out.println(JSON.toJSONString(feiji));
//        Collections.sort(feiji2);
//        System.out.println(JSON.toJSONString(feiji));
//        System.out.println(isFeijidaidan(feiji));
//        System.out.println(isFeijidaidan(feiji2));
        System.out.println(isFeijidaidan(feiji));
        System.out.println(whatPC(feiji));
        System.out.println(pcNum(feiji));

        //测试四代两对
//        int[] sdldnm = new int[]{10,11,12,13,2,3,6,7};
//        List<Poker> sdld = creatPC(sdldnm);
//        System.out.println(JSON.toJSONString(sdld));
//        System.out.println(isSidailiangdui(sdld));


//        int[]nums = new int[]{50,46,42,38,34};
//        List<Poker> pokers1 = creatPC(nums);
//        System.out.println(JSON.toJSONString(pokers1));
//        System.out.println(whatPC(pokers1));
//        System.out.println(pcNum(pokers1));
    }

    @org.junit.Test
    public void listTest(){
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        System.out.println(nums.size());
        nums.clear();
        System.out.println(nums.size());
        System.out.println(nums==null);
    }

    public static void main(String[] args) {
             preparePoker();
            Scanner sc = new Scanner(System.in);
            while (true){
                String msg = sc.nextLine();
                String [] pokerids = msg.split(",");
                int [] pokeridi = new int[pokerids.length];
                for (int i = 0; i < pokerids.length; i++) {
                    pokeridi[i] = Tools.turntoInt(pokerids[i]);
                }
                List<Poker>pokers = creatPC(pokeridi);
                System.out.println("牌型类型为："+whatPC(pokers)+" 牌型数值为："+pcNum(pokers));
            }
        }


}
