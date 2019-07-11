package javasss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class OrderBlock {
	
	private ArrayList<String> lines = new ArrayList<String>();//按行读取文件内容
	private ArrayList<String> records = new ArrayList<String>();//记录除packa和import以外的内容
	private String thisLine;
	private int i = 0;
	private int j = 0;
	private char ch;
	private String strToken; 
	
	public OrderBlock() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 读取指定路径文件
	 * @param fileSrc 读取文件路径
	 */
	public OrderBlock(ArrayList<String> list) {
		lines = list;
	}
	
	
	/**
	 * 将下一个输入字符读到ch中，搜索指示器前移一个字符
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
		strToken = ""; // 置strToken为空串
//		FileProcessing.clearFile();//清空文件
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
