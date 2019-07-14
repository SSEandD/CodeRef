package javapgb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class EnglishRecognition {
	WordStack ws= new WordStack(100);
	int i1=0;
	public int getI1() {
		return i1;
	}
	public void setI1(int i1) {
		this.i1 = i1;
	}
	/*输入为一个字符串（类名之类）,type为类型，0为小驼峰，1为大驼峰
	 * 输出为变成小驼峰的字符串
	 */
	public String englishWordRecognition(String s,int type){
		if(hasOther(s)){
			return s;
		}else{
			int k = Hybrid(s);
			if(k==0||k==1){
				if(type==1){
					return s;
				}else if(type==0){
					String s1=s.toLowerCase();
					char x=s1.charAt(0);
					s=x+s.substring(1,s.length());
					return s;
				}
			}else if(k==2){
				if(type==1){
					String s1=s.toUpperCase();
					char x=s1.charAt(0);
					s=x+s.substring(1,s.length());
					return s;
				}else if(type==0){
					return s;
				}
			}
		}
		String initials;//首字母
		String result="";
		String path1;
		String path2;
		String path;
		String line="";
		char c1=' ';
		char c2=' ';
		int z=0;
		String temp="";//用于存放下一个无法匹配成功，上一个要取出来的单词
		String temp1="";//用于存放删掉最后一个字母的temp字符串
		int length=s.length();
		for(int i=0;i<length;i++){
			//进来先识别首字母
			initials=s.substring(i,i+1);
			path1 = "src/standardName/word/";
			path2=".txt";
			path=path1+initials+path2;
			File filename = new File(path);
			//开始读取对应首字母txt
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
				BufferedReader br = new BufferedReader(reader);
				boolean isPair;//是否配对成功
				boolean isEntry=false;
				//读取
				while((line=br.readLine())!=null){
					//如果单词比字符串剩余长度还短就跳过
					if(line.length()>length-i){
						continue;
					}
					if(i!=length-1&&line.length()!=1){
						//当第二个字符（c）大过要匹对的(b)时则直接跳出循环
						if((int)s.charAt(i+1)<(int)line.charAt(1)){
							if(z==0)//i被改变了
							break;
						}
						//当第二个字符(b)小过要匹配的(c)时直接跳过到下一个
						else if((int)s.charAt(i+1)>(int)line.charAt(1)){
							continue;
						}
					}
					int j=i;
					int k=0;
					isPair=true;
					for(j=i+1,k=1;j<s.length()&&k<line.length();j++,k++){
						c1=s.charAt(j);
						c2=line.charAt(k);
						if(c1!=c2){
							isPair=false;
							break;
						}
					}
					if(isPair){
						//后面选出来的必比之前进入的长，则只要选到就前出后进
						if(!isEntry){
							if(!ws.isFull()){
								ws.push(line);
								isEntry=true;
							}
						}else{
							ws.pop();
							ws.push(line);
						}
					}
				}
				//若未能匹对上
				if(!isEntry){
					//检查是否有入栈，无入栈为一个单词，无法识别，退出
					if(ws.isEmpty()){
						break;
					//有入栈则取出，去掉最后一个字母并配对，若有一样的单词则入栈继续，若删完字母则在返回上一级取元素
					}else{
						//当栈不为空时取出元素
						while(!ws.isEmpty()){
							temp=ws.pop();
							//若单词只有一个字母则取前一个元素
							i=i-temp.length();
							if(temp.length()==1){
								continue;
							}else{
								String pathTemp=path1+temp.substring(0,1)+path2;
								File flienameTemp=new File(pathTemp);
								//是否有配对的单词并且字符短一些
								temp1=hasPairWord(temp,flienameTemp);
								//若无匹配
								if(temp1.equals("")){
									continue;
								}else{
									ws.push(temp1);
									i=i+temp1.length()-1;
									break;
								}
							}
						}
					}
				}else{
					i=i+ws.peek().length()-1;
				}
			}catch (Exception e) {
				return s;
			}
		}
	if(ws.isEmpty()){
		result=s;
	}
	while(!ws.isEmpty()){
		temp=ws.pop();
		if(!ws.isEmpty()){
			temp=firstToUpper(temp);
		}else if(type==1){
			temp=firstToUpper(temp);
		}
		result=temp+result;
	}
	return result;
	}
	public String hasPairWord(String s,File filename){
		String result="";
		String line="";
		char c1=' ';
		char c2=' ';
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			//读取
			while((line=br.readLine())!=null){
				//如果单词比字符串剩余长度还长就跳过
				if(line.length()>s.length()){
					continue;
				}
				if(line.equals(s)){
					break;
				}
				int j=0;
				int k=0;
				for(j=1,k=1;j<s.length()&&k<line.length();j++,k++){
					c1=s.charAt(j);
					c2=line.charAt(k);
					if(c1!=c2){
						break;
					}
				}
				if(k==line.length()){
					result=line;
				}
			}
		}catch (Exception e) {
			//e.printStackTrace();
		}
		return result;
	}
	public boolean isAllUpper(String s){
		boolean t=true;
		char c=' ';
		for(int i=0;i<s.length();i++){
			c=s.charAt(i);
			if((int)c<=90&&(int)c>=65){
				t=false;
			}
		}
		return t;
	}
	public String firstToUpper(String s){
		String result="";
		char c=s.charAt(0);
		result=(char)((int)c-32)+s.substring(1,s.length());
		return result;
	}
	public int Hybrid(String s){//0：开头大写后面有小写1：全大写 2:开头小写后面有大写，3：全小写
		int t= -1;
		char c=s.charAt(0);
		if((int)c<=90&&(int)c>=65){//开头大写字母
			for(int i=1;i<s.length();i++){
				c=s.charAt(i);
				if((int)c<=122&&(int)c>=97){
					return 0;
				}
			}
			return 1;
		}else if((int)c<=122&&(int)c>=97){
			for(int i=1;i<s.length();i++){
				c=s.charAt(i);
				if((int)c<=90&&(int)c>=65){
					return 2;
				}
			}
			return 3;
		}
		return t;
	}
	public boolean hasOther(String s){
		boolean t= true;
		char c=' ';
		for(int i=0;i<s.length();i++){
			c=s.charAt(i);
			if(((int)c<=90&&(int)c>=65)&&((int)c<=122&&(int)c>=97)){
				continue;
			}else{
				return false;
			}
		}
		return t;
	}
}
