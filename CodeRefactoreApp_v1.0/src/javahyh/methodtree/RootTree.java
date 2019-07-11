package javahyh.methodtree;

import java.util.List;

public class RootTree extends NodeTree{

    public RootTree(List<String> allWord,int level,int space){
        this.allWord=allWord;
        this.levelTree=level;
        this.spaceNum=space;
        super.ceateTree();
    }

    public void printOld() {
        super.printOld();
    }

    public void reStructure(){
        super.reStructure();
        newWord.add("\r");
        newWord.add("\n");
    }

}