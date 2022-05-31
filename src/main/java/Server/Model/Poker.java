package Server.Model;


import lombok.Data;

@Data
public class Poker {
    private int id;
    private int num;

    public Poker(int pid, int pnum) {
        this.id = pid;
        this.num = pnum;
    }

    public Poker(){

    }
}
