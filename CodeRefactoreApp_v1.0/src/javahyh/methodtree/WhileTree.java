package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class WhileTree extends NodeTree{
    private List<String> whileLine;

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
        for(int i=0;i<whileLine.size();i++){
            if(i+1==whileLine.size()) break;
            String word1=whileLine.get(i);
            String word2=whileLine.get(i+1);
            if(" ".equals(word1) || ".".equals(word1)) continue;
            if(!" ".equals(word2) && !".".equals(word2)) whileLine.add(i+1," ");
        }
        newWord.addAll(whileLine);
        newWord.add(" ");
        super.reStructure();

    }
}
