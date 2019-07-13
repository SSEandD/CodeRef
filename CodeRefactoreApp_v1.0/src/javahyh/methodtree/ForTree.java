package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class ForTree extends NodeTree {

    private List<String> forLine = new ArrayList<>();

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
        newWord.addAll(forLine);
        newWord.add(" ");
        super.reStructure();

    }
}
