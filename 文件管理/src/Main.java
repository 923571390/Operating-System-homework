import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 * @author Administrator
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Main {
	public static String selected=new String();
	public static String name=new String();
	public static int type;//0文件夹，1文件
	public static String contain=new String();//粘贴版文件内容
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); 
	}
}
class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("文件资源管理器");
		Toolkit kit=Toolkit.getDefaultToolkit();  
		Dimension screen=kit.getScreenSize();
		this.setSize(screen.width/3*2+5,screen.height/2);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		MainPanel panel = new MainPanel();
		contentPane.add(panel);
		this.setResizable(false);
  }
}
class MainPanel extends JPanel {
		private Disc mydisk;
		public String path = new String("$");
		private int CurBlock;
		private JTextField textCommand;
		private ArrayList CurDirs = new ArrayList();
		private ArrayList CurFiles = new ArrayList();
		private JTextArea DirInfo;
		private JTextArea FileInfo;
		private static final long serialVersionUID = 1L;
	    public static String localPath="";
	    public static String RButtonSelect="";
	    public JPanel panel = new JPanel();
	    public JPanel panel1 = new JPanel();
	    public JPanel panel_left = new JPanel();
	    public JPanel panel_right = new JPanel();
	    public JScrollPane scoll;
	    public MouseRightPopup popup;
	    public JButton btn_undo;
	    public JButton btn_new;
	    public JButton btn_newfile;
	    public static JButton saver;
	    public TextArea list;
	    public JLabel label1 = new JLabel("     ");
	    public JLabel label2 = new JLabel("     ");
		public void layoutTop() {
			btn_undo = new JButton("返回");
			btn_new = new JButton("新建文件夹");
			btn_newfile = new JButton("新建文件");
			saver = new JButton("保存");
			list = new TextArea();
			popup = new MouseRightPopup();
		}
		public MainPanel(){
			layoutTop();
			GridBagLayout layout = new GridBagLayout();
			this.setLayout(layout);
			this.add(label1);
			this.add(label2);
			this.add(saver);
			this.add(btn_undo);
			this.add(btn_new);
			this.add(btn_newfile);
			this.add(panel1);
			this.add(list);
			panel_right.setLayout(new FlowLayout(0,10,10));
			panel_right.setPreferredSize(new Dimension(0,1500));
			scoll = new JScrollPane(panel_right);
			this.add(scoll);
			panel_right.setBackground(Color.WHITE);
			saver.setBackground(null);
			list.setBackground(Color.LIGHT_GRAY);
			panel_right.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
	        constraints.gridwidth=1;
	        constraints.weightx = 0;
	        constraints.weighty = 0;
	        layout.setConstraints(label1, constraints);
	        constraints.gridwidth=2;
	        constraints.weightx = 0;
	        constraints.weighty = 0;
	        layout.setConstraints(label2, constraints);
	        constraints.gridwidth=1;
	        constraints.weightx = 0;
	        constraints.weighty = 0;
	        layout.setConstraints(saver, constraints);
	        constraints.gridwidth=1;
	        constraints.weightx = 0;
	        constraints.weighty = 0;
	        layout.setConstraints(btn_undo, constraints);
	        constraints.gridwidth=1;
	        constraints.weightx = 0;
	        constraints.weighty = 0;
	        layout.setConstraints(btn_new, constraints);
	        constraints.gridwidth=1;
	        constraints.weightx = 0;
	        constraints.weighty = 0;
	        layout.setConstraints(btn_newfile, constraints);
	        constraints.gridwidth=0;
	        constraints.weightx = 1;
	        constraints.weighty = 0;
	        layout.setConstraints(panel1, constraints);
	        constraints.gridwidth=3;
	        constraints.weightx = 0;
	        constraints.weighty = 1;
	        layout.setConstraints(list, constraints);
	        constraints.gridwidth=4;
	        constraints.weightx = 0;
	        constraints.weighty = 1;
	        layout.setConstraints(scoll, constraints);
			mouseControlFile();
			BtnListenner();
			try {
				ObjectInputStream in =
					new ObjectInputStream(new FileInputStream("filesystem.fy"));
				mydisk = (Disc) in.readObject();
				in.close();
				mydisk.reload(CurDirs, CurFiles);
				Ppaint();
				if(mydisk == null)
				{
					mydisk = new Disc();
				}
			} 
			catch (IOException err) {
				mydisk = new Disc();
				System.out.println(err.getMessage());
			} 
			catch (ClassNotFoundException err) {
				System.out.println(err.getMessage());
			}
		}
		public void Ppaint(){
			panel_right.removeAll();
			removeAll();
			for(int i = 0; i < CurDirs.size(); i++)
			{ 
					JPanel panel_item = new JPanel();
					ImageIcon icon = new ImageIcon("./src/file.png");
					JLabel label = new JLabel(icon);
					JLabel Jl1 = new JLabel(CurDirs.get(i).toString());
					label1.setPreferredSize(new Dimension(50,20));
					panel_item.setLayout(new BorderLayout());
					panel_item.setBackground(null);
					panel_item.add(label,BorderLayout.NORTH);
					panel_item.add(Jl1,BorderLayout.CENTER);
					Jl1.setHorizontalAlignment(SwingConstants.CENTER);
					panel_item.addMouseListener(new MouseAdapter() {
						@Override 
						public void mouseClicked(MouseEvent e)
						{
							if(e.getClickCount()==1){
								if(panel_item.getBackground()==Color.lightGray){
									panel_item.setBackground(null);
									Main.selected=" ";
								}
								else{
									panel_item.setBackground(Color.lightGray);
									JLabel la= (JLabel) panel_item.getComponent(1);
									Main.selected=la.getText();	
								}
							}
								if(e.getClickCount()==2 && e.getButton()==MouseEvent.BUTTON1)
								{
									JPanel item = (JPanel) e.getComponent();
									JLabel la= (JLabel) item.getComponent(1);
									String str=la.getText();
									if (str == null || str.equals("")) {
									}
									else {
									path = path + "\\" + str;
									CurBlock =mydisk.enterDirectory(CurBlock, str, CurDirs, CurFiles);
									label2.setText(path);
									Main.selected = str;
									list.setText(" ");
									panel_right.updateUI();
									Ppaint();
									}
								 }
							if(e.getButton()==MouseEvent.BUTTON3)
						    {
								panel_item.setBackground(Color.lightGray);
								JPanel item = (JPanel) e.getComponent();
								JLabel la= (JLabel) item.getComponent(1);
								RButtonSelect=la.getText();
								popup.show(e.getComponent(), e.getX(), e.getY());
								mouseRightMenuFunction();
							}
						}
					});
		        panel_right.add(panel_item);
			}
			for(int i = 0; i < CurFiles.size(); i++)
			{
				JPanel panel_item = new JPanel();
				ImageIcon icon_file = new ImageIcon("./src/page.png");
				JLabel label = new JLabel(icon_file);
				JLabel Jl1 = new JLabel(CurFiles.get(i).toString());
				label1.setPreferredSize(new Dimension(50,20));
				panel_item.setLayout(new BorderLayout());
				panel_item.setBackground(null);
				panel_item.add(label,BorderLayout.NORTH);
				panel_item.add(Jl1,BorderLayout.CENTER);
				Jl1.setHorizontalAlignment(SwingConstants.CENTER);
				panel_item.addMouseListener(new MouseAdapter() {
					@Override 
					public void mouseClicked(MouseEvent e)
					{
						if(e.getClickCount()==1){
							if(panel_item.getBackground()==Color.lightGray){
								panel_item.setBackground(null);
								Main.selected="";
							}
							else{
								panel_item.setBackground(Color.lightGray);
								JPanel item = (JPanel) e.getComponent();
								JLabel la= (JLabel) item.getComponent(1);
								Main.selected=la.getText();	
							}
						}
							if(e.getClickCount()==2 && e.getButton()==MouseEvent.BUTTON1)
							{
								JPanel item = (JPanel) e.getComponent();
								JLabel la= (JLabel) item.getComponent(1);
								String str=la.getText();
								Main.selected = str;
								if (str != null&&str!="") {
									String Text = mydisk.getFileContent(CurBlock, str);
									list.setText(Text);
								}															
							 }
						if(e.getButton()==MouseEvent.BUTTON3)
					    {
							panel_item.setBackground(Color.lightGray);
							JPanel item = (JPanel) e.getComponent();
							JLabel la= (JLabel) item.getComponent(1);
							RButtonSelect=la.getText();
							popup.show(e.getComponent(), e.getX(), e.getY());
							mouseRightMenuFunction();
					    }
					}
				});
		    panel_right.add(panel_item);
			}
			if(mydisk == null)  mydisk = new Disc();
			panel_right.updateUI();
		} 
		
