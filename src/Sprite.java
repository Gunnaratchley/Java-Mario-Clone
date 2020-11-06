//Gunnar Atchley
// 10/30/2020
import java.awt.Graphics;

abstract class Sprite //abstract parent class for goomba, tube, mario, and fireball
{
	int x, y; //sprites x and y coords
	int width, height; //sprites width and height
	
	abstract boolean update(); //forces children to update

	abstract void draw(Graphics g); //forces children to draw themselves
	
	abstract boolean didIClickOnASprite (int mouse_x, int mouse_y);
	
	//children override the values for these is functions
	public boolean isTube() //for checking if a sprite is a tube
	{
		return false;
	}
	public boolean isMario() //for checking if a sprite is a mario
	{
		return false;
	}
	public boolean isGoomba() //for checking if a sprite is a goomba
	{
		return false;
	}
	public boolean isFireball() //for checking if a sprite is a fireball
	{
		return false;
	}
	
}
