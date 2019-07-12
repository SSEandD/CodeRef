import javahyh.CodeFile;
import javahyh.lexical.LexicalAnalysis;
import javapgb.All;
import javasss.BlankCharacter;
import javasss.OrderBlock;

import java.util.ArrayList;

public class MainRun {
    //语句互转标志数组
    private ArrayList<Boolean> theJudge = new ArrayList<>();
    //外部传入的按行读取的文件内容
    private ArrayList<String> source = new ArrayList<>();
    //处理后的按字符串读取的文件内容
    private ArrayList<String> result = new ArrayList<>();
    //构造函数
    public MainRun(ArrayList<String> source, ArrayList<Boolean> theJudge) {
        this.source=source;
        this.theJudge=theJudge;
    }
    //主程序入口
    public String run() {
        StringBuilder result = new StringBuilder();
        //SYM
        OrderBlock ob = new OrderBlock(source);
        ArrayList<String>obLines = ob.analyse();
        BlankCharacter bc = new BlankCharacter(obLines);
        ArrayList<String>bcLines = bc.analyse();
        //PGB
        All all=new All();
        ArrayList allTable=all.all(bcLines,theJudge.get(0),theJudge.get(1),theJudge.get(2),
                theJudge.get(3),theJudge.get(4),theJudge.get(5));
        //HYH
        LexicalAnalysis start=new LexicalAnalysis("",null,null,allTable);
        CodeFile aFile=new CodeFile(start.ceate());
        aFile.reShape();
        for(String s:aFile.getGroup()) {
            result.append(s);
        }
        return result.toString();
    }
}
