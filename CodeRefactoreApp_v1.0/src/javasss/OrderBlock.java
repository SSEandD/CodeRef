package javasss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class OrderBlock {
	
	private ArrayList<String> lines = new ArrayList<String>();//���ж�ȡ�ļ�����
	private ArrayList<String> records = new ArrayList<String>();//��¼��packa��import���������
	private String thisLine;
	private int i = 0;
	private int j = 0;
	private char ch;
	private String strToken; 
	
	public OrderBlock() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��ȡָ��·���ļ�
	 * @param fileSrc ��ȡ�ļ�·��
	 */
	public OrderBlock(ArrayList<String> list) {
		lines = list;
	}
	
	
	/**
	 * ����һ�������ַ�����ch�У�����ָʾ��ǰ��һ���ַ�
	 */
	public void getChar() {
		ch = lines.get(j).charAt(i);
		i++;
	}
	
	public void concat() {
		strToken=FileProcessing.concat(strToken,ch);
	}


	
	public void combination() {
		lines.clear();
		for(String s:records) {
			lines.add(s);
		}
	}
	
	public boolean isLetter(char ch) {
		return Character.isLetter(ch);
	}
	
	public boolean isDigit(char ch) {
		return Character.isDigit(ch);
	}
	
	public String deleteBC(String s) {
		String result="";
		boolean flag=false;
		char ch;
		int i=0;
		while(i<s.length()) {
			ch = s.charAt(i);
			if(ch>=7&&ch<=13){
				i++;
				continue;
			}
			else if(ch==32&&flag){
				result += ch;
			}
			else if(ch!=32){
				result += ch;
				flag=true;
			}
			i++;
		}
		return result;
	}
	
	public ArrayList<String> analyse() {
		strToken = ""; // ��strTokenΪ�մ�
//		FileProcessing.clearFile();//����ļ�
		while(j<lines.size()) {
			i=0;
			thisLine="";
			thisLine = deleteBC(lines.get(j));
			records.add(thisLine);
			strToken="";
			j++;
		}
		combination();
		return lines;		
	}	
}
