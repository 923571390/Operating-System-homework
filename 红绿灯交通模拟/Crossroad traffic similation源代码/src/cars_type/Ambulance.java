package cars_type;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ambulance extends Specialtype { 
   public Ambulance() {
	   Size=2;
	   try {
			File file = new File("./src/ambulanceN.png");
			imageN = ImageIO.read(file);
			file = new File("./src/ambulanceS.png");
			imageS = ImageIO.read(file);
			file = new File("./src/ambulanceW.png");
			imageW = ImageIO.read(file);
			file = new File("./src/ambulanceE.png");
			imageE = ImageIO.read(file);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}  
   }
}