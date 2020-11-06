//Gunnar Atchley
// 10/30/2020
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Goomba extends Sprite
{
	static BufferedImage image;		//Normal Goomba
	static BufferedImage burning;	//Burning goomba
	Model model;		//Model instance
	int prevx, prevy;	//Goombas previous x and y
	double vert_vel;	//Gravity
	int floor;			//Ground
	boolean direction;	//Direction
	boolean dying;		//Goomba is on fire and dying if true
	boolean dead;		//Goomba is dead if true
	int onFire;			//Counter for frames goomba is on fire

	
	public Goomba(int x, int y, Model m)
	{
		this.x = x;
		this.y = y;
		width = 45;
		height = 54;
		floor = 400;
		vert_vel = 9.0;
		direction = false;
		this.model = m;
		dying = false;
		int onFire = 0;
		boolean dead = false;
		if (image == null)
			image = View.loadImage("goomba.png");
	
		if (burning == null)
			burning = View.loadImage("goomba_fire.png");
	}
	
	Goomba(Json ob, Model m)
	{
		model = m;
		width = 45;
		height = 54;
		floor = 400;
		vert_vel = 9.0;
		direction = false;
		dying = false;
		boolean dead = false;
		int onFire = 0;
	
		if (image == null)
			image = View.loadImage("goomba.png");
	
		if (burning == null)
			burning = View.loadImage("goomba_fire.png");
		
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");

	}
	
	//goomba update function
	boolean update()
	{	//Save previous coords then apply gravity
		this.savePreviousCoordinates();
		vert_vel += 2;
		y += vert_vel;
		//Set floor and Goomba to be on floor
		if(y > floor - height)
		{
			vert_vel = 0;
			y = floor - height;
		}
		//Move right or left depending on tube collision
		if (!direction)
		{
			x += 5;
		}
		else if (direction)
		{
			x -= 5;
		}
		//Goomba is dying and is now on fire counter increments frames on fire
		if(dying == true)
		{
			onFire++;
		}
		//if on fire for more than 10 frames the Goomba is dead
		if(dying == true)
		{
			if(onFire > 10)
			{
				dead = true;
			}
		}
		//if dead set to false to remove from array
		if(dead)
			return false;
		
		return true;
	}
	//Function to draw
	void draw (Graphics g)
	{
		// Draw living goomba
		if (dying == false)
			g.drawImage(image, x - model.mario.x + model.mario.marioStart, y, null);
		// Draw burning goomba
		if (dying == true)
			g.drawImage(burning, x - model.mario.x + model.mario.marioStart, y, null);

		//System.out.println("Goomba is loaded");

	}
	// Marshals goomba into the map
	Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
	
	//function to override if sprite is a goomba
	@Override
	public boolean isGoomba()
	{
		return true;
	}
	//Function to determine if you clicked on a goomba
	boolean didIClickOnASprite (int mouse_x, int mouse_y)
	{

		if (mouse_x < x) // left
			return false;
		if (mouse_x > x + width) // right
			return false;
		if (mouse_y > y + height) // below
			return false;
		if (mouse_y < y) // above
			return false;
		return true;
	}
	//Saves previous coords
	void savePreviousCoordinates()
	{
		prevx = x;
		prevy = y;
	}
	//Forces Goomba out of tube in one of four directions
	void getOutOfTube (Sprite t)
	{
		if (x + width >= t.x && prevx + width <= t.x)
			x = t.x - width; // to the left
		if (x <= t.x + t.width && prevx >= t.x + t.width)
			x = t.x + t.width; // to the right
		if (y + height >= t.y && prevy + height <= t.y)
			y = t.y - height; // on top
		if (y <= t.y && prevy >= t.y)
			y = t.y + t.height; // underneath
		//change direction
		direction = !direction;
	}
	
//	public String toString()
//	{
//		return "Goomba: " + super.toString();
//	}
	

}
