public class Estus extends EdibleItem
{
	public Estus()
	{
		super("Estus Flask", Commands.getRoom(6, 6, 3), 2, false, "\"A Flask of Fire\" The Estus Flask heals the user to max HP every use and will be refilled everytime the user visits a bonfire.");
	}
	
	public String use()
	{
		return eat();
	}
	
	public String eat()
	{
		if(uses > 0)
		{
			Commands.getPlayer().setHP(Commands.getPlayer().getMaxHP());
			Commands.getPlayer().refreshHP();
			return "You drank from the estus flask, your HP is now at max: " + Commands.getPlayer().getHealthPercentage() + Commands.getPlayer().getHealthBar();
		}
		else 
			return "There is no more estus in the estus flask.";
	}
	
	public String drop()
	{
		return "You dropped the estus flask";
	}
	
	public String examine()
	{
		return super.examine();
	}
	
	public String take()
	{
		return "You cannot take what you already have.";
	}
	
	public void refill()
	{
		super.uses = 4;
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
