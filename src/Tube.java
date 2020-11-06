import java.awt.image.BufferedImage;
import java.awt.Graphics;


class Tube extends Sprite
{
	Model model; //instance of model
	static BufferedImage tube_image; //variable holding tube image
	
//	public String toString()
//	{
//		return "Tube: " + super.toString();
//	}
	
	Tube(int x, int y, Model m)
	{
		this.x = x;
		this.y = y;
		width = 55;
		height = 400;
		model = m;
		if (tube_image == null)
			tube_image = View.loadImage("tube.png");
	}
	
	//function to override if sprite is a tube
	@Override
	public boolean isTube()
	{
		return true;
	}
	
	boolean update()
	{
		return true; //tubes can't die so always return true never remove unless editing map;
	}
	
	//function to check if you clicked on a tube
	boolean didIClickOnASprite (int mouse_x, int mouse_y)
	{

		if (mouse_x < x) // left
			return false;
		if (mouse_x > x + width) //right
			return false;
		if (mouse_y > y + height) //below
			return false;
		if (mouse_y < y) //above
			return false;
		return true;
	}
	
	//function to create Json tube objects and add to map
	Json marshal()
    {
        Json ob = Json.newObject(); //creates object
        ob.add("x", x); //adds tube x value
        ob.add("y", y); //adds tube y value
        return ob;
    }
	
	//Tube constructor for our Json object
	Tube(Json ob, Model m)
	{
		model = m;
		width = 55;
		height = 400;
		if (tube_image == null)
			tube_image = View.loadImage("tube.png");
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");

	}
	
	//function to draw tube image
	void draw (Graphics g)
	{
		g.drawImage(tube_image, x - model.mario.x + model.mario.marioStart, y, null);
		//System.out.println("tube is loaded");
	}
	

	
}

