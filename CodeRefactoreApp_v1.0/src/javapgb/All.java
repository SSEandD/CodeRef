package javapgb;

import java.util.ArrayList;

public class All {
	/* b1 for-while
	 * b2 while-for
	 * b3 if-switch
	 * b4 switch-if
	 * b5 ifs-if
	 * b6 if-ifs
	 */
	public ArrayList all(ArrayList<String> lines,boolean b1,boolean b2,boolean b3,boolean b4,boolean b5,boolean b6){
		ArrayList arrayList= new ArrayList();
		ArrayList arrayListTemp= new ArrayList();
		WordAnalysis wa = new WordAnalysis();
		for(int i=0;i<lines.size();i++){
			arrayList=wa.wordAnalysis(arrayList,lines.get(i));
		}
		StandardName standardName = new StandardName();
		arrayList = standardName.standardName(arrayList);
		if(b1){
			ForToWhile transformation1 = new ForToWhile();
			arrayList = transformation1.transformation(arrayList);
		}
		if(b2){
			WhileToFor transformation2 = new WhileToFor();
			arrayList = transformation2.transformation(arrayList);
		}
		if(b3){
			SwitchToIf transformation3 = new SwitchToIf();
			arrayList = transformation3.transformation(arrayList);
		}
		if(b4){
			IfToSwitch transformation4 = new IfToSwitch();
			arrayList = transformation4.transformation(arrayList);
		}
		if(b5){
			IfsToIf transformation5 = new IfsToIf();
			arrayList = transformation5.transformation(arrayList);
		}
		try{
		if(b6){
			IfToIfs transformation6 = new IfToIfs();
			arrayList = transformation6.transformation(arrayList);
		}
		}catch(Exception e){
			System.out.println(e);
		}
		return arrayList;
	}

}
