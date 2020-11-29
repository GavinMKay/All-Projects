public abstract class EdibleItem extends UsableItem 
{
	public EdibleItem(String name, Room room, int uses, boolean inInv, String desc)
	{
		super(name, room, uses, inInv, desc);
	}
	
	public String use()
	{
		super.uses--;
		return this.eat();
	}
	
	public abstract String eat();
}
