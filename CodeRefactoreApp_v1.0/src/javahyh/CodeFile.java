package javahyh;

import javahyh.lexical.LexicalAnalysis;
import javahyh.process.ClassProcess;
import javahyh.process.ImportProcess;
import javahyh.process.PackageProcess;

import java.util.ArrayList;
import java.util.List;

//整个代码文件
public class CodeFile {

    private List<String> packageGroup;
    private List<String> importGroup;
    private List<String> classGroup;

    public CodeFile(List<String> list) {
        packageGroup = new ArrayList<>();
        importGroup = new ArrayList<>();
        classGroup = new ArrayList<>();
        String word;
        //循环读取经过词法分析后的源文件
        for (int i = 0; i < list.size(); i++) {
            word = list.get(i);
            //分3部分处理
            if ("package".equals(word)) {
                do {
                    packageGroup.add(word);
                    if (i + 1 == list.size()) break;
                    word = list.get(++i);
                } while (!word.equals(";"));
                packageGroup.add(word);
            } else if ("import".equals(word)) {
                do {
                    importGroup.add(word);
                    if (i + 1 == list.size()) break;
                    word = list.get(++i);
                } while (!word.equals(";"));
                importGroup.add(word);
            } else if (word.matches("/\\*(.|\\s)*\\*/")) {
                if(i==0) {
                    packageGroup.add(word);
                }else {
                    classGroup.add(word);
                    classGroup.add("\r");
                    classGroup.add("\n");
                }
            } else if(word.matches("//(.|\\s)*")) {
                classGroup.add(word);
            } else if ("public".equals(word) || "abstract".equals(word) || "final".equals(word) ||
                    "class".equals(word) || "interface".equals(word)) {
                classGroup.add("THE_FLAG_IS_TOP_CLASS");
                int theFlag = 0;
                do {
                    if ("{".equals(word)) theFlag++;
                    if ("}".equals(word)) {
                        theFlag--;
                        if (theFlag == 0) {
                            classGroup.add(word);
                            break;
                        }
                    }
                    classGroup.add(word);
                    if (i + 1 == list.size()) break;
                    word = list.get(++i);
                } while (true);
                classGroup.add("\r");
                classGroup.add("\n");
                classGroup.add("\r");
                classGroup.add("\n");
            }
        }
    }

    public void reShape() {
        PackageProcess p = new PackageProcess(packageGroup);
        packageGroup = p.process();
        ImportProcess i = new ImportProcess(importGroup);
        importGroup = i.process();
        ClassProcess c = new ClassProcess(classGroup, 1, 4);
        classGroup = c.process();
    }

    public void print() {
        for (String s : packageGroup) System.out.print(s);
        for (String s : importGroup) System.out.print(s);
        for (String s : classGroup) System.out.print(s);
    }

    public List<String> getGroup() {
        List<String> groupAll = new ArrayList<>();
        groupAll.addAll(packageGroup);
        groupAll.addAll(importGroup);
        groupAll.addAll(classGroup);
        return groupAll;
    }

}