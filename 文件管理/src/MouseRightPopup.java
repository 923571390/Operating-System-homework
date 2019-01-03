import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
class MouseRightPopup extends JPopupMenu
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem[] item;
	public MouseRightPopup()
	{
		super();
		item=new JMenuItem[4];
		item[0]=new JMenuItem("打开");
		item[1]=new JMenuItem("删除");
		item[2]=new JMenuItem("复制");
		item[3]=new JMenuItem("粘贴");
		this.add(item[0]);
		this.add(item[1]);
		this.add(item[2]);
		this.add(item[3]);
	}
	public void addItemListener(int i,ActionListener a)
	{
		item[i].addActionListener(a);
	}
}
