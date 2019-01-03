import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Homepage extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel jp1,jp2,jp3;
	JButton jb1,jb2;
	public Homepage(){
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jb1=new JButton("动态分区");
		jb1.addActionListener(this);
		jb2=new JButton("请求分区");
		jb2.addActionListener(this);
		this.setLayout(new GridLayout(3, 1));
		jp3.add(jb1);
		jp3.add(jb2);
		this.add(jp2);
		this.add(jp3);
		this.add(jp1);
		this.setTitle("计算机内存管理模拟系统");
		this.setSize(300, 150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);	
	}
	public void actionPerformed(ActionEvent e) {  
        if(e.getActionCommand()=="动态分区")  
        {  
            new Dynamic(); 
        }
        else if(e.getActionCommand()=="请求分区")  
        {  
            new Request(); 
        } 
	}
	public static void main(String[] args) {
		    new Homepage();
	}
}
