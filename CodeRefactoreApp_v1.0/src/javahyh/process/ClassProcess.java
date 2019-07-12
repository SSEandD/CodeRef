package javahyh.process;

import javahyh.methodtree.NodeTree;
import javahyh.methodtree.RootTree;

import java.util.ArrayList;
import java.util.List;

public class ClassProcess {

    private int level;

    private int space;

    private List<String> classGroup;

    public ClassProcess(List<String> classGroup, int level, int space) {
        this.classGroup = classGroup;
        this.level = level;
        this.space = space;
    }

    //只有方法是（xxx）throws xxx{xxx}这种形式
    public List<String> process() {
        //标志是否为内部类
        boolean flag = false;
        List<String> newClassGroup = new ArrayList<>();
        String word;
        int num1 = 0; //识别"("是否配对
        for (int i = 0; i < classGroup.size(); i++) {
            word = classGroup.get(i);
            newClassGroup.add(word);
            //判断是否是内部类
            if ("THE_FLAG_IS_TOP_CLASS".equals(word)) {
                newClassGroup.remove(newClassGroup.size() - 1);
                flag = true;
            }
            if ("class".equals(word)) {
                //不是内部类则跳过以下处理
                if (flag) {
                    flag = false;
                    continue;
                }
                List<String> inClassList = new ArrayList<>();
                word = classGroup.get(++i);
                while (!"{".equals(word)) {
                    newClassGroup.add(word);
                    word = classGroup.get(++i);
                }
                //计数
                int num2 = 1;
                String s;
                inClassList.add("{");
                do {
                    if (i + 1 == classGroup.size()) break;
                    s = classGroup.get(++i);
                    inClassList.add(s);
                    if ("{".equals(s)) num2++;
                    if ("}".equals(s)) {
                        num2--;
                        if (num2 == 0) break;
                    }
                } while (true);
                ClassProcess inC = new ClassProcess(inClassList, level + 1, space + 4);
                newClassGroup.addAll(inC.process());
                newClassGroup.add("\r");
                newClassGroup.add("\n");
            } else if ("(".equals(word)) {
                num1++;
            } else if (")".equals(word)) {
                num1--;
                if (num1 == 0) {
                    //跳过")"后面的特殊字符
                    if (i + 1 == classGroup.size()) break;
                    word = classGroup.get(++i);
                    while (word.equals(" ") || word.equals("\n")) {
                        newClassGroup.add(word);
                        if (i + 1 == classGroup.size()) break;
                        word = classGroup.get(++i);
                    }
                    //如果后有throws也属于方法
                    if (word.equals("throws")) {
                        while (!word.equals("{")) {
                            if (!word.equals("\n")) newClassGroup.add(word);
                            if (i + 1 == classGroup.size()) break;
                            word = classGroup.get(++i);
                        }
                    }
                    if ("{".equals(word)) {
                        //去掉方法体{}前的多余字符
                        while (" ".equals(newClassGroup.get(newClassGroup.size() - 1)) || "\n".equals(newClassGroup.get(newClassGroup.size() - 1))
                                || "\r".equals(newClassGroup.get(newClassGroup.size() - 1)))
                            newClassGroup.remove(newClassGroup.size() - 1);
                        newClassGroup.add(" ");
                        //开始识别方法体
                        int num2 = 1; //识别"{"是否配对
                        String s;
                        List<String> theFun = new ArrayList<>();
                        theFun.add("{");
                        do {
                            if (i + 1 == classGroup.size()) break;
                            s = classGroup.get(++i);
                            theFun.add(s);
                            if ("{".equals(s)) num2++;
                            if ("}".equals(s)) {
                                num2--;
                                if (num2 == 0) break;
                            }
                        } while (true);
                        if (i + 1 < classGroup.size()) {
                            s = classGroup.get(++i);
                            while ("\n".equals(s) || "\r".equals(s)) {
                                theFun.add(s);
                                if (i + 1 == classGroup.size()) break;
                                s = classGroup.get(++i);
                            }
                            i--;
                        }
                        NodeTree aTree = new RootTree(theFun, level, space);
                        aTree.reStructure();
                        theFun = aTree.getNewWord();
                        newClassGroup.addAll(theFun);
                    } else {
                        newClassGroup.add(word);
                    }
                }
            }
        }
        return newClassGroup;
    }

}
