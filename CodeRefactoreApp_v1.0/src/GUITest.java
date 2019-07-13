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

    private String rightPath="";//��ǰ·��
//    private String result="";//���������
    private String extensionName="";//�ļ���׺��
    private String className="";//�ļ�����
    private String downloadpath="";//����·��
//	String listdata[]= {"1fsdfdsfsdfdsfdsfsdfdsfdsfsd","2dfdsfsd","3fsfdsfdfsd"};
    private ArrayList<String> fileNames = new ArrayList<>();//��¼�ļ���
    private ArrayList<String> paths = new ArrayList<>();//��¼�ļ���
    private ArrayList<String> results = new ArrayList<>();//��¼�ļ���
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
//		JOptionPane.showMessageDialog(null, "1.��ѡ��java/txt�ļ��ϴ� \n2.��ȷ��ѡ���Դ�ļ�����ͨ������\n3.��ʵ�ִ��շ弰С�շ�����");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();//������
		frame.setBackground(new Color(128, 128, 128));
		frame.setTitle("�����ع�ת����");
		frame.setResizable(false);
		frame.setBounds(100, 100, 1275, 715);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel choosePath = new JPanel();//ѡ��·��ģ��
		choosePath.setToolTipText("");
		choosePath.setBorder(new LineBorder(Color.LIGHT_GRAY));

		sourceFile = new JTextField();//��Ŷ�ȡ�ļ�·��
		sourceFile.setColumns(10);


		//����ѡ��ť
		JButton btnBrowse = new JButton("ѡ��");
		btnBrowse.setFont(new Font("����", Font.PLAIN, 14));
		btnBrowse.setBackground(new Color(220, 220, 220));

		JLabel label = new JLabel("��ѡ����Ҫ�����Դ�ļ�");
        label.setFont(new Font("����", Font.PLAIN, 14));
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

		//չʾ�ı�ģ��
		JPanel showText = new JPanel();
		JButton btnChangeButton = new JButton("ת��");
		btnChangeButton.setFont(new Font("����", Font.PLAIN, 15));
		btnChangeButton.setBackground(Color.LIGHT_GRAY);

		btnChangeButton.setBounds(662, 341, 100, 23);
		showText.setLayout(null);
		showText.add(btnChangeButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(790, 20, 465, 510);
		showText.add(scrollPane_1);

		JTextArea newText = new JTextArea();//����Ѵ�������
		newText.setEditable(false);
		scrollPane_1.setViewportView(newText);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(180, 20, 465, 510);
		showText.add(scrollPane);

		JTextArea oldText = new JTextArea();//���δ������ļ�����
		scrollPane.setViewportView(oldText);
		oldText.setEditable(false);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));

		JLabel lblNewLabel = new JLabel("�����Ϣ");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "1.��ѡ��java/txt�ļ��ϴ� \n2.��ȷ��ѡ���Դ�ļ�����ͨ������\n3.��ʵ�ִ��շ弰С�շ�����");
			}
		});
		lblNewLabel.setIcon(new ImageIcon(GUITest.class.getResource("icon.png")));

		JButton btnExit = new JButton("�˳�");
		btnExit.setFont(new Font("����", Font.PLAIN, 15));
		btnExit.setBackground(new Color(220, 220, 220));

		JButton btnDownload = new JButton("����");
		btnDownload.setFont(new Font("����", Font.PLAIN, 15));

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
        //ȷ�Ͽ������
        JCheckBox f_wbtn = new JCheckBox("forתwhile");
        f_wbtn.setFont(new Font("����", Font.BOLD, 14));
        f_wbtn.setBounds(657, 148, 122, 23);
        showText.add(f_wbtn);

        JCheckBox w_fbtn = new JCheckBox("whileתfor");
        w_fbtn.setFont(new Font("����", Font.BOLD, 14));
        //��ѡ��״̬�ı��¼�
        f_wbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //������ָ��ѡ���ˣ�����������������
                w_fbtn.setSelected(false);
            }
        });
        //�����¼�
        w_fbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //������ָ��ѡ���ˣ�����������������
                f_wbtn.setSelected(false);
            }
        });
        w_fbtn.setBounds(657, 173, 133, 23);
        showText.add(w_fbtn);

        JCheckBox i_sbtn = new JCheckBox("switchתif");
        i_sbtn.setFont(new Font("����", Font.BOLD, 14));
        i_sbtn.setBounds(657, 198, 133, 23);
        showText.add(i_sbtn);

        JCheckBox s_ibtn = new JCheckBox("��ifתswitch");
        s_ibtn.setFont(new Font("����", Font.BOLD, 14));
        //�����¼�
        i_sbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //������ָ��ѡ���ˣ�����������������
                s_ibtn.setSelected(false);
            }
        });
        ////�����¼�
        s_ibtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //������ָ��ѡ���ˣ�����������������
                i_sbtn.setSelected(false);
            }
        });
        s_ibtn.setBounds(657, 223, 133, 23);
        showText.add(s_ibtn);

        JCheckBox if_ifsbtn = new JCheckBox("��ifת��if");
        if_ifsbtn.setFont(new Font("����", Font.BOLD, 14));
        if_ifsbtn.setBounds(657, 248, 133, 23);
        showText.add(if_ifsbtn);

        JCheckBox ifs_ifbtn = new JCheckBox("��ifת��if");
        ifs_ifbtn.setFont(new Font("����", Font.BOLD, 14));
        //�����¼�
        if_ifsbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //������ָ��ѡ���ˣ�����������������
                ifs_ifbtn.setSelected(false);
            }
        });
        //�����¼�
        ifs_ifbtn.addChangeListener(es -> {
            JCheckBox checkBox = (JCheckBox) es.getSource();
            if(checkBox.isSelected()) {
                //������ָ��ѡ���ˣ�����������������
                if_ifsbtn.setSelected(false);
            }
        });
        ifs_ifbtn.setBounds(657, 273, 133, 23);
        showText.add(ifs_ifbtn);

		JLabel label_1 = new JLabel("����ǰ���룺");
		label_1.setFont(new Font("����", Font.PLAIN, 14));
		label_1.setBounds(181, 0, 100, 15);
		showText.add(label_1);

		JLabel label_2 = new JLabel("�������룺");
		label_2.setFont(new Font("����", Font.PLAIN, 14));
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
                    //�����¼�
                    int i;
                    selectFileNum=list.getSelectedIndex();
                    i=selectFileNum;
                    rightPath=paths.get(i);
                    sourceFile.setText(rightPath);
                    String o_text = readTxt(sourceFile.getText());//�����˶�ȡ�����ı����ݣ������з���
                    oldText.setText(o_text);
                    if(results.size()!=0){
                        String n_text = results.get(i);//�����˶�ȡ�����ı����ݣ������з���
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
		/**�Ӹ��Զ�����**/
		frame.getContentPane().setLayout(groupLayout);

        JButton btnFastButton = new JButton("һ���ع�");
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
                JOptionPane.showMessageDialog(null, "��ѡ��java/txt�ļ��ϴ�");
            }
            else{
                for(String path:paths){
                    source = FileProcessing.readFile(path);//���ж�ȡ�ļ����ݣ��޻��з�
                    MainRun theMain=new MainRun(source,judge);
                    result=theMain.run();
                    results.add(result);
                }
//                    JOptionPane.showMessageDialog(null, "done");
            }
        });
        btnFastButton.setBounds(38, 507, 105, 23);
        btnFastButton.setFont(new Font("����", Font.PLAIN, 15));
        btnFastButton.setBackground(Color.LIGHT_GRAY);
        showText.add(btnFastButton);
        frame.getContentPane().setLayout(groupLayout);

        JLabel lblNewLabel_1 = new JLabel("�ļ�Ŀ¼��");
        lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(10, 0, 100, 15);
        showText.add(lblNewLabel_1);
        frame.getContentPane().setLayout(groupLayout);

		//ת����ť
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
JOptionPane.showMessageDialog(null, "���ȵ����һ���ع�");
}
            else if("".equals(rightPath)) {
JOptionPane.showMessageDialog(null, "�����ļ�Ŀ¼��ѡ��һ���ļ�");
}
            else {
                source = FileProcessing.readFile(rightPath);//���ж�ȡ�ļ����ݣ��޻��з�
//������
MainRun theMain=new MainRun(source,judge);
String result=theMain.run();
results.set(selectFileNum,result);
//�����ʾ
newText.setText(result);//ת�����
            }
        });
		//ѡ��Դ�ļ�
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    //���
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
                            className = splitStr[0];     // ������ļ���
                            fileNames.add(className);
                        }
                        catch(Exception exception ) {
                            exception.printStackTrace();
                        }

