public interface Item
{
	public boolean getIsDroppable();
	
	public String examine();
	
	public String getName();
	
	public Room getRoom();
	
	public void setRoom(Room room);
	
	public void setInInventory(boolean inInv);
	
	public boolean getInInventory();
}
