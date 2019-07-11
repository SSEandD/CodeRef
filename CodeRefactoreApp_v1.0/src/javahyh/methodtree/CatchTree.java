package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class CatchTree extends NodeTree{

    boolean flag;

    private List<String> conLine=new ArrayList<>();

    public CatchTree(List<String> conLine, List<String> allWord, int level, int space, boolean flag){
        this.conLine=conLine;
        this.allWord=allWord;
        this.levelTree=level+1;
        this.spaceNum=space+4;
        this.flag=flag;
        super.ceateTree();
    }

    public void printOld() {
        for(String s:conLine){
            System.out.print(s);
        }
        super.printOld();
        System.out.println();
    }

    public void reStructure(){
        newWord.add(" ");
        newWord.addAll(conLine);
        newWord.add(" ");
        super.reStructure();
        if(flag){
            newWord.remove(newWord.size()-1);
            newWord.remove(newWord.size()-1);
        }
    }

}
