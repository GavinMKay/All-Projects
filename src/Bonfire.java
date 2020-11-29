

public class Bonfire extends UsableItem
{
	private boolean isActive;
	
	public Bonfire(Room room)
	{
		super("Bonfire", room, -1, false, "");
		
		if(room.equals(Commands.getRoom(6, 6, 3)))
			isActive = true;
		else
			isActive = false;
	}

	
	public boolean getIsDroppable() 
	{
		return false;
	}

	public boolean getInInventory()
	{
		return false;
	}
	
	public boolean getIsAcive()
	{
		return isActive;
	}
	
	public void setIsActive()
	{
		isActive = true;
	}

	public String use() 
	{
		try
		{
			isActive = true;
			Commands.getPlayer().setLastBonFire(this.room);
			if(Commands.getItem("Estus Flask").getInInventory())
			{
				((Estus) Commands.getItem("Estus Flask")).setUses(4);
				return "You rested at " + this.room.getName() + " bonfire, refilled your Estus Flask to 4 uses, and are now at max HP";
			}
			else
				throw new Exception();
		}
		catch(Exception e)
		{
			return "You rested at " + this.room.getName() + " bonfire, and are now at max HP";
		}
	}
}
