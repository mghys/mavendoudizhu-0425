package Client.Model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tools {
    public static List<Poker>pokers = new ArrayList<>();

    public static int turntoInt(String str){
        switch (str){
            case "0": return 0;
            case "1": return 1;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10": return 10;
            case "11": return 11;
            case "12": return 12;
            case "13": return 13;
            case "14": return 14;
            case "15": return 15;
            case "16": return 16;
            case "17": return 17;
            case "18": return 18;
            case "19": return 19;
            case "20": return 20;
            case "21": return 21;
            case "22": return 22;
            case "23": return 23;
            case "24": return 24;
            case "25": return 25;
            case "26": return 26;
            case "27": return 27;
            case "28": return 28;
            case "29": return 29;
            case "30": return 30;
            case "31": return 31;
            case "32": return 32;
            case "33": return 33;
            case "34": return 34;
            case "35": return 35;
            case "36": return 36;
            case "37": return 37;
            case "38": return 38;
            case "39": return 39;
            case "40": return 40;
            case "41": return 41;
            case "42": return 42;
            case "43": return 43;
            case "44": return 44;
            case "45": return 45;
            case "46": return 46;
            case "47": return 47;
            case "48": return 48;
            case "49": return 49;
            case "50": return 50;
            case "51": return 51;
            case "52": return 52;
            case "53": return 53;

        }
        return 0;
    }


    public static void preparePoker(){
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

    public static List<Poker> creatPC(int [] nums){
        List<Poker>pokerList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            pokerList.add(pokers.get(nums[i]));
        }
        Collections.sort(pokerList);
        return pokerList;
    }

    public static List<Poker> creatPC(List<Integer>nums){
        preparePoker();
        List<Poker>pokerList = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            pokerList.add(pokers.get(nums.get(i)));
        }
        Collections.sort(pokerList);
        return pokerList;
    }

    public static List<Poker> turntoPokers(String msg){
        List<Poker> pokers = new ArrayList<>();
        JSONArray pokersJsonArray = JSONArray.parseArray(msg);
        List<Poker> pokers1 = pokers;
        for (int i = 0; i < pokersJsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) pokersJsonArray.get(i);
            int pid = jsonObject.getInteger("id");
            int pnum = jsonObject.getInteger("num");
            Poker poker = new Poker(pid, pnum);
            pokers1.add(poker);
        }
        return pokers1;
    }

    public static int removeOut(int[] nums,List<Poker>pokers){
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < pokers.size(); j++) {
                if (pokers.get(j).getId()==nums[i]) pokers.remove(j);
            }
        }
        return pokers.size();
    }

    public static int removeOut(List<Integer> nums,List<Poker>pokers){
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < pokers.size(); j++) {
                if (pokers.get(j).getId()==nums.get(i)) pokers.remove(j);
            }
        }
        return pokers.size();
    }

    public static void move(PokerLabel pokerLabel,int x,int y){
        pokerLabel.setLocation(x,y);
    }

    public static List<Poker> daoxuSort(List<Poker>pokers){
        List<Poker>newpokers = new ArrayList<>();
        for (int i = pokers.size()-1; i >= 0 ; i--) {
            newpokers.add(pokers.get(i));
        }
        return newpokers;
    }

}
