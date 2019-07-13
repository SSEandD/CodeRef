package javasss;

import java.util.ArrayList;



public class OrderBlock {
	
	private ArrayList<String> lines;//按行读取文件内容
	private ArrayList<String> records = new ArrayList<>();//记录
	private String thisLine;
	private int j = 0;

	/**
	 * 读取指定路径文件
	 */
	public OrderBlock(ArrayList<String> list) {
		lines = list;
	}
	
	public void combination() {
		lines.clear();
		for(String s:records) {
			lines.add(s);
		}
	}
	
	public String deleteBC(String s) {
		String result="";
		boolean flag=false;
		char ch;
		int i=0;
		while(i<s.length()) {
			ch = s.charAt(i);
			if(ch>=7&&ch<=13){
				//包含换行符
				i++;
				continue;
			}
			else if(ch==32&&flag){
				//若为空格
				result += ch;
			}
			else if(ch!=32){
				//直到遇见第一个非空白符
				result += ch;
				flag=true;
			}
			i++;
		}
		return result;
	}
	
	public ArrayList<String> analyse() {
		//		FileProcessing.clearFile();//清空文件
		while(j<lines.size()) {
			thisLine="";
			thisLine = deleteBC(lines.get(j));
			records.add(thisLine);//未加换行符
			j++;
		}
		combination();
		return lines;		
	}	
}
