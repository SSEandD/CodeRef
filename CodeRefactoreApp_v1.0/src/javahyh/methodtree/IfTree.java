package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class IfTree extends NodeTree {
    //标志if前是否有else
    boolean flag;

    private List<String> ifLine = new ArrayList<>();

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
        newWord.addAll(ifLine);
        newWord.add(" ");
        super.reStructure();
    }

}
