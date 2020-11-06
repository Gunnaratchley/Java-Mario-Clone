//Gunnar Atchley
// 10/30/2020
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view; //view instance
	Model model; //model instance
	boolean keyLeft; //if left arrow key is pressed
	boolean keyRight; //if right arrow key is pressed
	boolean keyUp; //if the up arrow key is pressed
	boolean keyDown; //if the down arrow key is pressed
	boolean spacebar; //if spacebar key is pressed
	boolean control; //if control key is pressed
	boolean addTubesEditor = false; //Tube editor is off (allows us to modify and add tubes)
	boolean addGoombasEditor = false; //Goomba editor is off
	
	Controller(Model m)
	{
		model = m;
	}
	
	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
		
	}
	
	//mouse interaction code
	public void mousePressed(MouseEvent e)
	{
		//Line to add tubes when mouse is clicked
		if (addTubesEditor == true) //adds a tube if tube editor is true
		{
			model.addTube(e.getX() + model.mario.x - view.marioStart, e.getY()); 
		}
		else if (addGoombasEditor == true) //adds a goomba if goomba editor is true
		{
			model.addGoomba(e.getX() + model.mario.x - view.marioStart, e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) 
	{ 
		
	}
	
	//Start of key code for when pressed
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode()) //switch case for different key variables
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: spacebar = true; break;
			case KeyEvent.VK_CONTROL: control = true; break;

		}
	}
	//code for when key is released
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode()) //switch case for different key variables
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: spacebar = false; break;
			case KeyEvent.VK_CONTROL: control = false;
			{
				model.addFireball(model.mario.x, model.mario.y); //adds fireball on key release of control
				break;	
			}
			

		}
		
		char c = e.getKeyChar();
		if (c== 's') //press s to save map
		{
			//System.out.println("you have clicked the s key");
			model.marshal().save("map.json");
		}
		
		if (c=='l') //press l to load map
		{
			//System.out.println("You have pressed l");
			Json j = Json.load("map.Json");
			model.unmarshal(j);
			//System.out.println("You have loaded map.json");
		}
		if (c=='t') //press t to toggle on and off tube editor functionality
		{
			addGoombasEditor = false;
			addTubesEditor = !addTubesEditor;
			System.out.println("Tube editing" + addTubesEditor);

		}
		
		if (c=='g') //press g to toggle on and off goomba editor functionality
		{
			addTubesEditor = false;
			addGoombasEditor = !addGoombasEditor;
			System.out.println("Goomba editing" + addGoombasEditor);

		}
		if (c=='q') //press q to exit game
		{
			System.exit(0);
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void update()
	{
		model.mario.savePreviousCoordinates(); //save marios previous coords
		if(keyRight) //if right arrow key is pressed
		{
			Mario.marioImageNum++; //go to next mario image
			if(Mario.marioImageNum > 4) //if the counter is > 4 reset to original image
			{
				Mario.marioImageNum = 0; //reset to first image in array
			}
			model.mario.x += 5; //move mario to the right
		}
		if(keyLeft) //if left arrow key is pressed
		{
			Mario.marioImageNum++; //go to next mario image
			if(Mario.marioImageNum > 4) //if the counter is > 4 reset to original image
			{
				Mario.marioImageNum = 0; //reset to first image in array
			}
			model.mario.x -= 5; //move mario to the left
		}
		if(spacebar) //if you pressed spacebar jump
			model.mario.jump();
	}
	
}

