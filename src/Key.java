
public class Key extends UsableItem
{
	protected Room usableRoom;
	protected String openedDirection;
	
	public Key(String name, Room room, boolean inInv, String desc, Room usableRoom, String openedDirection) 
	{
		super(name, room, 1, inInv, desc);
		this.usableRoom = usableRoom;
		this.openedDirection = openedDirection;
	}

	
	public boolean getIsDroppable() {
		return false;
	}

	public boolean getInInventory() {
		return inInv;
	}
	
	public String use() {
		if(Commands.getCurrentRoom().equals(usableRoom))
		{
			usableRoom.setCanGo(openedDirection);
			return "You opened a way through!";
		}
		else 
			return "You cannont use this here.";
	}

}
