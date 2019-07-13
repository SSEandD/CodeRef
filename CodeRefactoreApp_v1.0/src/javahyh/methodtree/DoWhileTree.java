package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class DoWhileTree extends NodeTree {

    private List<String> doWhileLine;

    public DoWhileTree(List<String> conLine, List<String> allWord, int level, int space){
        this.doWhileLine =conLine;
        this.allWord=allWord;
        this.levelTree=level+1;
        this.spaceNum=space+4;
        super.ceateTree();
    }

    public void printOld() {
        System.out.print("do");
        super.printOld();
        for(String s:doWhileLine){
            System.out.print(s);
        }
        System.out.println();
    }

    public void reStructure(){
        for(int i=0;i<spaceNum;i++){
            newWord.add(" ");
        }
        newWord.add("do");
        super.reStructure();
        newWord.remove(newWord.size()-1);
        newWord.remove(newWord.size()-1);
        for(int i=0;i<doWhileLine.size();i++){
            if(i+1==doWhileLine.size()) break;
            String word1=doWhileLine.get(i);
            String word2=doWhileLine.get(i+1);
            if(" ".equals(word1) || ".".equals(word1)) continue;
            if(!" ".equals(word2) && !".".equals(word2)) doWhileLine.add(i+1," ");
        }
        newWord.addAll(doWhileLine);
        newWord.add("\r");
        newWord.add("\n");
    }
}
