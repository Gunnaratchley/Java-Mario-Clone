//Gunnar Atchley
// 10/30/2020
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Mario extends Sprite
{

	int prevx, prevy; //previous x and y coords
	static int marioImageNum; //counter for mario images 5 in total
	int framesInAir; //number of frames in air
	double vert_vel; //gravity
	static BufferedImage[] mario_images; //array of mario images
	int floor; //where mario stands can be thought of as floor or ground
	boolean onTube; // if you are on a tube
	int marioStart = 100; // mario's starting location

	
	public Mario()
	{
		floor = 400;
		vert_vel = 12.0;
		x = marioStart;
		y = 400;
		width = 60;
		height = 95;
		marioImageNum = 0;
		framesInAir = 0;
		onTube = true;
		
		mario_images = new BufferedImage[5]; //images to make it look as though he is running
		mario_images[0] = View.loadImage("mario1.png"); 
		mario_images[1] = View.loadImage("mario2.png");
		mario_images[2] = View.loadImage("mario3.png");
		mario_images[3] = View.loadImage("mario4.png");
		mario_images[4] = View.loadImage("mario5.png");
		
	}
	
	//function to allow marion to jump
	void jump()
	{
		if (framesInAir < 5) //if in air less than 5 frames allow jump
			vert_vel -= 7; // jump
	}
	
	boolean update()
	{
		//code for gravity
		vert_vel += 2;
		y += vert_vel;
		//code for stopping at floor
		if(y > floor - height)
		{
			vert_vel = 0; //stop pulling down
			y = floor - height; //reset toes to be on floor
			framesInAir = 0; // reset frames in air to 0
			if (onTube == false)
					floor = 400; // if no longer on tube reset floor to y = 400
		}
		framesInAir++;
		return true;
		
	}
	
//	public String toString()
//	{
//		return "Mario: " + super.toString();
//	}
	
	//function saves previous coords
	void savePreviousCoordinates()
	{
		prevx = x;
		prevy = y;
	}
	
	//function to push mario out of a tube
	void getOutOfTube (Sprite t)
	{
		if (x + width >= t.x && prevx + width <= t.x)
			x = t.x - width; //to the left
		if (x <= t.x + t.width && prevx >= t.x + t.width)
			x = t.x + t.width; //to the right
		if (y + height >= t.y && prevy + height <= t.y)
		{
			y = t.y - height; //on top
			floor = t.y; //reset floor to tubes top so mario can jump off tube
		}
		if (y <= t.y && prevy >= t.y)
			y = t.y + t.height; //underneath
	}
	
	//function to draw mario
	void draw (Graphics g)
	{
		g.drawImage(Mario.mario_images[marioImageNum], marioStart, y, null); //draws marios current image in array at marios starting location
	}
	
	//function to override if sprite is a mario
	public boolean isMario()
	{
		return true;
	}
	
	//function for if we clicked on mario to satisfy abstract parent sprite class
	boolean didIClickOnASprite (int mouse_x, int mouse_y)
	{
		return false; 	//Always return false because we never want to remove mario

	}
	
}

