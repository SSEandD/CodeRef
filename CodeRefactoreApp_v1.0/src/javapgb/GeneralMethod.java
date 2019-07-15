package javapgb;

import java.util.ArrayList;

public class GeneralMethod {
	static String[] keyWord = {"abstract", "boolean", "break", "byte","case", "catch",
			"char", "class", "continue", "default", "do","double", "else", "extends",
			"final", "finally", "float", "for","if", "implements", "import", "instanceof", "int",
			"interface", "long", "native", "new", "package", "private", "protected", "public", "return",
			"short", "static", "super", "switch","synchronized", "this", "throw","throws",
			"transient", "try","void","volatile","while","strictfp","enum","goto","const","assert"};
	static char[] Operator = { '+', '-', '*', '/', '>', '=', '<', '&', '|', '~' };
	static char[] Separator = { ',', ';', '{', '}', '(', ')', '[', ']', '_', ':', '.','"', '\\','\''};
	public boolean isKeyWord(String s){
		for(int i=0;i<keyWord.length;i++){
			if(s.equals(keyWord[i])){
				return true;
			}
		}
		return false;
	}
	public boolean isOperator(char c){
		for(int i=0;i<Operator.length;i++){
			if(c==Operator[i]){
				return true;
			}
		}
		return false;
	}
	public boolean isSeparator(char c){
		for(int i=0;i<Separator.length;i++){
			if(c==Separator[i]){
				return true;
			}
		}
		return false;
	}
	public boolean isSpace(char c){
		return c==' ';
	}
	/*在哪加就输入下标，在该下标位置加输入该下标，在下标后加输下标+1
	 * 
	 */
	public ArrayList addSentence(ArrayList arrayList,int i,String sentence){
		char c=' ';
		String temp="";
		String tempQuotation="";
		ArrayList arrayListTemp = new ArrayList();
		for(int j=0;j<sentence.length();j++){
			c=sentence.charAt(j);
			if(isSeparator(c)||isSpace(c)||isOperator(c)){
				if(!temp.equals("")){
					arrayListTemp.add(temp);
					temp="";
				}
				if(c=='"'||c=='\''){
					for(int k=j+1;k<sentence.length();k++){
						if(sentence.charAt(k)==c){
							arrayListTemp.add(c+tempQuotation+c);
							tempQuotation="";
							j=k;
							break;
						}else{
							tempQuotation+=sentence.charAt(k);
						}
					}
				}else{
					arrayListTemp.add(c);
				}
			}else{
				temp+=c;
			}
		}
		arrayList.add(i, arrayListTemp);
		return arrayList;
	}
	public ArrayList sentenceToArrayList(String sentence){
		ArrayList arrayList = new ArrayList();
		char c=' ';
		String temp="";
		String tempJ="";
		for(int i=0;i<sentence.length();i++){
			c=sentence.charAt(i);
			if(isOperator(c)||isSeparator(c)||isSpace(c)){
				if(!temp.equals("")){
					arrayList.add(temp);
					temp="";
				}
				if(c=='"'||c=='\''){
					tempJ+=c;
					for(int j=i+1;j<sentence.length();j++){
						if(sentence.charAt(j)==c){
							tempJ+=sentence.charAt(j);
							arrayList.add(tempJ);
							tempJ="";
						}else{
							tempJ+=sentence.charAt(j);
						}
					}
				}else{
					arrayList.add(c);
				}
			}else{
				temp+=c;
			}
		}
		return arrayList;
	}
	public ArrayList addArrayListToArrayList(ArrayList arrayList,int i,ArrayList addArrayList){
		int start=0;
		int end=0;
		for(int j=0;j<addArrayList.size();j++){
			if(String.valueOf(addArrayList.get(j)).equals(";")||String.valueOf(addArrayList.get(j)).equals("{")){
				end=j;
				ArrayList arrayListTemp= new ArrayList();
				for(int k=start;k<end+1;k++){
					arrayListTemp.add(String.valueOf(addArrayList.get(k)));
				}
				arrayList.add(i,arrayListTemp);
				i++;
				start=end+1;
			}else if(String.valueOf(addArrayList.get(j)).equals("}")){
				end=j;
				ArrayList arrayListTemp1= new ArrayList();
				for(int k=start;k<end;k++){
					arrayListTemp1.add(String.valueOf(addArrayList.get(k)));
				}
				arrayList.add(i,arrayListTemp1);
				ArrayList arrayListTemp2= new ArrayList();
				arrayListTemp2.add(String.valueOf(addArrayList.get(end)));
				i++;
				arrayList.add(i,arrayListTemp2);
				i++;
				start=end+1;
			}
		}
		return arrayList;
	}
	/*输入当前左括号的下标
	 * 输出到右括号为止的arrayList
	 */
	public ArrayList bracketsMatching(ArrayList arrayList,int i,int j){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		arrayListTemp=(ArrayList) arrayList.get(i);
		int bracketsI=0;
		int bracketsJ=0;
		String brackets=String.valueOf(arrayListTemp.get(j));
		String anotherBrackets="";
		arrayListResult.add(brackets);
		int bracket=1;
		if(brackets.equals("(")){
			anotherBrackets=")";
		}
		if(brackets.equals("{")){
			anotherBrackets="}";
		}
		if(brackets.equals("[")){
			anotherBrackets="]";
		}
		boolean t=false;
		for(int i1=i;i1<arrayList.size();i1++){
			if(i1==i){
				for(int j1=j+1;j1<arrayListTemp.size();j1++){
					if(String.valueOf(arrayListTemp.get(j1)).equals(anotherBrackets)){
						arrayListResult.add(anotherBrackets);
						bracket--;
						if(bracket==0){
							t=true;
							bracketsI=i1;
							bracketsJ=j1;
							break;
						}
					}else if(String.valueOf(arrayListTemp.get(j1)).equals(brackets)){
						arrayListResult.add(arrayListTemp.get(j1));
						bracket++;
					}else{
						arrayListResult.add(arrayListTemp.get(j1));
					}
				}
			}else{
				arrayListTemp=(ArrayList) arrayList.get(i1);
				for(int j1=0;j1<arrayListTemp.size();j1++){
					if(String.valueOf(arrayListTemp.get(j1)).equals(anotherBrackets)){
						arrayListResult.add(anotherBrackets);
						bracket--;
						if(bracket==0){
							t=true;
							bracketsI=i1;
							bracketsJ=j1;
							break;
						}
					}else if(String.valueOf(arrayListTemp.get(j1)).equals(brackets)){
						arrayListResult.add(arrayListTemp.get(j1));
						bracket++;
					}else{
						arrayListResult.add(arrayListTemp.get(j1));
					}
				}
			}
			if(t){
				break;
			}
		}
		arrayListResult.add(String.valueOf(i));
		arrayListResult.add(String.valueOf(j));
		arrayListResult.add(String.valueOf(bracketsI));
		arrayListResult.add(String.valueOf(bracketsJ));
		return arrayListResult;
	}
	/*输入当前下标
	 * 寻找左括号,调用函数,输出左括号到右括号为止的arrayList
	 */
	public ArrayList returnBracketsMatching(ArrayList arrayList,int i,int j,String brackets){
		String temp="";
		String nextWord = "";
		boolean isBreak= false;
		ArrayList arrayListTemp= new ArrayList();
		ArrayList arrayListResult= new ArrayList();
		if(brackets.equals("{")){
			nextWord = findNextWord(arrayList,i,j);
		}
		for(int i1=i;i1<arrayList.size();i1++){
			arrayListTemp=(ArrayList) arrayList.get(i1);
			int j1=0;
			if(i1==i){
				j1=j;
			}
			else{
				j1=0;
			}
			for(;j1<arrayListTemp.size();j1++){
				temp=String.valueOf(arrayListTemp.get(j1));
				if(temp.equals(brackets)){
					arrayListResult=bracketsMatching(arrayList, i1, j1);
					isBreak=true;
					break;
				}else{
					continue;
				}
			}
			if(isBreak){
				break;
			}
		}
		return arrayListResult;
	}
	public String findNextWord(ArrayList arrayList,int i,int j){
		String result = "";
		String temp="";
		boolean isBreak = false;
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
					if(temp.length()>=2){
						if(temp.charAt(0)=='/'&&(temp.charAt(0)=='/'||temp.charAt(0)=='*')){
							
						}else{
							result = temp;
							isBreak = true;
							break;
						}
					}else{
						result = temp;
						isBreak = true;
						break;
					}
				}
			}
			if(isBreak){
				break;
			}
		}
		return result;
	}
	public ArrayList arrayListRemove(ArrayList arrayList,int startI,int startJ,int endI,int endJ){
		ArrayList arrayListTemp = new ArrayList();
		String temp="";
		int size=0;
		for(int i=startI;i<endI+1;i++){
			arrayListTemp=(ArrayList) arrayList.get(i);
			size=arrayListTemp.size();
			int j=0;
			if(i==startI){
				j=startJ;
			}else{
				j=0;
			}
			if(i==endI){
				for(;j<endJ+1;j++){
					if(startI!=endI){
						arrayListTemp.remove(0);
					}else{
						arrayListTemp.remove(startJ);
					}
				}
			}else{
				for(;j<size;j++){
					if(i==startI){
						arrayListTemp.remove(startJ);
					}else{
						arrayListTemp.remove(0);
					}
				}
			}
		}
		/*
		for(int z=startI;z<endI+1;z++){
			ArrayList arrayListTempx = new ArrayList();
			arrayListTempx=(ArrayList) arrayList.get(z);
			if(arrayListTempx.size()==0){
				arrayList.remove(z);
				z--;
				endI--;
			}
		}
		*/
		return arrayList;
	}
	public ArrayList arrayListAdd(ArrayList arrayList,int i,int j,ArrayList addArrayList,int deviation){
		ArrayList arrayListTemp = new ArrayList();
		String temp="";
		arrayListTemp =(ArrayList) arrayList.get(i);
		for(int k=0;k<addArrayList.size()-deviation;k++){
			temp = String.valueOf(addArrayList.get(k));
			arrayListTemp.add(j,temp);
			j++;
		}
		return arrayList;
	}
	public ArrayList returnBracketsMatching1(ArrayList arrayList,int i,String brackets){
		String temp="";
		int bracket=0;
		int start=0;
		int end=0;
		String anotherBrackets = "";
		if(brackets.equals("(")){
			anotherBrackets=")";
		}else if(brackets.equals("{")){
			anotherBrackets="}";
		}else if(brackets.equals("[")){
			anotherBrackets="]";
		}
		ArrayList arrayListResult = new ArrayList();
		boolean found = false;
		for(int i1=i;i1<arrayList.size();i1++){
			temp = String.valueOf(arrayList.get(i1));
			if(temp.equals(brackets)){
				bracket++;
				if(!found){
					start=i1;
					found = true;
				}
				arrayListResult.add(temp);
				continue;
			}else if(temp.equals(anotherBrackets)){
				bracket--;
				if(bracket==0){
					end=i1;
					arrayListResult.add(temp);
					break;
				}
			}
			if(found){
				arrayListResult.add(temp);
			}
		}
		arrayListResult.add(start);
		arrayListResult.add(end);
		return arrayListResult;
	}
	public ArrayList addArrayListToArrayList1(ArrayList arrayList,ArrayList addArrayList){
		String temp="";
		for(int i=0;i<addArrayList.size();i++){
			temp=String.valueOf(addArrayList.get(i));
			arrayList.add(temp);
		}
		return arrayList;
	}
	public ArrayList addArrayListToArrayList2(ArrayList arrayList,ArrayList addArrayList,int j){
		String temp="";
		for(int i=0;i<addArrayList.size();i++){
			temp=String.valueOf(addArrayList.get(i));
			arrayList.add(j,temp);
			j++;
		}
		return arrayList;
	}
	public ArrayList arrayListRemove1(ArrayList arrayList,int start,int end){
		String temp="";
		for(int i=start;i<end+1;i++){
			arrayList.remove(start);
		}
		return arrayList;
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
	public ArrayList bracesMatching(ArrayList arrayList,int i1,int j1,String brackets) {
		ArrayList arrayListResult = new ArrayList();
		ArrayList nextWord = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListTemp1 = new ArrayList();
		ArrayList arrayListTemp2 = new ArrayList();
		String temp="";
		int bracketNum = 0;
		ArrayList b =  findNextWord2(arrayList,i1,j1);
		String bracket =String.valueOf(b.get(0));
		int startI=Integer.parseInt(String.valueOf(b.get(1)));
		int startJ=Integer.parseInt(String.valueOf(b.get(2)));
		String anotherBrcakets="";
		if(bracket.equals(brackets)) {
			if(bracket.equals("(")) {
				anotherBrcakets = ")";
			}
			if(bracket.equals("{")) {
				anotherBrcakets = "}";
			}
			arrayListResult.add(brackets);
			bracketNum=1;
			for(int i=startI;i<arrayList.size();i++) {
				arrayListTemp = (ArrayList) arrayList.get(i);
				int j=0;
				if(i==startI) {
					j=startJ+1;
				}else {
					j=0;
				}
				for(;j<arrayListTemp.size();j++) {
					temp = String.valueOf(arrayListTemp.get(j));
					if(temp.equals(anotherBrcakets)) {
						bracketNum--;
						arrayListResult.add(temp);
						if(bracketNum==0) {
							arrayListResult.add(startI);
							arrayListResult.add(startJ);
							arrayListResult.add(i);
							arrayListResult.add(j);
							return arrayListResult;
						}
					}else {
						if(temp.equals(brackets)) {
							bracketNum++;
						}
						arrayListResult.add(temp);
					}
				}
			}
		}else {
			for(int i=startI;i<arrayList.size();i++) {
				arrayListTemp = (ArrayList) arrayList.get(i);
				int j=0;
				if(i==startI) {
					j=startJ;
				}else {
					j=0;
				}
				for(;j<arrayListTemp.size();j++) {
					temp = String.valueOf(arrayListTemp.get(j));
					if(temp.equals("{")) {
						if(j==0) {
							arrayListTemp1 = (ArrayList) arrayList.get(i-1);
							arrayListTemp2 = bracesMatching(arrayList,i-1,arrayListTemp1.size()-1,"{");
						}else {
							arrayListTemp2 = bracesMatching(arrayList,i,j-1,"{");
						}
						for(int z=0;z<arrayListTemp2.size()-4;z++) {
							arrayListResult.add(String.valueOf(arrayListTemp2.get(z)));
						}
						arrayListResult.add(startI);
						arrayListResult.add(startJ);
						arrayListResult.add(String.valueOf(arrayListTemp2.get(arrayListTemp2.size()-2)));
						arrayListResult.add(String.valueOf(arrayListTemp2.get(arrayListTemp2.size()-1)));
						return arrayListResult;
					}else if(temp.equals("(")){
						if(j==0) {
							arrayListTemp1 = (ArrayList) arrayList.get(i-1);
							arrayListTemp2 = bracesMatching(arrayList,i-1,arrayListTemp1.size()-1,"(");
						}else {
							arrayListTemp2 = bracesMatching(arrayList,i,j-1,"(");
						}
						for(int z=0;z<arrayListTemp2.size()-4;z++) {
							arrayListResult.add(String.valueOf(arrayListTemp2.get(z)));
						}
						i=Integer.parseInt(String.valueOf(arrayListTemp2.get(arrayListTemp2.size()-2)));
						j=Integer.parseInt(String.valueOf(arrayListTemp2.get(arrayListTemp2.size()-1)));
						arrayListTemp = (ArrayList) arrayList.get(i);
					}else if(temp.equals(";")){
						arrayListResult.add(temp);
						arrayListResult.add(startI);
						arrayListResult.add(startJ);
						arrayListResult.add(i);
						arrayListResult.add(j);
						return arrayListResult;
					}else {
						arrayListResult.add(temp);
					}
				}
			}
		}
		return arrayListResult;
	}
	public ArrayList bracesMatching1(ArrayList arrayList,int i1){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		String temp1="";
		String nextWord = "";
		String temp="";
		int braces=0;
		int start=0;
		boolean found=false;
		for(int i=i1+1;i<arrayList.size();i++){
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals(" ")){
				continue;
			}else{
				if(temp.length()<2){
					i1=i;
					nextWord=temp;
					break;
				}else if(temp.charAt(0)=='/'){
					if(temp.charAt(1)=='*'||temp.charAt(1)=='/'){
						continue;
					}
				}
			}
		}
		arrayListResult.add(nextWord);
		start=i1;
		if(nextWord.equals("{")){
			braces++;
			for(int i=i1+1;i<arrayList.size();i++){
				temp = String.valueOf(arrayList.get(i));
				if(temp.equals("}")){
					braces--;
					if(braces==0){
						arrayListResult.add(temp);
						arrayListResult.add(start);
						arrayListResult.add(i);
						return arrayListResult;
					}
				}else if(temp.equals("{")){
					arrayListResult.add(temp);
					braces++;
				}else{
					arrayListResult.add(temp);
				}
			}
		}else{
			for(int i=i1+1;i<arrayList.size();i++){
				temp = String.valueOf(arrayList.get(i));
				if(temp.equals("{")){
					arrayListTemp=bracesMatching1(arrayList,i1-1);
					for(int z=0;z>arrayListTemp.size()-2;z++){
						temp = String.valueOf(arrayListTemp.get(z));
						arrayListResult.add(temp);
					}
					arrayListResult.add(start);
					arrayListResult.add(i);
					return arrayListResult;
				}else if(temp.equals(";")){
					arrayListResult.add(temp);
					arrayListResult.add(start);
					arrayListResult.add(i);
					return arrayListResult;
				}else{
					arrayListResult.add(temp);
				}
			}
		}
		return arrayListResult;
	}
	public ArrayList findNextWord2(ArrayList arrayList,int i1,int j1) {
		ArrayList result = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		String temp="";
		for(int i=i1;i<arrayList.size();i++) {
			arrayListTemp = (ArrayList) arrayList.get(i);
			int j=0;
			if(i==i1) {
				j=j1+1;
			}else {
				j=0;
			}
			for(;j<arrayListTemp.size();j++) {
				temp = String.valueOf(arrayListTemp.get(j));
				if(!temp.equals("")) {
					if(temp.length()>=2){
						if(temp.charAt(0)=='/'&&(temp.charAt(0)=='/'||temp.charAt(0)=='*')){
							
						}else{
							result.add(temp);
							result.add(i);
							result.add(j);
							return result;
						}
					}else{
						result.add(temp);
						result.add(i);
						result.add(j);
						return result;
					}
				}
			}
		}
		return result;
	}
}
