package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class ForTree extends NodeTree {

    private List<String> forLine;

    public ForTree(List<String> conLine, List<String> allWord, int level, int space) {
        this.forLine = conLine;
        this.allWord = allWord;
        this.levelTree = level + 1;
        this.spaceNum = space + 4;
        super.ceateTree();
    }

    public void printOld() {
        for (String s : forLine) {
            System.out.print(s);
        }
        super.printOld();
        System.out.println();
    }

    public void reStructure() {

        for (int i = 0; i < spaceNum; i++) {
            newWord.add(" ");
        }
        for(int i=0;i<forLine.size();i++){
            if(i+1==forLine.size()) break;
            String word1=forLine.get(i);
            String word2=forLine.get(i+1);
            if(" ".equals(word1) || ".".equals(word1)) continue;
            if(!" ".equals(word2) && !".".equals(word2)) forLine.add(i+1," ");
        }
        newWord.addAll(forLine);
        newWord.add(" ");
        super.reStructure();

    }
}
