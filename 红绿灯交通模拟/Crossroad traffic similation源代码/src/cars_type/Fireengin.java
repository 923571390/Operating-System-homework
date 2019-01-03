package cars_type;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fireengin extends Specialtype { 
	public Fireengin() {
		Size=3;
		try {
			File file = new File("./src/fireenginN.png");
			imageN = ImageIO.read(file);
			file = new File("./src/fireenginS.png");
			imageS = ImageIO.read(file);
			file = new File("./src/fireenginW.png");
			imageW = ImageIO.read(file);
			file = new File("./src/fireenginE.png");
			imageE = ImageIO.read(file);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
