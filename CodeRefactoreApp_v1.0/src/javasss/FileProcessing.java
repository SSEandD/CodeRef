package javasss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileProcessing {
	
	/**
	 * 文件读取到缓冲区
	 * @param path 文件路径
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
	 * 追加方式写文件
	 */
	public static boolean writeFile(String path,String args) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(args);
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	/**
	 * 清空文件
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
	
	/** 字符连接*/
	public static String concat(String str, Character ch) {
		str += ch;
		return str;
	}

}
