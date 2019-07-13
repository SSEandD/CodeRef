package javahyh.process;

import java.util.List;

public class PackageProcess {

    private List<String> packageGroup;

    public PackageProcess(List<String> packageGroup) {
        this.packageGroup = packageGroup;
    }

    public List<String> process() {
        String word;
        for (int i = 0; i < packageGroup.size(); i++) {
            word = packageGroup.get(i);
            if(word.matches("/\\*(.|\\s)*\\*/")) {
                i++;
                packageGroup.add(i, "\r\n");
            }
            if (word.equals(";")) {
                i++;
                packageGroup.add(i, "\r\n\r\n");
            }
        }
        return packageGroup;
    }

}
