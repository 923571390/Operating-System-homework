import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;  
public class Dyncompute extends JFrame implements ActionListener 
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;  
	JTable table2;  
	JButton jb = null;
    public Dyncompute(String[][] tabledata1,String[][] tabledata2)  
    {  
    	super("1~11步后内存情形如下表所示"); 
    	jb=new JButton("返回");
		jb.addActionListener(this);
        setSize(1300,300);  
        try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }catch(Exception e){}  
        Container c=getContentPane();  
        Container c2=getContentPane(); 
        Container c1=getContentPane(); 
        
        Object[][]data={  
            {"1","","","","","",""},  
            {"2","","","","","",""},
            {"3","","","","","",""}, 
            {"4","","","","","",""}, 
            {"5","","","","","",""}, 
        };  
        Object[][]data2={  
                {"1","","","","",""},  
                {"2","","","","",""},
                {"3","","","","",""}, 
                {"4","","","","",""}, 
                {"5","","","","",""}, 
                {"6","","","","",""}, 
            };  
        String[] rowName={"编号","After1：位置与占用情况","After2：位置与占用情况","After3：位置与占用情况","After4：位置与占用情况","After5：位置与占用情况","After6：位置与占用情况"}; 
        String[] rowName2={"编号","After7：位置与占用情况","After8：位置与占用情况","After9：位置与占用情况","After10：位置与占用情况","After11：位置与占用情况"};  
        
        for (int p=0;p<6;p++) {
            if(p>=data.length||p>=tabledata1.length) break;
            else for(int pos = 1; pos <=6; pos++) {
            	if(pos>data[p].length) break;
            	else if(tabledata1[p][pos-1]!=null) data[p][pos]=tabledata1[p][pos-1];
            }
        }
        for (int p=0;p<6;p++) {
            if(p>=data2.length||p>=tabledata2.length) break;
            else for(int pos = 1; pos < 6; pos++) {
            	if(pos>data2[p].length) break;
            	else if(tabledata2[p][pos-1]!=null) data2[p][pos]=tabledata2[p][pos-1];
            }
        }
        
        table=new JTable(data,rowName);
        table2=new JTable(data2,rowName2);
        c.add(new JScrollPane(table));  
        c2.add(new JScrollPane(table2));  
        c1.add(jb); 
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(
			    new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
			);
        setVisible(true);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 
		if(e.getActionCommand()=="返回")  
        {  
		  this.dispose();
        }
	}  
}