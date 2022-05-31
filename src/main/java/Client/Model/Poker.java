package Client.Model;

import lombok.Data;

@Data
public class Poker implements Comparable{
    private int id;
    private int num;

    public Poker(){

    }

    public Poker(int id,int num){
        this.id = id;
        this.num = num;
    }
    @Override
    public int compareTo(Object O) {
        Poker poker = (Poker)O;
        int i = this.getNum() - poker.getNum();
        return i;
    }

}
