//Gunnar Atchley
// 10/30/2020
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Fireball extends Sprite
{
	static BufferedImage fireball; //fireball image
	Model model; //model instance 
	double vert_vel; //gravity
	int floor; //ground
	final int window = 800; //view width
	
	public Fireball(int x, int y, Model m)
	{
		this.x = x;
		this.y = y;
		width = 47;
		height = 47;
		floor = 400; //floor of our game
		vert_vel = 9.0; //Gravity
		this.model = m;
		if (fireball == null)
			fireball = View.loadImage("fireball.png");

	}
	
	void draw (Graphics g)
	{
		//draw fireball at marios location
		g.drawImage(fireball, x - model.mario.x + model.mario.marioStart, y, null);
		//System.out.println("Fireball is loaded");

	}
	
	boolean update()
	{
		// gravity for fireballs
		x += 5;
		vert_vel += 2;
		y += vert_vel;
		// when fireballs hit the floor bounce up by 10
		if(y > floor - height)
		{
			vert_vel -= 10;
		}
		
		//Iterator to check for collision between goomba and fireball and to set 
		//fireball to false in order to delete and set goomba to dying status to set him on fire
		Iterator<Sprite> spriteIterator = model.sprites.iterator();
			while (spriteIterator.hasNext())
			{
				Sprite g = spriteIterator.next();
		
				if (g.isGoomba())
				{
					Goomba goomba = (Goomba)g;
					//System.out.println(g.toString()+"  " + s.toString());
					if(collision(goomba))
					{
						goomba.dying = true;
						return false;
					}
				}
			}
		//If fireball passes edge of view return false and delete the fireball
		if(x > window + model.mario.marioStart + model.mario.x)
			return false;
		
		return true;
	}
	
	//function to override if sprite is a fireball
	@Override
	public boolean isFireball()
	{
		return true;
	}
	// Code for if you click on a sprite
	boolean didIClickOnASprite (int mouse_x, int mouse_y)
	{

		if (mouse_x < x)
			return false;
		if (mouse_x > x + width)
			return false;
		if (mouse_y > y + height)
			return false;
		if (mouse_y < y)
			return false;
		return true;
	}
	
	//Overloaded collision to accept 1 sprite to check against fireball
	boolean collision (Sprite t)
	{

		if (x + width <= t.x) //right side less than sprites left side
			return false;
		if (x >= t.x + t.width) // Left side is greater than or equal to sprite right
			return false;
		if (y >= t.y + t.height) // head is underneath sprite feet
			return false;
		if (y + height <= t.y) // feet is above sprite head
			return false;

			
		//System.out.println("colliding");
		return true;
	}
}
