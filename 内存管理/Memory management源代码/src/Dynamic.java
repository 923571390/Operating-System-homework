import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
public class Dynamic extends JFrame implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ArrayList<Process> list = new ArrayList<Process>();  
	public static String[][] tabledata1 = new String[6][6];
	public static String[][] tabledata2 = new String[6][5];
	
    static void FF(int number, int size, int task){
        Process date = new Process();
        date.number = number;
        date.length = size;
        int i;
        for ( i = 0; i < list.size(); i++) {
            if (date.length <= ((Process)list.get(i)).length && ((Process)list.get(i)).flag == 0) {
                break;
            }
        }
        if (i == list.size()) {
            System.out.println("没有足够的内存进行分配");
        }
        if (((Process)list.get(i)).length - date.length <= 4 && i != list.size() - 1 ) {
            ((Process)list.get(i)).number = date.number;
            ((Process)list.get(i)).flag = 1;
        }
        else {
            date.flag = 1;
            ((Process)list.get(i)).length -= date.length;
            date.startAddress = ((Process)list.get(i)).startAddress;
            ((Process)list.get(i)).startAddress += date.length;
            list.add(i, date);
        }
        disPlay(task);
    }
    
    static void BF(int number, int size,int task){
        Process date = new Process();
        date.number = number;
        date.length = size;
        int m, j;
        Process target = new Process();
            for (m = 1; m < list.size()-1; m++){
                j = m;
                target.number = ((Process)list.get(m)).number;
                target.flag = ((Process)list.get(m)).flag;
                target.length = ((Process)list.get(m)).length;
                target.startAddress = ((Process)list.get(m)).startAddress;
                while(j>0 && ((Process)list.get(j-1)).length>target.length){
                    
                    ((Process)list.get(j)).number = ((Process)list.get(j-1)).number;
                    ((Process)list.get(j)).flag = ((Process)list.get(j-1)).flag;
                    ((Process)list.get(j)).length = ((Process)list.get(j-1)).length;
                    ((Process)list.get(j)).startAddress = ((Process)list.get(j-1)).startAddress + ((Process)list.get(j)).length;
                    j--;
                }
                ((Process)list.get(j)).number = target.number;
                ((Process)list.get(j)).length = target.length;
                ((Process)list.get(j)).flag = target.flag;
                ((Process)list.get(j)).startAddress = target.startAddress-((Process)list.get(j+1)).length;
                
            }
            int i;
            for ( i = 0; i < list.size(); i++) {
                if (date.length <= ((Process)list.get(i)).length && ((Process)list.get(i)).flag == 0) {
                    //当有适合的内存，且未被使用
                    break;
                }
            }
            if (i == list.size()) {
            }
            if (((Process)list.get(i)).length - date.length <= 4 && i != list.size() - 1 ) {
                ((Process)list.get(i)).number = date.number;
                ((Process)list.get(i)).flag = 1;
            }
            else {
                //分片分配内存
                date.flag = 1;
                ((Process)list.get(i)).length -= date.length;
                date.startAddress = ((Process)list.get(i)).startAddress;
                ((Process)list.get(i)).startAddress += date.length;
                list.add(i, date);
            }
            disPlay(task);
    }
    //回收部分
    static void Free(int number, int size, int task){
        Process date = new Process();
        date.number = number;
        date.length = size;
        int i;
        for ( i = 0; i < list.size(); i++) {
            if (number==((Process)list.get(i)).number) {
                break;
            }
        }
        if (i == list.size()) {
            return;
        }
        if (i == 0 && ((Process)list.get(i+1)).flag == 0) {        //回收第一个进程，且第二个内存位置空闲，合并这两者
            ((Process)list.get(i)).flag = 0;
            ((Process)list.get(i)).length += ((Process)list.get(i+1)).length;
            ((Process)list.get(i)).number = 0;
            list.remove(1);
        }
        else if (i == 0 && ((Process)list.get(i+1)).flag == 1) {    //回收第一个进程，但第二个内存位置被占用
            ((Process)list.get(i)).number = 0;
            ((Process)list.get(i)).flag = 0;
        }
        else if (((Process)list.get(i-1)).flag == 0 && ((Process)list.get(i+1)).flag == 0) {//回收位置的进程左右两边的内存空间都空闲
            ((Process)list.get(i)).number = 0;
            ((Process)list.get(i)).flag = 0;
            ((Process)list.get(i-1)).length += ((Process)list.get(i)).length + ((Process)list.get(i+1)).length; 
            list.remove(i);
            list.remove(i+1);
        }
        else if (((Process)list.get(i-1)).flag == 0 && ((Process)list.get(i+1)).flag == 1) {//回收位置左边的内存空闲，而右边的内存被占用
            ((Process)list.get(i)).number = 0;
            ((Process)list.get(i)).flag = 0;
            ((Process)list.get(i-1)).length += ((Process)list.get(i)).length;
            list.remove(i);
        }
        else if (((Process)list.get(i-1)).flag == 1 && ((Process)list.get(i+1)).flag == 0) {//回收位置右边的内存空闲，而左边的内存被占用
            ((Process)list.get(i)).number = 0;
            ((Process)list.get(i)).flag = 0;
            ((Process)list.get(i)).length += ((Process)list.get(i+1)).length;
            list.remove(i+1);
        }else {//左右两边的内存都被占用
            ((Process)list.get(i)).number = 0;
            ((Process)list.get(i)).flag = 0;
        }
        disPlay(task);
    }
    static void disPlay(int task ){
        for (int p = 0; p < list.size(); p++) {
            if(task<7) {
            	tabledata1[p][task-1]=String.valueOf(list.get(p));
            }
            else if(task>=7){
            	tabledata2[p][task-7]=String.valueOf(list.get(p));
            }
        }
    }
    static void check() {
    	for(int i=0;i<6;i++) {
    		for(int j=0;j<6;j++) {
    			System.out.printf("%d %d\n",i,j);
    			System.out.println(tabledata1[i][j]);
    		}
    	}
    	for(int i=0;i<6;i++) {
    		for(int j=0;j<5;j++) {
    			System.out.println(tabledata2[i].length);
    			System.out.printf("%d %d\n",i,j);
    			System.out.println(tabledata2[i][j]);
    		}
    	}
    }

    
	
	
	    // 定义组件
		JButton jb1,jb2,jb3=null;
		JPanel jp1,jp2,jp3=null;
		JLabel jlb1=null;
		public  Dynamic()    //不能申明为void!!!!!否则弹不出新界面
		{
			//创建组件
			jb1=new JButton("首次适应算法");
			jb1.addActionListener(this);//加入事件监听
			jb2=new JButton("最佳适应算法");
			jb2.addActionListener(this);//加入事件监听
			jb3=new JButton("返回");
			jb3.addActionListener(this);//加入事件监听
			jp1=new JPanel();
			jp2=new JPanel();
			jp3=new JPanel();
			jlb1=new JLabel("请选择内存分配方式：");	
			jp1.add(jlb1);
			jp2.add(jb1);
			jp2.add(jb2);
			jp3.add(jb3);
			this.add(jp1);
			this.add(jp2);
			this.add(jp3);
			//设置布局管理器
			this.setLayout(new GridLayout(3,1));
			this.setTitle("动态分区内存管理");
			this.setSize(300, 150);//窗体大小
			this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
			this.setVisible(true);//显示窗体

}
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="返回") {
				this.dispose();
			}
	        if(e.getActionCommand()=="首次适应算法")  
	        {   list = new ArrayList<Process>(); 
	            tabledata1 = new String[6][13];
	    	    tabledata2 = new String[6][11];
	        	Process pro = new Process(0, 0, 640, 0);
	        	list.add(pro);
	        	FF(1,130,1);
		        FF(2,60,2);
		        FF(3,100,3);
		        Free(2,60,4);
		        FF(4,200,5);
		        Free(3,100,6);
		        Free(1,130,7);
		        FF(5,140,8);
		        FF(6,60,9);
		        FF(7,50,10);
		        Free(6,60,11);
	        	new Dyncompute(tabledata1,tabledata2); 
	        	check();
	        }
		    if(e.getActionCommand()=="最佳适应算法")  
		    {  
		    	list = new ArrayList<Process>(); 
                tabledata1 = new String[6][13];
    	        tabledata2 = new String[6][11];
		    	Process pro = new Process(0, 0, 640, 0);
        	    list.add(pro);
		        BF(1,130,1);
		        BF(2,60,2);
		        BF(3,100,3);
		        Free(2,60,4);
		        BF(4,200,5);
		        Free(3,100,6);
		        Free(1,130,7);
		        BF(5,140,8);
		        BF(6,60,9);
		        BF(7,50,10);
		        Free(6,60,11);
		        new Dyncompute(tabledata1,tabledata2); 
		    }
		}
}
