package javahyh.process;

import java.util.ArrayList;

public class Table {

    public ArrayList<ArrayList<String>> table;

    public Table(){
        this.table= new ArrayList<>();
    }

    public Table(ArrayList<ArrayList<String>> table){
        this.table=table;
    }

    public void print(){
        for(ArrayList line:table)
            for (Object word:line)
                System.out.print((String) word);
    }
}
