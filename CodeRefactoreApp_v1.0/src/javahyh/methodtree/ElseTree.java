package javahyh.methodtree;

import java.util.List;

public class ElseTree extends NodeTree{

    private String conLine="else";

    public ElseTree(List<String> allWord, int level, int space){
        this.allWord=allWord;
        this.levelTree=level+1;
        this.spaceNum=space+4;
        super.ceateTree();
    }

    public void printOld() {
        System.out.print(conLine);
        if(allWord.size()==0){
            System.out.print(" ");
        }
        else{
            super.printOld();
            System.out.println();
        }
    }

    public void reStructure(){

        for(int i=0;i<spaceNum;i++){
            newWord.add(" ");
        }
        newWord.add(conLine);
        newWord.add(" ");
        if(allWord.size()!= 0){
            super.reStructure();
        }

    }

}