import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;  
import java.util.Arrays;  
import java.util.Random;  
import java.util.Timer;  
import java.util.TimerTask;   
import javax.imageio.ImageIO;  
import javax.swing.JFrame;  
import javax.swing.JPanel;

import cars_type.Ambulance;
import cars_type.Cars;
import cars_type.Fireengin;
import cars_type.Large;
import cars_type.Medium;
import cars_type.Police;
import cars_type.Small;
import cars_type.road;
import light.Light; 

public class Crossroad_traffic_similation extends JPanel{
	private static final long serialVersionUID = 1L;
    public Crossroad_traffic_similation(){
        this.setVisible(true);   
        this.setLayout(null);
        this.setBounds(90, 90, 780, 780);     
        }
    
    public static BufferedImage background;
    public static BufferedImage roads;
    public static BufferedImage start;
    static{  
        try {  
        	File file = new File("./src/background.png");
            background = ImageIO.read(file);  
            file = new File("./src/road.png");
            roads = ImageIO.read(file);  
            file = new File("./src/start.png");
            start = ImageIO.read(file);  
        } 
        catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    public Cars[] cars = {}; //存储所有车
    public static final int START = 0;  
    public static final int RUNNING = 1;  
    public static final int PAUSE = 2; 
    public static Light light=new Light();
    public static void main(String[] args) {
    	JFrame frame = new JFrame("十字路口交通控制模拟");  
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    	frame.setLocationRelativeTo(null);
    	frame.setAlwaysOnTop(true);
        frame.setSize(780,780);
        frame.setAlwaysOnTop(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Crossroad_traffic_similation system = new Crossroad_traffic_similation();  
        frame.add(system);  
        frame.setVisible(true); 
        system.setVisible(true); 
        frame.add(light);
        system.initiate();
        system.action();   
    }
    public void initiate() {
    	for(int i = 0;i < 4;i++){  
    		for(int j = 0;j < 4;j++) {
    			road.roadstate[i][j]=7;
    		}
    		for(int j = 4;j < 8;j++) {
    			road.roadstate[i][j]=0;
    		}
    		for(int j = 8;j < 12;j++) {
    			road.roadstate[i][j]=7;
    		}	
        }  
    	for(int i = 4;i < 8;i++){  
    		for(int j = 0;j < 12;j++) {
    			road.roadstate[i][j]=0;
    		}
        } 
    	for(int i = 8;i < 12;i++){  
    		for(int j = 0;j < 4;j++) {
    			road.roadstate[i][j]=7;
    		}
    		for(int j = 4;j < 8;j++) {
    			road.roadstate[i][j]=0;
    		}
    		for(int j = 8;j < 12;j++) {
    			road.roadstate[i][j]=7;
    		}	
        }  
    }
    public void action(){  
    	Timer timer = new Timer();  
        timer.schedule(
        	new TimerTask(){  
        		int Timesnap=0;
                public void run() {   
                	Timesnap++;              	
                    if(Timesnap%5==1) nextOne(); //每秒创建新车 
                    for(int i = 0;i < cars.length;i++){  
                    	if(cars[i]!=null) {
                    		if(cars[i].arrive==1) cars[i]=null;                     		
                    		else {cars[i].action();
                    		}
                    	}
                    	else continue;
                    }  
                    repaint();  
                }  
           },0,1000); //界面每隔1秒变化一次  
    }  
    public void paint(Graphics g) {  
        g.drawImage(background, 0, 0, null);  
        g.drawImage(roads, 30, 30, null);  
        paintcars(g);  
    }  
    public void paintcars(Graphics g){  
        for(int i = 0;i < cars.length;i++){ 
        	if(cars[i]==null) continue;
            else if(cars[i].Dirtnum==1) g.drawImage(cars[i].imageE, 60*(cars[i].x-cars[i].Size)+30, 60*cars[i].y+30, null);  
        	else if(cars[i].Dirtnum==2) g.drawImage(cars[i].imageW, 60*cars[i].x+30, 60*cars[i].y+30, null);  
        	else if(cars[i].Dirtnum==3) g.drawImage(cars[i].imageS, 60*cars[i].x+30, 60*(cars[i].y-cars[i].Size)+30, null);  
        	else if(cars[i].Dirtnum==4) g.drawImage(cars[i].imageN, 60*cars[i].x+30, 60*cars[i].y+30, null);
        }  
    }  
    public void nextOne(){  
        Random r = new Random();  
        Cars f = null;  
        int c=r.nextInt(10);
        if(c==0||c==5||c==8) f = new  Small();  
        else if(c==1||c==6) f = new  Medium();  
        else if(c==2||c==7) f = new  Large();  
        else if(c==3) f = new  Police();  
        else if(c==4) f = new  Ambulance();  
        else  f = new  Fireengin();  
        cars = Arrays.copyOf(cars, cars.length + 1);  
        cars[cars.length - 1] = f;  
    }  
}
