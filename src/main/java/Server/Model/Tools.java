package Server.Model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tools {
    //转为扑克列表
    public static List<Poker> turntoPokers(String msg) {
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

    public static int turntoPokersNum(String msg){
        JSONArray pokersJsonArray = JSONArray.parseArray(msg);
        return pokersJsonArray.size();
    }
}