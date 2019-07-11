package javapgb;

import java.util.ArrayList;
public class IfToIfs {
	GeneralMethod generalMethod = new GeneralMethod();
	public ArrayList transformation(ArrayList arrayList){
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();
		ArrayList arrayListTempParentheses = new ArrayList();
		ArrayList arrayListTempBraces = new ArrayList();
		String result="";
		String temp="";
		String temp1="";
		int startI=0;
		int startJ=0;
		int endI=0;
		int endJ=0;
		for(int i=0;i<arrayList.size();i++){
			try{
			arrayListTemp = (ArrayList) arrayList.get(i);
			for(int j=0;j<arrayListTemp.size();j++){
				temp=String.valueOf(arrayListTemp.get(j));
				if(temp.equals("if")){
					arrayListTempParentheses = generalMethod.returnBracketsMatching(arrayList, i, j, "(");
					endI=Integer.parseInt(String.valueOf(arrayListTempParentheses.get(arrayListTempParentheses.size()-2)));
					endJ=Integer.parseInt(String.valueOf(arrayListTempParentheses.get(arrayListTempParentheses.size()-1)));
					arrayListTempBraces = generalMethod.bracesMatching(arrayList, endI, endJ, "{");
					endI=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
					endJ=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
					//检查下一个是否为else,是则退出
					boolean isElse=false;
					boolean isBreak=false;
					for(int i1=endI;i1<arrayList.size();i1++){
						arrayListTemp1=(ArrayList) arrayList.get(i1);
						int j1=0;
						if(i1==endI){
							j1=endJ+1;
						}else{
							j1=0;
						}
						if(i1==endI&&(endJ==(arrayListTemp1.size()-1))){
							continue;
						}
						for(;j1<arrayListTemp1.size();j1++){
							temp1=String.valueOf(arrayListTemp1.get(j1));
							if(temp.equals(" ")){
								continue;
							}
							if(temp.equals("else")){
								isElse=true;
								isBreak=true;
								break;
							}else{
								isBreak=true;
								break;
							}
						}
						if(isBreak){
							break;
						}
					}
					if(isElse){
						continue;
					}else{//否则判断小括号内是否没有&和|
						boolean isOnlyOneCondition = true;
						for(int i1=0;i1<arrayListTempParentheses.size();i1++){
							temp1=String.valueOf(arrayListTempParentheses.get(i1));
							if(temp1.equals("&")||temp1.equals("|")){
								isOnlyOneCondition = false;
								break;
							}
						}
						if(isOnlyOneCondition){
							continue;
						}else{//两个都不符合则开始分解内容,变成后缀式子
							ArrayList arrayListTempPostfix = new ArrayList();
							arrayListTempPostfix = deleteSurplus(arrayListTempParentheses);
							arrayListTempParentheses = deleteSurplusParentheses(arrayListTempParentheses);
							arrayListTempPostfix = addBraces(arrayListTempPostfix);
							arrayListTempPostfix=postfix(arrayListTempPostfix);
							//分解成多个if
							arrayListTempBraces=deleteSurplus(arrayListTempBraces);
							if(String.valueOf(arrayListTempBraces.get(0)).equals("{")){
								arrayListTempBraces.remove(arrayListTempBraces.size()-1);
								arrayListTempBraces.remove(0);
							}
							arrayListTemp=ifToIfs(arrayListTempPostfix,arrayListTempBraces);
							arrayList=generalMethod.arrayListRemove(arrayList, i, j, endI, endJ);
							arrayList=generalMethod.arrayListAdd(arrayList, i, j, arrayListTemp,0);
							//System.out.println(arrayList);
						}
					}
				}
			}
		}catch(Exception e){
			continue;
		}
		}
		return arrayList;
	}
	public ArrayList ifToIfs(ArrayList arrayList1,ArrayList arrayList2){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		String result="";
		String temp="";
		String temp1="";
		int braces=0;
		int braces1=0;//{1
		int braces2=0;//}1
		int braces3=0;//{2
		int braces4=0;//}2
		boolean isBreak=false;
		for(int i=0;i<arrayList1.size();i++){
			temp=String.valueOf(arrayList1.get(i));
			if(temp.equals("|")||temp.equals("&")){
				for(int j=i-1;j>-1;j--){
					temp1=String.valueOf(arrayList1.get(j));
					if(temp1.equals("}")){
						braces++;
						if(braces==1){
							braces4=j;
						}else{
							braces2=j;
						}
						for(int z=j-1;z>-1;z--){
							temp1=String.valueOf(arrayList1.get(z));
							if(temp1.equals("{")){
								if(braces==1){
									braces3=z;
									j=z;
									break;
								}else{
									braces1=z;
									j=z;
									isBreak=true;
									break;
								}
							}
						}
					}else if(hasOpenString(temp1)){
						braces++;
						if(braces==1){
							braces3=j;
							braces4=j;
						}
						if(braces==2){
							braces1=j;
							braces2=j;
							isBreak=true;
						}
					}
					if(isBreak){
						break;
					}
				}
				isBreak=false;
				braces=0;
				arrayList1.remove(i);
				arrayList1.remove(i);
				if(temp.equals("|")){
					arrayList1 = or(arrayList1,braces1,braces2,braces3,braces4);
					i=i+braces1-braces4;
				}else{
					arrayList1 = and(arrayList1,braces1,braces2,braces3,braces4);
					i=i+braces1-braces4;
				}
			}
		}
		temp=String.valueOf(arrayList1.get(0));
		WordAnalysis wa = new WordAnalysis();
		arrayListResult = wa.lineAnalysis(arrayListResult, temp);
		ArrayList t = new ArrayList();
		ArrayList arrResult = new ArrayList();
		t=(ArrayList) arrayListResult.get(0);
		String tempxx="";
		for(int i=0;i<t.size();i++){
			tempxx=String.valueOf(t.get(i));
			if(tempxx.equals("{")){
				if((i!=t.size()-1)&&(String.valueOf(t.get(i+1)).equals("}"))){
					arrResult.add("{");
					arrResult = addTo(arrResult,arrayList2);	
				}else{
					arrResult.add(tempxx);
				}
			}else{
				arrResult.add(tempxx);
			}
		}
		return arrResult;
	}
	public ArrayList and(ArrayList arrayList,int i1,int i2,int i3,int i4){
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();
		String left="";
		boolean leftHas=false;
		boolean rightHas=false;
		String right="";
		String temp="";
		String result="";
		//找出两个对应列表。不包含{}
		if(i1!=i2){
			for(int i=i1+1;i<i2;i++){
				temp=String.valueOf(arrayList.get(i));
				arrayListTemp1.add(temp);
			}
		}else{
			arrayListTemp1.add(String.valueOf(arrayList.get(i1)));
		}
		if(i3!=i4){
			for(int i=i3+1;i<i4;i++){
				temp=String.valueOf(arrayList.get(i));
				arrayListTemp2.add(temp);
			}
		}else{
			arrayListTemp2.add(String.valueOf(arrayList.get(i3)));
		}
		//计算长度，若大于一直接open,否则检测是否展开了，得到结果是否展开
		if(!(arrayListTemp1.size()==1)){
			left+=open(arrayListTemp1);
		}else{
			temp=String.valueOf(arrayListTemp1.get(0));
			left=temp;
			leftHas=hasOpen(arrayListTemp1);
		}
		if(!(arrayListTemp2.size()==1)){
			right+=open(arrayListTemp2);
		}else{
			temp=String.valueOf(arrayListTemp2.get(0));
			right=temp;
			rightHas=hasOpen(arrayListTemp2);
		}
		//计算结果Sring
		if(leftHas&&rightHas){
			for(int i=0;i<left.length();i++){
				if(left.charAt(i)=='{'){
					if(left.charAt(i+1)=='}'){
						result+=left.substring(0,i+1)+right+left.substring(i+1);
					}
				}
			}
		}else if(leftHas&&!rightHas){
			for(int i=0;i<left.length();i++){
				if(left.charAt(i)=='{'){
					if(left.charAt(i+1)=='}'){
						result+="{"+"if("+right+"){}"+"}";
						i++;
					}else{
						result+=left.charAt(i);
					}
				}else{
					result+=left.charAt(i);
				}
			}
		}else if(!leftHas&&rightHas){
			for(int i=0;i<right.length();i++){
				if(right.charAt(i)=='{'){
					if(right.charAt(i+1)=='}'){
						result+="{"+"if("+left+"){}"+"}";
					}else{
						result+=left.charAt(i);
					}
				}else{
					result+=right.charAt(i);
				}
			}
		}else{
			result+="if("+left+"){if("+right+"){}}";
		}
		//删掉i1-i4部分
		for(int i=i1;i<i4+1;i++){
			arrayList.remove(i1);
		}
		//加入结果
		arrayList.add(i1,result);
		return arrayList;
	}
	public ArrayList or(ArrayList arrayList,int i1,int i2,int i3,int i4){
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();
		String left="";
		boolean leftHas=false;
		boolean rightHas=false;
		String right="";
		String temp="";
		String result="";
		//找出两个对应列表。不包含{}
		if(i1!=i2){
			for(int i=i1+1;i<i2;i++){
				temp=String.valueOf(arrayList.get(i));
				arrayListTemp1.add(temp);
			}
		}else{
			arrayListTemp1.add(String.valueOf(arrayList.get(i1)));
		}
		if(i3!=i4){
			for(int i=i3+1;i<i4;i++){
				temp=String.valueOf(arrayList.get(i));
				arrayListTemp2.add(temp);
			}
		}else{
			arrayListTemp2.add(String.valueOf(arrayList.get(i3)));
		}
		//计算长度，若大于一直接open,否则检测是否展开了，得到结果是否展开
		if(!(arrayListTemp1.size()==1)){
			left+=open(arrayListTemp1);
		}else{
			temp=String.valueOf(arrayListTemp1.get(0));
			left=temp;
			leftHas=hasOpen(arrayListTemp1);
		}
		if(!(arrayListTemp2.size()==1)){
			right+=open(arrayListTemp2);
		}else{
			temp=String.valueOf(arrayListTemp2.get(0));
			right=temp;
			rightHas=hasOpen(arrayListTemp2);
		}
		//计算结果String
		if(leftHas&&rightHas){
			result+=left+right;
		}else if(leftHas&&!rightHas){
			result+=left+"if("+right+"){}";
		}else if(!leftHas&&rightHas){
			result+="if("+left+"){}"+right;
		}else{
			result+="if("+left+"){}if("+right+"){}";
		}
		//删掉i1-i4部分
		for(int i=i1;i<i4+1;i++){
			arrayList.remove(i1);
		}
		//加入结果
		arrayList.add(i1,result);
		return arrayList;
	}
	public String open(ArrayList arrayList){
		String result="";
		for(int i=0;i<arrayList.size();i++){
			result+=String.valueOf(arrayList.get(i));
		}
		return result;
	}
	public boolean hasOpen(ArrayList arrayList){
		boolean t=false;
		String temp="";
		temp=String.valueOf(arrayList.get(0));
		if(temp.charAt(0)=='i'&&temp.charAt(1)=='f'&&temp.charAt(temp.length()-1)=='}'){
			t=true;
		}
		return t;
	}
	public boolean hasOpenString(String s){
		boolean t=false;
		if(s.charAt(0)=='i'&&s.charAt(1)=='f'&&s.charAt(s.length()-1)=='}'){
			t=true;
		}
		return t;
	}
	public boolean hasBraces(ArrayList arrayList){
		boolean t = false;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("{")||temp.equals("}")){
				t=true;
				break;
			}
		}
		return t;
	}
	public ArrayList deleteSurplus(ArrayList arrayList){
		for(int i=0;i<4;i++){
			arrayList.remove(arrayList.size()-1);
		}
		return arrayList;
	}
	public ArrayList deleteSurplus1(ArrayList arrayList){
		for(int i=0;i<2;i++){
			arrayList.remove(arrayList.size()-1);
		}
		return arrayList;
	}
	public ArrayList parenthesesToBraces(ArrayList arrayList){
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("(")){
				arrayList.set(i, "{");
			}
			if(temp.equals(")")){
				arrayList.set(i, "}");
			}
		}
		return arrayList;
	}
	public ArrayList addBraces(ArrayList arrayList){
		String temp="";
		String temp1="";
		int parentheses1=0;
		int parentheses2=0;
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("|")||temp.equals("&")){
				arrayList.add(i,"}");
				i=i+3;
				arrayList.add(i,"{");
			}
		}
		arrayList.set(0, "{");
		arrayList.set(arrayList.size()-1, "}");
		//多余小括号转换成中括号
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("{")){
				ArrayList arrayListTemp = new ArrayList();
				int j=i+1;
				for(;j<arrayList.size();j++){
					temp1=String.valueOf(arrayList.get(j));
					if(temp1.equals("}")){
						break;
					}
					arrayListTemp.add(temp1);
				}
				for(int k=0;k<arrayListTemp.size();k++){
					temp1=String.valueOf(arrayListTemp.get(k));
					if(temp1.equals("(")){
						parentheses1++;
					}else if(temp1.equals(")")){
						parentheses2++;
					}
				}
				if(parentheses1==parentheses2){
					continue;
				}else if(parentheses1>parentheses2){
					int z=0;
					for(int k=i;k<j;k++){
						temp1=String.valueOf(arrayList.get(k));
						if(temp1.equals("(")){
							arrayList.set(k,"{");
							z++;
							if(z==parentheses1-parentheses2){
								break;
							}
						}
					}
				}else{
					int z=0;
					for(int k=j;k>i;k--){
						temp1=String.valueOf(arrayList.get(k));
						if(temp1.equals(")")){
							arrayList.set(k,"}");
							z++;
							if(z==parentheses2-parentheses1){
								break;
							}
						}
					}
				}
				parentheses1=0;
				parentheses2=0;
				i=j;
			}
		}
		return arrayList;
	}
	public boolean hasAndOrOr(ArrayList arrayList){
		boolean has=false;
		String temp="";
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("|")||temp.equals("&")){
				has=true;
				break;
			}
		}
		return has;
	}
	public ArrayList postfix(ArrayList arrayList){
		String temp="";
		int parentheses=0;
		int start1=0;
		int end1=0;
		int parenthesesJ1=0;
		int parenthesesJ2=0;
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("{")){
				parenthesesJ1=i;
				arrayListTemp=generalMethod.returnBracketsMatching1(arrayList,i,"{");
				i=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				start1=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-2)));
				arrayListTemp=deleteSurplus1(arrayListTemp);
				if(hasAndOrOr(arrayListTemp)){
					ArrayList arrayListTemp11 = new ArrayList();
					arrayListTemp11=arrayListTemp;
					arrayListTemp11.remove(0);
					arrayListTemp11.remove(arrayListTemp.size()-1);
					arrayListTemp=postfix(arrayListTemp11);
					arrayListTemp.add(0,"{");
					arrayListTemp.add("}");
				}
			}else if(temp.equals("&")||temp.equals("|")){
				ArrayList arrayListTemp1 = new ArrayList();
				arrayListTemp1=generalMethod.returnBracketsMatching1(arrayList, i+2,"{");
				parenthesesJ2=Integer.parseInt(String.valueOf(arrayListTemp1.get(arrayListTemp1.size()-1)));
				arrayListTemp1=deleteSurplus1(arrayListTemp1);
				i=parenthesesJ2;
				if(hasAndOrOr(arrayListTemp1)){
					ArrayList arrayListTemp11 = new ArrayList();
					arrayListTemp11=arrayListTemp1;
					arrayListTemp11.remove(arrayListTemp1.size()-1);
					arrayListTemp11.remove(0);
					arrayListTemp1=postfix(arrayListTemp11);
					arrayListTemp1.add(0,"{");
					arrayListTemp1.add("}");
				}
				arrayListTemp2=generalMethod.addArrayListToArrayList1(arrayListTemp, arrayListTemp1);
				arrayListTemp2.add(temp);
				arrayListTemp2.add(temp);
				arrayList=generalMethod.arrayListRemove1(arrayList, start1, parenthesesJ2);
				arrayList=generalMethod.addArrayListToArrayList2(arrayList,arrayListTemp2,start1);
				parenthesesJ1=parenthesesJ2;
				arrayListTemp=arrayListTemp2;
			}
		}
		return arrayList;
	}
	public ArrayList addTo(ArrayList arrayList,ArrayList add){
		String temp="";
		for(int i=0;i<add.size();i++){
			temp = String.valueOf(add.get(i));
			arrayList.add(temp);
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
		int startI=0;
		for(int i=1;i<arrayList.size()-1;i++){
			temp =String.valueOf(arrayList.get(i));
			if(temp.equals("(")){
				arrayListTemp = generalMethod.returnBracketsMatching1(arrayList, i, "(");
				if(hasAndOrOr(arrayListTemp)){
					continue;
				}else{
					endI=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
					startI=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-2)));
					arrayList.remove(endI);
					arrayList.remove(startI);
					String temp1="";
					i=0;
				}
			}
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
	public String nextWord(ArrayList arrayList,int j){
		String nextWord="";
		String temp="";
		for(int i=j+1;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(!temp.equals(" ")){
				return temp;
			}
		}
		return nextWord;
	}
	public String previousWord(ArrayList arrayList,int j){
		String nextWord="";
		String temp="";
		for(int i=j-1;i>-1;i--){
			temp=String.valueOf(arrayList.get(i));
			if(!temp.equals(" ")){
				return temp;
			}
		}
		return nextWord;
	}
}
