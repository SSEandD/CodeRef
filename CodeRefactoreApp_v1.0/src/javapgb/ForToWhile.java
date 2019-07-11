package javapgb;

import java.util.ArrayList;

public class ForToWhile {
	GeneralMethod generalMethod = new GeneralMethod();
	//一句for后面没括号可处理不了
	public ArrayList transformation(ArrayList arrayList){
		ArrayList<String> arrayListTemp = new ArrayList();
		ArrayList<String> arrayListTempInitialization = new ArrayList();
		ArrayList<String> arrayListTempCondition = new ArrayList();
		ArrayList<String> arrayListTempLoop = new ArrayList();
		ArrayList<String> arrayListParentheses = new ArrayList();
		ArrayList<String> arrayListBraces = new ArrayList();
		ArrayList<String> arrayListInitialization = new ArrayList();
		ArrayList<String> arrayListCondition = new ArrayList();
		ArrayList<String> arrayListLoop = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();//存初始化和循环
		int startI=0;
		int endI=0;
		int startJ=0;
		int endJ=0;
		String temp="";
		String tempParentheses="";
		String tempJ="";
		String tempK="";
		for(int i=0;i<arrayList.size();i++){
			try{
				arrayListTemp=(ArrayList<String>) arrayList.get(i);
				for(int z=0;z<arrayListTemp.size();z++){
					arrayListTemp.set(z,String.valueOf(arrayListTemp.get(z)));
				}
				for(int j=0;j<arrayListTemp.size();j++){
					try{
						tempJ=String.valueOf(arrayListTemp.get(j));
						if(tempJ.equals("for")){
							arrayListParentheses=generalMethod.returnBracketsMatching(arrayList, i, j, "(");
							if(isStrongFor(arrayListParentheses)){
								continue;
							}
							endI=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-2)));
							endJ=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-1)));
							arrayListBraces=generalMethod.bracesMatching(arrayList, endI, endJ, "{");
							//定位}位置
							startI=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-4)));
							startJ=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-3)));
							endI=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-2)));
							endJ=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-1)));
							//找出初始化，循环条件，循环实现
							arrayListInitialization=commaToSemicolon(getInitialization(arrayListParentheses));
							arrayListLoop=commaToSemicolon(getLoop(arrayListParentheses));
							//先在}前插入loop部分
							arrayListBraces.remove(arrayListBraces.size()-1);
							arrayListBraces.remove(arrayListBraces.size()-1);
							arrayListBraces.remove(arrayListBraces.size()-1);
							arrayListBraces.remove(arrayListBraces.size()-1);
							if(!String.valueOf(arrayListBraces.get(0)).equals("{")){
								arrayListBraces.add(arrayListBraces.size(),"}");
								arrayListBraces.add(0,"{");
							}
							int z1=arrayListBraces.size()-1;
							for(int z=0;z<arrayListLoop.size();z++){
								arrayListBraces.add(z1,String.valueOf(arrayListLoop.get(z)));
								z1++;
							}
							arrayList = generalMethod.arrayListRemove(arrayList, startI, startJ, endI, endJ);
							arrayList = generalMethod.arrayListAdd(arrayList, startI, startJ, arrayListBraces, 0);
							//删掉( =》;   ;=》)
							startI=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-4)));
							startJ=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-3)));
							endI=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-2)));
							endJ=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-1)));
							int parentheses=0;
							boolean t=false;
							for(int i1=startI;i1<endI+1;i1++){
								arrayListCondition=(ArrayList<String>) arrayList.get(i1);
								int j1=0;
								if(i1==i){
									j1=startJ;
								}else{
									j1=0;
								}
								arrayListCondition.size();
								for(;j1<arrayListCondition.size();j1++){
									tempParentheses=String.valueOf(arrayListCondition.get(j1));
									if(tempParentheses.equals("(")){
										continue;
									}
									if(tempParentheses.equals(")")){
										t=true;
										break;
									}
									if(tempParentheses.equals(";")){
										arrayListCondition.remove(j1);
										j1--;
										parentheses++;
										continue;
									}
									if(parentheses==0){
										arrayListCondition.remove(j1);
										j1--;
									}else if(parentheses==1){
										continue;
									}else{
										arrayListCondition.remove(j1);
										j1--;
									}
								}
								if(t){
									break;
								}
							}
							//for=>while
							arrayListTemp.set(j, "while");
							//while之前插入初始化
							int k=j;
							for(int i1=0;i1<arrayListInitialization.size();i1++){
								arrayListTemp.add(k,String.valueOf(arrayListInitialization.get(i1)));
								k++;
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
	public ArrayList commaToSemicolon(ArrayList arrayList){
		for(int i=0;i<arrayList.size();i++){
			if(String.valueOf(arrayList.get(i)).equals(",")){
				arrayList.set(i, ";");
			}
		}
		return arrayList;
	}
	public ArrayList getInitialization(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		String temp="";
		for(int j=0;j<arrayList.size();j++){
			temp=String.valueOf(arrayList.get(j));
			if(temp.equals("(")){
				continue;
			}else if(temp.equals(";")){
				break;
			}else{
				arrayListResult.add(temp);
			}
		}
		arrayListResult.add(";");
		return arrayListResult;
	}
	public ArrayList getLoop(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		String temp="";
		int parentheses=0;
		boolean t=false;
		for(int j=0;j<arrayList.size();j++){
			temp=String.valueOf(arrayList.get(j));
			if(temp.equals(";")){
				parentheses++;
				continue;
			}
			if(temp.equals(")")){
				break;
			}
			if(parentheses<2){
				continue;
			}else{
				arrayListResult.add(temp);
			}
		}
		arrayListResult.add(";");
		return arrayListResult;
	}
	public boolean isStrongFor(ArrayList arrayList){
		boolean t=false;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals(":")){
				return true;
			}
		}
		return t;
	}

}
