package javapgb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javasss.OrderBlock;

public class WordAnalysis {
	GeneralMethod generalMethod = new GeneralMethod();
	public ArrayList wordAnalysis(ArrayList arrayList,String line){
		ArrayList arrayListRow = new ArrayList();
		arrayList=lineAnalysis(arrayList,line);
		return arrayList;
	}
	public ArrayList lineAnalysis(ArrayList arrayList,String row){
		ArrayList arrRow=new ArrayList();//行
		ArrayList arrTemp=new ArrayList();
		String temp="";
		char c=' ';
		String tempQuotation="";
		String lastWord = "";
		boolean isUnfinishedNotes=false;
		if(arrayList.size()>0){
			arrTemp = (ArrayList) arrayList.get(arrayList.size()-1);
			if(arrTemp.size()>0){
				lastWord = String.valueOf(arrTemp.get(arrTemp.size()-1));
				//判断上一个开头是否为/*...
				if(lastWord.length()>=2){
					if(lastWord.charAt(0)=='/'&&lastWord.charAt(1)=='*'){
						if(lastWord.charAt(lastWord.length()-2)=='*'&&lastWord.charAt(lastWord.length()-1)=='/'){
							isUnfinishedNotes=false;
						}else{
							isUnfinishedNotes=true;
						}
					}
				}
			}
		}
		//如果是先把下面的加进去，直到碰到*/
		if(isUnfinishedNotes){
			int k=0;
			boolean hasFinished=false;
			for(k=0;k<row.length()-1;k++){
				c=row.charAt(k);
				if(c=='*'&&row.charAt(k+1)=='/'){
					lastWord+=c;
					lastWord+='/';
					isUnfinishedNotes=false;
					break;
				}else{
					lastWord+=c;
				}
			}
			arrTemp.set(arrTemp.size()-1, lastWord);
			if(isUnfinishedNotes){
				return arrayList;
			}else{
				row = row.substring(k+2,row.length());
				if(row.length()==0){
					return arrayList;
				}
			}
		}
		if(!isUnfinishedNotes){
			for(int i=0;i<row.length();i++){
				c=row.charAt(i);
				if(generalMethod.isSeparator(c)||generalMethod.isSpace(c)||generalMethod.isOperator(c)){
					if(c=='_'){
						temp+=c;
						continue;
					}
					if(!temp.equals("")){
						arrRow.add(temp);
					}
					if(c=='"'||c=='\''){
						for(int j=i+1;j<row.length();j++){
							if(row.charAt(j)==c){
								arrRow.add(c+tempQuotation+c);
								tempQuotation="";
								i=j;
								break;
							}else{
								tempQuotation+=row.charAt(j);
							}
						}
					}else if(c=='/'){
						if(i<row.length()-1){
							String temp1="";
							if(row.charAt(i+1)=='/'){
								int i1=0;
								for(i1=i;i1<row.length();i1++){
									temp1+=row.charAt(i1);
								}
								temp1+="\n";
								arrRow.add(temp1);
								i=i1;
								continue;
							}else if(row.charAt(i+1)=='*'){
								temp1+='/';
								temp1+='*';
								boolean isBreak = false;
								if(i+2>=row.length()-1){
									arrRow.add(temp1);
									arrayList.add(arrRow);
									return arrayList;
								}
								for(int i2=i+2;i2<row.length()-1;i2++){
									if(row.charAt(i2)=='*'&&row.charAt(i2+1)=='/'){
										i=i2+1;
										temp1+='*';
										temp1+='/';
										arrRow.add(temp1);
										isBreak=true;
										break;
									}else{
										temp1+=row.charAt(i2);
									}
								}
								if(!isBreak){
									temp1+=row.charAt(row.length()-1);
									arrRow.add(temp1);
									i=row.length()-1;
								}
							}
						}
					}else{
						arrRow.add(c);
					}
					temp="";
				}else{
					temp+=c;
				}
			}
			if(temp!=""){
				arrRow.add(temp);
				temp="";
			}
		}
		arrayList.add(arrRow);
		return arrayList;
	}

}
