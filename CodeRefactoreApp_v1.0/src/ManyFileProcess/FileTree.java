package ManyFileProcess;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public abstract class FileTree {
//    public static void main(String[] args){
////        FileTree root=new FilesNode("C:\\Users\\Lumia-man\\Desktop\\Test");
////        root.print();
//    }
    //此节点文件地址
    String nowAddress;
    //节点的子树，也就是它里面的文件或文件夹
    List<FileTree> sonTree=new ArrayList<>();
    //获取文件地址的方法
    public String getAddress(){
        return nowAddress;
    }

    //获取所有文件的地址
    public ArrayList<String> getAllFileList(){
        ArrayList<String> allFileList=new ArrayList<>();
        allFileList.add(nowAddress);
        if(sonTree!=null) {
            for(FileTree t:sonTree){
                allFileList.addAll(t.getAllFileList());
            }
        }
        return allFileList;
    }
    //获取java或txt文件地址列表的方法
    public ArrayList<String> getFileList(){
        ArrayList<String> allFileList=new ArrayList<>();
        for(FileTree t:sonTree){
            ArrayList<String> a=t.getFileList();
            if(a!=null){
                allFileList.addAll(a);
            }
        }
        return allFileList;
    }
    //打印找到的java或txt文件地址列表
    public void print(){
        for (String add:getFileList()){
            System.out.println(add);
        }
    }
    //生成子树的方法
    public void createTree(){
        if(sonTree==null) return;
        //获取此文件地址下的所有文件地址
        File[] allAddress=new File(nowAddress).listFiles();
        if(allAddress==null) return;
        for(int i=0;i<allAddress.length;i++){
            if(allAddress[i].isFile()){
                sonTree.add(new FileNode(allAddress[i].getPath()));
            }else if(allAddress[i].isDirectory()){
                sonTree.add(new FilesNode(allAddress[i].getPath()));
            }
        }
    }

}
