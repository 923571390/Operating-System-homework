import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Request extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton jb1, jb2, jb3 = null;
	JPanel jp1, jp2, jp3 = null;
	JLabel jlb1 = null;
	public static Block[] block = new Block[4];
	public static final int Bsize = 4;
	public static StringBuilder sb = new StringBuilder();
	public int pc;
	public int n;
	public int[] temp = new int[320];

	void initiate() {
		for (int i = 0; i < Bsize; i++) {
			block[i] = new Block();
			block[i].pagenum = -1;
			block[i].accessed = 0;
		}
		pc = n = 0;
		sb.delete(0, sb.length());
	}

	int Exist(int curpage) {
		for (int i = 0; i < Bsize; i++) {
			if (block[i] == null || i >= block.length) break;
			else if (block[i].pagenum == curpage) return i;
		}
		return -1;
	}

	int Space() {
		for (int i = 0; i < Bsize; i++) {
			if (block[i] == null || i >= block.length) break;
			else if (block[i].pagenum == -1) {
				System.out.printf("fi %d\n", i);
				return i;
			} 
		}
		return -1;
	}

	int Replace() {
		int pos = 0;
		for (int i = 0; i < Bsize; i++) {
			if (block[i] == null || i >= block.length) break;
			else if (block[i].accessed > block[pos].accessed) pos = i;
		}
		return pos;
	}

	void Rand() {
		int flag = 0;
		Random a = new Random();
		Random b = new Random();
		Random c = new Random();
		pc = a.nextInt(320);
		for (int i = 0; i < 320; i++) {
			temp[i] = pc;
			System.out.printf("temp%d %03d\n", i, temp[i]);
			if (flag % 2 == 0) pc = pc % 320 + 1;
			if (flag == 1) pc = b.nextInt(pc - 1);
			if (flag == 3) pc = pc + 1 + (c.nextInt(320 - (pc + 1)));
			flag = flag % 4 + 1;
		}
	}

	void LRU() {
		Rand();
		initiate();
		int exist, space, position;
		int curpage;
		for (int i = 0; i < 320; i++) {
			pc = temp[i];
			curpage = pc / 10;
			exist = Exist(curpage);
			if (exist == -1) {
				space = Space();
				if (space != -1) {
					block[space].pagenum = curpage;
					n = n + 1;
				} 
				else {
					position = Replace();
					if (position >= block.length || block[position] == null) break;
					else {
						block[position].pagenum = curpage;
						n++;
					}
				}
			} 
			else block[exist].accessed = -1;
			for (int j = 0; j < 4; j++) {
				block[j].accessed++;
			}
		}
	}

	void FIFO() {
		Rand();
		initiate();
		int exist, space, position;
		int curpage;
		for (int i = 0; i < 320; i++) {
			pc = temp[i];
			curpage = pc / 10;
			exist = Exist(curpage);
			if (exist == -1) {
				space = Space();
				if (space != -1) {
					block[space].pagenum = curpage;
					n = n + 1;
				} 
				else {
					position = Replace();
					if (position >= block.length || block[position] == null) {
						break;
					} 
					else {
						block[position].pagenum = curpage;
						n++;
						block[position].accessed--;
					}
				}
			}
			for (int j = 0; j < Bsize; j++)
				block[j].accessed++;
		}
	}

	public Request() 
	{
		jb1 = new JButton("FIFO算法");
		jb1.addActionListener(this);
		jb2 = new JButton("LRU算法");
		jb2.addActionListener(this);
		jb3 = new JButton("返回");
		jb3.addActionListener(this);
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jlb1 = new JLabel("请选择内存分配方式：");
		jp1.add(jlb1);
		jp2.add(jb1);
		jp2.add(jb2);
		jp2.add(jb3);
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.setLayout(new GridLayout(3, 1));
		this.setTitle("请求分区内存管理");
		this.setSize(300, 150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "返回") {
			this.dispose();
		}
		if (e.getActionCommand() == "FIFO算法") {
			FIFO();
			new Reqcompute(n, pc, temp);

		}
		if (e.getActionCommand() == "LRU算法") {
			LRU();
			new Reqcompute(n, pc, temp);
		}
	}
}
