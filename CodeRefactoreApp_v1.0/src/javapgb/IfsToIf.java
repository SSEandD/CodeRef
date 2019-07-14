package javapgb;

import java.util.ArrayList;

public class IfsToIf {
	GeneralMethod generalMethod = new GeneralMethod();
	public ArrayList transformation(ArrayList arrayList){
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTempBraces = new ArrayList();
		ArrayList arrayListTempBraces1 = new ArrayList();
		ArrayList arrayListTempParentheses = new ArrayList();
		ArrayList arrayListTempParentheses1 = new ArrayList();
		ArrayList arrayListIf = new ArrayList();
		ArrayList arrayListInBraces = new ArrayList();
		ArrayList arrayListInBracesDeleteOther = new ArrayList();
		int endI = 0;
		int endJ = 0 ;
		String temp="";
		String temp1="";
		String nextWord = "";
		ArrayList nextTwoWords = new ArrayList();
		for(int i=0;i<arrayList.size();i++){
			arrayListTemp = (ArrayList) arrayList.get(i);
			for(int j=0;j<arrayListTemp.size();j++){
				temp = String.valueOf(arrayListTemp.get(j));
				if(temp.equals("if")){
					//��{}
					ArrayList al = new ArrayList();
					ArrayList alt = new ArrayList();
					al=generalMethod.returnBracketsMatching(arrayList, i, j, "(");
					endI=Integer.parseInt(String.valueOf(al.get(al.size()-2)));
					endJ=Integer.parseInt(String.valueOf(al.get(al.size()-1)));
					alt = (ArrayList) arrayList.get(endI);
					if(endJ==alt.size()-1){
						endI++;
						endJ=0;
					}else{
						endJ++;
					}
					arrayListTempBraces = generalMethod.bracesMatching(arrayList, endI, endJ,"{");
					if(!onlyIf(arrayListTempBraces)){
						continue;
					}
//					System.out.println("arrayListTempBraces="+arrayListTempBraces);
					endI=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
					endJ=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
					nextWord = findNextWord(arrayList,endI,endJ);
					//һֱ�����ң�ֱ��û��ifΪֹ
					while(nextWord.equals("if")){
						al=generalMethod.returnBracketsMatching(arrayList, i, j, "(");
						endI=Integer.parseInt(String.valueOf(al.get(al.size()-2)));
						endJ=Integer.parseInt(String.valueOf(al.get(al.size()-1)));
						if(endJ==alt.size()-1){
							endI++;
							endJ=0;
						}else{
							endJ++;
						}
						arrayListTempBraces = generalMethod.bracesMatching(arrayList, endI, endJ,"{");
//						System.out.println("arrayListTempBraces="+arrayListTempBraces);
						endI=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-2)));
						endJ=Integer.parseInt(String.valueOf(arrayListTempBraces.get(arrayListTempBraces.size()-1)));
						nextWord = findNextWord(arrayList,endI,endJ);
					}
					//ȡ��if���ΪArrayList
					arrayListIf = getIfArrayList(arrayList,i,j,endI,endJ);
					//��������{}�ڲ������Ƿ�һ�£���һ��������һ�����if
//					System.out.println("i="+i);
//					System.out.println("arrayListIf111="+arrayListIf);
					arrayListInBraces = getAllInBraces(arrayListIf);
					System.out.println("arrayListInBraces111="+arrayListInBraces);
					if(arrayListInBraces.size()==1&&String.valueOf(arrayListInBraces.get(0)).equals("false")){
						arrayListTemp = (ArrayList) arrayList.get(endI);
						j=endJ;
						break;
					}
					//ɾ����Ӧ�±�����Ȼ�����ȡ������{}����
					else{
						for(int z = arrayListInBraces.size()-1;;z=z-2){
							temp1 = String.valueOf(arrayListInBraces.get(z));
							//���պ�����±����ɾ��ԭ�б������{}����
							if(!temp1.equals("}")){
								int start = Integer.parseInt(String.valueOf(arrayListInBraces.get(z-1)));
								int end = Integer.parseInt(temp1);
//								System.out.println("arrayListIf11111="+arrayListIf);
								arrayListIf=arrayListRemove(arrayListIf,start,end);
//								System.out.println("arrayListIf11111="+arrayListIf);
							}
							//ȡ��{}����
							else if(temp1.equals("}")){
//								System.out.println("arrayListInBraces1="+arrayListInBraces);
								for(int z1=1;z1<z;z1++){
									arrayListInBracesDeleteOther.add(String.valueOf(arrayListInBraces.get(z1)));
								}
								break;
							}
						}
						arrayListResult=ifsToIf(arrayListIf);
						arrayListResult.add(0,"if");
						arrayListResult.add(arrayListResult.size(),"{");
						arrayListResult=addArrayListToArrayList(arrayListResult,arrayListResult.size(),arrayListInBracesDeleteOther);
						arrayListResult.add(arrayListResult.size(),"}");
						arrayList = generalMethod.arrayListRemove(arrayList, i, j, endI, endJ);
						arrayList = generalMethod.arrayListAdd(arrayList, i, j, arrayListResult, 0);
						int size = arrayListResult.size();
						if(i==endI){
							arrayListTemp = (ArrayList) arrayList.get(i);
							j=endJ-j+size;
						}else{
							for(int k=i+1;k<endI;k++){
								arrayList.remove(i+1);
							}
							i++;
							arrayListTemp = (ArrayList) arrayList.get(i);
							j=-1;
						}
					}
					
				}
			}
		}
		return arrayList;
	}
	/*����if��
	 * ���ת�����if��
	*/
	public ArrayList ifsToIf(ArrayList arrayListIf){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListParentheses = new ArrayList();
		ArrayList arrayListBraces = new ArrayList();
		String temp="";
		String temp1="";
		int sumIf = 0;
		int endI=0;
		for(int i=0;i<arrayListIf.size();i++){
			temp = String.valueOf(arrayListIf.get(i));
			if(temp.equals("if")){
				sumIf++;
				if(sumIf>=2){
					arrayListResult.add("|");
					arrayListResult.add("|");
				}
				//ȡ��()���ݲ�ɾ��(   )
				arrayListParentheses = generalMethod.returnBracketsMatching1(arrayListIf, i, "(");
				arrayListParentheses = generalMethod.deleteSurplus1(arrayListParentheses);
				arrayListParentheses.remove(0);
				arrayListParentheses.remove(arrayListParentheses.size()-1);
				//���ϣ������
				arrayListResult = addArrayListToArrayList(arrayListResult, arrayListResult.size(), arrayListParentheses);
				//ȡ���ڲ�{}if
				arrayListBraces=generalMethod.bracesMatching1(arrayListIf, i);
				endI = Integer.parseInt(String.valueOf(arrayListBraces.get(arrayListBraces.size()-1)));
				arrayListBraces=generalMethod.deleteSurplus1(arrayListBraces);
				//�����ڲ�{}if
				if(hasIf(arrayListBraces)){
					arrayListResult.add("&");
					arrayListResult.add("&");
					arrayListResult = generalMethod.addArrayListToArrayList(arrayListResult, arrayListResult.size(), ifsToIf(arrayListBraces));
				}
				i=endI;
			}
		}
		arrayListResult.add(arrayListResult.size(),")");
		arrayListResult.add(0,"(");
		return arrayListResult;
	}
	public boolean hasIf(ArrayList arrayList){
		boolean t = false;
		String temp ="";
		for(int i=0;i<arrayList.size();i++){
			temp =String.valueOf(arrayList.get(i));
			if(temp.equals("if")){
				t=true;
				break;
			}
		}
		return t;
	}
	/*���뵱ǰ�±���б�
	 * ������һ����Ϊ�ղ�Ϊ�ո��ַ�
	 */
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
	/*����arrayList
	 * ȡ����Ӧ�±������[if��]����άתһά
	 */
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
	/*����arrayList
	 * �����Ӧ�±�������
	 */
	public ArrayList getArrayList(ArrayList arrayList,int start,int end){
		ArrayList arrayListResult = new ArrayList();
		String temp="";
		for(int i=start;i<end+1;i++){
			temp = String.valueOf(arrayList.get(i));
			arrayListResult.add(temp);
		}
		return arrayListResult;
	}
	/*����if��
	 * ������ת�������{}����ArrayList,��ǰ��λ�ֱ�ΪҪɾ�����������ŵ��±�;���򷵻�[false]
	 */
	public ArrayList getAllInBraces(ArrayList arrayList){
		ArrayList arrayListResult = new ArrayList();
		ArrayList arrayListTemp = new ArrayList();
		ArrayList arrayListBraces1 = new ArrayList();
		ArrayList arrayListBraces2 = new ArrayList();
		int braces = 0 ;
		int bracesL = 0 ;
		int bracesR = 0;
		String temp="";
		String temp1="";
		int  time = 0;
		boolean isBreak= false;
		boolean canBeChange= true;
		boolean isFirstDelete = true;
		for(int i=0;i<arrayList.size();i++){
			if(!canBeChange){
				break;
			}
			temp = String.valueOf(arrayList.get(i));
			if(temp.equals("if")){
				isFirstDelete = true;
				for(int j=i+1;j<arrayList.size();j++){
					temp1=String.valueOf(arrayList.get(j));
					if(temp1.equals("{")){
						braces++;
						bracesL=j;
					}
					if(temp1.equals("}")){
						braces--;
						if(isFirstDelete){
							bracesR=j;
							arrayListResult.add(arrayListResult.size(),bracesL);
							arrayListResult.add(arrayListResult.size(),bracesR);
							if(time==0){
								arrayListBraces1=getArrayList(arrayList,bracesL,j);
								time++;
							}else{
								arrayListBraces2=getArrayList(arrayList,bracesL,j);
								time++;
							}
							isFirstDelete=false;
						}
						if(braces==0){
							isBreak = true;
							i=j;
						}
					}
					//������������ڲ��������ˣ��ͱȽ��Ƿ����
					if(time>=2){
						if(!arrayListEquals(arrayListBraces1,arrayListBraces2)){
							canBeChange = false;
							isBreak = true;
						}
					}
					if(isBreak){
						isBreak=false;
						break;
					}
				}
			}
		}
		if(!canBeChange){
			ArrayList result = new ArrayList();
			result.add("false");
			return result;
		}
		arrayListResult = addArrayListToArrayList(arrayListResult, 0, arrayListBraces1);
		return arrayListResult;
	}
	public ArrayList arrayListRemove(ArrayList arrayList,int start,int end){
		String temp="";
		for(int i=start+1;i<end;i++){
			arrayList.remove(start+1);
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
	public ArrayList addArrayListToArrayList(ArrayList arrayList,int i,ArrayList addArrayList){
		String temp="";
		for(int j=0;j<addArrayList.size();j++){
			temp = String.valueOf(addArrayList.get(j));
			arrayList.add(i,temp);
			i++;
		}
		return arrayList;
	}
	public boolean onlyIf(ArrayList arrayList){
		boolean t=false;
		String temp="";
		int endI=0;
		ArrayList arrayListTemp = new ArrayList();
		for(int i=0;i<arrayList.size();i++){
			temp=String.valueOf(arrayList.get(i));
			if(temp.equals("if")){
				arrayListTemp = generalMethod.returnBracketsMatching1(arrayList, i, "(");
				endI=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				arrayListTemp = generalMethod.bracesMatching1(arrayList, endI);
				endI=Integer.parseInt(String.valueOf(arrayListTemp.get(arrayListTemp.size()-1)));
				i=endI;
			}else{
				return false;
			}
		}
		return t;
	}
}
