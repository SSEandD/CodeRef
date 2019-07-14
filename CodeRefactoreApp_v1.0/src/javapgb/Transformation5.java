package javapgb;

import java.util.ArrayList;

public class Transformation5 {
	GeneralMethod generalMethod = new GeneralMethod();
	public ArrayList transformation(ArrayList arrayList){
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListParentheses1 = new ArrayList();
		ArrayList arrayListBraces1 = new ArrayList();
		ArrayList arrayListParentheses2 = new ArrayList();
		ArrayList arrayListBraces2 = new ArrayList();
		ArrayList arrayListBraces3 = new ArrayList();
		ArrayList arrayListParentheses3 = new ArrayList();
		ArrayList arrayListNextWord = new ArrayList();
		String temp ="";
		String previousWord = "";
		//0-4:)}if)}下标
		int endI[]={0,0,0,0,0,0};
		int endJ[]={0,0,0,0,0,0};
		for(int i = 0 ;i<arrayList.size();i++){
			try{
				arrayListTemp = (ArrayList) arrayList.get(i);
				for(int j=0;j<arrayListTemp.size();j++){
					try{
						temp = String.valueOf(arrayListTemp.get(j));
						//找到if
						if(temp.equals("if")){
							//如果是else的if就不转
							previousWord=findPreviousWord(arrayList,i,j);
							if(previousWord.equals("else")){
								continue;
							}
							//找到（）{}部分
							arrayListParentheses1 = generalMethod.returnBracketsMatching(arrayList, i, j, "(");
							endJ[0] = Integer.parseInt(String.valueOf(arrayListParentheses1.get(arrayListParentheses1.size()-1)));
							endI[0] = Integer.parseInt(String.valueOf(arrayListParentheses1.get(arrayListParentheses1.size()-2)));
							arrayListBraces1 = generalMethod.returnBracketsMatching(arrayList, endI[0], endJ[0], "{");
							endJ[1] = Integer.parseInt(String.valueOf(arrayListBraces1.get(arrayListBraces1.size()-1)));
							endI[1] = Integer.parseInt(String.valueOf(arrayListBraces1.get(arrayListBraces1.size()-2)));
							arrayListNextWord = generalMethod.findNextWord2(arrayList, endI[1], endJ[1]);
							//判断}下一个是否为if
							if(String.valueOf(arrayListNextWord.get(0)).equals("if")){
								endJ[2] = Integer.parseInt(String.valueOf(arrayListNextWord.get(arrayListNextWord.size()-1)));
								endI[2] = Integer.parseInt(String.valueOf(arrayListNextWord.get(arrayListNextWord.size()-2)));
								arrayListParentheses2 = generalMethod.returnBracketsMatching(arrayList, endI[2], endJ[2], "(");
								endJ[3] = Integer.parseInt(String.valueOf(arrayListParentheses2.get(arrayListParentheses2.size()-1)));
								endI[3] = Integer.parseInt(String.valueOf(arrayListParentheses2.get(arrayListParentheses2.size()-2)));
								arrayListBraces2 = generalMethod.returnBracketsMatching(arrayList, endI[3], endJ[3], "{");
								endJ[4] = Integer.parseInt(String.valueOf(arrayListBraces2.get(arrayListBraces2.size()-1)));
								endI[4] = Integer.parseInt(String.valueOf(arrayListBraces2.get(arrayListBraces2.size()-2)));
								//删掉后面坐标
								for(int z=0;z<4;z++){
									arrayListParentheses1.remove(arrayListParentheses1.size()-1);
									arrayListBraces1.remove(arrayListBraces1.size()-1);
									arrayListParentheses2.remove(arrayListParentheses2.size()-1);
									arrayListBraces2.remove(arrayListBraces2.size()-1);
								}
								//判断代码块是否相同
								if(isIdentical(arrayListBraces1,arrayListBraces2)){
									arrayList = generalMethod.arrayListRemove(arrayList, endI[2], endJ[2], endI[4], endJ[4]);
									arrayListParentheses2.remove(0);
									arrayListParentheses2.remove(arrayListParentheses2.size()-1);
									arrayListTemp1 = (ArrayList) arrayList.get(endI[0]);
									arrayListTemp1 = addCondition(arrayListTemp1,arrayListParentheses2,endJ[0]);
									if(j==0){
										i--;
										arrayListTemp1=(ArrayList)arrayList.get(i);
										j=arrayListTemp1.size()-1;
									}else{
										j--;
									}
									continue;
								}
							}else{
								//如果下面是if且只囊括if句子则可进行&&修改
								if(nextIsOnlyIf(arrayListBraces1)){
									arrayListNextWord = generalMethod.findNextWord2(arrayList, endI[0], endJ[0]);
									endJ[3]=Integer.parseInt(String.valueOf(arrayListNextWord.get(arrayListNextWord.size()-1)));
									endI[3]=Integer.parseInt(String.valueOf(arrayListNextWord.get(arrayListNextWord.size()-2)));
									arrayListParentheses2 = generalMethod.returnBracketsMatching(arrayList, endI[3], endJ[3], "(");
									endI[4]=Integer.parseInt(String.valueOf(arrayListParentheses2.get(arrayListParentheses2.size()-2)));
									endJ[4]=Integer.parseInt(String.valueOf(arrayListParentheses2.get(arrayListParentheses2.size()-1)));
									arrayListBraces2 = generalMethod.returnBracketsMatching(arrayList, endI[4], endJ[4], "{");
									endJ[5]=Integer.parseInt(String.valueOf(arrayListBraces2.get(arrayListBraces2.size()-1)));
									endI[5]=Integer.parseInt(String.valueOf(arrayListBraces2.get(arrayListBraces2.size()-2)));
									//删掉后面坐标
									for(int z=0;z<4;z++){
										arrayListParentheses1.remove(arrayListParentheses1.size()-1);
										arrayListBraces1.remove(arrayListBraces1.size()-1);
										arrayListBraces2.remove(arrayListBraces2.size()-1);
										arrayListParentheses2.remove(arrayListParentheses2.size()-1);
									}
									//删除要合并的if(i==0)部分
									arrayList = generalMethod.arrayListRemove(arrayList, endI[3], endJ[3], endI[1], endJ[1]);
									arrayListTemp1 = (ArrayList) arrayList.get(endI[0]);
									//将内部if的{}代替外层{}
									int endJTemp=endJ[0];
									String temp1="";
									for(int z=0;z<arrayListBraces2.size();z++){
										temp1 = String.valueOf(arrayListBraces2.get(z));
										arrayListTemp1.add(++endJTemp,temp1);
									}
									arrayListParentheses2.remove(0);
									arrayListParentheses2.remove(arrayListParentheses2.size()-1);
									arrayListTemp1.add(endJ[0],")");
									arrayListTemp1.add(++endJ[0],"&&");
									for(int z=0;z<arrayListParentheses2.size();z++){
										temp1 = String.valueOf(arrayListParentheses2.get(z));
										arrayListTemp1.add(++endJ[0],temp1);
									}
									arrayListTemp.add(j+1,"(");
									arrayListTemp = (ArrayList) arrayList.get(i);
									if(j!=0){
										if(!String.valueOf(arrayListTemp.get(j-1)).equals("\r\n/***** Revised:ifs transform if! :( *****/\r\n")){
											arrayListTemp.add(j,"\r\n/***** Revised:ifs transform if! :( *****/\r\n");
										}
									}
								}else{
									continue;
								}
							}
						}
					}catch(Exception e){
						e.printStackTrace();//continue;
					}
				}
			}catch(Exception e){
				e.printStackTrace();//continue;
			}
		}
		return arrayList;
	}
	public boolean isIdentical(ArrayList arrayList1,ArrayList arrayList2){
		//删除空格和注释
		arrayList1 = deleteSpaceAndNote(arrayList1);
		arrayList2 = deleteSpaceAndNote(arrayList2);
		//比较是否相同
		if(arrayList1.size()!=arrayList2.size()){
			return false;
		}else{
			String temp1 = "";
			String temp2 = "";
			for(int i=0;i<arrayList1.size();i++){
				temp1 = String.valueOf(arrayList1.get(i));
				temp2 = String.valueOf(arrayList2.get(i));
				if(!temp1.equals(temp2)){
					return false;
				}
			}
			return true;
		}
	}
	public ArrayList deleteSpaceAndNote(ArrayList arrayList){
		String temp = "";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals(" ")){
				arrayList.remove(i);
				i--;
			}else if(temp.charAt(0)=='/'&&(temp.charAt(0)=='*'||temp.charAt(0)=='/')){
				arrayList.remove(i);
				i--;
			}
		}
		return arrayList;
	}
	public ArrayList addCondition(ArrayList arrayList,ArrayList arrayListCondition,int j){
		arrayList.add(j,"||");
		for(int i=0;i<arrayListCondition.size();i++){
			arrayList.add(++j,String.valueOf(arrayListCondition.get(i)));
		}
		return arrayList;
	}
	public boolean nextIsOnlyIf(ArrayList arrayList){
		ArrayList arrayListBraces = new ArrayList();
		ArrayList arrayListNextWord = new ArrayList();
		//找下一个是否为if
		arrayListNextWord = findNextWord(arrayList,0);
		if(!String.valueOf(arrayListNextWord.get(0)).equals("if")){
			return false;
		}else{
			int endI = Integer.parseInt(String.valueOf(arrayListNextWord.get(1)));
			arrayListBraces = generalMethod.returnBracketsMatching1(arrayList, endI, "{");
			int endI1 = Integer.parseInt(String.valueOf(arrayListBraces.get(arrayListBraces.size()-1)));
			arrayListNextWord = findNextWord(arrayList,endI1);
			if(Integer.parseInt(String.valueOf(arrayListNextWord.get(1)))==arrayList.size()-5){
				return true;
			}else{
				return false;
			}
		}
	}
	public ArrayList findNextWord(ArrayList arrayList,int j){
		ArrayList arrayListResult = new ArrayList();
		String temp = "";
		for(int i=j+1;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals(" ")){
				continue;
			}else if(temp.charAt(0)=='/'&&(temp.charAt(0)=='*'||temp.charAt(0)=='/')){
				continue;
			}else{
				arrayListResult.add(temp);
				arrayListResult.add(i);
				return arrayListResult;
			}
		}
		return arrayListResult;
	}
	public String findPreviousWord(ArrayList arrayList,int i,int j){
		String result = "";
		String temp="";
		boolean isBreak = false;
		ArrayList arrayListTemp = new ArrayList();
		for(int i1=i;i1>-1;i1--){
			arrayListTemp = (ArrayList) arrayList.get(i1);
			int j1=0;
			if(i1==i){
				j1=j-1;
			}else{
				j1=arrayListTemp.size()-1;
			}
			for(;j1>-1;j1--){
				temp=String.valueOf(arrayListTemp.get(j1));
				if(!temp.equals(" ")){
					result = temp;
					isBreak = true;
					break;
				}
			}
			if(isBreak){
				break;
			}
		}
		return result;
	}
}
