package cars_type;

public abstract class Specialtype extends Cars {
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
		else if(jam!=1) {
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
