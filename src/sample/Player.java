package sample;

public class Player {
    private String playername;
    private Integer playertime;

    Player(String name, Integer time){
        this.playername = name;
        this.playertime = time;
    }

    public String getName(){
        return this.playername;
    }

    public Integer getTime(){
        return this.playertime;
    }

    public void setName(String name){
        this.playername = name;
    }

    public void setTime(Integer time){
        this.playertime = time;
    }

}
