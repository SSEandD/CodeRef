package javahyh.methodtree;

import java.util.List;

public class TryOrFinTree extends NodeTree {

    private String conLine;

    public TryOrFinTree(List<String> allWord, int level, int space, int flag){
        this.allWord=allWord;
        this.levelTree=level+1;
        this.spaceNum=space+4;
        if(flag==1) conLine="try";
        else conLine="finally";
        super.ceateTree();
    }

    public void printOld() {
        System.out.print(conLine);
        super.printOld();
        System.out.println();
    }

    public void reStructure(){
        if(conLine.equals("try")){
            for(int i=0;i<spaceNum;i++){
                newWord.add(" ");
            }
        }
        else {
            newWord.add(" ");
        }
        newWord.add(conLine);
        newWord.add(" ");
        super.reStructure();
        //如果为try则{}后不换行
        if(conLine.equals("try")){
            newWord.remove(newWord.size()-1);
            newWord.remove(newWord.size()-1);
        }
    }

}
