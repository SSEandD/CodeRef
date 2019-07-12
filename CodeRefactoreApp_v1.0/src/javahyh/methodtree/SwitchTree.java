package javahyh.methodtree;

import java.util.ArrayList;
import java.util.List;

public class SwitchTree extends NodeTree {

    private List<String> conLine;

    public SwitchTree(List<String> conLine, List<String> allWord, int level, int space) {
        this.conLine = conLine;
        this.allWord = allWord;
        this.levelTree = level + 1;
        this.spaceNum = space + 4;
        createCaseTree();
    }

    public void reStructure() {
        for(int i=0;i<spaceNum;i++){
            newWord.add(" ");
        }
        newWord.addAll(conLine);
        newWord.add(" ");
        super.reStructure();
    }

    public void createCaseTree(){
        String word;
        for (int i=0;i<allWord.size();i++) {
            word=allWord.get(i);
            if("case".equals(word) || "default".equals(word)){
                List<String> conLine=new ArrayList<>();
                do{
                    conLine.add(word);
                    if(i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                } while (!":".equals(word));
                conLine.add(word);
                //生成case内的语句块
                List<String> all=new ArrayList<>();
                if(i+1 == allWord.size()) break;
                word=allWord.get(++i);
                while (!"case".equals(word) && !"default".equals(word)){
                    all.add(word);
                    if(i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                }
                i--;
                //构建子树
                NodeTree aTree;
                aTree=new CaseTree(conLine,all,levelTree,spaceNum);
                sonTree.add(aTree);
            }
        }
    }

}
