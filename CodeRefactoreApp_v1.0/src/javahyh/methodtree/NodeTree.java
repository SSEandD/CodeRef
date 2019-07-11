package javahyh.methodtree;

import javahyh.lexical.LexicalAnalysis;

import java.util.ArrayList;
import java.util.List;

public abstract class NodeTree {

    List<String> newWord=new ArrayList<>(); //当前节点修改后（不止{ }内）的字符串

    List<String> allWord = new ArrayList<>(); //当前节点{ }内的所有字符串

    List<NodeTree> sonTree = new ArrayList<>(); //当前树的子树

    int levelTree = 0; //当前树所在层数

    int spaceNum = 0; //当前缩进空格数

    //实现重构的方法
    public void reStructure(){
        newWord.add("{");
        newWord.add("\r");
        newWord.add("\n");
        for(NodeTree node:sonTree){
            node.reStructure();
            newWord.addAll(node.getNewWord());
        }
        for(int i=0;i<spaceNum;i++){
            newWord.add(" ");
        }
        newWord.add("}");
        newWord.add("\r");
        newWord.add("\n");
    }

    public void printNew(){
        for(String s:newWord){
            System.out.print(s);
        }
    }

    public void printOld(){
        System.out.print("{");
        for(NodeTree t:sonTree){
            t.printOld();
        }
        System.out.print("}");
    }

