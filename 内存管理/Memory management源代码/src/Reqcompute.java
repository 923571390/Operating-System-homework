import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Reqcompute extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton jb1,jb2,jb3=null;
	JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7=null;
	JLabel jlb1,jlb2,jlb3,jlb4=null;
	public static JTextArea jt1,jt2;
	public static JTextField jtf1= new  JTextField(10);
	public static JTextField jtf2= new  JTextField(10);

	public  Reqcompute(int n,int pc,int[] temp)
	{
		jb3=new JButton("返回");
		jb3.addActionListener(this);
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel();
		jp6=new JPanel();
		jp7=new JPanel();
		jlb1=new JLabel("算法产生的320个随机数为");	
		jlb2=new JLabel("对应的调用队列为");	
		jlb3=new JLabel("缺页次数");	
		jlb4=new JLabel("缺页率");
		jt1 = new JTextArea(12, 75);
		jt2 = new JTextArea(12, 30);
		for(int i=0;i<320;i++){
			if(i%32==0) {
				jt1.append("\n");
				jt2.append("\n");
			}
			jt1.append(String.format("%03d ",temp[i]));
			jt2.append(String.format("%02d ",temp[i]/10));
		}
		jtf1 = new JTextField(10);
		jtf1.setText(String.valueOf(n));
		jtf2 = new JTextField(10);
		DecimalFormat df=new DecimalFormat("0.00");
		jtf2.setText(df.format((float)n/320));
		jp1.add(jlb1);
		jp4.add(jt1);
		jp2.add(jlb2);
		jp5.add(jt2);
		jp6.add(jlb3);
		jp6.add(jtf1);
		jp7.add(jlb4);
		jp7.add(jtf2);
		jp3.add(jb3);
		this.add(jp1);
		this.add(jp4);
		this.add(jp2);
		this.add(jp5);
		this.add(jp3);
		this.add(jp6);
		this.add(jp7);

		getContentPane().setLayout(
			    new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
			);
		this.setTitle("请求分区内存管理结果如下");
		this.setSize(950, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
}
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getActionCommand()=="返回")  
	        {  
			  this.dispose();
	        }
	}
}
