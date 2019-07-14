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
		WordAnalysis wa = new WordAnalysis();
		for(int i=0;i<lines.size();i++){
			arrayList=wa.wordAnalysis(arrayList,lines.get(i));
		}
		StandardName standardName = new StandardName();
		arrayList = standardName.standardName(arrayList);
		try {
			if(b1){
				Transformation1 transformation1 = new Transformation1();
				arrayList = transformation1.transformation(arrayList);
			}
			if(b2){
				Transformation2 transformation2 = new Transformation2();
				arrayList = transformation2.transformation(arrayList);
			}
			if(b3){
				Transformation3 transformation3 = new Transformation3();
				arrayList = transformation3.transformation(arrayList);
			}
			if(b4){
				Transformation4 transformation4 = new Transformation4();
				arrayList = transformation4.transformation(arrayList);
			}
			if(b5){
				Transformation5 transformation5 = new Transformation5();
				arrayList = transformation5.transformation(arrayList);
			}
			if(b6){
				Transformation6 transformation6 = new Transformation6();
				arrayList = transformation6.transformation(arrayList);
			}
		}catch (Exception e){
			//e.printStackTrace();
		}
		return arrayList;
	}

}
