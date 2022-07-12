package edu.neu.madscourse.a8stickittoem;

public class Sticker implements Comparable<Sticker>{
    private int num;
    private String name;
    private int id;


    public Sticker(int num, String name, int id) {
        this.num = num;
        this.name = name;
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public int getNum(){
        return this.num;
    }


    @Override
    public int compareTo(Sticker other) {
        return String.valueOf(this.num).compareTo(String.valueOf(other.getNum()));
    }
}