		public void mouseRightMenuFunction()
		{
			ActionListener itemAction=new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					String temp = e.getActionCommand();
					if(RButtonSelect!=null) {
						if(temp.equals("打开"))
						{
							if(CurDirs.indexOf(RButtonSelect)!=-1) {
								if (RButtonSelect == null || RButtonSelect.equals("")) {	
								}
								else {
								path = path + "\\" + RButtonSelect;
								CurBlock =mydisk.enterDirectory(CurBlock, RButtonSelect, CurDirs, CurFiles);
								label2.setText(path);
								list.setText(" ");
								panel_right.updateUI();
								Ppaint();
								}
							}
								else if(CurFiles.indexOf(RButtonSelect)!=-1) {								
									if (RButtonSelect != null&&RButtonSelect!="") {
										String Text = mydisk.getFileContent(CurBlock, RButtonSelect);
										list.setText(Text);
									}						
								}
								Ppaint();
								panel_right.updateUI();
						}
						if(temp.equals("删除"))
						{
							if(CurFiles.indexOf(RButtonSelect)!=-1) {
							mydisk.delFile(CurBlock, RButtonSelect);
								CurFiles.remove(RButtonSelect);}
							else if(CurDirs.indexOf(RButtonSelect)!=-1) {
								mydisk.delDirectory(CurBlock, RButtonSelect);
								CurDirs.remove(RButtonSelect);
								}
							Ppaint();
							panel_right.updateUI();
						}
						if(temp.equals("复制"))
						{
							if(CurFiles.indexOf(RButtonSelect)!=-1) {
								Main.name=RButtonSelect;
								Main.type=1;
								Main.contain=mydisk.getFileContent(CurBlock, RButtonSelect);
								}
								else if(CurDirs.indexOf(RButtonSelect)!=-1) {
									Main.name=RButtonSelect;
									Main.type=0;
									Main.contain=" ";
								}
						}
						if(temp.equals("粘贴"))
						{
							if(Main.type==0) {
								for (int i = 0; i < CurDirs.size(); ++i) {
									if (Main.name.equals(CurDirs.get(i))) {
										JOptionPane.showMessageDialog(null, "该文件已存在");
										return;
									}
								}
								if (mydisk.addDirectory(CurBlock, Main.name)) {
									CurDirs.add(Main.name);
									Ppaint();
								} 
								else {
								}
							}
							else if(Main.type==1) {
								for (int i = 0; i < CurFiles.size(); ++i) {
									if (Main.name.equals(CurFiles.get(i))) {
										JOptionPane.showMessageDialog(null, "该文件已存在");
										return;
									}
								}
								if (mydisk.addFile(CurBlock, Main.name)) {
									CurFiles.add(Main.name);
									String Text = Main.contain;
									mydisk.saveFileContent(CurBlock, Main.name, Text);
									Ppaint();
								} 
								else {
								}
							}
						}
					}
				}
			};
			popup.addItemListener(0,itemAction);
			popup.addItemListener(1, itemAction);
			popup.addItemListener(2, itemAction);
			popup.addItemListener(3, itemAction);
		
		}
		
		public void mouseControlFile()
		{
			panel_right.addMouseListener(new MouseAdapter() {
				@Override 
				public void mouseClicked(MouseEvent e)
				{
					if(e.getButton()==MouseEvent.BUTTON3)
				    {
						popup.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			});
		}
	public void BtnListenner(){
		btn_undo.addMouseListener(new MouseAdapter(){
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				int newBlock = mydisk.exitDirectory(CurBlock, CurDirs, CurFiles);
				if (newBlock == -1) {
					return;
				}
				CurBlock = newBlock;
				int j = path.lastIndexOf("\\");
				path = path.substring(0, j);
				label2.setText(path);
				Ppaint();
				list.setText(" ");
			}
		});
		saver.addMouseListener(new MouseAdapter(){
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				String FileName = Main.selected;
				if(FileName!=null&&CurFiles.indexOf(FileName)!=-1) {
				String Text = list.getText().trim();
				mydisk.saveFileContent(CurBlock, FileName, Text);
				String FileText = "文本已保存\n";
				FileText += "保存位置："
					+ mydisk.getLocation(CurBlock, FileName)
					+ "\n";
				FileText += "空间：" + mydisk.getSize(CurBlock, FileName) + "\n";
				list.setText(FileText);
				}
			}
		});
		btn_new.addMouseListener(new MouseAdapter(){
			@Override 
			public void mouseClicked(MouseEvent e)
			{
					String DirName = JOptionPane.showInputDialog(null, "请输入文件夹名");
					if (DirName == null) {
						return;
					} 
					else {
						DirName = DirName.trim();
						if (DirName.length() > 16) {
							JOptionPane.showMessageDialog(null, "文件夹名应小于16个字符");
							return;
						}
					}
					if (DirName.equals("")) {
						//判断是否为空
						JOptionPane.showMessageDialog(null, "请输入文件夹名");
						return;
					} 
					else {
						//判断是否重复
						if(CurDirs!=null) {
						for (int i = 0; i < CurDirs.size(); ++i) {
							if (DirName.equals(CurDirs.get(i))) {
								JOptionPane.showMessageDialog(null, "该文件夹已存在");
								return;
							}
						}
					}
						if (mydisk.addDirectory(CurBlock, DirName)) {
							CurDirs.add(DirName);
							Ppaint();
						} 
						else {
						}
					}
			}
		});
		btn_newfile.addMouseListener(new MouseAdapter(){
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				String FileName = JOptionPane.showInputDialog(null, "请输入文件名");
				if (FileName == null) {
					return;
				} else {
					FileName = FileName.trim();
					if (FileName.length() > 16) {
						JOptionPane.showMessageDialog(null, "文件名应小于16个字符");
						return;
					}
				}
				if (FileName.equals("")) {
					//判断是否为空
					JOptionPane.showMessageDialog(null, "请输入文件名");
					return;
				} else {
					//判断是否重复
					for (int i = 0; i < CurFiles.size(); ++i) {
						if (FileName.equals(CurFiles.get(i))) {
							JOptionPane.showMessageDialog(null, "该文件已存在");
							return;
						}
					}
					if (mydisk.addFile(CurBlock, FileName)) {
						CurFiles.add(FileName);
						Ppaint();
					} 
					else {
					}
				}
			}
		});
	}//返回。
	public void removeAll(){
		panel_right.repaint();
	}
}

