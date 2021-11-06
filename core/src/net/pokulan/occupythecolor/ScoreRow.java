package net.pokulan.occupythecolor;

public class ScoreRow {
    String name;
    String score;
    boolean is;

    ScoreRow(){
        name = "----------";
        score = "0";
        is = false;
    }

    ScoreRow(String n, String s){
        name = n;
        score = s;
        is = true;
    }

    public void setName(String s){name = s;}
    public void setIs(boolean b){is = b;}
    public void setScore(int s){score = "" + s;}
    public void setScore(String s){score = s;}

    public String getName(){return name;}
    public boolean getIs(){return is;}
    public String getScore(){return score;}
}
