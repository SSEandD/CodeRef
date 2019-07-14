package ManyFileProcess;

import org.omg.CORBA.ARG_IN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileNode extends FileTree{
    public FileNode(String address){
        this.nowAddress=address;
        this.sonTree=null;
        super.createTree();
    }
    @Override
    public ArrayList<String> getFileList() {
        File theFile= new File(nowAddress);
        String name=theFile.getName();
        name=name.substring(name.lastIndexOf(".")+1);
//        if("java".equals(name) || "txt".equals(name)){
        if("java".equals(name)){
            ArrayList<String> l=new ArrayList<>();
            l.add(nowAddress);
            return l;
        }
        return null;
    }
}
