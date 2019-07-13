import javasss.FileProcessing;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUITest {

	private JFrame frame;
	private JTextField sourceFile;

    private String rightPath="";//当前路径
//    private String result="";//最后输出结果
    private String extensionName="";//文件后缀名
    private String className="";//文件名字
    private String downloadpath="";//下载路径
//	String listdata[]= {"1fsdfdsfsdfdsfdsfsdfdsfdsfsd","2dfdsfsd","3fsfdsfdfsd"};
    private ArrayList<String> fileNames = new ArrayList<>();//记录文件名
    private ArrayList<String> paths = new ArrayList<>();//记录文件名
    private ArrayList<String> results = new ArrayList<>();//记录文件名
    private File[] sourcefiles ;
    private int selectFileNum;
	/**
     *
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                GUITest window = new GUITest();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	/**
	 * Create the application.
	 */
	public GUITest() {
		initialize();
//		JOptionPane.showMessageDialog(null, "1.请选择java/txt文件上传 \n2.请确保选择的源文件可以通过编译\n3.可实现大驼峰及小驼峰命名");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//整体框架
		frame.setBackground(new Color(128, 128, 128));
		frame.setTitle("代码重构转换器");
		frame.setResizable(false);
		frame.setBounds(100, 100, 1275, 715);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel choosePath = new JPanel();//选择路径模块
		choosePath.setToolTipText("");
		choosePath.setBorder(new LineBorder(Color.LIGHT_GRAY));

		sourceFile = new JTextField();//存放读取文件路径
		sourceFile.setColumns(10);


		//定义选择按钮
		JButton btnBrowse = new JButton("选择");
		btnBrowse.setFont(new Font("宋体", Font.PLAIN, 14));
		btnBrowse.setBackground(new Color(220, 220, 220));

		JLabel label = new JLabel("请选择需要处理的源文件");
        label.setFont(new Font("宋体", Font.PLAIN, 14));
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
		btnChangeButton.setFont(new Font("宋体", Font.PLAIN, 15));
		btnChangeButton.setBackground(Color.LIGHT_GRAY);

		btnChangeButton.setBounds(662, 341, 100, 23);
		showText.setLayout(null);
		showText.add(btnChangeButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(790, 20, 465, 510);
		showText.add(scrollPane_1);

		JTextArea newText = new JTextArea();//存放已处理内容
		newText.setEditable(false);
		scrollPane_1.setViewportView(newText);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(180, 20, 465, 510);
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
		btnExit.setFont(new Font("宋体", Font.PLAIN, 15));
		btnExit.setBackground(new Color(220, 220, 220));

		JButton btnDownload = new JButton("下载");
		btnDownload.setFont(new Font("宋体", Font.PLAIN, 15));

		btnDownload.setBackground(new Color(220, 220, 220));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 1037, Short.MAX_VALUE)
								.addComponent(btnDownload, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addGap(24))
		);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap(20, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnExit)
										.addComponent(btnDownload))
								.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(16, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(choosePath, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1269, Short.MAX_VALUE)
						.addComponent(showText, GroupLayout.DEFAULT_SIZE, 1269, Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1269, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(choosePath, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(showText, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
		);
        //确认框定义代码
        JCheckBox f_wbtn = new JCheckBox("for转while");
        f_wbtn.setFont(new Font("宋体", Font.BOLD, 14));
        f_wbtn.setBounds(657, 148, 122, 23);
        showText.add(f_wbtn);

        JCheckBox w_fbtn = new JCheckBox("while转for");
        w_fbtn.setFont(new Font("宋体", Font.BOLD, 14));
        //复选框状态改变事件
        f_wbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //这里是指被选中了！！！！！！！！！
                w_fbtn.setSelected(false);
            }
        });
        //监听事件
        w_fbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //这里是指被选中了！！！！！！！！！
                f_wbtn.setSelected(false);
            }
        });
        w_fbtn.setBounds(657, 173, 133, 23);
        showText.add(w_fbtn);

        JCheckBox i_sbtn = new JCheckBox("switch转if");
        i_sbtn.setFont(new Font("宋体", Font.BOLD, 14));
        i_sbtn.setBounds(657, 198, 133, 23);
        showText.add(i_sbtn);

        JCheckBox s_ibtn = new JCheckBox("多if转switch");
        s_ibtn.setFont(new Font("宋体", Font.BOLD, 14));
        //监听事件
        i_sbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //这里是指被选中了！！！！！！！！！
                s_ibtn.setSelected(false);
            }
        });
        ////监听事件
        s_ibtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //这里是指被选中了！！！！！！！！！
                i_sbtn.setSelected(false);
            }
        });
        s_ibtn.setBounds(657, 223, 133, 23);
        showText.add(s_ibtn);

        JCheckBox if_ifsbtn = new JCheckBox("多if转单if");
        if_ifsbtn.setFont(new Font("宋体", Font.BOLD, 14));
        if_ifsbtn.setBounds(657, 248, 133, 23);
        showText.add(if_ifsbtn);

        JCheckBox ifs_ifbtn = new JCheckBox("单if转多if");
        ifs_ifbtn.setFont(new Font("宋体", Font.BOLD, 14));
        //监听事件
        if_ifsbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //这里是指被选中了！！！！！！！！！
                ifs_ifbtn.setSelected(false);
            }
        });
        //监听事件
        ifs_ifbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //这里是指被选中了！！！！！！！！！
                if_ifsbtn.setSelected(false);
            }
        });
        ifs_ifbtn.setBounds(657, 273, 133, 23);
        showText.add(ifs_ifbtn);

		JLabel label_1 = new JLabel("处理前代码：");
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		label_1.setBounds(181, 0, 100, 15);
		showText.add(label_1);

		JLabel label_2 = new JLabel("处理后代码：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 14));
		label_2.setBounds(792, 0, 100, 15);
		showText.add(label_2);

//		frame.getContentPane().setLayout(groupLayout);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(5, 20, 165, 465);
		showText.add(scrollPane_2);

		JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			    try {
                    //监听事件
                    int i;
                    selectFileNum=list.getSelectedIndex();
                    i=selectFileNum;
                    rightPath=paths.get(i);
                    sourceFile.setText(rightPath);
                    String o_text = readTxt(sourceFile.getText());//保存了读取出的文本内容（含换行符）
                    oldText.setText(o_text);
                    if(results.size()!=0){
                        String n_text = results.get(i);//保存了读取出的文本内容（含换行符）
                        newText.setText(n_text);
                    }
                }catch (Exception ex){
                    //ex.printStackTrace();
                }

//                JOptionPane.showMessageDialog(null, i);
//				oldText.setText("hhhhhhhhhhhhhh");

			}
		});
		scrollPane_2.setViewportView(list);
		/**加个自动排序？**/
		frame.getContentPane().setLayout(groupLayout);

        JButton btnFastButton = new JButton("一键重构");
        btnFastButton.addActionListener(e -> {
            results.clear();
            String result;
            ArrayList<String> source;
            ArrayList<Boolean> judge=new ArrayList<>();

            judge.add(f_wbtn.isSelected());
            judge.add(w_fbtn.isSelected());
            judge.add(i_sbtn.isSelected());
            judge.add(s_ibtn.isSelected());
            judge.add(if_ifsbtn.isSelected());
            judge.add(ifs_ifbtn.isSelected());
            if(paths.size()==0){
                JOptionPane.showMessageDialog(null, "请选择java/txt文件上传");
            }
            else{
                for(String path:paths){
                    source = FileProcessing.readFile(path);//按行读取文件内容（无换行符
                    MainRun theMain=new MainRun(source,judge);
                    result=theMain.run();
                    results.add(result);
                }
//                    JOptionPane.showMessageDialog(null, "done");
            }
        });
        btnFastButton.setBounds(38, 507, 105, 23);
        btnFastButton.setFont(new Font("宋体", Font.PLAIN, 15));
        btnFastButton.setBackground(Color.LIGHT_GRAY);
        showText.add(btnFastButton);
        frame.getContentPane().setLayout(groupLayout);

        JLabel lblNewLabel_1 = new JLabel("文件目录：");
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(10, 0, 100, 15);
        showText.add(lblNewLabel_1);
        frame.getContentPane().setLayout(groupLayout);

		//转换按钮
		btnChangeButton.addActionListener(arg0 -> {
//				result="";
            ArrayList<String> source;
            ArrayList<Boolean> judge=new ArrayList<>();

            judge.add(f_wbtn.isSelected());
            judge.add(w_fbtn.isSelected());
            judge.add(i_sbtn.isSelected());
            judge.add(s_ibtn.isSelected());
            judge.add(if_ifsbtn.isSelected());
            judge.add(ifs_ifbtn.isSelected());

            if(results.size()==0){
JOptionPane.showMessageDialog(null, "请先点击“一键重构");
}
            else if("".equals(rightPath)) {
JOptionPane.showMessageDialog(null, "请在文件目录中选择一个文件");
}
            else {
                source = FileProcessing.readFile(rightPath);//按行读取文件内容（无换行符
//主程序
MainRun theMain=new MainRun(source,judge);
String result=theMain.run();
results.set(selectFileNum,result);
//结果显示
newText.setText(result);//转换完成
            }
        });
		//选择源文件
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    //清空
			    clearAll();
			    oldText.setText("");
			    newText.setText("");

                JFileChooser jfc=new JFileChooser();

                jfc.setMultiSelectionEnabled(true);
                boolean someFileWrong = false;
                int option = jfc.showOpenDialog(jfc);
                if(option == JFileChooser.APPROVE_OPTION) {
                    sourcefiles = jfc.getSelectedFiles();
					String path;

                    for(File file:sourcefiles) {
//						fileNames += file.getName() + " ";
//						JOptionPane.showMessageDialog(null, fileNames);
                        try{
                            path = file.getPath();
                            paths.add(path);
//                            sourceFile.setText(file.getPath());
                            String[] strArray = path.split("\\\\");
                            String[] splitStr = strArray[strArray.length-1].split("\\.");
                            className = splitStr[0];     // 保存的文件名
                            fileNames.add(className);
                        }
                        catch(Exception exception ) {
                            exception.printStackTrace();
                        }

//                        extensionName = getExtensionName(sourceFile.getText());



                        //判断输入文件是否符合要求
                        extensionName = getExtensionName(file.getPath());
                        //如果是java或者txt文件
                        if("java".equals(extensionName) || "txt".equals(extensionName)) {
//                            String text = readTxt(file.getPath());//保存了读取出的文本内容（含换行符）
//                            oldText.setText(text);
                        }
                        else {
                            someFileWrong = true;
//                            oldText.setText(null);
//                            JOptionPane.showMessageDialog(null, "请选择java/txt文件上传");
                        }





                    }
                }
                if(someFileWrong){
                    JOptionPane.showMessageDialog(null, "上传文件存在非java/txt文件");
                }
                else{
                    list.setListData(fileNames.toArray());
                }

			}
		});
		//退出按钮
		btnExit.addActionListener(e -> System.exit(0));
		//下载按钮
		btnDownload.addActionListener(e -> {

			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File f;
			int flag = fc.showOpenDialog(null);
			if(flag == JFileChooser.APPROVE_OPTION)
			{
				f = fc.getSelectedFile();
				downloadpath=f.getPath();                // path是保存好的下载路径，可以输出一下
			}



			String newfilePath = downloadpath+"\\"+"_new";//新建一个文件夹存放结果
			File newFile = new File(newfilePath);
			//如果文件夹不存在则创建
			if  (!newFile .exists()  && !newFile .isDirectory())
			{
				newFile .mkdir();
			}

            String result;
			String path;
			try {
                if(flag == JFileChooser.APPROVE_OPTION) {
                    for(int i=0;i<paths.size();i++){
                        result=results.get(i);
                        path= newfilePath + "\\" + fileNames.get(i)+".java";

                        FileProcessing.clearFile(path);
                        FileProcessing.writeFile(path,result);
                    }
                    JOptionPane.showMessageDialog(null, "下载成功！");
                }
                else if(flag == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(null, "取消下载！");
                }
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "下载错误！");
            }

		});
	}

	//清空记录
    private void clearAll(){
	    paths.clear();
	    fileNames.clear();
	    sourcefiles=null;
	    results.clear();
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
		InputStreamReader read;
		try {
//			FileReader fileReader = new FileReader(path);
			read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(read);
			String temp;
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