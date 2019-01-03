package cars_type;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Police extends Specialtype { 
	public Police() {
		Size=1;
		try {
			File file = new File("./src/policeN.png");
			imageN = ImageIO.read(file);
			file = new File("./src/policeS.png");
			imageS = ImageIO.read(file);
			file = new File("./src/policeW.png");
			imageW = ImageIO.read(file);
			file = new File("./src/policeE.png");
			imageE = ImageIO.read(file);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
