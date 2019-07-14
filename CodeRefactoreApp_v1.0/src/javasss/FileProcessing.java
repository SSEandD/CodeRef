package javasss;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileProcessing {
	
	/**
	 * �ļ���ȡ��������
	 * @param path �ļ�·��
	 */
	public static ArrayList<String> readFile(String path) {
		ArrayList<String> line = new ArrayList<String>();
		File file = new File(path);
		InputStreamReader read = null;
		try {
//			FileReader fileReader = new FileReader(path);
			read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(read);
			String temp;
			while ((temp = br.readLine()) != null) {
				line.add(temp);
			}
			br.close();
			return line;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * ׷�ӷ�ʽд�ļ�
	 */
	public static boolean writeFile(String path,String args) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			//FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			bw.write(args);
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * ����ļ�
	 */
	public static boolean clearFile(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/** �ַ�����*/
	public static String concat(String str, Character ch) {
		str += ch;
		return str;
	}

}
