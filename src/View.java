//Gunnar Atchley
// 10/30/2020
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{

	Model model; //instance of model
	int marioStart; //start location away from the screen.

	
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		marioStart = 100; //marios starting x position

	}
	
	//function to load images by utilizing a try catch block to make sure it gets the
	//correct string names.
	static BufferedImage loadImage(String filename)
	{
		BufferedImage im = null;
		try
		{
			im = 
					ImageIO.read(new File(filename));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return im;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255)); //set sky color
		g.fillRect(0, 0, this.getWidth(), this.getHeight()); //fill the sky with color
		
		g.setColor(new Color(100, 255, 80)); //set ground color
		g.fillRect(0, 400, this.getWidth(), 100); //fill ground with color

		g.setColor(new Color(0, 0, 0)); //sets color to black
		g.drawLine(0, 400, 800, 400); //draws line on ground

		for(int i = 0; i < model.sprites.size(); i++) //iterate through sprites arrayList
		{
	          model.sprites.get(i).draw(g); //calls sprite draw which forces children classes to draw themselves
		}
	}
}


