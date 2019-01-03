package cars_type;

import light.Light;

public abstract class Normaltype extends Cars {
	public void action() {
		 arrive();
		 toturn();
		 cross();
		 jam();
		 if(arrive==1) {
			 delete();
		     Diret=null;
		     Dirtnum=0;
		 }
		 else if((cross==1&&Light.Winkle!=1&&(Diret==Directions.South||Diret==Directions.North))||jam==1) {
		 }
		 else if((cross==1&&Light.Winkle!=2&&(Diret==Directions.West||Diret==Directions.East))||jam==1) {
		 }
		 else{
			 if(toturn==1) {
                   canturn();
                   if(canturn==1){
				     delete();
				     turn();
				     refresh();
				     straight();
                   }
			 }
			 else straight();
		 }
	   
	}
}
