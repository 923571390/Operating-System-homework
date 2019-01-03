package light;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
public class Light extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	Thread runner;
	colorT colors;
	public static int Winkle;

	public Light() {
		this.setLayout(new GridLayout(1,1,15,15)); 
		this.setBounds(140, 0, 108, 248);
		this.setBackground(new Color(51, 153, 102));
        setVisible(true);  
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}
	@Override
	protected void paintComponent(Graphics arg0) {
		Graphics2D comp2D = (Graphics2D) arg0;
		float thick = 8f; 
		Graphics2D g = comp2D; 
		g.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g.setColor(Color.gray);
		RoundRectangle2D rect = new RoundRectangle2D.Double(4, 4, 100, 240, 50, 50);
		g.draw(rect);
		comp2D.setColor(Color.black);
		comp2D.fill(rect);
		comp2D.setColor(Color.yellow);
		Shape shape = new Ellipse2D.Float(35, 30, 50, 50);
		comp2D.draw(shape);
		comp2D.fill(shape);
		comp2D.setColor(Color.green);
		shape = new Ellipse2D.Float(35, 100, 50, 50);
		comp2D.draw(shape);
		comp2D.fill(shape);
		comp2D.setColor(Color.red);
		shape = new Ellipse2D.Float(35, 170, 50, 50);
		comp2D.draw(shape);
		comp2D.fill(shape);

		if (colors.equals(colorT.Green)) {

			shape = new Ellipse2D.Float(35, 100, 50, 50);
			comp2D.setColor(Color.green);
			comp2D.draw(shape);
			comp2D.fill(shape);
		}
		if (colors.toString() == "Yellow") {

			comp2D.setColor(Color.yellow);
			shape = new Ellipse2D.Float(35, 30, 50, 50);
			comp2D.draw(shape);
			comp2D.fill(shape);
		}
		if (colors.toString() == "Red") {

			shape = new Ellipse2D.Float(35, 170, 50, 50);
			comp2D.setColor(Color.red);
			comp2D.draw(shape);
			comp2D.fill(shape);
		}
		if (colors.toString() == "Pause") {
			if (Winkle == 0) {
				shape = new Ellipse2D.Float(35, 30, 50, 50);
				comp2D.setColor(new Color(139, 101, 0));
				comp2D.draw(shape);
				comp2D.fill(shape);
			} else if (Winkle == 1) {
				shape = new Ellipse2D.Float(35, 100, 50, 50);
				comp2D.setColor(new Color(47, 79, 79));
				comp2D.draw(shape);
				comp2D.fill(shape);
			} else if (Winkle == 2) {
				shape = new Ellipse2D.Float(35, 170, 50, 50);
				comp2D.setColor(new Color(52, 0, 0));
				comp2D.draw(shape);
				comp2D.fill(shape);
			}
		}
	}
	public void run() {
		while (true) {
			changeColor(1, 8, 8);
		}
	}

	public void changeColor(int Ystop, int Bstop, int Rstop) {

		for (int i = 0; i < Ystop; i++) {
			Winkle = 0;
			colors = colorT.Yellow;
			repaint();
			pause(500);
			colors = colorT.Pause;
			repaint();
			pause(500);

		}
		for (int i = 0; i < Bstop; i++) {
			Winkle = 1;
			colors = colorT.Green;
			repaint();
			pause(500);
			colors = colorT.Pause;
			repaint();
			pause(500);
		}
		for (int i = 0; i < Rstop; i++) {
			Winkle = 2;
			colors = colorT.Red;
			repaint();
			pause(500);
			colors = colorT.Pause;
			repaint();
			pause(500);
		}
	}

	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}
}
enum colorT {
	Red, Yellow, Green, Pause
};