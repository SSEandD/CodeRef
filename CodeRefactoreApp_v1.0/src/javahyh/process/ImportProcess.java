package javahyh.process;

import java.util.*;

public class ImportProcess {
    //包含所有import
    private List<String> importGroup;

    private ArrayList<ArrayList<String>> table;

    public ImportProcess(List<String> importGroup){
        this.importGroup=importGroup;
        table=new ArrayList<>();
        init();
    }

    public List<String> process(){
        List<String> list=new ArrayList<>();
        table=blockSort();
        for(ArrayList<String> line:table){
            list.addAll(line);
        }
        return list;
    }

    private void init(){
        for(int i=0;i<importGroup.size();){
            ArrayList<String> line=new ArrayList<>();
            String word;
            word=importGroup.get(i);
            i++;
            while (!word.equals(";")){
                line.add(word);
                word=importGroup.get(i);
                i++;
            }
            line.add(word);
            line.add("\r\n");
            table.add(line);
        }
    }

    private ArrayList<ArrayList<String>> blockSort(){
        ArrayList<ArrayList<String>> newTable=new ArrayList<>();
        ArrayList<String> tran= new ArrayList<String>();
        tran.add("\r\n");
        //标志是否有此类型的import语句
        int isHaveImport=0;
        //静态导入分组
        for(ArrayList<String> line:table){
            String word=line.get(2);
            if(word.equals("static")){
                newTable.add(line);
                isHaveImport=1;
            }
        }
        if(isHaveImport==1){
            newTable.add(tran);
            isHaveImport=0;
        }
        //第三方包分组（顶级包为字典序）
        ArrayList<String> s=new ArrayList<>();
        for(ArrayList<String> line:table){
            String word=line.get(2);
            if(word.equals("static") || word.equals("java") || word.equals("javax")) continue;
            isHaveImport=1;
            int flag=0;
            for(String i:s){
                if(word.equals(i)){
                    flag=1;
                    break;
                }
            }
            if(flag==0) s.add(word);
        }
        String[] ss=s.toArray(new String[0]);
        Arrays.sort(ss);
        for(int i=0;i<ss.length;i++){
            for(ArrayList<String> line:table){
                String word=line.get(2);
                if(word.equals(ss[i])){
                    newTable.add(line);
                }
            }
        }
        if(isHaveImport==1){
            newTable.add(tran);
            isHaveImport=0;
        }
        //java包分组
        for(ArrayList<String> line:table){
            String word=line.get(2);
            if(word.equals("java")){
                newTable.add(line);
                isHaveImport=1;
            }
        }
        if(isHaveImport==1){
            newTable.add(tran);
            isHaveImport=0;
        }
        //javax包分组
        for(ArrayList<String> line:table){
            String word=line.get(2);
            if(word.equals("javax")){
                newTable.add(line);
                isHaveImport=1;
            }
        }
        if(isHaveImport==1){
            newTable.add(tran);
            isHaveImport=0;
        }
        return newTable;
    }
}
