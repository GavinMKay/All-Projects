import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Room 
{
	private boolean canGoNorth;
	private boolean canGoSouth;
	private boolean canGoWest;
	private boolean canGoEast;
	private boolean canGoUp;
	private boolean canGoDown;
	private String description;
	private String name;
	private ImageIcon background;
	
	
	public Room() 
	{
		canGoNorth = false;
		canGoSouth = false;
		canGoEast = false;
		canGoWest = false;
		canGoUp = false;
		canGoDown = false;
		name = "The Void";
		description = "This is not a room.";
	}
	public Room(String name, boolean canGoNorth, boolean canGoSouth, boolean canGoEast, boolean canGoWest, boolean canGoUp, boolean canGoDown, String description, ImageIcon background) 
	{
		this.name = name;
		this.description = description;
		this.canGoEast = canGoEast;
		this.canGoWest = canGoWest;
		this.canGoSouth = canGoSouth;
		this.canGoNorth = canGoNorth;
		this.canGoUp = canGoUp;
		this.canGoDown = canGoDown;
		this.background = background;
	}
	
	
	
	public String getDescription()
	{
		return description;
	}
	public String getName()
	{
		return name;
	}
	public boolean getCanGoNorth() 
	{
		return canGoNorth;
	}
	public boolean getCanGoSouth() 
	{
		return canGoSouth;
	}
	public boolean getCanGoEast()
	{
		return canGoEast;
	}
	public boolean getCanGoWest()
	{
		return canGoWest;
	}
	public boolean getCanGoUp() 
	{
		return canGoUp;
	}
	public boolean getCanGoDown() 
	{
		return canGoDown;
	}
	public ImageIcon getImage() 
	{
		return background;
	}
	public int getX()
	{
		for(int l = 0; l < 6; l++) 
		{
			for(int r = 0; r < 18; r++)
			{
				for(int c = 0; c < 13; c++) 
				{
					if (this.equals(Commands.getRoom(r, c, l)))
						return c;
				}
			}
		}
		return 0;
	}
	public int getY()
	{
		for(int l = 0; l < 6; l++) 
		{
			for(int r = 0; r < 18; r++)
			{
				for(int c = 0; c < 13; c++) 
				{
					if (this.equals(Commands.getRoom(r, c, l)))
						return r;
				}
			}
		}
		return 0;
	}
	public int getZ()
	{
		for(int l = 0; l < 6; l++) 
		{
			for(int r = 0; r < 18; r++)
			{
				for(int c = 0; c < 13; c++) 
				{
					if (this.equals(Commands.getRoom(r, c, l)))
						return l;
				}
			}
		}
		return 0;
	}
	
	
	
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setCanGoNorth(boolean canGoNorth)
	{
		this.canGoNorth = canGoNorth;
	}
	public void setCanGoSouth(boolean canGoSouth) 
	{
		this.canGoSouth = canGoSouth;
	}
	public void setCanGoEast(boolean canGoEast)
	{
		this.canGoEast = canGoEast;
	}
	public void setCanGoWest(boolean canGoWest) 
	{
		this.canGoWest = canGoWest;
	}
	public void setCanGoUp(boolean canGoUp) 
	{
		this.canGoUp = canGoUp;
	}
	public void setCanGoDown(boolean canGoDown) 
	{
		this.canGoDown = canGoDown;
	}
	public void setCanGo(String direction)
	{
		direction += " ";
		for(int i = 0; i <= 1; i++)
		{
			try
			{
				if(direction.substring(direction.indexOf('n'), direction.indexOf('h') + 1).equals("north"))
					canGoNorth = true;

			}
			catch(Exception e1)
			{
				try
				{
					if(direction.substring(direction.indexOf('s'), direction.indexOf('h') + 1).equals("south"))
						canGoSouth = true;
				}
				catch(Exception e2)
				{
					try
					{
						if(direction.substring(direction.indexOf('e'), direction.indexOf('t') + 1).equals("east"))
							canGoEast = true;
					}
					catch(Exception e3)
					{
						try
						{
							if(direction.substring(direction.indexOf('w'), direction.indexOf('t') + 1).equals("west"))
								canGoWest = true;
						}
						catch(Exception e4)
						{
							try
							{
								if(direction.substring(direction.indexOf('u'), direction.indexOf('p') + 1).equals("up"))
									canGoUp = true;
							}
							catch(Exception e5)
							{
								try
								{
									if(direction.substring(direction.indexOf('d'), direction.indexOf('n') + 1).equals("down"))
										canGoDown = true;
								}
								catch(Exception e6) {}
							}
						}
							
					}
				}
			}
			direction = direction.substring(direction.indexOf(' ') + 1);
		}
	}
	
	public String toString(){
		String north = "canGoNorth = " + Boolean.toString(canGoNorth);
		String south = "canGoSouth = " + Boolean.toString(canGoSouth);
		String east = "canGoEast = " + Boolean.toString(canGoEast);
		String west = "canGoWest = " + Boolean.toString(canGoWest);
		String up = "canGoUp = " + Boolean.toString(canGoUp);
		String down = "canGoDown = " + Boolean.toString(canGoDown);
		return name + ":\n" + description + "\n" + north + "\n" + south + "\n" + east + "\n" + west + "\n" + up + "\n" + down;
	}
	
}
