package cars_type;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Small extends Normaltype { 
	   public Small() {
		   Size=1;
		   try {
				File file = new File("./src/smallN.png");
				imageN = ImageIO.read(file);
				file = new File("./src/smallS.png");
				imageS = ImageIO.read(file);
				file = new File("./src/smallW.png");
				imageW = ImageIO.read(file);
				file = new File("./src/smallE.png");
				imageE = ImageIO.read(file);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}  
	   }
}
