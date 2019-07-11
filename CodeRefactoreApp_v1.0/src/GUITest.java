import javahyh.CodeFile;
import javahyh.lexical.LexicalAnalysis;
import javapgb.All;
import javasss.BlankCharacter;
import javasss.FileProcessing;
import javasss.OrderBlock;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JSplitPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class GUITest {

	private JFrame frame;
	private JTextField sourceFile;

	String path="";//路径
	String result="";//最后输出结果
	String extensionName="";//文件后缀名
	String className="";//文件名字
	String downloadpath="";//下载路径
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITest window = new GUITest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUITest() {
		initialize();
		JOptionPane.showMessageDialog(null, "1.请选择java/txt文件上传 \n2.请确保选择的源文件可以通过编译\n3.可实现大驼峰及小驼峰命名");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//整体框架
		frame.setBackground(new Color(128, 128, 128));
		frame.setTitle("代码结构及流程重构系统");
		frame.setResizable(false);
		frame.setBounds(100, 100, 1217, 715);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel choosePath = new JPanel();//选择路径模块
		choosePath.setToolTipText("");
		choosePath.setBorder(null);
		
		sourceFile = new JTextField();//存放读取文件路径
		sourceFile.setColumns(10);
		
		
		//定义选择按钮
		JButton btnBrowse = new JButton("选择");
		btnBrowse.setBackground(new Color(220, 220, 220));
		
		JLabel label = new JLabel("请选择需要处理的源文件");
		GroupLayout gl_choosePath = new GroupLayout(choosePath);
		gl_choosePath.setHorizontalGroup(
			gl_choosePath.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_choosePath.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_choosePath.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_choosePath.createSequentialGroup()
							.addComponent(sourceFile, GroupLayout.PREFERRED_SIZE, 529, GroupLayout.PREFERRED_SIZE)
							.addGap(35)
							.addComponent(btnBrowse))
						.addComponent(label))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		gl_choosePath.setVerticalGroup(
			gl_choosePath.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_choosePath.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addGroup(gl_choosePath.createParallelGroup(Alignment.BASELINE)
						.addComponent(sourceFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addGap(19))
		);
		choosePath.setLayout(gl_choosePath);
		
		//展示文本模块
		JPanel showText = new JPanel();  
		JButton btnChangeButton = new JButton("转换");
		btnChangeButton.setBackground(Color.LIGHT_GRAY);

		btnChangeButton.setBounds(528, 309, 100, 23);
		showText.setLayout(null);
		showText.add(btnChangeButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(665, 20, 500, 510);
		showText.add(scrollPane_1);
		
		JTextArea newText = new JTextArea();//存放已处理内容
		newText.setEditable(false);
		scrollPane_1.setViewportView(newText);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 20, 500, 510);
		showText.add(scrollPane);
		
		JTextArea oldText = new JTextArea();//存放未处理的文件内容
		scrollPane.setViewportView(oldText);
		oldText.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JLabel lblNewLabel = new JLabel("相关信息");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "1.请选择java/txt文件上传 \n2.请确保选择的源文件可以通过编译\n3.可实现大驼峰及小驼峰命名");
			}
		});
		lblNewLabel.setIcon(new ImageIcon(GUITest.class.getResource("icon.png")));
		
		JButton btnExit = new JButton("退出");
		btnExit.setBackground(new Color(220, 220, 220));
		
		JButton btnDownload = new JButton("下载");
		
		btnDownload.setBackground(new Color(220, 220, 220));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 850, Short.MAX_VALUE)
					.addComponent(btnDownload)
					.addGap(18)
					.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnExit)
							.addComponent(btnDownload))
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addComponent(showText, GroupLayout.PREFERRED_SIZE, 1165, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(choosePath, GroupLayout.PREFERRED_SIZE, 673, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(518, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(choosePath, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(showText, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		);
		//确认框定义代码
		JCheckBox f_wbtn = new JCheckBox("for 转 while");
		f_wbtn.setBounds(525, 84, 103, 23);
		showText.add(f_wbtn);
		
		JCheckBox w_fbtn = new JCheckBox("while 转 for");
		//复选框状态改变事件
		f_wbtn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent es) {
				JCheckBox checkBox = (JCheckBox) es.getSource();
				if(checkBox.isSelected()) {
					//这里是指被选中了！！！！！！！！！
					w_fbtn.setSelected(false);
				}
			}
		});
		//监听事件
		w_fbtn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent es) {
				JCheckBox checkBox = (JCheckBox) es.getSource();
				if(checkBox.isSelected()) {
					//这里是指被选中了！！！！！！！！！
					f_wbtn.setSelected(false);
				}
			}
		});
		w_fbtn.setBounds(525, 109, 103, 23);
		showText.add(w_fbtn);
		
		JCheckBox i_sbtn = new JCheckBox("switch 转 if");
		i_sbtn.setBounds(525, 134, 103, 23);
		showText.add(i_sbtn);
		
		JCheckBox s_ibtn = new JCheckBox("多if 转 switch");
		//监听事件
				i_sbtn.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent es) {
						JCheckBox checkBox = (JCheckBox) es.getSource();
						if(checkBox.isSelected()) {
							//这里是指被选中了！！！！！！！！！
							s_ibtn.setSelected(false);
						}
					}
				});
		////监听事件
		s_ibtn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent es) {
				JCheckBox checkBox = (JCheckBox) es.getSource();
				if(checkBox.isSelected()) {
					//这里是指被选中了！！！！！！！！！
					i_sbtn.setSelected(false);
				}
			}
		});
		s_ibtn.setBounds(525, 159, 103, 23);
		showText.add(s_ibtn);
		
		JCheckBox if_ifsbtn = new JCheckBox("多if 转 单if");
		if_ifsbtn.setBounds(525, 184, 103, 23);
		showText.add(if_ifsbtn);
		
		JCheckBox ifs_ifbtn = new JCheckBox("单if 转 多if");
		//监听事件
				if_ifsbtn.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent es) {
						JCheckBox checkBox = (JCheckBox) es.getSource();
						if(checkBox.isSelected()) {
							//这里是指被选中了！！！！！！！！！
							ifs_ifbtn.setSelected(false);
						}
					}
				});
		//监听事件
		ifs_ifbtn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent es) {
				JCheckBox checkBox = (JCheckBox) es.getSource();
				if(checkBox.isSelected()) {
					//这里是指被选中了！！！！！！！！！
					if_ifsbtn.setSelected(false);
				}
			}
		});
		ifs_ifbtn.setBounds(525, 209, 103, 23);
		showText.add(ifs_ifbtn);
		
		JLabel label_1 = new JLabel("处理前代码：");
		label_1.setBounds(0, 0, 100, 15);
		showText.add(label_1);
		
		JLabel label_2 = new JLabel("处理后代码：");
		label_2.setBounds(665, 0, 100, 15);
		showText.add(label_2);
		frame.getContentPane().setLayout(groupLayout);
		
		//转换按钮
		btnChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				result="";
				//SYM
				ArrayList<String> obLines = new ArrayList<String>();//记录按行读取文件内容
				ArrayList<String> bcLines = new ArrayList<String>();//按行读取文件内容
				ArrayList<String> source = new ArrayList<String>();//按行读取文件内容
				if(path=="") {
					JOptionPane.showMessageDialog(null, "请选择java/txt文件上传");
				}
				else {
					source = FileProcessing.readFile(path);//按行读取文件内容（无换行符
				}
				OrderBlock ob = new OrderBlock(source);
				obLines = ob.analyse();
				BlankCharacter bc = new BlankCharacter(obLines);
				bcLines = bc.analyse();
				//PGB
				All all=new All();
				ArrayList allTable=all.all(bcLines,f_wbtn.isSelected(),w_fbtn.isSelected(),
						i_sbtn.isSelected(),false,false,ifs_ifbtn.isSelected());
				//HYH
				LexicalAnalysis start=new LexicalAnalysis("",null,null,allTable);
				CodeFile aFile=new CodeFile(start.ceate());
				aFile.reShape();
				for(String s:aFile.getGroup()) {
					result += s;
				}
				newText.setText(result);//转换完成
			}
		});
		//选择源文件
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();			
				jfc.showOpenDialog(jfc);
				File file = jfc.getSelectedFile();
				
				try{
					path=file.getPath();
					sourceFile.setText(file.getPath());
					String[] strArray = path.split("\\\\");
					String[] splitStr = strArray[strArray.length-1].split("\\.");
					className = splitStr[0];     // 保存的文件名
				}
				catch(Exception exception ) {
					exception.printStackTrace();
				}
				
				extensionName = getExtensionName(sourceFile.getText());
				/**如果是java或者txt文件*/
				if ("java".equals(extensionName) || "txt".equals(extensionName)) {
					String text = readTxt(sourceFile.getText());//保存了读取出的文本内容（含换行符）
					oldText.setText(text);
				}
				else {
					oldText.setText(null);
					JOptionPane.showMessageDialog(null, "请选择java/txt文件上传");
				}
				
			}
		});
		//退出按钮
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		//下载按钮
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File f = null;
				int flag = fc.showOpenDialog(null);
				if(flag == JFileChooser.APPROVE_OPTION) 
	                        {
					f = fc.getSelectedFile();
					downloadpath=f.getPath();                // path是保存好的下载路径，可以输出一下
				}
				String newfile = downloadpath+"\\"+className+"_new"+ ".java";
				FileProcessing.clearFile(newfile);
				FileProcessing.writeFile(newfile,result);
				if(result!="") {
					if(flag == JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "下载成功！");
					}
					else if(flag == JFileChooser.CANCEL_OPTION){
						JOptionPane.showMessageDialog(null, "取消下载！");
					}
				}
				else if(result=="") {
					JOptionPane.showMessageDialog(null, "下载错误！");
				}
			}
		});
	}
	
    //获取用户上传的文件名
	private String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	//读取上传文件
	private String readTxt(String path) {
		if (path == null || "".equals(path)) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		File file = new File(path);
		InputStreamReader read = null;
		try {
//			FileReader fileReader = new FileReader(path);
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(read);
			String temp = null;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\r");
				buffer.append("\n");
			}
			read.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}


