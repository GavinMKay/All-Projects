
public class Weapon extends UsableItem
{
	protected int damage;
	protected double attackSpeed;
	
	public Weapon(String name, Room room, boolean inInv, String desc, int damage, double attackSpeed) 
	{
		super(name, room, -1, inInv, desc + "Does: " + damage + " and can attack every " + attackSpeed + " seconds.");
		this.damage = damage;
		this.attackSpeed = attackSpeed;
	}

	public boolean getIsDroppable() 
	{
	
		return true;
	}

	public boolean getInInventory() 
	{
		return inInv;
	}

	public String use() 
	{
		return Commands.fight();
	}

	public int getDamage() 
	{
		return damage;
	}
	
	public double getAttackSpeed()
	{
		return attackSpeed;
	}

}
