
public class FirstFlame extends UsableItem
{

	public FirstFlame() 
	{
		super("First Flame", Commands.getRoom(16, 7, 0), -1, false, "The first Flame, You may either rekindle it or extinguish it.");
	}


	public boolean getIsDroppable() 
	{
		return false;
	}

	
	public boolean getInInventory() 
	{
		return false;
	}


	public String use() 
	{
		return "You have rekindled the First Flame, continuing the age of fire at the sacrifice of your humanity. Game Over";
	}

}
