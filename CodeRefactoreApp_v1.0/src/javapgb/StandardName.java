package javapgb;

import java.util.ArrayList;

public class StandardName {
	public static String[] Modifier = {"public","protected","private","abstract","static","final","transient","volatile","synchronized","native","strictfp"};
	public ArrayList standardName(ArrayList arrayList){
		GeneralMethod generalMethod= new GeneralMethod();
		EnglishRecognition englishWordRecognition = new EnglishRecognition();
		ArrayList nextWord = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListClass = new ArrayList();
		ArrayList oldPackageName = new ArrayList();
		ArrayList newPackageName = new ArrayList();
		String temp="";
		String temp1="";
		String oldName="";
		String newName="";
		int nameI=0;
		int nameJ=0;
		for(int i=0;i<arrayList.size();i++){
			arrayListTemp=(ArrayList) arrayList.get(i);
			for(int j=0;j<arrayListTemp.size();j++){
				temp=String.valueOf(arrayListTemp.get(j));
				//发现package
				if(temp.equals("package")){
					boolean isBreak=false;
					oldPackageName = new ArrayList();
					newPackageName = new ArrayList();
					for(int i1=i;i1<arrayList.size();i1++){
						//循环查找该行
						arrayListTemp1=(ArrayList) arrayList.get(i1);
						int j1=0;
						if(i==i1){
							j1=j+1;
						}else{
							j1=0;
						}
						for(;j1<arrayListTemp1.size();j1++){
							temp1=String.valueOf(arrayListTemp1.get(j1));
							//发现；时为该语句终点
							if(temp1.equals(";")){
								isBreak=true;
								break;
							}
							if(temp1.length()==1){
								if((!generalMethod.isOperator(temp1.charAt(0)))
										&&(!generalMethod.isSpace(temp1.charAt(0)))
											&&(!generalMethod.isSeparator(temp1.charAt(0)))){
									oldPackageName.add(temp1);
									arrayListTemp1.set(j1, temp1.toLowerCase());
									newPackageName.add(temp1.toLowerCase());
								}else{
									continue;
								}
							}else{
								if(temp1.charAt(0)=='/'){
									continue;
								}
								arrayListTemp1.set(j1, temp1.toLowerCase());
								oldPackageName.add(temp1);
								newPackageName.add(temp1.toLowerCase());
							}
						}
						if(isBreak){
							break;
						}
					}
					if(!isIdentical(oldPackageName,newPackageName)){
						arrayListTemp = (ArrayList) arrayList.get(0);
						arrayListTemp.add(0,"/***** Suggest:package's name must consist of lowercase! *****/");
					}
				}
				else if(temp.equals("class")){
					try{
						nextWord = nextWord(arrayList,i,j);
						int endI=Integer.parseInt(String.valueOf(nextWord.get(1)));
						int endJ=Integer.parseInt(String.valueOf(nextWord.get(2)));
						oldName=String.valueOf(nextWord.get(0));
						newName=englishWordRecognition.englishWordRecognition(oldName, 1);
						arrayListClass.add(newName);
						if(!oldName.equals(newName)){
							arrayList = newName(arrayList,oldName,newName);
							ArrayList classNameAddNotes = new ArrayList();
							classNameAddNotes = addNotes(arrayList,i,j);
							arrayListTemp = (ArrayList) arrayList.get(Integer.parseInt(String.valueOf(classNameAddNotes.get(0))));
							arrayListTemp.add(Integer.parseInt(String.valueOf(classNameAddNotes.get(1)))+1,"\r\n/***** Suggest:class name must be great hump structure  *****/\r\n");
						}
					}catch(Exception e){
						continue;
					}
				}else if(temp.equals("final")){
					boolean isBreak=false;
					//0属性1常量2类
					int type = 0;
					int words=0;
					for(int i1=i;i1<arrayList.size();i1++){
						arrayListTemp1=(ArrayList) arrayList.get(i1);
						int j1=0;
						if(i==i1){
							j1=j+1;
						}else{
							j1=0;
						}
						for(;j1<arrayListTemp1.size();j1++){
							temp1=String.valueOf(arrayListTemp1.get(j1));
							if(temp1.equals(";")||temp1.equals("=")){
								type=0;
								isBreak=true;
								break;
							}else if(temp1.equals("(")){
								type=1;
								isBreak=true;
								break;
							}else if(temp1.equals("{")){
								type=2;
								isBreak=true;
								break;
							}
							if(!temp1.equals(" ")){
								oldName = temp1;
							}
						}
						if(isBreak){
							break;
						}
					}
					
					if(type==0){
						newName = oldName.toUpperCase();
						if(!newName.equals(oldName)){
							arrayList = newName(arrayList,oldName,newName);
						}
						if(!oldName.equals(oldName.toUpperCase())){
							ArrayList addNotes = new ArrayList();
							addNotes = addNotes(arrayList,i,j);
							arrayListTemp = (ArrayList) arrayList.get(Integer.parseInt(String.valueOf(addNotes.get(0))));
							//arrayListTemp.add(Integer.parseInt(String.valueOf(addNotes.get(1)))+1,"\r\n/***** Suggest:constant's name must be uppercase  *****/\r\n");
						}
					}else if(type==1){
						newName = englishWordRecognition.englishWordRecognition(oldName, 0);
						if(!newName.equals(oldName)){
							arrayList = newName(arrayList,oldName,newName);
							ArrayList addNotes = new ArrayList();
							addNotes = addNotes(arrayList,i,j);
							arrayListTemp = (ArrayList) arrayList.get(Integer.parseInt(String.valueOf(addNotes.get(0))));
							//arrayListTemp.add(Integer.parseInt(String.valueOf(addNotes.get(1)))+1,"\r\n/***** Suggest:Method's name must be hump structure  *****/\r\n");
						}
					}
				}else if(temp.equals("(")){
					//(往回找，找到;或者}为止，识别其中是否有new，没有则视为方法
					ArrayList arrayListNew = new ArrayList();
					int words=0;
					boolean isBreak=false;
					boolean needChange =true;
					for(int i1=i;i1>-1;i1--){
						arrayListTemp1=(ArrayList) arrayList.get(i1);
						int j1=0;
						if(i1==i){
							j1=j-1;
						}else{
							j1=arrayListTemp1.size()-1;
						}
						for(;j1>-1;j1--){
							if(j1>arrayListTemp1.size()) break;
							temp1 = String.valueOf(arrayListTemp1.get(j1));
							if(!temp1.equals(" ")){
								words++;
								if(words==1){//回找第一个词，有可能为方法名先记录
									if((!generalMethod.isKeyWord(temp1))&&(!temp1.equals("("))){
										oldName = temp1;
										arrayListNew.add(0,temp1);
										continue;
									}
									//如果前一个为关键字或者（则肯定不是，跳出
									else{
										needChange=false;
										isBreak=true;
										break;
									}
								}
								if(words==2){//两个字时，往前一个为.为调用的方法，不理，跳出，只改定义时的方法
									if((!temp1.equals("."))){
										if(temp1.length()==1){
											if(generalMethod.isOperator(temp1.charAt(0))||generalMethod.isSeparator(temp1.charAt(0))){
												needChange=false;
												isBreak=true;
												break;
											}else{
												arrayListNew.add(0,temp1);
											}
										}else{
											arrayListNew.add(0,temp1);
										}
									}
									else{
										needChange=false;
										isBreak=true;
										break;
									}
								}
							}else{
								continue;
							}
							if(words>2){
								if(temp1.length()==1){
									if(generalMethod.isOperator(temp1.charAt(0))||generalMethod.isSeparator(temp1.charAt(0))){
										isBreak=true;
										break;
									}else{
										arrayListNew.add(0,temp1);
									}
								}else{
									arrayListNew.add(0,temp1);
								}
							}
						}
						if(isBreak){
							break;
						}
					}
					arrayListNew = removeNotes(arrayListNew);
					if(hasNew(arrayListNew)){
						continue;
					}else{
						try{
							if((!isClassName(arrayListClass,oldName))&&needChange&&(arrayListNew.size()>1)){
								if(oldName.length()==1){
									if(generalMethod.isOperator(String.valueOf(oldName).charAt(0))){
										continue;
									}
								}
								newName =  englishWordRecognition.englishWordRecognition(oldName, 0);
								arrayList = newName(arrayList,oldName,newName);
								ArrayList addNotes = new ArrayList();
								addNotes = addNotes(arrayList,i,j);
								arrayListTemp = (ArrayList) arrayList.get(Integer.parseInt(String.valueOf(addNotes.get(0))));
								//arrayListTemp.add(Integer.parseInt(String.valueOf(addNotes.get(1)))+1,"\r\n/***** Suggest:Method's name must be hump structure  *****/\r\n");
							}
						}catch(Exception e){
							continue;
						}
					}
				}
			}
		}
		return arrayList;
	}
	public boolean isModifier(String s){
		boolean t=false;
		for(int i=0;i<Modifier.length;i++){
			if(s.equals(Modifier[i])){
				t=true;
				break;
			}
		}
		return t;
	}
	public ArrayList newName(ArrayList arrayList,String name,String newName){
		ArrayList arrayListTemp = new ArrayList();
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			arrayListTemp=(ArrayList) arrayList.get(i);
			for(int j=0;j<arrayListTemp.size();j++){
				temp=String.valueOf(arrayListTemp.get(j));
				if(temp.equals(name)){
					arrayListTemp.set(j,newName);
				}
			}
		}
		return arrayList;
	}
	public ArrayList nextWord(ArrayList arrayList,int i,int j){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		String temp="";
		boolean isBreak=false;
		for(int i1=i;i1<arrayList.size();i1++){
			arrayListTemp=(ArrayList) arrayList.get(i1);
			int j1=0;
			if(i==i1){
				j1=j+1;
			}else{
				j1=0;
			}
			for(;j1<arrayListTemp.size();j1++){
				temp = String.valueOf(arrayListTemp.get(j1));
				if(!temp.equals(" ")){
					arrayListResult.add(temp);
					arrayListResult.add(i1);
					arrayListResult.add(j1);
					return arrayListResult;
				}
			}
		}
		return arrayListResult;
	}
	public boolean hasNew(ArrayList arrayList){
		boolean hasNew=false;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("new")){
				return true;
			}
		}
		return hasNew;
	}
	public boolean isClassName(ArrayList arrayList,String s){
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals(s)){
				return true;
			}
		}
		return false;
	}
	public boolean isNotes(String s){
		if(s.length()==1){
			return false;
		}
		if(s.charAt(0)=='/'){
			if(s.charAt(1)=='/'||s.charAt(1)=='*'){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public ArrayList removeNotes(ArrayList arrayList){
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(isNotes(temp)){
				arrayList.remove(i);
				i--;
			}
		}
		return arrayList;
	}
	public boolean isIdentical(ArrayList arrayList1,ArrayList arrayList2){
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
	public ArrayList addNotes(ArrayList arrayList,int i1,int j1){
		String temp = "";
		int endI=0;
		int endJ=0;
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListResult = new ArrayList();
		if(j1==0){
			for(int i=i1-1;i>-1;i--){
				arrayListTemp = (ArrayList) arrayList.get(i);
				for(int j=arrayListTemp.size()-1;j>-1;j--){
					temp = String.valueOf(arrayListTemp.get(j));
					if(temp.equals(";")||temp.equals("{")||temp.equals("}")){
						endI=i;
						endJ=j;
						arrayListResult.add(endI);
						arrayListResult.add(endJ);
						return arrayListResult;
					}
				}
			}
		}else{
			for(int i=i1;i>-1;i--){
				arrayListTemp = (ArrayList) arrayList.get(i);
				int k=0;
				if(i==i1){
					k=j1;
				}else{
					k=arrayListTemp.size()-1;
				}
				for(int j=k;j>-1;j--){
					temp = String.valueOf(arrayListTemp.get(j));
					if(temp.equals(";")||temp.equals("{")||temp.equals("}")){
						arrayListResult.add(i);
						arrayListResult.add(j);
						return arrayListResult;
					}
				}
			}
		}
		arrayListResult.add(endI);
		arrayListResult.add(endJ);
		return arrayListResult;
	}
}
