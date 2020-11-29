public abstract class UsableItem implements Item
{
	protected int uses;
	protected boolean inInv;
	protected Room room;
	protected String desc;
	protected String name;
	
	public UsableItem(String name, Room room, int uses, boolean inInv, String desc)
	{
		this.room = room;
		this.uses = uses;
		this.inInv = inInv;
		this.desc = desc;
		this.name = name;
	}
	
	public abstract String use();
	
	public String examine()
	{
		return desc;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Room getRoom()
	{
		return room;
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	public void setInInventory(boolean inInv)
	{
		this.inInv = inInv;
	}
	
	public void setUses(int uses)
	{
		this.uses = uses;
	}
	
	public int getUses()
	{
		return uses;
	}
}
