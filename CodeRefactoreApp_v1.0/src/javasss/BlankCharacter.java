package javasss;
import java.util.ArrayList;
import javasss.FileProcessing;

public class BlankCharacter {
    private int i = 0;
    private int count = 0;
    private int j=0;
    private int flag = 0;
    private char ch; // �ַ�������������¶�����Դ�����ַ�
    private char fch; // �ַ�����
    private String strToken; // �ַ����飬��Ź��ɵ��ʷ��ŵ��ַ���
    private String blankStr="";
    private String thisline="";
    private ArrayList<String> line = new ArrayList<String>();
    private ArrayList<String> record = new ArrayList<String>();
    public BlankCharacter() {
    }
    /**
    * ��ȡָ��·���ļ�
    * @param fileSrc ��ȡ�ļ�·��
    */
    public BlankCharacter(String fileSrc, String outputSrc) {
        line = FileProcessing.readFile(fileSrc);
    }
    public BlankCharacter(ArrayList<String> array) {
        line = array;
    }
    /**
    * ����һ�������ַ�����ch�У�����ָʾ��ǰ��һ���ַ�
    */
    public void getChar() {
        ch = line.get(j).charAt(i);
        i++;
    }
    public void concat() {
        strToken=FileProcessing.concat(strToken,ch);
    }
    /** ���ch�е��ַ��Ƿ�Ϊ�հף����������getChar()ֱ��ch�н���һ���ǿհ��ַ�*/
    public void deleteBC() {
        //isSpaceChar(char ch) ȷ��ָ���ַ��Ƿ�Ϊ Unicode �հ��ַ���
        //������������ʶ���з�
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
    /**�������ڵĿհ��ַ���飬��chΪ�հף�����ת���ַ���*/
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
    /**�������ڵ�����ո����ת��*/
    public void inQuotation() {
        if(ch=='"') {//˫������
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
        if(ch=='\'') {//��������
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
    /**ע������*/
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
    /**��¼�������ı�ĵȼ�*/
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
    * �ı�������*/
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
    /**д��������*/
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
    /**����һ���ַ�������*/
    public int getFirstCh() {
        int a=0;
        do {
            fch=line.get(j).charAt(a++);
        }while(Character.isWhitespace(fch)&&a<line.get(j).length());
        return a;
    }
    /**ɾ������*/
    public boolean deleteBlankLine() {
        if(line.get(j).equals("")) {
            return true;
        }
        return false;
    }
    /**����������д���ļ�*/
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
    * �ʷ�����
    */
    public ArrayList<String> analyse() {
        strToken = ""; // ��strTokenΪ�մ�
//        FileProcessing.clearFile(outputPath);//����ļ�
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
                    flag=1;//����,�����Ʊ����ɵĿ���
                    strToken = "";
                    continue;
                }
                try {
                    inQuotation();//��˫������
                    retract();//����
                }
                catch(Exception e) {//������Ǽ�ⲻ���ɶ����ŵ�ʱ�������
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
//        return record;//��ҫ����Ҫ��
        return line;//�������Ҫ��
    }
}

