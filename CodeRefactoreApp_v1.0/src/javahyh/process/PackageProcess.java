package javahyh.process;

import java.util.List;

public class PackageProcess {

    private List<String> packageGroup;

    public PackageProcess(List<String> packageGroup){
        this.packageGroup=packageGroup;
    }

    public List<String> process(){
        if(packageGroup.size()>1) packageGroup.add(0,"/* Suggest:Too Much Package :( */\r\n");
        String word;
        for(int i=0;i<packageGroup.size();i++){
            word=packageGroup.get(i);
            if(word.equals(";")) {
                i++;
                packageGroup.add(i,"\r\n\r\n");
            }
        }
        return packageGroup;
    }

}
