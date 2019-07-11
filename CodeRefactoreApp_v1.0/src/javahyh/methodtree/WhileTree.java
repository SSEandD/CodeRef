package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class WhileTree extends NodeTree{
    private List<String> whileLine =new ArrayList<>();

    public WhileTree(List<String> conLine, List<String> allWord, int level, int space){
        this.whileLine =conLine;
        this.allWord=allWord;
        this.levelTree=level+1;
        this.spaceNum=space+4;
        super.ceateTree();
    }

    public void printOld() {
        for(String s:whileLine){
            System.out.print(s);
        }
        super.printOld();
        System.out.println();
    }

    public void reStructure(){

        for(int i=0;i<spaceNum;i++){
            newWord.add(" ");
        }
        newWord.addAll(whileLine);
        newWord.add(" ");
        super.reStructure();

    }
}
