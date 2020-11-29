public class FarronCandle extends UsableItem
{
	private boolean isUsed = false;
	
	public FarronCandle(String name, Room room, int uses, boolean inInv, String desc) 
	{
		super(name, room, uses, inInv, desc);
	}

	
	public boolean getIsDroppable() 
	{
		return false;
	}
	
	public boolean getInInventory() 
	{
		return inInv;
	}

	public boolean isUsed()
	{
		return isUsed;
	}
	
	public String use() 
	{
		isUsed = true;
		Commands.setCandlesUsed(Commands.getCandlesUsed() + 1);
		return "You extinguished the Candle!";
	}

}
