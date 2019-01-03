package cars_type;
import java.awt.image.BufferedImage;
import java.util.Random;  
public abstract class Cars {
	public int x; //车头x坐标  
	public int y; //车头y坐标  
    public int Size; //车长
    public BufferedImage imageE;
    public BufferedImage imageW;
    public BufferedImage imageS; 
    public BufferedImage imageN; //车各方向的图片  
    public abstract void action();
    public int cross=0;
    public int jam=0;
    public int arrive=0;
    public int toturn=0;
    public int canturn=0;
    public int Dirtnum;
	protected Directions froms;
	protected Directions gos;
	public Directions Diret;
	enum Directions{ East, West , South , North}
    public void cross() {
    	if(arrive==1||froms==null) cross=0;
        else if(froms.equals(Directions.East)&&x==8) {
    		cross=1;
    	}
    	else if(froms.equals(Directions.West)&&x==3) {
    		cross=1;
    	}
    	else if(froms.equals(Directions.North)&&y==3) {
    		cross=1;
    	}
    	else if(froms.equals(Directions.South)&&y==8) {
    		cross=1;
    	}
    	else cross=0;
    };  //判断是否到达路口
    public void Dir() {
    	if (froms==null||arrive==1) Diret=null;
        else if(froms.equals(Directions.East)) {
    		Diret=Directions.West;
    	}
    	else if(froms.equals(Directions.West)) {
    		Diret=Directions.East;
    	}
    	else if(froms.equals(Directions.North)) {
    		Diret=Directions.South;
    	}
    	else if(froms.equals(Directions.South)) {
    		Diret=Directions.North;
    	}
    	if(Diret==null) Dirtnum=0;
    	else if(Diret.equals(Directions.East)) Dirtnum=1;
    	else if(Diret.equals(Directions.West)) Dirtnum=2;
    	else if(Diret.equals(Directions.North)) Dirtnum=4;
    	else if(Diret.equals(Directions.South)) Dirtnum=3;
    	
    };  //判断此时前进方向
    public void jam() {
    	if(Diret==null||arrive==1) jam=0;
        else if(Diret.equals(Directions.East)) {
    		if(x<11&&road.roadstate[x+1][y]==1) jam=1;
    		else jam=0;
    	}
    	else if(Diret.equals(Directions.West)) {
    		if(x>0&&road.roadstate[x-1][y]==1) jam=1;
    		else jam=0;
    	}
    	else if(Diret.equals(Directions.North)) {
    		if(y>0&&road.roadstate[x][y-1]==1) jam=1;
    		else jam=0;
    	}
    	else if(Diret.equals(Directions.South)) {
    		if(y<11&&road.roadstate[x][y+1]==1) jam=1;
    		else jam=0;
    	}
    };  //判断前方是否有车
    public void canturn() {
    	if(Diret==null||arrive==1) canturn=0;
        else if(Diret.equals(Directions.East)||Diret.equals(Directions.West)) {
    		if(gos.equals(Directions.North)) {
    			canturn=1;
    			for(int i=0;i<=Size;i++) {
    			    if(road.roadstate[x][y-1+i]==1&&i!=1) canturn=0;
    			}
    		}
    		else if(gos.equals(Directions.South)) {
    			canturn=1;
    			for(int i=0;i<=Size;i++) {
    			    if(road.roadstate[x][y+1-i]==1&&i!=1) canturn=0;
    			}
    		}
    	}
        else if(Diret.equals(Directions.North)||Diret.equals(Directions.South)) {
    		if(gos.equals(Directions.West)) {
    			canturn=1;
    			for(int i=0;i<=Size;i++) {
    			    if(road.roadstate[x-1+i][y]==1&&i!=1) canturn=0;
    			}
    		}
    		else if(gos.equals(Directions.East)) {
    			canturn=1;
    			for(int i=0;i<=Size;i++) {
    			    if(road.roadstate[x+1-i][y]==1&&i!=1) canturn=0;
    			}
    		}
    	}
    	System.out.printf("Canturn is %d\n",canturn); 
    };  //判断前方是否有车
    public void arrive() {
        if(gos==null) arrive=1;
        else if(gos.equals(Directions.East)&&x==11) {
    		arrive=1;
    	}
    	else if(gos.equals(Directions.West)&&x==0) {
    		arrive=1;
    	}
    	else if((gos.equals(Directions.North)||Diret.equals(Directions.North))&&y==0) {
    		arrive=1;
    	}
    	else if(gos.equals(Directions.South)&&y==11) {
    		arrive=1;
    	}
    	else arrive=0;
    };  //判断是否到达
    public void delete(){ 
    	if(Diret==null) {x=0;y=0;}
        else if(Diret.equals(Directions.East)) {
    	    for(int i=x;i>=x-Size;i--) {
    	    	road.roadstate[i][y]=0;
    	    }
    	}
    	else if(Diret.equals(Directions.West)){
    		for(int i=x;i<=x+Size;i++) {
    	    	road.roadstate[i][y]=0;
    	    }
        }
    	else if(Diret.equals(Directions.South)) {
    		for(int i=y;i>=y-Size;i--) {
    	    	road.roadstate[x][i]=0;
    	    }
        }
    	else if(Diret.equals(Directions.North))  {
    		for(int i=y;i<=y+Size;i++) {
    	    	road.roadstate[x][i]=0;
    	    }
    	}
    };
    public void refresh(){ 
    	if(Diret==null) {x=0;y=0;}
        else if(Diret.equals(Directions.East)) {
    	    for(int i=x;i>=x-Size;i--) {
    	    	road.roadstate[i][y]=1;
    	    }
    	}
    	else if(Diret.equals(Directions.West)){
    		for(int i=x;i>=x+Size;i++) {
    	    	road.roadstate[i][y]=1;
    	    }
        }
    	else if(Diret.equals(Directions.South)) {
    		for(int i=y;i>=y-Size;i--) {
    	    	road.roadstate[x][i]=1;
    	    }
        }
    	else if(Diret.equals(Directions.North))  {
    		for(int i=y;i>=y+Size;i++) {
    	    	road.roadstate[x][i]=1;
    	    }
    	}
    };
    public void toturn(){
    	if(arrive==1||gos==null||Diret==null) toturn=0;
    	else if(gos!=Diret) {
    		if (gos==null) toturn=0;
    	    else if(gos.equals(Directions.East)) {
    		    if(froms.equals(Directions.North)&&((x==4&&y==7)||(x==5&&y==6))) {
    		    	toturn=1;
    		    }	
    		    else if(froms.equals(Directions.South)&&((x==6&&y==6)||(x==7&&y==7))){
    			    toturn=1;
    		    }
    		    else toturn=0;
    	    }
    	    else if(gos.equals(Directions.West)) {
    		    if(froms.equals(Directions.North)&&((x==4&&y==4)||(x==5&&y==5))) {
    		    	toturn=1;
    		    }
    		    else if(froms.equals(Directions.South)&&((x==6&&y==5)||(x==7&&y==4))){
    			    toturn=1;
    		    }
    		    else toturn=0;
    	    }
    	    else if(gos.equals(Directions.North)) {
    		    if(froms.equals(Directions.East)&&((x==7&&y==4)||(x==6&&y==5))) {
    		    	toturn=1;
    		    }
    		    else if(froms.equals(Directions.West)&&((x==6&&y==6)||(x==7&&y==7))){
    			    toturn=1;
    		    }
    		    else toturn=0;
    	    }
    	    else if(gos.equals(Directions.South)) {
    		    if(froms.equals(Directions.East)&&((x==4&&y==4)||(x==5&&y==5))){
    		    	toturn=1;
    		    }
    		    else if(froms.equals(Directions.West)&&((x==5&&y==6)||(x==4&&y==7))){
    			    toturn=1;
    		    }
    		    else toturn=0;
    	    }
    	}
    	else toturn=0;
    	System.out.printf("Toturn is %d\n",toturn); 
    }
    public void turn(){
    	Diret=gos;
    	if(gos==null) Dirtnum=0;
    	else if(gos.equals(Directions.East)) Dirtnum=1;
    	else if(gos.equals(Directions.West)) Dirtnum=2;
    	else if(gos.equals(Directions.North)) Dirtnum=4;
    	else if(gos.equals(Directions.South)) Dirtnum=3;
    }
    public void straight(){ 
    	if(Diret==null) {x=0;y=0;}
        else if(Diret.equals(Directions.East)) {
    	    if(x-Size>=0&&x<11) road.roadstate[x-Size][y]=0;
    	    x++;
    	    road.roadstate[x][y]=1;
    	}
    	else if(Diret.equals(Directions.West)){
            if(x+Size<=11&&x>0) road.roadstate[x+Size][y]=0;
        	x--;
        	road.roadstate[x][y]=1;
        }
    	else if(Diret.equals(Directions.South)) {
        	if(y-Size>=0&&y<11) road.roadstate[x][y-Size]=0;
        	y++;
        	road.roadstate[x][y]=1;
        }
    	else if(Diret.equals(Directions.North))  {
    		if(y+Size<=11&&y>0) road.roadstate[x][y+Size]=0;
        	y--;
        	road.roadstate[x][y]=1;
        	}
    }
	public Cars() { 
		Random a = new Random();  
		Random b = new Random(); 
		
		int m=a.nextInt(20)%4;
		int n=b.nextInt(20)%4;
		if(m<0) m++;
		if(n<0) n++;
		if(m>3) m--;
		if(n>3) n--;
		
		if(m==0) {
			froms=Directions.West;  
			Diret=Directions.East;
			x=0;
			if(road.roadstate[0][7]==0) y=7;
			else if(road.roadstate[0][6]==0) y=6;
			else m=1;
		}
		if(m==1) {
			froms=Directions.East; 
			Diret=Directions.West;
			x=11;
			if(road.roadstate[11][4]==0) y=4;
			else if(road.roadstate[11][5]==0) y=5;
			else m=2;
		}
		if(m==2) {
			froms=Directions.North; 
			Diret=Directions.South;
			y=0;
			if(road.roadstate[4][0]==0) x=4;
			else if(road.roadstate[5][0]==0) x=5;
			else m=3;
		}
		if(m==3) {
			froms=Directions.South;  
			Diret=Directions.North;
			y=11;
			if(road.roadstate[7][11]==0) x=7;
			else if(road.roadstate[6][11]==0) x=6;
			else {
				x=0;y=0;
				gos=null;
				Diret=null;
			}
		}
		
		if(m==n&&m<3) n++;
		else if(m==n&&m>0) n--;
		
		if(n==0) {
			gos=Directions.West;  
		}
		else if(n==1) {
			gos=Directions.East;  
		}
		else if(n==2) {
			gos=Directions.North;  
		}
		else if(n==3) {
			gos=Directions.South;  
		}
		road.roadstate[x][y]=1;
		Dir();
    } 
}