//                        extensionName = getExtensionName(sourceFile.getText());



                        //�ж������ļ��Ƿ����Ҫ��
                        extensionName = getExtensionName(file.getPath());
                        //�����java����txt�ļ�
                        if("java".equals(extensionName) || "txt".equals(extensionName)) {
//                            String text = readTxt(file.getPath());//�����˶�ȡ�����ı����ݣ������з���
//                            oldText.setText(text);
                        }
                        else {
                            someFileWrong = true;
//                            oldText.setText(null);
//                            JOptionPane.showMessageDialog(null, "��ѡ��java/txt�ļ��ϴ�");
                        }





                    }
                }
                if(someFileWrong){
                    JOptionPane.showMessageDialog(null, "�ϴ��ļ����ڷ�java/txt�ļ�");
                }
                else{
                    list.setListData(fileNames.toArray());
                }

			}
		});
		//�˳���ť
		btnExit.addActionListener(e -> System.exit(0));
		//���ذ�ť
		btnDownload.addActionListener(e -> {

			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File f;
			int flag = fc.showOpenDialog(null);
			if(flag == JFileChooser.APPROVE_OPTION)
			{
				f = fc.getSelectedFile();
				downloadpath=f.getPath();                // path�Ǳ���õ�����·�����������һ��
			}



			String newfilePath = downloadpath+"\\"+"_new";//�½�һ���ļ��д�Ž��
			File newFile = new File(newfilePath);
			//����ļ��в������򴴽�
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
                    JOptionPane.showMessageDialog(null, "���سɹ���");
                }
                else if(flag == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(null, "ȡ�����أ�");
                }
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, "���ش���");
            }

		});
	}

	//��ռ�¼
    private void clearAll(){
	    paths.clear();
	    fileNames.clear();
	    sourcefiles=null;
	    results.clear();
    }

	//��ȡ�û��ϴ����ļ���
	private String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	//��ȡ�ϴ��ļ�
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