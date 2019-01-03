package cars_type;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Medium extends Normaltype { 
	   public Medium() {
		   Size=2;
		   try {
				File file = new File("./src/mediumN.png");
				imageN = ImageIO.read(file);
				file = new File("./src/mediumS.png");
				imageS = ImageIO.read(file);
				file = new File("./src/mediumW.png");
				imageW = ImageIO.read(file);
				file = new File("./src/mediumE.png");
				imageE = ImageIO.read(file);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}  
	   }
}