    public List<String> getNewWord(){
        return newWord;
    }
    //构造子树的方法
    public void ceateTree() {
        //递归出口
        if (sonTree == null) return;
        //递归构造
        boolean theflag=false; //出此下策，有时间再改，标志是否为else-if
        String word; //标志当前读取的字符串
        for (int i = 0; i < allWord.size(); i++) {
            word = allWord.get(i);
            //分析此代码块，构造子树
            if (word.matches("//(.|\\s)*")) {
                List<String> all=new ArrayList<>();
                all.add(word);
                //构建子树-普通语句节点-2
                NodeTree aFinal;
                aFinal = new NormalTree(all, levelTree, spaceNum, 2);
                sonTree.add(aFinal);
            } else if(word.matches("/\\*(.|\\s)*\\*/")){
                List<String> all=new ArrayList<>();
                all.add(word);
                //构建子树-普通语句节点-3
                NodeTree aFinal;
                aFinal = new NormalTree(all, levelTree, spaceNum, 3);
                sonTree.add(aFinal);
            } else if(word.equals("@")){
                List<String> all=new ArrayList<>();
                do{
                    all.add(word);
                    if(i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                } while (!word.equals("\n") && !word.equals("\r"));
                //构建子树-普通语句节点-2
                NodeTree aFinal;
                aFinal = new NormalTree(all,levelTree,spaceNum, 2);
                sonTree.add(aFinal);
            }else if (word.equals("if") || word.equals("for") || word.equals("while")) {
                int flag = 0;
                switch (word) {
                    case "if":
                        flag = 1;
                        break;
                    case "for":
                        flag = 2;
                        break;
                    default:
                        flag = 3;
                        break;
                }
                //构造条件判断语句
                int num1 = 0;
                List<String> conLine = new ArrayList<>();
                do {
                    conLine.add(word);
                    if(i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                    if (word.equals("(")) num1++;
                    if (word.equals(")")) {
                        num1--;
                        if (num1 == 0) {
                            conLine.add(word);
                            break;
                        }
                    }
                } while (true);
                //过滤语句块前的特殊字符
                if (i+1 == allWord.size()) break;
                word = allWord.get(++i);
                while (word.equals(" ") || word.equals("\n")) {
                    if (i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                }
                //构造子树
                int num2 = levelTree;
                List<String> all = new ArrayList<>();
                if (word.equals("{")) {
                    num2++;
                    do {
                        all.add(word);
                        if (i+1 == allWord.size()) break;
                        word = allWord.get(++i);
                        if (word.equals("{")) num2++;
                        if (word.equals("}")) {
                            num2--;
                            if (num2 == levelTree){
                                all.add(word);
                                break;
                            }
                        }
                    } while (true);
                } else {
                    int num3 = 0;
                    all.add("{");
                    while (!word.equals(";") || num2 != levelTree || num3 != 0) {
                        all.add(word);
                        if(i+1 == allWord.size()) break;
                        word = allWord.get(++i);
                        if (word.equals("(")) {
                            num3++;
                        }
                        if (word.equals(")")) {
                            num3--;
                        }
                        if (word.equals("{")) {
                            num2++;
                        }
                        if (word.equals("}")) {
                            num2--;
                            if (num2 == levelTree) break;
                        }
                    }
                    all.add(word);
                    all.add("}");
                }
                //生成if/for/while节点
                NodeTree aTree;
                if (flag == 1){
                    aTree = new IfTree(conLine, all, levelTree, spaceNum, theflag);
                    theflag=false;
                }
                else if (flag == 2){
                    aTree = new ForTree(conLine, all, levelTree, spaceNum);
                }
                else {
                    aTree = new WhileTree(conLine, all, levelTree, spaceNum);
                }
                sonTree.add(aTree);
            } else if (word.equals("else")) {
                int num1 = levelTree;
                List<String> all = new ArrayList<>();
                //略过特殊字符
                if (i+1 == allWord.size()) break;
                word = allWord.get(++i);
                while (word.equals(" ") || word.equals("\n") || word.equals("\r")) {
                    if (i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                }
                //构造子树
                if (word.equals("{")) {
                    num1++;
                    do {
                        all.add(word);
                        if (i+1 == allWord.size()) break;
                        word = allWord.get(++i);
                        if (word.equals("{")) {
                            num1++;
                        }
                        if (word.equals("}")) {
                            num1--;
                            if (num1 == levelTree) {
                                all.add(word);
                                break;
                            }
                        }
                    } while (true);
                } else if (word.equals("if")) {
                    NodeTree aTree;
                    aTree = new ElseTree(all, levelTree, spaceNum);
                    sonTree.add(aTree);
                    theflag=true;
                    i--;
                    continue;
                } else {
                    int num2 = 0;
                    all.add("{");
                    while (!word.equals(";") || num1 != levelTree || num2 != 0) {
                        all.add(word);
                        if(i+1 == allWord.size()) break;
                        word = allWord.get(++i);
                        if (word.equals("(")) {
                            num2++;
                        }
                        if (word.equals(")")) {
                            num2--;
                        }
                        if (word.equals("{")) {
                            num1++;
                        }
                        if (word.equals("}")) {
                            num1--;
                            if (num1 == levelTree) break;
                        }
                    }
                    all.add(word);
                    all.add("}");
                }
                //生成else节点
                NodeTree aTree;
                aTree = new ElseTree(all, levelTree, spaceNum);
                sonTree.add(aTree);
            } else if (word.equals("do")) {
                //略去特殊字符
                if (i+1 == allWord.size()) break;
                word = allWord.get(++i);
                while (word.equals(" ") || word.equals("\n")) {
                    if (i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                }
                //构造子树
                int num1 = levelTree;
                List<String> all = new ArrayList<>();
                if (word.equals("{")) {
                    num1++;
                    do {
                        all.add(word);
                        if (i+1 == allWord.size()) break;
                        word = allWord.get(++i);
                        if (word.equals("{")) {
                            num1++;
                        }
                        if (word.equals("}")) {
                            num1--;
                            if (num1 == levelTree) break;
                        }
                    } while (true);
                    all.add(word);
                } else {
                    int num2 = 0;
                    all.add("{");
                    while (!word.equals(";") || num1 != levelTree || num2 != 0) {
                        all.add(word);
                        if (i+1 == allWord.size()) break;
                        word = allWord.get(++i);
                        if (word.equals("(")) {
                            num2++;
                        }
                        if (word.equals(")")) {
                            num2--;
                        }
                        if (word.equals("{")) {
                            num1++;
                        }
                        if (word.equals("}")) {
                            num1--;
                            if (num1 == levelTree) break;
                        }
                    }
                    all.add(word);
                    all.add("}");
                }
                //略去特殊字符
                if (i+1 == allWord.size()) break;
                word = allWord.get(++i);
                while (!word.equals("while")) {
                    if (i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                }
                //构造控制表达式
                List<String> conLine=new ArrayList<>();
                do{
                    conLine.add(word);
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                }while (!word.equals(";"));
                conLine.add(word);
                //生成do-while节点
                NodeTree aTree;
                aTree=new DoWhileTree(conLine,all,levelTree,spaceNum);
                sonTree.add(aTree);
            } else if(word.equals("switch")){
                //生成表达式
                int num1=0;
                List<String> conLine=new ArrayList<>();
                do{
                    conLine.add(word);
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                    if(word.equals("(")) num1++;
                    if(word.equals(")")){
                        num1--;
                        if (num1==0){
                            conLine.add(word);
                            break;
                        }
                    }
                }while (true);
                //略过特殊字符
                if (i+1 == allWord.size()) break;
                word = allWord.get(++i);
                while (word.equals(" ") || word.equals("\n") || word.equals("\r")) {
                    if (i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                }
                //生成语句块
                int num2=levelTree;
                List<String> all=new ArrayList<>();
                num2++;
                do{
                    all.add(word);
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                    if(word.equals("{")) num2++;
                    if(word.equals("}")){
                        num2--;
                        if(num2==levelTree){
                            all.add(word);
                            break;
                        }
                    }
                }while(true);
                //生成switch子节点
                NodeTree aTree;
                aTree=new SwitchTree(conLine,all,levelTree,spaceNum);
                sonTree.add(aTree);
            } else if(word.equals("try") || word.equals("finally")){
                int flag = 0;
                if(word.equals("try")) flag=1;
                else flag=2;
                //略过特殊字符
                do{
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                }while (word.equals(" ") || word.equals("\n") || word.equals("\r"));
                //生成子树
                int num1=levelTree;
                List<String> all=new ArrayList<>();
                num1++;
                do{
                    all.add(word);
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                    if(word.equals("{")) num1++;
                    if(word.equals("}")){
                        num1--;
                        if(num1==levelTree) {
                            all.add(word);
                            break;
                        }
                    }
                }while (true);
                //生成try/finally子节点
                NodeTree aTree;
                aTree=new TryOrFinTree(all,levelTree,spaceNum,flag);
                sonTree.add(aTree);
            }else if(word.equals("catch")){
                //构造条件判断语句
                int num1 = 0;
                List<String> conLine = new ArrayList<>();
                do {
                    conLine.add(word);
                    if (i+1 == allWord.size()) break;
                    word = allWord.get(++i);
                    if (word.equals("(")) {
                        num1++;
                    }
                    if (word.equals(")")) {
                        num1--;
                        if (num1 == 0) {
                            conLine.add(word);
                            break;
                        }
                    }
                } while (true);
                //过滤多余字符
                do{
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                }while (word.equals(" ") || word.equals("\n") || word.equals("\r"));
                //构造子树
                int num2=levelTree;
                List<String> all=new ArrayList<>();
                num2++;
                do{
                    all.add(word);
                    if (i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                    if(word.equals("{")) num2++;
                    if(word.equals("}")){
                        num2--;
                        if(num2==levelTree) {
                            all.add(word);
                            break;
                        }
                    }
                }while (true);
                //过滤多余字符
                do{
                    if(i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                }while (word.equals(" ") || word.equals("\n") || word.equals("\r"));
                //判断catch后面是否还有catch或者finally
                boolean flag=false;
                if(word.equals("catch") || word.equals("finally")) flag=true;
                i--;
                //生成catch节点
                NodeTree aTree;
                aTree=new CatchTree(conLine,all,levelTree,spaceNum,flag);
                sonTree.add(aTree);
            }else if(!word.equals(" ") && !word.equals("\n") && !word.equals("\r") && !word.equals("{") && !word.equals("}")){
                List<String> all=new ArrayList<>();
                while (!word.equals(";")){
                    all.add(word);
                    if(i+1 == allWord.size()) break;
                    word=allWord.get(++i);
                }
                all.add(word);
                //构建子树-普通语句节点
                NodeTree aFinal;
                aFinal=new NormalTree(all,levelTree,spaceNum,1);
                sonTree.add(aFinal);
            }
        }
    }

}