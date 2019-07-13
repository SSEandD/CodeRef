package ManyFileProcess;

import java.util.ArrayList;
import java.util.List;

public class FilesNode extends FileTree{
    public FilesNode(String address){
        this.nowAddress=address;
        super.createTree();
    }
}
