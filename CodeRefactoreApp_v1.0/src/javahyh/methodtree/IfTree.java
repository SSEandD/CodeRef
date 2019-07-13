package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class IfTree extends NodeTree {
    //标志if前是否有else
    boolean flag;

    private List<String> ifLine;

    public IfTree(List<String> conLine, List<String> allWord, int level, int space, boolean flag) {
        this.ifLine = conLine;
        this.allWord = allWord;
        this.levelTree = level + 1;
        this.spaceNum = space + 4;
        this.flag = flag;
        super.ceateTree();
    }

    public void printOld() {
        for (String s : ifLine) {
            System.out.print(s);
        }
        super.printOld();
        System.out.println();
    }

    public void reStructure() {
        if (!flag) {
            for (int i = 0; i < spaceNum; i++) {
                newWord.add(" ");
            }
        }
        for(int i=0;i<ifLine.size();i++){
            if(i+1==ifLine.size()) break;
            String word1=ifLine.get(i);
            String word2=ifLine.get(i+1);
            if(" ".equals(word1) || ".".equals(word1)) continue;
            if(!" ".equals(word2) && !".".equals(word2)) ifLine.add(i+1," ");
        }
        newWord.addAll(ifLine);
        newWord.add(" ");
        super.reStructure();
    }

}
