package javapgb;

import java.util.ArrayList;

public class Transformation3 {
	GeneralMethod generalMethod = new GeneralMethod();
	public ArrayList transformation(ArrayList arrayList) {
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();
		ArrayList arrayListTemp3 = new ArrayList();
		ArrayList arrayListTempBraces = new ArrayList();
		String temp="";
		String temp1="";
		String temp2="";
		ArrayList condition1= new ArrayList();
		ArrayList condition2= new ArrayList();
		boolean isLeftParentheses=false;
		boolean isRightParentheses=false;
		int RightParenthesesI=0;
		int RightParenthesesJ=0;
		int RightBracesI=0;
		int RightBracesJ=0;
		String result="";
		for(int i=0;i<arrayList.size();i++){
			try{
				arrayListTemp=(ArrayList) arrayList.get(i);
				for(int j=0;j<arrayListTemp.size();j++){
					try{
						temp=String.valueOf(arrayListTemp.get(j));
						if(temp.equals("switch")){
							//找(x)
							condition1=generalMethod.returnBracketsMatching(arrayList, i, j, "(");
							//定位）坐标
							RightParenthesesI=Integer.parseInt(String.valueOf(condition1.get(condition1.size()-2)));
							RightParenthesesJ=Integer.parseInt(String.valueOf(condition1.get(condition1.size()-1)));
							//找((x))中(x)
							condition1.remove(condition1.size()-1);
							condition1.remove(condition1.size()-1);
							condition1.remove(condition1.size()-1);
							condition1.remove(condition1.size()-1);
							condition1.remove(condition1.size()-1);
							condition1.remove(0);
							//找到{}内容
							arrayListTempBraces=generalMethod.returnBracketsMatching(arrayList, RightParenthesesI, RightParenthesesJ, "{");
							if(isConditionBreak(arrayListTempBraces)){
								continue;
							}
							ArrayList arrayListSwitchToIf = new ArrayList();
							arrayListSwitchToIf=switchToIf(arrayListTempBraces,condition1);
							//删除switch,加入switchToIf
							RightBracesI=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
							RightBracesJ=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
							//如果switch和}在同一行
							if(i==RightBracesI){
								for(int z=j;z<RightBracesJ+1;z++){
									arrayListTemp.remove(j);
								}
								int j1=j;
								for(int z=0;z<arrayListSwitchToIf.size();z++){
									arrayListTemp.add(j1,String.valueOf(arrayListSwitchToIf.get(z)));
									j1++;
								}
							}else{
								for(int i1=i;i1<RightBracesI+1;i1++){
									arrayListTemp3=(ArrayList) arrayList.get(i1);
									int j1=0;
									if(i1==i){
										j1=j;
									}else{
										j1=0;
									}
									if(i1==RightBracesI){
										for(;j1<RightBracesJ+1;j1++){
											arrayListTemp3.remove(0);
											break;
										}
									}
									int j2=j1;
									int size=arrayListTemp3.size();
									for(;j1<size;j1++){
										arrayListTemp3.remove(j2);
									}
								}
								int j1=j;
								for(int z=0;z<arrayListSwitchToIf.size();z++){
									arrayListTemp.add(j1,String.valueOf(arrayListSwitchToIf.get(z)));
									j1++;
								}
							}
							arrayListTemp.add(j,"\r\n/***** Revised:switch transform if! :( *****/\r\n");
							j++;
						}
					}catch(Exception e){
						continue;
					}
				}
			}catch(Exception e){
				continue;
			}
		}
		return arrayList;
	}
	public ArrayList switchToIf(ArrayList arrayList,ArrayList condition1){
		String result="";
		String tempI="";
		String tempJ="";
		ArrayList arrayListCondition = new ArrayList();
		ArrayList arrayListResult=new ArrayList();
		ArrayList arrayListSentence=new ArrayList();
		ArrayList arrayListBraces = new ArrayList();
		for(int i=1;i<arrayList.size();i++){
			tempI=String.valueOf(arrayList.get(i));
			if(tempI.equals("case")){
				if(arrayListResult.size()==0){
					arrayListResult.add("if");
				}else{
					arrayListResult.add("else");
					arrayListResult.add(" ");
					arrayListResult.add("if");
				}
				arrayListResult.add("(");
				for(int z=0;z<condition1.size();z++){
					arrayListResult.add(String.valueOf(condition1.get(z)));
				}
				arrayListCondition=getCondition(arrayList,i);
				if(isString(arrayListCondition)){
					arrayListResult.add(".");
					arrayListResult.add("equals");
					arrayListResult.add("(");
					for(int z=0;z<arrayListCondition.size()-1;z++){
						arrayListResult.add(String.valueOf(arrayListCondition.get(z)));
					}
					arrayListResult.add(")");
					arrayListResult.add(")");
					arrayListResult.add("{");
				}else{
					arrayListResult.add("=");
					arrayListResult.add("=");
					for(int z=0;z<arrayListCondition.size()-1;z++){
						arrayListResult.add(String.valueOf(arrayListCondition.get(z)));
					}
					arrayListResult.add(")");
					arrayListResult.add("{");
				}
				arrayListSentence=getSentence(arrayList,Integer.parseInt(String.valueOf(arrayListCondition.get(arrayListCondition.size()-1))));
				for(int k=0;k<arrayListSentence.size();k++){
					arrayListResult.add(String.valueOf(arrayListSentence.get(k)));
				}
				arrayListResult.add("}");
			}else if(tempI.equals("default")){
				arrayListCondition=getCondition(arrayList,i);
				arrayListResult.add("else");
				arrayListResult.add("{");
				arrayListSentence=getSentence(arrayList,Integer.parseInt(String.valueOf(arrayListCondition.get(0))));
				for(int k=0;k<arrayListSentence.size();k++){
					arrayListResult.add(String.valueOf(arrayListSentence.get(k)));
				}
				arrayListResult.add("}");
			}else if(tempI.equals("{")){
				arrayListBraces = generalMethod.returnBracketsMatching1(arrayList, i, "{");
				i=Integer.parseInt(String.valueOf(arrayListBraces.get(arrayListBraces.size()-1)));
			}
		}
		return arrayListResult;
	}
	public ArrayList getCondition(ArrayList arrayList,int j){
		String temp="";
		int resultI=0;
		ArrayList arrayListResult=new ArrayList();
		for(int i=j+1;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals(" ")){
				continue;
			}else if(temp.equals(":")){
				resultI=i;
				break;
			}else{
				arrayListResult.add(temp);
			}
		}
		arrayListResult.add(String.valueOf(resultI));
		return arrayListResult;
	}
	public ArrayList getSentence(ArrayList arrayList,int j){
		String temp="";
		ArrayList arrayListResult=new ArrayList();
		ArrayList arrayListTemp=new ArrayList();
		ArrayList arrayListBraces = new ArrayList();
		int braces=1;
		for(int i=j+1;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("case")){
				arrayListTemp=getCondition(arrayList,i);
				i=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
			}else if(temp.equals("default")){
				arrayListTemp=getCondition(arrayList,i);
				i=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
			}else if(temp.equals("break")){
				break;
			}else if(temp.equals("{")){
				arrayListBraces = generalMethod.returnBracketsMatching1(arrayList, i, "{");
				for(int z =0;z<arrayListBraces.size()-2;z++){
					arrayListResult.add(String.valueOf(arrayListBraces.get(z)));
				}
				i=Integer.parseInt(String.valueOf(arrayListBraces.get(arrayListBraces.size()-1)));
			}else if(temp.equals("}")){
				break;
			}
			else{
				arrayListResult.add(temp);
			}
		}
		return arrayListResult;
	}
	public boolean isString(ArrayList arrayList){
		boolean  t =false;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp =String.valueOf(arrayList.get(i));
			if(temp.charAt(0)=='"'){
				return true;
			}
		}
		return t;
	}
	public boolean isConditionBreak(ArrayList arrayList){
		boolean t=false;
		String temp="";
		String temp1="";
		int endI=0;
		ArrayList arr1 = new ArrayList();
		ArrayList arr2 = new ArrayList();
		for(int i=1;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("if")){
				arr1 = generalMethod.returnBracketsMatching1(arrayList, i, "(");
				endI = Integer.parseInt(String.valueOf(arr1.get(arr1.size()-1)));
				arr2 = generalMethod.bracesMatching1(arrayList, endI);
				endI = Integer.parseInt(String.valueOf(arr2.get(arr2.size()-1)));
				for(int z=1;z<arr2.size()-3;z++){
					temp1 = String.valueOf(arr2.get(z));
					if(temp1.equals("break")){
						return true;
					}
				}
				i=endI;
			}
		}
		return t;
	}
}
