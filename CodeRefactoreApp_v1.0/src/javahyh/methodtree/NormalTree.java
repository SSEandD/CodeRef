package javahyh.methodtree;

import java.util.List;

public class NormalTree extends NodeTree {
    //标志符，此语句是注释或者普通语句，flag={1,2,3}
    private int flag;

    //终结节点，没有子树
    public NormalTree(List<String> allWord, int level, int space, int flag){
        this.allWord=allWord;
        this.levelTree=level+1;
        this.spaceNum=space+4;
        this.sonTree =null;
        this.flag=flag;
    }

    public void printOld() {
        for (String s:allWord){
            System.out.print(s);
        }
        if(flag!=1) System.out.println();
    }
    //重构基本语句格式
    public void reStructure(){
        if(!allWord.get(0).equals(";")){
            for(int i=0;i<spaceNum;i++){
                newWord.add(" ");
            }
            newWord.addAll(allWord);
            newWord.add("\r");
            newWord.add("\n");
        }
    }

}