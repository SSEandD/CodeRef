package javahyh.lexical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class LexicalAnalysis {

    private static final String[] operator = {
            ".", "[", "]", "(", ")",
            "!", "~", "++", "--",
            "*", "/", "%", "+", "-",
            ">>", "<<", ">>>", "<=", ">=", "<", ">",
            "==", "!=", ",", "?", ":",
            "&", "|", "^", "&&", "||",
            "=", "+=", "-=",
            "*=", "/=", "%=", "&=", "|=",
            "^=", "~=", "<<=", ">>=", ">>>=",
    };
    //字符流
    private ArrayList<Character> resources = null;
    //读取位置标识
    private int varMark = 0;

    //接收路径
    public LexicalAnalysis(String addName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(addName));
            resources = new ArrayList<Character>(500);
            int ch;
            while ((ch = reader.read()) != -1) {
                resources.add((char) ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收按单字符-字符串的List
    public LexicalAnalysis(String addName, List<String> stringRes) {
        this.resources = new ArrayList<>();
        try {
            for (String s : stringRes) {
                if (!s.equals("")) {
                    char c;
                    if (s.length() == 1) {
                        c = s.charAt(0);
                        resources.add(c);
                    } else for (int i = 0; i < s.length(); i++) {
                        c = s.charAt(i);
                        resources.add(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收按行-字符串的List
    public LexicalAnalysis(String addName, List<String> stringRes, List<String> lineRes) {
        this.resources = new ArrayList<>();
        try {
            for (String line : lineRes) {
                char[] cLine = line.toCharArray();
                for (char c : cLine) resources.add(c);
                resources.add('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //接收按List-词-字符串的List
    public LexicalAnalysis(String addName, List<String> stringRes, List<String> lineRes, List tableRes) {
        resources = new ArrayList<>();
        try {
            for (Object o : tableRes) {
                ArrayList line = (ArrayList) o;
                for (Object o1 : line) {
                    String s = o1.toString();
                    char[] c = s.toCharArray();
                    for (char aC : c) {
                        resources.add(aC);
                    }
                }
                if(resources.get(resources.size()-1) != '\n') {
                    resources.add('\n');
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> ceate() {
        List<String> list = new ArrayList<String>();
        String word;
        while (varMark < resources.size()) {
            word = scaner();
            list.add(word);
        }
        varMark = 0;
        return list;
    }

    //打印词法分析后的结果
    public void print() {
        String word;
        while (varMark < resources.size()) {
            word = scaner();
            System.out.println(word);
        }
        varMark = 0;
    }

    //判断是否为数字
    private boolean isDigit(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    //判断是否为合法字符
    private boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch == '_' || ch == '$');
    }

    //判断是否为运算符
    private boolean isOperator(String token) {
        for (String i : operator) {
            if (token.equals(i)) {
                return true;
            }
        }
        return false;
    }

    //词法分析程序
    private String scaner() {
        if (varMark == resources.size()) return "";
        StringBuilder token = new StringBuilder(); //识别的字符串
        char ch = resources.get(varMark++); //读入一个字符、并标识下移
        token.append(ch);  //连接成为字符串
        if (ch == '/' && varMark < resources.size()) { //处理注释
            ch = resources.get(varMark);
            if (ch == '/') {
                do {
                    token.append(ch);
                    if (varMark + 1 == resources.size()) {
                        break;
                    }
                    ch = resources.get(++varMark);
                } while (ch != '\n');
            } else if (ch == '*') {
                do {
                    token.append(ch);
                    if (varMark + 1 == resources.size()) {
                        break;
                    }
                    ch = resources.get(++varMark);
                } while (resources.get(varMark) != '*' || resources.get(varMark + 1) != '/');
                token.append(resources.get(varMark++));
                token.append(resources.get(varMark++));
            }
        } else if (isDigit(ch)) {
            ch = resources.get(varMark);
            while (isDigit(ch) || ch == '.') {
                token.append(ch);
                if (varMark + 1 == resources.size()) {
                    break;
                }
                ch = resources.get(++varMark);
            }
        } else if (isLetter(ch)) {
            ch = resources.get(varMark);
            while (isLetter(ch) || isDigit(ch)) {
                token.append(ch);
                if (varMark + 1 == resources.size()) {
                    break;
                }
                ch = resources.get(++varMark);
            }
        } else if (isOperator(token.toString())) {
            for (int i = 0; i < 3; i++) {
                if (varMark == resources.size()) break;
                ch = resources.get(varMark++);
                token.append(ch);
                if (!isOperator(token.toString())) {
                    token.deleteCharAt(token.length() - 1);
                    varMark--;
                    break;
                }
            }
        } else if (ch == '\"') {
            ch = resources.get(varMark);
            if (ch != '\'') {
                while (ch != '\"') {
                    if (ch == '\\') {
                        token.append(ch);
                        ch = resources.get(++varMark);
                        token.append(ch);
                    } else {
                        token.append(ch);
                    }
                    if (varMark + 1 == resources.size()) break;
                    ch = resources.get(++varMark);
                }
                token.append(ch);
                if (varMark < resources.size()) varMark++;
            }
        } else if (ch == '\'') {
            ch = resources.get(varMark);
            int k = 0;
            if (ch == '\\') k = 3;
            else k = 2;
            for (int i = 0; i < k; i++) {
                token.append(ch);
                ch = resources.get(++varMark);
            }
        }
        return token.toString();
    }

}