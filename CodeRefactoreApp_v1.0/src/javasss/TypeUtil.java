package javasss;

/**
 * 对读取字符分类相关操作
 */
public class TypeUtil {
	
	
	private final String keyWords[] = { "abstract", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do",
			"double", "else", "extends", "final", "finally", "float", "for",
			"if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "package", "private", "protected",
			"public", "return", "short", "static", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "try",
			"void", "volatile", "while","strictfp","enum","goto","const","assert" }; // 关键字数组
	private final char operators[] = { '+', '-', '*', '/', '=', '>', '<', '&', '|',
			'!' }; // 运算符数组
	private final char separators[] = { ',', ';', '{', '}', '(', ')', '[', ']', '_',
			':', '.', '"','\\'}; // 界符数组
	
	private final String exKeyWords[] = {
			"if","else","try","catch","finally"
	};
	
	/**
	 * 判断是否为字母
	 * @param ch 需判断的字符
	 * @return boolean
	 */
	public boolean isLetter(char ch) {
		return Character.isLetter(ch);
	}

	/**
	 * 判断是否为数字
	 * @param ch 需判断的字符
	 * @return boolean
	 */
	public boolean isDigit(char ch) {
		return Character.isDigit(ch);
	}
	/**
	 * 判断是否为关键字
	 * @param s 需判断的字符串
	 * @return boolean
	 */
	public boolean isKeyWord(String s) {
		for (int i = 0; i < keyWords.length; i++) {
			if (keyWords[i].equals(s))
				return true;
		}
		return false;
	}

	/**
	 * 判断是否为例外关键字
	 * @param s 需判断的字符串
	 * @return boolean
	 */
	public boolean isExKeyWord(String s) {
		for (int i = 0; i < exKeyWords.length; i++) {
			if (exKeyWords[i].equals(s))
				return true;
		}
		return false;
	}

	/**
	 * 判断是否为运算符
	 * @param ch 需判断的字符
	 * @return boolean
	 */
	public boolean isOperator(char ch) {
		for (int i = 0; i < operators.length; i++) {
			if (ch == operators[i])
				return true;
		}
		return false;
	}

	/**
	 * 判断是否为分隔符
	 * @param ch 需判断的字符
	 * @return boolean
	 */
	public boolean isSeparators(char ch) {
		for (int i = 0; i < separators.length; i++) {
			if (ch == separators[i])
				return true;
		}
		return false;
	}


}
