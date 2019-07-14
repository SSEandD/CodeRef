package javapgb;

import java.util.ArrayList;

public class Transformation1 {
	GeneralMethod generalMethod = new GeneralMethod();
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
		//遍历列
		for(int i=0;i<arrayList.size();i++){
			try{
				arrayListTemp=(ArrayList<String>) arrayList.get(i);
				//遍历行
				for(int j=0;j<arrayListTemp.size();j++){
					try{
						tempJ=String.valueOf(arrayListTemp.get(j));
						//匹配到for
						if(tempJ.equals("for")){
							//获取小括号内全部内容
							arrayListParentheses=generalMethod.returnBracketsMatching(arrayList, i, j, "(");
							//判断是否为增强for循环，是则退出
							if(isStrongFor(arrayListParentheses)){
								continue;
							}
							//右小括号I
							endI=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-2)));
							//右小括号J
							endJ=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-1)));
							//找{}或下一个句子
							arrayListBraces=generalMethod.bracesMatching(arrayList, endI, endJ, "{");
							//定位语句块范围
							startI=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-4)));
							startJ=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-3)));
							endI=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-2)));
							endJ=Integer.valueOf(String.valueOf(arrayListBraces.get(arrayListBraces.size()-1)));
							//找判断条件
							arrayListCondition=getCondition(arrayListParentheses);
							if(arrayListCondition.size()==0){
								continue;
							}
							//找出初始化（int i = 0）
							arrayListInitialization=commaToSemicolon(getInitialization(arrayListParentheses));
							//找出循环实现（i++）
							arrayListLoop=commaToSemicolon(getLoop(arrayListParentheses));
							//先在}前插入loop部分
							arrayListBraces.remove(arrayListBraces.size()-1);
							arrayListBraces.remove(arrayListBraces.size()-1);
							arrayListBraces.remove(arrayListBraces.size()-1);
							arrayListBraces.remove(arrayListBraces.size()-1);
							//如果是没有{}的则先加上{}
							if(!String.valueOf(arrayListBraces.get(0)).equals("{")){
								arrayListBraces.add(arrayListBraces.size(),"}");
								arrayListBraces.add(0,"{");
							}
							//将i++部分插入}之前
							int z1=arrayListBraces.size()-1;
							for(int z=0;z<arrayListLoop.size();z++){
								arrayListBraces.add(z1,String.valueOf(arrayListLoop.get(z)));
								z1++;
							}
							arrayList = generalMethod.arrayListRemove(arrayList, startI, startJ, endI, endJ);
							arrayList = generalMethod.arrayListAdd(arrayList, startI, startJ, arrayListBraces, 0);
							//删掉( =》;   ;=》)
							//获取（）下标
							startI=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-4)));
							startJ=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-3)));
							endI=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-2)));
							endJ=Integer.valueOf(String.valueOf(arrayListParentheses.get(arrayListParentheses.size()-1)));
							int semicolon=0;
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
										parentheses++;
										continue;
									}
									if(tempParentheses.equals(")")){
										parentheses--;
										if(parentheses==0){
											t=true;
											break;
										}
									}
									if(tempParentheses.equals(";")){
										arrayListCondition.remove(j1);
										j1--;
										semicolon++;
										continue;
									}
									if(semicolon==0){
										arrayListCondition.remove(j1);
										j1--;
									}else if(semicolon==1){
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
							arrayListTemp.add(j,"\r\n/***** Revised:for transform while! :( *****/\r\n");
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
		if(arrayListResult.size()!=0){
			arrayListResult.add(";");
		}
		return arrayListResult;
	}
	public ArrayList getCondition(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		//第一个;前为0，之后为1，再到;2，为1时保存
		int j = 0;
		String temp = "";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals(";")){
				j++;
				continue;
			}
			if(j==1){
				arrayListResult.add(temp);
			}
			if(j==2){
				return arrayListResult;
			}
		}
		return arrayListResult;
	}
	//获取（）内i++部分
	public ArrayList getLoop(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		String temp="";
		boolean t=false;
		for(int j=arrayList.size()-2;j>0;j--){
			temp=String.valueOf(arrayList.get(j));
			if(temp.equals(")")){
				t = true;
				continue;
			}
			if(temp.equals(";")){
				if(arrayListResult.size()!=0){
					arrayListResult.add(";");
				}
				return arrayListResult;
			}
			if(t){
				arrayListResult.add(0,temp);
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
