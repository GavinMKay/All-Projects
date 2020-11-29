import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

public class Player
{
	private int hp;
	private int maxHP;
	private String healthBar;
	private String healthPercentage = "%" + ((double)hp / maxHP);
	private int poisonCount;
	private Weapon currentWeapon;
	private boolean poisonIsRunning = false;
	private boolean isPoisoned = false;
	private boolean cured = true;
	private ArrayList<Item> items = new ArrayList<Item>();
	private Room lastBonfire = Commands.getRoom(6, 6, 3);
	
	public Player()
	{
		hp = 100;
		maxHP = 100;
		healthBar = "|------------|";
		healthPercentage = "%100";
		poisonCount = 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	public void setMaxHP(int maxHP)
	{
		this.maxHP = maxHP;
	}
	
	public void setHP(int healthValue)
	{
		if(healthValue >= maxHP)
			this.hp = maxHP;
		
		else 
			this.hp = healthValue;
	}
	
	public void setHealthPercent() 
	{
		this.healthPercentage = "%" + ( ((double) hp)/maxHP) * 100;
	}
	
	public void setHealthBar() 
	{
		if(hp <= 0) 
			this.healthBar = "  |            |";
		
		else if(hp >= maxHP/4.0 && hp < maxHP/2.0) 
			this.healthBar = "  |---         |";
		
		else if(hp >= maxHP/2 && hp < maxHP * 0.75) 
			this.healthBar = "  |------      |";
		
		else if(hp >= maxHP * 0.75 && hp < maxHP) 
			this.healthBar = "  |---------   |";
		
		else if(hp == maxHP) 
			this.healthBar = "  |------------|";
	}
	
	public int getMaxHP()
	{
		return maxHP;
	}

	public double getHP() 
	{
		return hp;
	}

	public String getHealthBar() 
	{
		return healthBar;
	}
	
	public String getHealthPercentage() 
	{
		return healthPercentage;
	}
	
	public void pRefreshHP()
	{
		if(isPoisoned)
			hp -= maxHP * .1;
		setHealthBar();
		setHealthPercent();
	}
	
	public void refreshHP()
	{
		setHealthBar();
		setHealthPercent();
	}
	
	
	
	
	
	
	
	
	
	
	
	public void setCured(boolean cured)
	{
		this.cured = cured;
	}
	
	public boolean getPoisonIsRunning()
	{
		return poisonIsRunning;
	}

	public int getPoisonCount()
	{
		return poisonCount;
	}
	
	public void runPoison()
	{
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{	
				if(Commands.getCurrentRoom().getName().equals("Farron_Keep") == false && cured)
				{
					timer.cancel();
					timer.purge();				
					poisonIsRunning = false;
				}
				else if(hp == 0)
				{
					ZorkJFrame.setTextArea("\nYou have died and respawned at the last bonfire!");
					Commands.setCurrentRoom(lastBonfire);
					ZorkJFrame.updateFrame();
				}
				else if(cured)
				{
					isPoisoned = false;
					poisonCount++;
					if(poisonCount >= 5)
					{
						poisonCount = 0;
						isPoisoned = true;
						cured = false;
					}
				}
				else if(isPoisoned && !cured)
				{
					poisonIsRunning = true;
					pRefreshHP();
					if(ZorkJFrame.isActive())
						ZorkJFrame.setTextArea("You are poisoned! Get out of here as quick as you can! Current HP: " + healthPercentage + healthBar);
					
					else
						System.out.println("You are poisoned! Get out of here as quick as you can! Current HP: " + healthPercentage + healthBar);	
				}
				

			}
		};
		timer.scheduleAtFixedRate(task, 0L, 1000L);
	}
	
	
	
	
	
	
	
	
	
	
	
	public String isAttackedBy(Enemy o)
	{
		hp -= o.getAttackVal() * 20;
		refreshHP();
		String temp = "You have been attacked by: " + o.getName() + ", Your current HP: " + healthPercentage + healthBar;
		if(hp <= 0)
		{
			temp += "\nYou have died and respawned at the last bonfire!";
			Commands.setCurrentRoom(lastBonfire);
			ZorkJFrame.updateFrame();
		}
		return temp;
		
	}
	
	public int attack() 
	{
		try
		{
			int playerDMG = 100 + currentWeapon.getDamage();
			Commands.getCurrentBoss().subtractHP(playerDMG);
			return playerDMG;
		}
		catch(Exception e)
		{
			int playerDMG = 100;
			Commands.getCurrentBoss().subtractHP(playerDMG);
			return playerDMG;
		}
	}

	
	
	
	
	
	
	
	
	
	
	public void pickUp(Item item)
	{
		items.add(item);
	}
	
	public Item getItem(String itemName)
	{
		for(Item i : items)
		{
			if(i.getName().toLowerCase().equals(itemName.substring(1).toLowerCase()))
				return i;
			
		}
		return null;
	}
	
	public ArrayList<Item> getInventory()
	{
		return items;
	}

	
	
	
	
	
	
	
	
	
	
	public Room getLastBonfire()
	{
		return lastBonfire;
	}
	
	public void setLastBonFire(Room lastBonfire)
	{
		this.lastBonfire = lastBonfire;
	}
	
	public void setWeapon(Weapon weapon) 
	{
		try
		{
			Commands.executeCommand("drop", currentWeapon.getName());
			this.currentWeapon = weapon;
		}
		catch(Exception e)
		{
			this.currentWeapon = weapon;
		}
		
	}

	public Weapon getCurrentWeapon() 
	{
		
		return currentWeapon;
	}
}
