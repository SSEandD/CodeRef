package javapgb;

import java.util.ArrayList;

public class Transformation4 {
	GeneralMethod generalMethod = new GeneralMethod();
	public ArrayList transformation(ArrayList arrayList){
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListTempBraces = new ArrayList();
		ArrayList arrayListTempBraces1 = new ArrayList();
		ArrayList arrayListTempParentheses = new ArrayList();
		ArrayList arrayListTempParentheses1 = new ArrayList();
		ArrayList arrayListIf = new ArrayList();
		ArrayList arrayListCondition = new ArrayList();
		ArrayList arrayListCondition1 = new ArrayList();
		ArrayList arrayListCondition2 = new ArrayList();
		ArrayList arrayListCondition3 = new ArrayList();
		ArrayList arrayListAfterDelete = new ArrayList();
		int endI = -1;
		int endJ = -1 ;
		int endI1 = 0;
		int endJ1 = 0 ;
		int endI2 = 0;
		int endJ2 = 0;
		String temp="";
		String firstWord = "";
		String firstWord1 = "";
		String previousWord = "";
		ArrayList nextWord = new ArrayList();
		for(int i=0;i<arrayList.size();i++){
			try{
				arrayListTemp = (ArrayList) arrayList.get(i);
				for(int j=0;j<arrayListTemp.size();j++){
					try{
						temp = String.valueOf(arrayListTemp.get(j));
						if(temp.equals("if")){
							//如果前面一个是else则不进行处理
							previousWord=findPreviousWord(arrayList,i,j);
							if(previousWord.equals("else")){
								continue;
							}
							//找（）和{}内容
							arrayListTempParentheses = generalMethod.returnBracketsMatching(arrayList, i, j, "(");
							//如果括号内不是匹对==就退出
							if(!hasEquals(arrayListTempParentheses)){
								continue;
							}
							//()里面有与或也不转换
							if(hasAnd(arrayListTempParentheses)||hasOr(arrayListTempParentheses)){
								continue;
							}
							endI=Integer.parseInt(String.valueOf(arrayListTempParentheses.get(arrayListTempParentheses.size()-2)));
							endJ=Integer.parseInt(String.valueOf(arrayListTempParentheses.get(arrayListTempParentheses.size()-1)));
							arrayListTempParentheses = generalMethod.deleteSurplus(arrayListTempParentheses);
							//取大括号内容
							arrayListTempBraces = generalMethod.bracesMatching(arrayList, endI, endJ, "{");
							//如果{}部分存在break则不处理
							if(hasBreak(arrayListTempBraces)){
								continue;
							}
							endI1=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
							endJ1=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
							//找}后下一个单词
							nextWord = findNextWord(arrayList,endI1,endJ1);
							if(nextWord.size()!=0){
								firstWord = String.valueOf(nextWord.get(0));
								endI2 = Integer.parseInt(String.valueOf(nextWord.get(nextWord.size()-2)));
								endJ2 = Integer.parseInt(String.valueOf(nextWord.get(nextWord.size()-1)));
							}else{
								firstWord = "";
							}
							//判断下一个是否为else，不是则直接单个if转switch
							if(!firstWord.equals("else")){
								arrayListCondition1 = getCondition1(arrayListTempParentheses);
								arrayListCondition2 = getCondition2(arrayListTempParentheses);
								arrayListTemp1 = (ArrayList) arrayList.get(endI1);
								//1==i
								if(isConstant(arrayListCondition1)&&(!isConstant(arrayListCondition2))){
									arrayListCondition3 = arrayListCondition1;
									arrayListCondition1 = arrayListCondition2;
								}
								//i==1
								else if(isConstant(arrayListCondition2)&&(!isConstant(arrayListCondition1))){
									arrayListCondition3 = arrayListCondition2;
								}
								//不是一常量一变量则退出
								else{
									continue;
								}
								//}前添加break;  {后加case 1:
								if(String.valueOf(arrayListTemp1.get(endJ1)).equals("}")){
									arrayListTemp1.add(endJ1,";");
									arrayListTemp1.add(endJ1,"break");
									endI2 = Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-4)));
									endJ2 = Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-3)));
									arrayListTemp1=(ArrayList) arrayList.get(endI2);
									arrayListTemp1.add(endJ2+1,":");
									for(int z=arrayListCondition3.size()-1;z>-1;z--){
										arrayListTemp1.add(endJ2+1,String.valueOf(arrayListCondition3.get(z)));
									}
									arrayListTemp1.add(endJ2+1," ");
									arrayListTemp1.add(endJ2+1,"case");
								}else{
									arrayListTemp1.add(endJ1+1,"}");
									arrayListTemp1.add(endJ1+1,";");
									arrayListTemp1.add(endJ1+1,"break");
									//)后加{case 1：
									arrayListTemp1=(ArrayList) arrayList.get(endI);
									arrayListTemp1.add(endJ+1,":");
									for(int z=arrayListCondition3.size()-1;z>-1;z--){
										arrayListTemp1.add(endJ+1,String.valueOf(arrayListCondition3.get(z)));
									}
									arrayListTemp1.add(endJ+1," ");
									arrayListTemp1.add(endJ+1,"case");
									arrayListTemp1.add(endJ+1,"{");
								}
								//删除if（i==1）
								arrayList = generalMethod.arrayListRemove(arrayList, i, j, endI, endJ);
								arrayListTemp1 = (ArrayList) arrayList.get(i);
								arrayListTemp1.add(j,")");
								for(int z=arrayListCondition1.size()-1;z>-1;z--){
									arrayListTemp1.add(j,String.valueOf(arrayListCondition1.get(z)));
								}
								arrayListTemp1.add(j,"(");
								arrayListTemp1.add(j,"switch");
								arrayListTemp1.add(j,"\r\n/***** Revised:if transform switch! :( *****/\r\n");
								continue;
							}
							//取出代码块if、else、else if 代码块
							boolean hasBreak=false;
							while(firstWord.equals("else")){
								//判断else后面是否为if
								nextWord = findNextWord(arrayList,endI2,endJ2);
								if(nextWord.size()!=0){
									firstWord1 = String.valueOf(nextWord.get(0));
									if(!firstWord1.equals("if")){
										break;
									}
								}
								endI=endI1;
								endJ=endJ1;
								arrayListTempParentheses = generalMethod.returnBracketsMatching(arrayList, endI1, endJ1, "(");
								//如果（）没有==也退出
								if(!hasEquals(arrayListTempParentheses)){
									hasBreak=true;
									break;
								}
								//()里面有与或也不转换
								if(hasAnd(arrayListTempParentheses)||hasOr(arrayListTempParentheses)){
									hasBreak=true;
									break;
								}
								endI1 = Integer.parseInt(String.valueOf(arrayListTempParentheses.get(arrayListTempParentheses.size()-2)));
								endJ1 = Integer.parseInt(String.valueOf(arrayListTempParentheses.get(arrayListTempParentheses.size()-1)));
								arrayListTempBraces = generalMethod.bracesMatching(arrayList, endI1, endJ1, "{");
								//{}内有break则不可以转换，跳出
								if(hasBreak(arrayListTempBraces)){
									hasBreak=true;
									break;
								}
								endI1=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
								endJ1=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
								nextWord = findNextWord(arrayList,endI1,endJ1);
								if(nextWord.size()!=0){
									firstWord = String.valueOf(nextWord.get(0));
									endI2 = Integer.parseInt(String.valueOf(nextWord.get(nextWord.size()-2)));
									endJ2 = Integer.parseInt(String.valueOf(nextWord.get(nextWord.size()-1)));
								}else{
									firstWord = "";
								}
							}
							//后面的else if/else存在break，不转换//括号中不存在==不转换//（）里面有与或不转换
							if(hasBreak){
								continue;
							}
							//下一个不为else则确定范围了,下一个是else则包括else{}内容
							if(!firstWord.equals("else")){
								endI=endI1;
								endJ=endJ1;
							}else{
								arrayListTempBraces = generalMethod.returnBracketsMatching(arrayList, endI1, endJ1, "{");
								endI1=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
								endJ1=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
								endI=endI1;
								endJ=endJ1;
							}
							arrayListIf = getIfArrayList(arrayList,i,j,endI,endJ);
							arrayListCondition = isSameCondition1(arrayListIf);
							if(String.valueOf(arrayListCondition.get(0)).equals("false")){
								continue;
							}
							int s=0;
							int e=0;
							String s1 = String.valueOf(arrayListCondition.get(arrayListCondition.size()-2));
							String e1 = String.valueOf(arrayListCondition.get(arrayListCondition.size()-1));
							while(!e1.equals("|")){
								s = Integer.parseInt(s1);
								e = Integer.parseInt(e1);
								arrayListIf = generalMethod.arrayListRemove1(arrayListIf, s, e);
								arrayListCondition.remove(arrayListCondition.size()-1);
								arrayListCondition.remove(arrayListCondition.size()-1);
								s1 = String.valueOf(arrayListCondition.get(arrayListCondition.size()-2));
								e1 = String.valueOf(arrayListCondition.get(arrayListCondition.size()-1));
							}
							arrayListCondition.remove(arrayListCondition.size()-1);
							arrayListAfterDelete= deleteIfAndElse(arrayListIf);
							ArrayList arrayListResult = new ArrayList();
							arrayListResult.add("/***** Revised:if transform switch! :( *****/");
							arrayListResult.add("switch");
							arrayListResult.add("(");
							arrayListResult = generalMethod.addArrayListToArrayList1(arrayListResult, arrayListCondition);
							arrayListResult.add(")");
							arrayListResult.add("{");
							arrayListResult=generalMethod.addArrayListToArrayList1(arrayListResult, bracketsToCase(arrayListAfterDelete));
							arrayListResult.add("}");
							arrayList = generalMethod.arrayListRemove(arrayList, i, j, endI, endJ);
							arrayList = generalMethod.arrayListAdd(arrayList, i, j, arrayListResult, 0);
							int size = arrayListResult.size();
							if(i==endI){
								arrayListTemp = (ArrayList) arrayList.get(i);
								j=endJ-j+size;
							}else{
								for(int k=i;k<endI;k++){
									arrayList.remove(i+1);
								}
								arrayListTemp = (ArrayList) arrayList.get(i);
								j=-1;
							}
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
	/*输入删掉if、else if的语句
	 * 输出转成case部分
	 */
	public ArrayList bracketsToCase(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		int brackets=0;
		int endI=0;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("(")){
				arrayListTemp = generalMethod.returnBracketsMatching1(arrayList, i, "(");
				endI = Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				arrayListResult=bracketsToCaseParentheses(arrayListResult,arrayListTemp);
				arrayListTemp = generalMethod.bracesMatching1(arrayList, endI);
				endI = Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				arrayListTemp = bracketsToCaseBraces(arrayListResult,arrayListTemp,false);
				i=endI;
			}else if(temp.equals("else")){
				arrayListTemp = generalMethod.bracesMatching1(arrayList, i);
				endI = Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				arrayListResult.add("default");
				arrayListResult.add(":");
				arrayListResult = bracketsToCaseBraces(arrayListResult,arrayListTemp,true);
			}
		}
		return arrayListResult;
	}
	public ArrayList bracketsToCaseParentheses(ArrayList arrayListResult,ArrayList arrayListTemp){
		arrayListResult.add("case");
		arrayListResult.add(" ");
		arrayListTemp.remove(arrayListTemp.size()-1);
		arrayListTemp.remove(arrayListTemp.size()-1);
		arrayListTemp.remove(arrayListTemp.size()-1);
		arrayListTemp.remove(0);
		arrayListResult = generalMethod.addArrayListToArrayList1(arrayListResult, arrayListTemp);
		arrayListResult.add(":");
		return arrayListResult;
	}
	public ArrayList bracketsToCaseBraces(ArrayList arrayListResult,ArrayList arrayListTemp,boolean isElse){
		arrayListTemp.remove(arrayListTemp.size()-1);
		arrayListTemp.remove(arrayListTemp.size()-1);
		arrayListTemp.remove(arrayListTemp.size()-1);
		arrayListTemp.remove(0);
		arrayListResult = generalMethod.addArrayListToArrayList1(arrayListResult, arrayListTemp);
		if(!isElse){
			arrayListResult.add("break");
			arrayListResult.add(";");
		}
		return arrayListResult;
	}
	/*输入if,else块,()内已处理
	 * 返回只有(){}的列表
	 */
	public ArrayList deleteIfAndElse(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		int endI;
		String temp="";
		String temp1="";
		String nextWord="";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("if")){
				arrayList.remove(i);
				i--;
			}else if(temp.equals("else")){
				temp1=findNextWord1(arrayList,i);
				if(temp1.equals("if")){
					arrayList.remove(i);
					arrayList.remove(i);
					arrayList.remove(i);
					i--;
				}
			}
		}
		return arrayList;
	}
	/*输入if列表
	 * 输出判断条件[i,startIndex,endIndex],不可以转换则输出[false]
	 */
	public ArrayList isSameCondition1(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListParentheses = new ArrayList();
		ArrayList condition11 = new ArrayList();
		ArrayList condition12 = new ArrayList();
		ArrayList condition21 = new ArrayList();
		ArrayList condition22 = new ArrayList();
		String temp="";
		String temp1="";
		String temp2="";
		int time = 0;
		int conditionStart1 = 0;
		int conditionMiddle1 = 0;
		int conditionEnd1 = 0;
		int conditionStart2 = 0;
		int conditionMiddle2 = 0;
		int conditionEnd2 = 0;
		int start = 0;
		int end = 0;
		boolean isGetCondition2 = false;
		boolean isBreak = false;
		boolean canBeChange = true;
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("if")){
				time++;
				arrayListParentheses = generalMethod.returnBracketsMatching1(arrayList, i, "(");
				end = Integer.parseInt(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-1)));
				start = Integer.parseInt(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-2)));
				//不要开头的（和结尾的）
				int j=start+1;
				for(;j<end;j++){
					temp1=String.valueOf(arrayList.get(j));
					if(temp1.equals("=")&&String.valueOf(arrayList.get(j+1)).equals("=")){
						if(time==1){
							conditionStart1 = start+1;
							conditionMiddle1 = j;
						}else{
							conditionStart2 = start+1;
							conditionMiddle2 = j;
						}
						j++;
						isGetCondition2 = true;
					}else{
						if(time==1){
							if(isGetCondition2){
								condition12.add(temp1);
							}else{
								condition11.add(temp1);
							}
						}else{
							if(isGetCondition2){
								condition22.add(temp1);
							}else{
								condition21.add(temp1);
							}
						}
					}
				}
				isGetCondition2 = false;
				if(time==1){
					conditionEnd1=j-1;
				}else{
					conditionEnd2=j-1;
				}
				if(time == 2){
					//如果都不相等则没有一致的判断条件，退出
					if((!arrayListEquals(condition11,condition21))&&(!arrayListEquals(condition11,condition22))){
						if((!arrayListEquals(condition12,condition21))&&(!arrayListEquals(condition12,condition22))){
							canBeChange = false;
							break;
						}else{
							arrayListResult.add(conditionMiddle1);
							arrayListResult.add(conditionEnd1);
							if(arrayListEquals(condition12,condition21)){
								arrayListResult.add(conditionStart2);
								arrayListResult.add(conditionMiddle2+1);
							}else{
								arrayListResult.add(conditionMiddle2);
								arrayListResult.add(conditionEnd2);
							}
							condition11 = condition12;
						}
					}else{
						arrayListResult.add(conditionStart1);
						arrayListResult.add(conditionMiddle1+1);
						if(arrayListEquals(condition11,condition21)){
							arrayListResult.add(conditionStart2);
							arrayListResult.add(conditionMiddle2+1);
						}else{
							arrayListResult.add(conditionMiddle2);
							arrayListResult.add(conditionEnd2);
						}
					}
				}
				if(time>2){
					if((!arrayListEquals(condition11,condition21))&&(!arrayListEquals(condition11,condition22))){
						canBeChange = false;
						break;
					}else{
						if(arrayListEquals(condition11,condition21)){
							arrayListResult.add(conditionStart2);
							arrayListResult.add(conditionMiddle2+1);
						}else{
							arrayListResult.add(conditionMiddle2);
							arrayListResult.add(conditionEnd2);
						}
					}
				}
			}
			for(int z=0;z<condition21.size();z++){
				condition21.remove(0);
			}
			for(int z=0;z<condition22.size();z++){
				condition22.remove(0);
			}
		}
		if(!canBeChange){
			ArrayList result = new ArrayList();
			result.add("false");
			return result;
		}
		if(time==1){
			//一个常量一个不是常量，而且返回不为常量那个
			//后面那个是常量添加前面那个作为条件，后面的作为删除下标
			if(!isConstant(condition11)&&isConstant(condition12)){
				arrayListResult.add("|");
				arrayListResult.add(conditionStart1);
				arrayListResult.add(conditionMiddle1+1);
				arrayListResult=addArrayListToArrayList(arrayListResult,0,condition11);
				return arrayListResult;
			}
			else if(isConstant(condition11)&&!isConstant(condition12)){
				arrayListResult.add("|");
				arrayListResult.add(conditionMiddle1);
				arrayListResult.add(conditionEnd1);
				arrayListResult=addArrayListToArrayList(arrayListResult,0,condition12);
				return arrayListResult;
			}else{
				ArrayList result = new ArrayList();
				result.add("false");
				return result;
			}
		}
		if(!isConstant(condition11)){
			arrayListResult.add(0,"|");
			arrayListResult = addArrayListToArrayList(arrayListResult,0,condition11);
			return arrayListResult;
		}else{
			ArrayList result = new ArrayList();
			result.add("false");
			return result;
		}
	}
	/*输入条件
	 * 判断是否为''或者是否全为数字或操作符
	 */
	public boolean isConstant(ArrayList arrayList){
		boolean is = true;
		String temp="";
		if(String.valueOf(arrayList.get(0)).charAt(0)=='\''&&String.valueOf(arrayList.get(0)).charAt(2)=='\''){
			return true;
		}else{
			for(int i=0;i<arrayList.size();i++){
				temp = String.valueOf(arrayList.get(i));
				if(temp.length()==1){
					if(generalMethod.isOperator(temp.charAt(0))){
						continue;
					}else{
						if((int)temp.charAt(0)>47&&(int)temp.charAt(0)<58){
							continue;
						}else{
							return false;
						}
					}

				}else{
					for(int j=0;j<temp.length();j++){
						if(temp.charAt(j)<48||temp.charAt(j)>57){
							return false;
						}
					}
				}
			}
		}
		return is;
	}
	public ArrayList findNextWord(ArrayList arrayList,int i,int j){
		ArrayList result = new ArrayList();
		String temp="";
		ArrayList arrayListTemp = new ArrayList();
		for(int i1=i;i1<arrayList.size();i1++){
			arrayListTemp = (ArrayList) arrayList.get(i1);
			int j1=0;
			if(i1==i){
				j1=j+1;
			}else{
				j1=0;
			}
			for(;j1<arrayListTemp.size();j1++){
				temp=String.valueOf(arrayListTemp.get(j1));
				if(!temp.equals(" ")){
					result.add(temp);
					result.add(i1);
					result.add(j1);
					return result;
				}
			}
		}
		return result;
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
	public String findNextWord1(ArrayList arrayList,int i){
		String result = "";
		String temp = "";
		for(int j=i+1;j<arrayList.size();j++){
			temp= String.valueOf(arrayList.get(j));
			if(!temp.equals(" ")){
				result = temp;
				break;
			}
		}
		return result;
	}
	public boolean hasEquals(ArrayList arrayList){
		boolean t = false;
		boolean isBreak=false;
		String temp="";
		String nextWord = "";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("=")){
				nextWord = findNextWord1(arrayList,i);
				if(nextWord.equals("=")){
					t = true;
					break;
				}
			}
		}
		return t;
	}
	public ArrayList getCondition(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		String temp = "";
		boolean isEquals = false;
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
		}
		return arrayListResult;
	}
	public boolean hasAnd(ArrayList arrayList){
		boolean has= false;
		String temp = "";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("&")){
				has = true;
				break;
			}
		}
		return has;
	}
	public boolean hasOr(ArrayList arrayList){
		boolean has= false;
		String temp = "";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("|")){
				has = true;
				break;
			}
		}
		return has;
	}
	public ArrayList getIfArrayList(ArrayList arrayList,int i1,int j1,int i2,int j2){
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListResult = new ArrayList();
		boolean isBreak = false;
		String temp="";
		for(int i=i1;i<i2+1;i++){
			arrayListTemp = (ArrayList) arrayList.get(i);
			int j=0;
			if(i==i1){
				j=j1;
			}else{
				j=0;
			}
			if(i==i2){
				for(;j<j2+1;j++){
					temp = String.valueOf(arrayListTemp.get(j));
					arrayListResult.add(temp);
				}
				isBreak = true;
				break;
			}
			for(;j<arrayListTemp.size();j++){
				temp = String.valueOf(arrayListTemp.get(j));
				arrayListResult.add(temp);
			}
			if(isBreak){
				break;
			}
		}
		return arrayListResult;
	}
	public boolean arrayListEquals(ArrayList arrayList1,ArrayList arrayList2){
		boolean isEqual = true;
		String temp1="";
		String temp2="";
		if(arrayList1.size()!=arrayList2.size()){
			return false;
		}else{
			for(int i=0;i<arrayList1.size();i++){
				temp1=String.valueOf(arrayList1.get(i));
				temp2=String.valueOf(arrayList2.get(i));
				if(!temp1.equals(temp2)){
					return false;
				}
			}
		}
		return isEqual;
	}
	public ArrayList addArrayListToArrayList(ArrayList arrayList,int i,ArrayList addArrayList){
		String temp="";
		for(int j=0;j<addArrayList.size();j++){
			temp = String.valueOf(addArrayList.get(j));
			arrayList.add(i,temp);
			i++;
		}
		return arrayList;
	}
	public boolean hasBreak(ArrayList arrayList){
		boolean t=false;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("break")){
				return true;
			}
		}
		return t;
	}
	public ArrayList getCondition1(ArrayList arrayList){
		String temp = "";
		ArrayList arrayListResult = new ArrayList();
		for(int i=1;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("=")&&String.valueOf(arrayList.get(i+1)).equals("=")){
				return arrayListResult;
			}else{
				arrayListResult.add(temp);
			}
		}
		return arrayList;
	}
	public ArrayList getCondition2(ArrayList arrayList){
		String temp = "";
		ArrayList arrayListResult = new ArrayList();
		for(int i=arrayList.size()-2;i>-1;i--){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("=")&&String.valueOf(arrayList.get(i-1)).equals("=")){
				return arrayListResult;
			}else{
				arrayListResult.add(0,temp);
			}
		}
		return arrayList;
	}
}
