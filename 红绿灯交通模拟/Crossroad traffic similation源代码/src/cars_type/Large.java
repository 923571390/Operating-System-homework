package cars_type;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Large extends Normaltype { 
	   public Large() {
		   Size=3;
		   try {
				File file = new File("./src/largeN.png");
				imageN = ImageIO.read(file);
				file = new File("./src/largeS.png");
				imageS = ImageIO.read(file);
				file = new File("./src/largeW.png");
				imageW = ImageIO.read(file);
				file = new File("./src/largeE.png");
				imageE = ImageIO.read(file);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}  
	   }
}

