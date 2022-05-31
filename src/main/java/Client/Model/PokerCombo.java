package Client.Model;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class PokerCombo {

    //是否是单张
    public static boolean isDanzhang(List<Poker>pokers){
        if (pokers.size()==1) return true;
        else return false;
    }

    //是否是对子
    public static boolean isDuizi(List<Poker>pokers){
        if (pokers.size()==2){
            if (pokers.get(0).getNum()==pokers.get(1).getNum()) return true;
            else return false;
        }
        else return false;
    }

    //是否王炸
    public static boolean isWangzha(List<Poker>pokers){
        if (pokers.size()==2){
            if ((pokers.get(0).getNum()==16)&&(pokers.get(1).getNum()==17)) return true;
            else return false;
        }
        else return false;
    }

    //是否三不带
    public static boolean isSanbudai(List<Poker>pokers){
        if (pokers.size()==3){
            if ((pokers.get(0).getNum()==pokers.get(1).getNum())&&(pokers.get(0).getNum()==pokers.get(2).getNum()))
                return true;
            else return false;
        }
        else return false;
    }

    //是否炸弹
    public static boolean isZhadan(List<Poker>pokers){
        if (pokers.size()==4){
            if ((pokers.get(0).getNum()==pokers.get(1).getNum())&&(pokers.get(0).getNum()==pokers.get(2).getNum())&&(pokers.get(0).getNum()==pokers.get(3).getNum()))
                return true;
            else return false;
        }
        return false;
    }

    //是否三带一
    public static boolean isSandaiyi(List<Poker>pokers){
        if (pokers.size()==4&&!isZhadan(pokers)){
            List<Poker>pokers1 = pokers.subList(0,3);
            List<Poker>pokers2 = pokers.subList(1,4);
            if (isSanbudai(pokers1)||isSanbudai(pokers2)) return true;
            else return false;
        }
        return false;
    }

    //是否三带二
    public static boolean isSandaier(List<Poker>pokers){
        if (pokers.size()==5){
            List<Poker>pokers11 = pokers.subList(0,3);
            List<Poker>pokers12 = pokers.subList(3,5);
            List<Poker>pokers21 = pokers.subList(0,2);
            List<Poker>pokers22 = pokers.subList(2,5);
            if ((isSanbudai(pokers11)&&isDuizi(pokers12))||(isDuizi(pokers21)&&isSanbudai(pokers22))) return true;
            else return false;
        }
        else return false;
    }

    //是否顺子
    public static boolean isShunzi(List<Poker>pokers){
        if (pokers.size()>=5&&(pokers.get(pokers.size()-1).getNum()<15)){
            for (int i = 0; i < pokers.size()-1; i++) {
                if (!(pokers.get(i).getNum()==pokers.get(i+1).getNum()-1)) return false;
            }
            return true;
        }
        else return false;
    }

    public static boolean isXiaoshunzi(List<Poker>pokers){
        if (pokers.size()>=3&&(pokers.get(pokers.size()-1).getNum()<15)){
            for (int i = 0; i < pokers.size()-1; i++) {
                if (!(pokers.get(i).getNum()==pokers.get(i+1).getNum()-1)) return false;
            }
            return true;
        }
        else return false;
    }

    //是否连对
    public static boolean isLiandui(List<Poker>pokers){
        if (pokers.size()>=6&&pokers.size()%2==0&&pokers.get(pokers.size()-1).getNum()<15){
            List<Poker>pokers1 = new ArrayList<>();
            List<Poker>pokers2 = new ArrayList<>();
            for (int i = 0; i < pokers.size(); i++) {
                if (i%2==0) pokers1.add(pokers.get(i));
                else pokers2.add(pokers.get(i));
            }
            if (isXiaoshunzi(pokers1)&&isXiaoshunzi(pokers2)){
                for (int i = 0; i < pokers1.size(); i++) {
                    if (!(pokers1.get(i).getNum()==pokers2.get(i).getNum())) return false;
                }
                return true;
            }
        }
        return false;
    }

    //是否四带二
    public static boolean isSidaier(List<Poker>pokers){
        if (pokers.size()==6){
            List<Poker>pokers1=pokers.subList(0,4);
            List<Poker>pokers2=pokers.subList(1,5);
            List<Poker>pokers3=pokers.subList(2,6);
            if (isZhadan(pokers1)||isZhadan(pokers2)||isZhadan(pokers3)) return true;
            else return false;
        }
        else return false;
    }

    //是否飞机不带
    public static boolean isFeijibudai(List<Poker>pokers){
        if (pokers.size()%3==0&&pokers.size()>=6){
            int num = pokers.size()/3;//飞机长度
            for (int i = 0; i < num; i++) {
                List<Poker>pokers1 = pokers.subList(i*3,i*3+3);
                if (!isSanbudai(pokers1)) return false;
            }
            for (int i = 0; i < num-3; i++) {
                if (!(pokers.get(i).getNum()==pokers.get(i+3).getNum()-1)) return false;
            }
            return true;
        }
        else return false;
    }

    //是否飞机带单
    public static boolean isFeijidaidan(List<Poker>pokers){
        if (pokers.size()%4==0&&pokers.size()>=8){
            //排除炸弹干扰
            if (haveZhadan(pokers)) reSort(pokers);
            int num = pokers.size()/4;//飞机长度
            for (int i = 0; i <= num; i++) {
                List<Poker>pokers1 = pokers.subList(i,i+3*num);
                if (isFeijibudai(pokers1)) return true;
            }
            return false;
        }
        else return false;
    }
    public static boolean isFeijidaidui(List<Poker>pokers){
        if (pokers.size()>=10&&pokers.size()%5==0){


            //备份pokers
            List<Poker>pokersEX = new ArrayList<>();
            for (int i = 0; i < pokers.size(); i++) {
                pokersEX.add(pokers.get(i));
            }


            int num = pokers.size()/5;//飞机长度
            for (int i = 0; i <=num; i++) {
                List<Poker>pokers1 = pokers.subList(i*2,i*2+num*3+1);
                if (isFeijibudai(pokers1)){
                    int idex = i*2;
                    for (int j = 0; j < num*3; j++) {
                        pokers.remove(idex);
                    }
                    for (int k = 0; k < pokers.size(); k+=2) {
                        List<Poker>pokers2 = pokers.subList(k,k+2);
                        if (!isDuizi(pokers2)){

                            //还原pokers
                            pokers.clear();
                            for (int j = 0; j < pokersEX.size(); j++) {
                                pokers.add(pokersEX.get(i));
                            }

                            return false;
                        }
                    }
                    //还原pokers
                    pokers.clear();
                    for (int j = 0; j < pokersEX.size(); j++) {
                        pokers.add(pokersEX.get(j));
                    }
                    return true;
                }
            }

            //还原pokers
            pokers.clear();
            for (int j = 0; j < pokersEX.size(); j++) {
                pokers.add(pokersEX.get(j));
            }
            return false;
        }
        else {
            System.out.println(JSON.toJSONString(pokers));
            return false;}
    }

    //是否四带两对
    public static boolean isSidailiangdui(List<Poker>pokers){
        if (pokers.size()==8){

            //备份pokers
            List<Poker>pokersEX = new ArrayList<>();
            for (int i = 0; i < pokers.size(); i++) {
                pokersEX.add(pokers.get(i));
            }

            for (int i = 0; i < 2; i++) {
                List<Poker>pokers1 = pokers.subList(i*2,i*2+4);
                if (isZhadan(pokers1)){
                    for (int j = 0; j < 4; j++) {
                        pokers.remove(i*2);
                    }
                    for (int j = 0; j < 2; j++) {
                        List<Poker>pokers2 = pokers.subList(j*2,j*2+2);
                        if (!isDuizi(pokers2)) {

                            //还原pokers
                            pokers.clear();
                            for (int k = 0; k < pokersEX.size(); k++) {
                                pokers.add(pokersEX.get(k));
                            }
                            return false;
                        }
                    }
                    //还原pokers
                    pokers.clear();
                    for (int k = 0; k < pokersEX.size(); k++) {
                        pokers.add(pokersEX.get(k));
                    }
                    return true;
                }
            }
            //还原pokers
            pokers.clear();
            for (int k = 0; k < pokersEX.size(); k++) {
                pokers.add(pokersEX.get(k));
            }
            return false;
        }

        else return false;
    }

    //判断牌型，非法—1
    public static int whatPC(List<Poker>pokers){
        if (isDanzhang(pokers)) return 1;
        if (isDuizi(pokers)) return 2;
        if (isSanbudai(pokers)) return 3;
        if (isSandaiyi(pokers)) return 31;
        if (isSandaier(pokers)) return 32;
        if (isFeijibudai(pokers)) return 33;
        if (isFeijidaidan(pokers)) return 3311;
        if (isZhadan(pokers)) return 9;
        if (isWangzha(pokers)) return 99;
        if (isShunzi(pokers)) return 11111;
        if (isLiandui(pokers)) return 222;
        if (isSidaier(pokers)) return 411;
        if (isFeijidaidui(pokers)) return 3322;
        if (isSidailiangdui(pokers)) return 422;
        return -1;
    }

    //给出牌型大小
    public static int pcNum(List<Poker>pokers){
        int num = whatPC(pokers);
        if (num == -1) return -1;
        switch (num){
            case 1:
            case 2:
            case 3:
            case 9:
            case 11111:
            case 222:
            case 33:
                return pokers.get(0).getNum();
            case 99:
                return 99;
            case 31:
            case 32:
            case 3311:
            case 3322:
                for (int i = 0; i <=pokers.size() - 3; i++) {
                    List<Poker> pokers1 = pokers.subList(i, i + 3);
                    if (isSanbudai(pokers1)) return pokers1.get(0).getNum();
                }
            case 411:
            case 422:
                for (int i = 0; i <= pokers.size()-4; i++) {
                    List<Poker> pokers1 = pokers.subList(i,i+4);
                    if (isZhadan(pokers1)) return pokers1.get(0).getNum();
                }
        }
        return -1;
    }

    //判断牌型中是否含有炸弹
    public static boolean haveZhadan(List<Poker>pokers){
        for (int i = 0; i < pokers.size()-4; i++) {
            List<Poker>pokers1 = pokers.subList(i,i+4);
            if (isZhadan(pokers1)) return true;
        }
        return false;
    }

    //含炸弹牌型重排序
    public static List<Poker> reSort(List<Poker>pokers){
        for (int i = 0; i < pokers.size()-3; i++) {
            List<Poker> pokers1 = pokers.subList(i,i+4);
            if (isZhadan(pokers1)&&i!=pokers.size()-4){
                Poker pokerChange = pokers.get(i);
                pokers.remove(i);
                pokers.add(pokerChange);
                i--;
            }
            else if (isZhadan(pokers1)&&i==pokers.size()-4){
                Poker pokerChange = pokers.get(i);
                pokers.remove(i);
                pokers.add(pokerChange);
            }
        }
        return pokers;
    }

}
