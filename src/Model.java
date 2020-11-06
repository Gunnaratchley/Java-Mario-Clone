//Gunnar Atchley
// 10/30/2020
import java.util.ArrayList; //import of the ArrayList class
import java.util.Iterator;

class Model
{
	ArrayList<Sprite> sprites; //sprites array
	Mario mario;  //mario instance
	int counter; //counter to space out fireballs


	Model()
	{
		sprites = new ArrayList<Sprite>(); //create new ArrayList of sprites
		mario = new Mario();
		sprites.add(mario); //add mario to sprites array
		int counter = 0; 		

	}

	public void update()
	{
		//Code to check for collision between mario and tube as well as goomba and tube
		Iterator<Sprite> spriteIterator = sprites.iterator(); //First iterator
		while (spriteIterator.hasNext()) //iterate through
		{
			Iterator<Sprite> spriteIterator2 = sprites.iterator(); //second iterator
				Sprite s = spriteIterator.next(); //next/current sprite
				//System.out.println(s.toString());
				if (!s.update()) //if update returns false object needs to be remove
				{
					spriteIterator.remove(); //remove sprite from array
				}
				if (s.isTube()) //if sprite is tube
				{
					if(collision(mario, s)) //check for collision between mario and tube
					{
						mario.getOutOfTube((Tube)s); //push mario out of tube
					}
					while (spriteIterator2.hasNext()) //second iteration through the collection
					{
						Sprite g = spriteIterator2.next(); //next sprite
				
						if (g.isGoomba()) //if goomba
						{
							Goomba goomba = (Goomba)g; //cast sprite as goomba
							//System.out.println(g.toString()+"  " + s.toString());
							if(collision(goomba, s)) //check for collision between goomba and tubes
							{
								goomba.getOutOfTube((Tube)s); //push goomba out of tube
							}
						}
					}
				}
				
			}
			counter++; //increment space between fireballs
		}
				
	//Function to add tube
	public void addTube (int mouse_x, int mouse_y)
	{
		boolean clickedOnTube = false; //you have not clicked on a tube
		Tube t = new Tube(mouse_x, mouse_y, this); // create tube instance
		for(int i = 0; i < sprites.size(); i++) //iterate through sprites array
		{
			if(sprites.get(i) instanceof Tube) //if sprite is tube. can also use sprites.get(i).isTube() == true;
			{
				Tube temp = (Tube)sprites.get(i); //cast sprite as tube setting equal to an instance of tube
				if(temp.didIClickOnASprite(mouse_x, mouse_y)) // if you clicked on a tube
				{
					sprites.remove(temp); //remove tube
					clickedOnTube = true; //you clicked on a tube
					break;
				}
			}
		}
		if (!clickedOnTube) //if you didn't click on a tube
		{
			sprites.add(t); //add tube
		}
		
		
	}
	//Function to add Goombas
	public void addGoomba (int x, int y)
	{
		Goomba g = new Goomba(x, y, this); //create new goomba
		boolean goombaExists = false;
		//System.out.println("goomba was added");
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i) instanceof Goomba) // if sprite is a goomba
			{
				Goomba temp = (Goomba)sprites.get(i); //cast as a gomba
				if(temp.didIClickOnASprite(x, y)) // if you click on a goomba remove goomba
				{
					sprites.remove(temp); //remove goomba
					goombaExists = true; //there was a goomba there
					break;
				}
			}
		}
		if(!goombaExists) //if there is no goomba add goomba
		{
			sprites.add(g); //add goomba to sprites array
		}
	}
	// Function to add fireball
	public void addFireball (int x, int y)
	{
		if (counter > 10) // to seperate the fireballs a little bit
		{
			Fireball f = new Fireball(x, y, this); //create new fireball object
			sprites.add(f); // add fireball to sprites array
			counter = 0; // reset the counter to zero since the fireball is already in the array
		}
		
	}
	
	//fucntion to save objects to a map
	Json marshal()
	{
		Json ob = Json.newObject();
		Json spriteOb = Json.newObject();
		Json tempList = Json.newList();
		ob.add("sprites", spriteOb);  //sprites object for our map
		spriteOb.add("tubes", tempList); //object to hold list of tubes in map
      for(int i = 0; i < sprites.size(); i++) //iterate through sprites array
      {
    	  if(sprites.get(i).isTube()) //if sprite is a tube
    	  {
    		  Tube t = (Tube)sprites.get(i);  //cast sprite as a tube
    		  tempList.add(t.marshal());  //add to map.json
    	  }
      }
      tempList = Json.newList(); //creates new list to keep our tubes and goombas seperate in our map
      spriteOb.add("goombas", tempList); //object to hold list of goombas in map
      for(int i = 0; i < sprites.size(); i++) //iterate through sprites array
      {
    	  if(sprites.get(i).isGoomba())	 //if sprite is a goomba
    	  {
    		  Goomba g = (Goomba)sprites.get(i); //cast sprite as a goomba
    		  tempList.add(g.marshal());  //add to map.json
    	  }
      }
      return ob;
	}
	
	//Function to add objects saved in map into our model
	void unmarshal (Json ob)
	{
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
		Json jsonList = ob.get("sprites");
		Json tubesList = jsonList.get("tubes");
		Json goombasList = jsonList.get("goombas");
		for (int i = 0; i < tubesList.size(); i++)
		{
			sprites.add(new Tube(tubesList.get(i), this)); //adds tubes from map.json
		}
		for (int i = 0; i < goombasList.size(); i++) 
		{
			sprites.add(new Goomba(goombasList.get(i), this)); //adds goombas from map.json
		}
		
	}
	
	// Checks for collision between two sprites typical examples include mario and tube or goomba and tube
	// Checks for when mario is not touching 
	boolean collision (Sprite s, Sprite t)
	{

		if (s.x + s.width <= t.x) //right side less than sprites left side
			return false;
		if (s.x >= t.x + t.width) // Left side is greater than or equal to sprite right
			return false;
		if (s.y >= t.y + t.height) // head is underneath sprite feet
			return false;
		if (s.y + s.height <= t.y) // feet is above sprite head
			return false;
		// code to reset ground if colliding
		if (s.isMario() || t.isMario())
			mario.onTube = false;
			
		//System.out.println("colliding");
		return true; //returns ture if collison occured
	}
	

	

}