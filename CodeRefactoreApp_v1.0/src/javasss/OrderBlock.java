package javasss;

import java.util.ArrayList;



public class OrderBlock {
	
	private ArrayList<String> lines;//���ж�ȡ�ļ�����
	private ArrayList<String> records = new ArrayList<>();//��¼
	private String thisLine;
	private int j = 0;

	/**
	 * ��ȡָ��·���ļ�
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
				//�������з�
				i++;
				continue;
			}
			else if(ch==32&&flag){
				//��Ϊ�ո�
				result += ch;
			}
			else if(ch!=32){
				//ֱ��������һ���ǿհ׷�
				result += ch;
				flag=true;
			}
			i++;
		}
		return result;
	}
	
	public ArrayList<String> analyse() {
		//		FileProcessing.clearFile();//����ļ�
		while(j<lines.size()) {
			thisLine="";
			thisLine = deleteBC(lines.get(j));
			records.add(thisLine);//δ�ӻ��з�
			j++;
		}
		combination();
		return lines;		
	}	
}
