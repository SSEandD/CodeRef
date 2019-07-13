package javapgb;

import java.util.ArrayList;

public class Transformation2 {
	GeneralMethod generalMethod = new GeneralMethod();
	//先默认"xxx"里面为一个
	public ArrayList transformation(ArrayList arrayList){
		ArrayList<String> arrayListTemp = new ArrayList();
		ArrayList<String> arrayListTempParentheses = new ArrayList();
		String tempJ="";
		String tempK="";
		String tempParentheses="";
		int parentheses=0;
		int startI=0;
		int startJ=0;
		int endI=0;
		int endJ=0;
		ArrayList arrList = new ArrayList();
		for(int i=0;i<arrayList.size();i++){
			arrayListTemp=(ArrayList<String>) arrayList.get(i);
			for(int z=0;z<arrayListTemp.size();z++){
				arrayListTemp.set(z,String.valueOf(arrayListTemp.get(z)));
			}
			for(int j=0;j<arrayListTemp.size();j++){
				try{
					tempJ=arrayListTemp.get(j);
					if(tempJ.equals("while")){
						//先判断是否有多余括号，有先删掉
						arrList = generalMethod.returnBracketsMatching(arrayList, i,j,"(");
						startI=Integer.parseInt(String.valueOf(arrList.get(arrList.size()-4)));
						startJ=Integer.parseInt(String.valueOf(arrList.get(arrList.size()-3)));
						endI=Integer.parseInt(String.valueOf(arrList.get(arrList.size()-2)));
						endJ=Integer.parseInt(String.valueOf(arrList.get(arrList.size()-1)));
						arrList.remove(arrList.size()-1);
						arrList.remove(arrList.size()-1);
						arrList.remove(arrList.size()-1);
						arrList.remove(arrList.size()-1);
						arrList = deleteSurplusParentheses(arrList);
						arrList.add(arrList.size()-1,";");
						arrList.add(1,";");
						arrayList = generalMethod.arrayListRemove(arrayList, startI, startJ, endI, endJ);
						arrayList = generalMethod.arrayListAdd(arrayList, i, j+1, arrList, 0);
						arrayListTemp.set(j,"for");
						arrayListTemp.add(j,"/***** Revised:while transform for! :( *****/");
						j++;
					}
				}catch(Exception e){
					continue;
				}
			}
		}
		return arrayList;
	}
	public ArrayList deleteSurplusParentheses(ArrayList arrayList){
		String temp="";
		int endI=0;
		ArrayList arrayListTemp = new ArrayList();
		for(int i=0;i<arrayList.size();i++){
			temp =String.valueOf(arrayList.get(i));
			if(temp.equals("(")){
				arrayListTemp = generalMethod.returnBracketsMatching1(arrayList, i, "(");
				endI=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				arrayListTemp.remove(arrayListTemp.size()-1);
				arrayListTemp.remove(arrayListTemp.size()-1);
				if(arrayListTemp.size()>4){
					if(String.valueOf(arrayListTemp.get(1)).equals("(")&&String.valueOf(arrayListTemp.get(arrayListTemp.size()-2)).equals(")")){
						if(isMatch(arrayListTemp)){
							arrayList.remove(endI);
							arrayList.remove(i);
							i=-1;
						}
					}
					else{
						continue;
					}
				}else{
					continue;
				}
			}
		}
		if(String.valueOf(arrayList.get(0)).equals("(")){
			arrayListTemp = generalMethod.returnBracketsMatching1(arrayList, 0, "(");
			if(!(Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)))==arrayList.size()-1)){
				arrayList.add(0,"(");
				arrayList.add(")");
			}
		}else{
			arrayList.add(0,"(");
			arrayList.add(")");
		}
		return arrayList;
	}
	public boolean isMatch(ArrayList arrayList){
		boolean t=true;
		String temp = "";
		int p=0;
		for(int i=2;i<arrayList.size()-2;i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("(")){
				p++;
			}
			if(temp.equals(")")){
				p--;
			}
		}
		if(p==0){
			return true;
		}
		return t;
	}

}
