package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class CaseTree extends NodeTree{

    private List<String> conLine=new ArrayList<>();

    public CaseTree(List<String> conLine, List<String> allWord, int level, int space) {
        this.conLine = conLine;
        this.allWord = allWord;
        this.levelTree = level + 1;
        this.spaceNum = space + 4;
        super.ceateTree();
    }

    public void printOld() {
        for(String s:conLine){
            System.out.print(s);
        }
        for(NodeTree t:sonTree){
            t.printOld();
        }
    }

    public void reStructure(){
        for(int i=0;i<spaceNum;i++){
            newWord.add(" ");
        }
        newWord.addAll(conLine);
        newWord.add("\r");
        newWord.add("\n");
        for(NodeTree node:sonTree){
            node.reStructure();
            newWord.addAll(node.getNewWord());
        }
    }

}
