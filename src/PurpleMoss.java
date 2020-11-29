public class PurpleMoss extends EdibleItem
{
	public PurpleMoss()
	{
		super("Purple Moss Clump", Commands.getRoom(10, 4, 1), 4, false, "Purple Moss Clumps are eaten to cure and prevent poison from taking hold. They can be used 4 times.");
	}
	
	public String use()
	{
		return eat();
	}
	
	public String eat()
	{
		Commands.getPlayer().setCured(true);
		return "You ate the purple moss clump, and are now immune to the effects of poison for 25 seconds.";
	}
	
	public String Drop()
	{
		return "You dropped the purple moss clump";
	}
	
	public String examine()
	{
		return super.examine();
	}
	
	public boolean getInInventory() 
	{
		return super.inInv;
	}
	
	public boolean getIsDroppable()
	{
		return true;
	}
}
