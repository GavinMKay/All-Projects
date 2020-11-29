import java.util.Timer;
import java.util.TimerTask;


public class Enemy implements Runnable
{
	protected int hP;
	protected int maxHP;
	protected double attackVal;
	protected Room room;
	protected boolean isKilled = false;	
	protected boolean activated;
	protected double attackSpeed;
	protected String name;
	protected String openedPassages;
	
	public Enemy(String name, int maxHP, double attackVal, double attackSpeed, Room room, String openedPassages)
	{
		this.name = name;
		this.maxHP = maxHP;
		this.hP = maxHP;
		this.attackVal = attackVal;
		this.room = room;
		this.attackSpeed = attackSpeed;
		this.openedPassages = openedPassages;
	}
	
	public void runTimer()
	{
		Enemy enemy = this;
		Timer timer = new Timer();
		if(attackSpeed > 0)
		{
			long delay = (long)(1000 * attackSpeed);
			TimerTask task = new TimerTask() 
			{
				
				public void run()
				{
					Commands.updateRoom();
					if(!activated)
					{
						timer.cancel();
						timer.purge();
					}
					else if(Commands.getPlayer().getHP() > 0 && Commands.getCurrentRoom().equals(room) && hP > 0)
					{
						if(ZorkJFrame.isActive())
						{
							ZorkJFrame.setTextArea(Commands.getPlayer().isAttackedBy(enemy));
						}
						else
							System.out.println(Commands.getPlayer().isAttackedBy(enemy));
					}
					
					else if(Commands.getPlayer().getHP() <= 0)
					{
						Commands.setCurrentRoom(Commands.getPlayer().getLastBonfire());
						timer.cancel();
						timer.purge();
					}
					else
					{
						try
						{
							room.setCanGo(openedPassages);
							if(name.equals("Deacans"))
								dropKey();
							timer.cancel();
							timer.purge();
						}
						catch(Exception e){ e.printStackTrace(); }
					}
				}
			};
			timer.scheduleAtFixedRate(task, delay, delay);
		}
		
	}	
	
	
	public boolean getIsKilled() {	return isKilled;	}
	
	public void setIsKilled(boolean isKilled) {	this.isKilled = isKilled;	}
	
	public void subtractHP(int hP) {	this.hP -= hP;	}
	
	public String getName() {	return name;	}
	
	public int getHP() {	return hP;	}
	
	public Room getRoom() {	return room;	}
	
	public double getAttackVal() {	return attackVal;	}
	
	public void setActivated(boolean activated) {	this.activated = activated;	}
	
	public boolean getIsActivated() {	return activated;	}
	
	public void run(){	runTimer();	}
	
	public String getOpenedPassages(){ 	return "! You may now travel: " + openedPassages; 	}
	
	public int getMaxHP() {	return maxHP;	}

	public void setHP(int hP) {	this.hP = hP;	}
	
	public void dropKey() { Commands.getPlayer().getInventory().add(new Key("Small doll", Commands.getRoom(11, 5, 1), true, "A small doll of some kind... you feel a strange presence.", Commands.getRoom(17, 6, 1), "east")); } 

}
