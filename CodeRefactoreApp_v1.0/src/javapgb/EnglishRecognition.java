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
	/*����Ϊһ���ַ���������֮�ࣩ,typeΪ���ͣ�0ΪС�շ壬1Ϊ���շ�
	 * ���Ϊ���С�շ���ַ���
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
		String initials;//����ĸ
		String result="";
		String path1;
		String path2;
		String path;
		String line="";
		char c1=' ';
		char c2=' ';
		int z=0;
		String temp="";//���ڴ����һ���޷�ƥ��ɹ�����һ��Ҫȡ�����ĵ���
		String temp1="";//���ڴ��ɾ�����һ����ĸ��temp�ַ���
		int length=s.length();
		for(int i=0;i<length;i++){
			//������ʶ������ĸ
			initials=s.substring(i,i+1);
			path1 = "src/standardName/word/";
			path2=".txt";
			path=path1+initials+path2;
			File filename = new File(path);
			//��ʼ��ȡ��Ӧ����ĸtxt
			try {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
				BufferedReader br = new BufferedReader(reader);
				boolean isPair;//�Ƿ���Գɹ�
				boolean isEntry=false;
				//��ȡ
				while((line=br.readLine())!=null){
					//������ʱ��ַ���ʣ�೤�Ȼ��̾�����
					if(line.length()>length-i){
						continue;
					}
					if(i!=length-1&&line.length()!=1){
						//���ڶ����ַ���c�����Ҫƥ�Ե�(b)ʱ��ֱ������ѭ��
						if((int)s.charAt(i+1)<(int)line.charAt(1)){
							if(z==0)//i���ı���
							break;
						}
						//���ڶ����ַ�(b)С��Ҫƥ���(c)ʱֱ����������һ��
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
						//����ѡ�����ıر�֮ǰ����ĳ�����ֻҪѡ����ǰ�����
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
				//��δ��ƥ����
				if(!isEntry){
					//����Ƿ�����ջ������ջΪһ�����ʣ��޷�ʶ���˳�
					if(ws.isEmpty()){
						break;
					//����ջ��ȡ����ȥ�����һ����ĸ����ԣ�����һ���ĵ�������ջ��������ɾ����ĸ���ڷ�����һ��ȡԪ��
					}else{
						//��ջ��Ϊ��ʱȡ��Ԫ��
						while(!ws.isEmpty()){
							temp=ws.pop();
							//������ֻ��һ����ĸ��ȡǰһ��Ԫ��
							i=i-temp.length();
							if(temp.length()==1){
								continue;
							}else{
								String pathTemp=path1+temp.substring(0,1)+path2;
								File flienameTemp=new File(pathTemp);
								//�Ƿ�����Եĵ��ʲ����ַ���һЩ
								temp1=hasPairWord(temp,flienameTemp);
								//����ƥ��
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
			//��ȡ
			while((line=br.readLine())!=null){
				//������ʱ��ַ���ʣ�೤�Ȼ���������
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
			e.printStackTrace();
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
	public int Hybrid(String s){//0����ͷ��д������Сд1��ȫ��д 2:��ͷСд�����д�д��3��ȫСд
		int t= -1;
		char c=s.charAt(0);
		if((int)c<=90&&(int)c>=65){//��ͷ��д��ĸ
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
