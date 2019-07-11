package javasss;
import java.util.ArrayList;
import javasss.FileProcessing;

public class BlankCharacter {
    private int i = 0;
    private int count = 0;
    private int j=0;
    private int flag = 0;
    private char ch; // 字符变量，存放最新读进的源程序字符
    private char fch; // 字符变量
    private String strToken; // 字符数组，存放构成单词符号的字符串
    private String blankStr="";
    private String thisline="";
    private ArrayList<String> line = new ArrayList<String>();
    private ArrayList<String> record = new ArrayList<String>();
    public BlankCharacter() {
    }
    /**
    * 读取指定路径文件
    * @param fileSrc 读取文件路径
    */
    public BlankCharacter(String fileSrc, String outputSrc) {
        line = FileProcessing.readFile(fileSrc);
    }
    public BlankCharacter(ArrayList<String> array) {
        line = array;
    }
    /**
    * 将下一个输入字符读到ch中，搜索指示器前移一个字符
    */
    public void getChar() {
        ch = line.get(j).charAt(i);
        i++;
    }
    public void concat() {
        strToken=FileProcessing.concat(strToken,ch);
    }
    /** 检查ch中的字符是否为空白，若是则调用getChar()直至ch中进入一个非空白字符*/
    public void deleteBC() {
        //isSpaceChar(char ch) 确定指定字符是否为 Unicode 空白字符。
        //上述方法不能识别换行符
        while (Character.isWhitespace(ch)&&i<line.get(j).length()) {
            if(ch==32) {
                while(ch==32&&i<line.get(j).length()) {
                    getChar();
                }
                if(ch!=' ') {
                    record.add(" ");
                }
            }
            else {
                getChar();
            }
        }
    }
    /**在引号内的空白字符检查，若ch为空白，则变成转义字符，*/
    public void transBC() {
        if(ch==7) {
            strToken+="\\a";
        }
        else if(ch==8) {
            strToken+="\\b";
        }
        else if(ch==12) {
            strToken+="\\f";
        }
        else if(ch==10) {
            strToken+="\\n";
        }
        else if(ch==13) {
            strToken+="\\r";
        }
        else if(ch==9) {
            strToken+="\\t";
        }
        else if(ch==11) {
            strToken+="\\v";
        }
    }
    /**对引号内的特殊空格进行转义*/
    public void inQuotation() {
        if(ch=='"') {//双引号内
            concat();
            getChar();
            while(ch!='"') {
            	if(ch=='\\') {
            		concat();
            		getChar();
            		concat();
                    getChar();
                    continue;
            	}
                concat();
                transBC();
                getChar();
            }
            record.add(strToken);
            strToken="";
        }
        if(ch=='\'') {//单引号内
            concat();
            getChar();
            while(ch!='\'') {
                if(ch=='\\') {
                    concat();
                    getChar();
                    concat();
                    getChar();
                    continue;
                }
                concat();
                transBC();
                getChar();
            }
            record.add(strToken);
            strToken="";
        }
    }
    /**注释内容*/
    public boolean isAnnotation() {
        boolean isOut=false;
        int a = getFirstCh();
        if(fch=='/') {
            fch = line.get(j).charAt(a++);
            if(fch=='/') {
                record.add(blankStr);
                while(i<line.get(j).length()) {
                    getChar();
                    deleteBC();
                    concat();
                    record.add(strToken);
                    strToken="";
                }
                record.add("\n");
                return true;
            }
            else if(fch=='*') {
                while(true) {
                    record.add(blankStr);
                    while(i<line.get(j).length()) {
                        getChar();
                        if(ch=='*'&&i<line.get(j).length()) {
                            concat();
                            getChar();
                            if(ch=='/') {
                                isOut=true;
                            }
                        }
                        concat();
                        record.add(strToken);
                        strToken="";
                    }
                    record.add("\n");
                    if(isOut) {
                        break;
                    }
                    else {
                        j++;
                        i=0;
                    }
                }
                return true;
            }
        }
        return false;
    }
    /**记录缩进量改变的等级*/
    public void retract (){
        if(ch=='{'){
            count++;
            changeBlankStr();
        }
        if(ch=='}'){
            count--;
            changeBlankStr();
        }
    }
    /**
    * 改变缩进量*/
    public void changeBlankStr() {
        if(count==0) {
            blankStr="";
        }
        else {
            blankStr = "";
            for(int i=0; i<count;i++) {
                blankStr +="    ";
            }
        }
    }
    /**写入缩进量*/
    public void addRetact() {
        getFirstCh();
        if(fch=='}') {
            blankStr = "";
            for(int i=1; i<count;i++) {
                blankStr +="    ";
            }
            record.add(blankStr);
        }
        else{
            record.add(blankStr);
        }
    }
    /**检测第一个字符并返回*/
    public int getFirstCh() {
        int a=0;
        do {
            fch=line.get(j).charAt(a++);
        }while(Character.isWhitespace(fch)&&a<line.get(j).length());
        return a;
    }
    /**删除空行*/
    public boolean deleteBlankLine() {
        if(line.get(j).equals("")) {
            return true;
        }
        return false;
    }
    /**将数组内容写进文件*/
    public void writeInFile() {
    	thisline="";
    	line.clear();
        for(String s:record) {
            if(s!="\n") {
            	thisline += s;
            }
            else {
            	line.add(thisline);
//                System.out.println(thisline);
            	thisline="";
            }

        }
    }
    /***
    * 
    * 
    * *****/
    /**
    * 词法分析
    */
    public ArrayList<String> analyse() {
        strToken = ""; // 置strToken为空串
//        FileProcessing.clearFile(outputPath);//清空文件
        while(j<line.size()) {
            i=0;
            if(deleteBlankLine()) {
                j++;
                continue;
            }
            if(isAnnotation()) {
                j++;
                continue;
            }
            if(flag!=1) {
                addRetact();
            }
            flag=0;
            //getFirstCh();
            while(i<line.get(j).length()) {
                getChar();
                deleteBC();
                if(i==line.get(j).length()&&Character.isWhitespace(ch)) {
                    flag=1;//换行,跳过制表符造成的空行
                    strToken = "";
                    continue;
                }
                try {
                    inQuotation();//在双引号内
                    retract();//缩进
                }
                catch(Exception e) {//解决的是检测不到成对引号的时候的问题
                    throw(e);
                }
                concat();
                record.add(strToken);
                strToken="";
            }
            if(flag==1) {
                j++;
                continue;
            }
            record.add("\n");
            if(count==1&&(ch=='}'||ch=='{')) {
            	record.add("\n");
            }
            j++;
        }
        writeInFile();
//        return record;//胡耀辉需要的
        return line;//彭国倍需要的
    }
}

