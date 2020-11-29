import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Commands 
{


private static Room[][][] map = new Room[18][13][7];
private static int currentRoomY = 2;
private static int currentRoomX = 12;
private static int currentRoomZ = 6;
private static Room currentRoom = map[2][12][1]; // start at 6 6 3
private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
private static Player player;
private static ArrayList<Item> roomInventory = new ArrayList<Item>();
private static ArrayList<Item> allItems = new ArrayList<Item>();
private static int numCandlesUsed = 0;




	public static void main(String[] args) 
	{
		
		runGame();

	}
	
	
	public static String gameIntro() 
	{
		
		return "You are in a bleak, dead forest. There is a large engraving in stone underneath your feet. The woods are too thick to traverse in every direction but north. What do you do? ";
	}
	
	public static void createEnemies() 
	{
		enemies.add(new Enemy("Abyss Watchers", 1500, 1.5, 1.6, getRoom(13, 4, 1), "west, down"));
		enemies.add(new Enemy("Aldrich", 4000, 2.1, 3, getRoom(13, 7, 2), "south"));
		enemies.add(new Enemy("Cinder", 3000, 2, 1.5, getRoom(16, 6, 0), "east, west"));
		enemies.add(new Enemy("Dancer", 4500, 1, 1.3, getRoom(2, 4, 3), "up, south"));
		enemies.add(new Enemy("Deacans", 3000, .25, 1.2, getRoom(11, 5, 3), "down \n a small doll apeared in your inventory."));
		enemies.add(new Enemy("Dragon Scale Armor", 5000, 4, 2.5, getRoom(2, 7, 4), "up, west"));
		enemies.add(new Enemy("Ludux Gundir", 1500, 1.2, 4, getRoom(4, 6, 3), "north, south"));
		enemies.add(new Enemy("Lorian", 3000, 2, 1.8, getRoom(2, 12, 6), "Lothric looks down at you in disgust. \"Ohh, dear brother... Im on my way... My brother, unyeilding sword of Lothric's prince... Rise if you would, For that is our curse.\"\n He falls from his throne, landing atop his brother. There is a flash of light and Lorian is gone..."));
		enemies.add(new Enemy("Crystal Sage", 2000, 2, 2, getRoom(10, 3, 2), "north, south"));
		enemies.add(new Enemy("Vordt", 2000, 1.2, 2, getRoom(4, 4, 3), "south, north"));
		
	}
	
	public static void spawnEnemies() 
	{
		for(Enemy o : enemies)
		{
			if(currentRoom.equals( o.getRoom() ) && !o.getIsKilled() && !o.getIsActivated() && !o.getName().equals("Lothric"))
			{
				o.setActivated(true);
				o.runTimer();
			}
			else if(!currentRoom.equals(o.getRoom()))
				o.setActivated(false);
				
			else if(o.getIsKilled())
			{
				if(o.getName().equals("Lorian"))
				{
					enemies.get(8).setActivated(true);
				}
				if(o.getName().equals("Lothric"))
				{
					currentRoomY = 16;
					currentRoomX = 5;
					currentRoomZ = 0;
				}
				else
				{
					allItems.add(new Bonfire(o.getRoom()));
					enemies.remove(o); 
				}
			}
		}
		
	}

	public static String fight() 
	{
		try
		{
			String bossText;
			if(getCurrentBoss().getIsActivated())
			{
				if(getCurrentBoss().getHP() > 0)
					bossText = "You attacked " + getCurrentBoss().getName() + " and delt " + player.attack() + " damage.";
				
				else
				{
					bossText = getCurrentBoss().getOpenedPassages();
					currentRoom.setDescription("Defeated " + getCurrentBoss().getName() + " lays on the ground" + bossText);
					bossText = "You have killed " + getCurrentBoss().getName() + bossText;
					if(currentRoom.equals(getRoom(2, 12, 6)))
					{
						currentRoomY = 16;
						currentRoomX = 5;
						currentRoomZ = 0;
						updateRoom();
					}
				}
				return bossText;
					
			}
		}
		catch(Exception e) {}
			
			return "There's nothing to fight";
			
	}

	
	public static void initializeItems()
	{
		allItems.add(new Estus());
		allItems.add(new PurpleMoss());
		allItems.add(new Bonfire(map[6][6][3]));
		allItems.add(new Bonfire(map[3][4][3]));
		allItems.add(new Bonfire(map[2][6][4]));
		allItems.add(new Bonfire(map[2][11][6]));
		allItems.add(new Bonfire(map[8][3][2]));
		allItems.add(new Bonfire(map[9][4][1]));
		allItems.add(new Bonfire(map[2][6][3]));
		allItems.add(new Weapon("Straight Sword", getRoom(0, 5, 3), false, "A shortsword", 250, 1));
		allItems.add(new Weapon("Dragon's Tail", getRoom(2, 6, 4), false, "A GreatAxe forged from the tail of a dragon.", 1000, 2.5));
		allItems.add(new Weapon("Carthus Greatsword", getRoom(16, 4, 0), false, "A Greatsword owned by Hgih Lord Wolnir himself", 500, .75));
		allItems.add(new Weapon("Blade of the Darkmoon", getRoom(15, 5, 2), false, "A legendary sword of the Darkmoon order, extremely powerful magic has been infused into it.", 600, .6));
		allItems.add(new FirstFlame());
		allItems.add(new Key("Lothric Banner", getRoom(0, 5, 3), false, "An ancient Banner of Lothric", getRoom(5, 4, 3), "south"));
	}
	public static void fillRooms() 
	{
		
		for(int l = 0; l < 6; l++) 
		{
			for(int r = 0; r < 18; r++)
			{
				for(int c = 0; c < 13; c++) 
				{
					map[r][c][l] = new Room();
				}
			}
		}
		
		
		//Image icon for JFrame
		map[6][6][3] = new Room("Untended_Graves", true, false, false, false, false, false, gameIntro(), new ImageIcon("src/Images/untended.png"));
		map[5][6][3] = new Room("Decrepid_Pathway", true, true, false, false, false, false, "A decrepid path goes further North... To the East and West are thick, impassable brush.", new ImageIcon("src/Images/decrepid1.png"));
		
		map[4][6][3] = new Room("Ludex_Gundir's_Arena", false, false, false, false, false, false," Ludex Gundir, old Champion of Ashe Stands before you, ready for a fight. The door behind him is barred off, the way you entered is still accessable.", new ImageIcon("src/Images/gundir.png"));
		
		map[3][6][3] = new Room("Decrepid_Pathway", true, true, false, false, false, false, "A decrepid path goes further north... To the East is thick, impassable brush, and the West a large cliffside.", new ImageIcon("src/Images/decrepid2.png"));
		map[2][6][3] = new Room("Firelink_Shrine,", false, true, false, true, false, false, "You see a small bonfire, with a beautiful white haired women tending to the flame. You can travel South and West.", new ImageIcon("src/Images/firelink.png"));
		map[2][5][3] = new Room("High_Wall_of_Lothric", true, true, true, false, false, false, "You see a wonderfull veiw of Lothric castle, and are able to travel in any direction except West.", new ImageIcon("src/Images/highwall.png"));
		map[1][5][3] = new Room("High_Wall_of_Lothric", true, true, false, false, false, false, "At this part of the wall you may only go North and South... there is nothing of interest.", map[2][5][3].getImage());
		map[0][5][3] = new Room("High_Wall_of_Lothric", false, true, false, false, false, false, "At this part of the wall you may only go South...", map[2][5][3].getImage());
		map[3][5][3] = new Room("High_Wall_of_Lothric", true, false, false, true, false, false, "At this part of the wall you may only go North and West...", map[2][5][3].getImage());
		map[3][4][3] = new Room("High_Wall_of_Lothric", true, true, true, false, false, false, "At this part of the wall you may only go North, South, and East... There are enourmous doorways to your North and South...", map[2][5][3].getImage());
		
		map[2][4][3] = new Room("Dancer's_Dwelling", false, false, false, false, false, false, "You enter a large church-like room... there is an old lady seemingly awaiting your arrival.", new ImageIcon("src/Images/dancer.png"));
		
		map[2][4][4] = new Room("Foot_of_Lothric", false, false, true, false, false, true, "You see the Castle of Lothric in the Distance... your only viable routes are eastwards towards a large gate and downwards back into the dancer's dwelling.", new ImageIcon("src/Images/lothricfoot.png"));
		map[2][5][4] = new Room("Dragon_Gate", true, false, false, true, false, false, "There lays two Wyverns within the gear systems of a large gate blocking your path, it is impossible to go foward, maybe look for another way through?", new ImageIcon("src/Images/dragonbridge.png"));
		map[1][5][4] = new Room("Hidden_Passage", false, true, true, false, false, false, "You are within a dark hidden passage.", new ImageIcon("src/Images/black.png"));
		map[1][6][4] = new Room("Hidden_Passage", false, true, false, true, false, false, "You are within a dark hidden passage... But you feel a lever in a corner of the room...", map[1][5][4].getImage());
		map[2][6][4] = new Room("Dragon_Gate", true, false, true, false, false, false, "You are on the opposite side of the Wyverns and the gate, you can continue eastwards...", new ImageIcon("src/Images/dragonbridge2.png"));
		
		map[2][7][4] = new Room("Dragon_Scale_Armor", false, false, false, false, false, false, "You approach an empty suit of armor blocking your way... it's moving... there is a ladder behind it you must get access to", new ImageIcon("src/Images/dragonslayer.png"));
		
		map[2][7][5] = new Room("Grand_Archives", false, false, false, false, false, true, "You see a door, requiring a sacrifice to get through... something by the name of Aldrich.", new ImageIcon("src/Images/archive1.png"));
		
		map[2][8][5] = new Room("Grand_Archives", false, false, true, true, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", new ImageIcon("src/Images/archive2.png"));
		map[2][9][5] = new Room("Grand_Archives", true, true, false, true, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", map[2][8][5].getImage());
		map[1][9][5] = new Room("Grand_Archives", false, true, true, true, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", map[2][8][5].getImage());
		map[1][8][5] = new Room("Grand_Archives", false, false, true, false, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", map[2][8][5].getImage());
		map[1][10][5] = new Room("Grand_Archives", false, true, false, true, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", map[2][8][5].getImage());
		map[3][9][5] = new Room("Grand_Archives", true, false, false, false, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", map[2][8][5].getImage());
		map[2][10][5] = new Room("Grand_Archives", true, false, true, false, false, false, "The bookshelves seem to move around on their own... there is no clear way through.", map[2][8][5].getImage());
		map[2][11][5] = new Room("Elevator_Room", false, false, false, true, true, false, "There is an elevator leading up into the main castle...", new ImageIcon("src/Images/elevator.png"));
		map[2][11][6] = new Room("Royal_Hallway", false, false, false, true, false, true, "You see a door to your East... a sense of fear engulfs you.", new ImageIcon("src/Images/royalhall.png"));
		
		map[2][12][6] = new Room("Twin_Princes_Dorm", false, false, false, false, false, false, "You enter Lothric and Lorean's dorm...", new ImageIcon("src/Images/lorian.png"));
		
		map[4][4][3] = new Room("Vordt_of_the_Boreal_Valley", false, false, false, false, false, false, "You enter the large doorway to see another entangled in roots on the south side... as you examine it Vordt approaches you from behind.", new ImageIcon("src/Images/vordt.png"));
		
		map[5][4][3] = new Room("Empty_Cliffside", true, false, false, false, false, false, "You see a large cliffside, and a small parchment hanging on the wall that reads, \"You must raise the Loth...\" it cuts off, you feel as though you are missing something important. The only viable pathway is back north.", new ImageIcon("src/Images/angel.png"));
		
		map[6][4][3] = new Room("Undead_Settlement_Wall", true, false, false, false, false, true, "You arrive at a wall surrounding a small settlement within the woods.. you may only go down, or have the angels take you back North.", new ImageIcon("src/Images/undeadwall.png"));
		map[6][4][2] = new Room("Undead_Settlement", false, false, true, false, true, false, "You arrive at the undead settlement, a forelorn shell of it's former glory, you may travel back up the wall or continue East.", new ImageIcon("src/Images/undead.png"));
		map[6][5][2] = new Room("Undead_Settlement", false, true, false, true, false, false, "You travel through the alleyways, you may turn back West or continue South. ", map[6][4][2].getImage());
		map[7][5][2] = new Room("Undead_Settlement", true, false, false, true, false, false, "The alleyways tighten, you may turn back North or continue West.", map[6][4][2].getImage());
		map[7][4][2] = new Room("Undead_Settlement", false, false, true, true, false, false, "You see a light in the distance, a forest maybe... continue West or turn back East.", map[6][4][2].getImage());
		map[7][3][2] = new Room("Road_Of_Sacrifices", false, true, true, false, false, false, "You stand at the entrance of the Road of Sacrifices, dead bodies hang around you, you hear screeching in the distance.", new ImageIcon("src/Images/road.png"));
		map[8][3][2] = new Room("Crusifiction_Woods", true, true, false, false, false, false, "You arrive at the crusifiction woods. Stange, passive beings attached to broken crusifixes surround you as you walk. You either treck North or South.", new ImageIcon("src/Images/crucifiction.png"));
		map[9][3][2] = new Room("Crusifiction_Woods", true, true, true, false, false, false, "To your East, you see a ladder, to the South a doorway, and to the North the Road of Sacrifices entrance.", map[8][3][2].getImage());
		
		map[10][3][2] = new Room("Crystal_Sage", false, false, false, false, false, false, "You enter through the doorway to see the Crystal Sage, one of a few Elite magic users, ready to engage you. Behind her lays your path ahead.", new ImageIcon("src/Images/sage.png"));
	
		map[11][3][2] = new Room("Cliffside_Passage", true, false, true, false, false, false, "You enter a short passage along a cliffside, and approach the Church of Aldridch to your East.", new ImageIcon("src/Images/cliffpas.png"));
		map[11][4][2] = new Room("Cliffside_Passage", false, false, true, true, false, false, "You see the Church of Aldrich to your east, and the cliffside passage entrance to your west.", map[11][3][2].getImage());
		map[11][5][2] = new Room("Church_of_Aldrich", false, false, false, true, true, false, "You enter the church of Aldrich, there is a passageway upwards and an exit to the west.", new ImageIcon("src/Images/churchald.png"));
		
		map[11][5][3] = new Room("Tomb_of_Aldrich", false, false, false, false, false, false, "You enter Aldrich's tomb; however, Instead of finding the Lord of Cinder, you find his deciples, the Deacans of the Deep...", new ImageIcon("src/Images/deacans.png"));
		
		map[9][4][2] = new Room("The_Decent", false, false, false, true, false, true, "You enter a small room with a ladder leading downwards and an exit to the west", new ImageIcon("src/Images/decent.png"));
		map[9][4][1] = new Room("Farron_Keep_Entrance", false, true, false, false, true, false, "You enter a small room with a ladder leading upwards and an exit to the south.", new ImageIcon("src/Images/farronent.png"));
		map[10][4][1] = new Room("Farron_CrossRoads", true, false, true, true, false, false, "You enter a crossroad with a ladder room to the north, and two paths to the east and west, purple sludge surrounds your feet... you feel woozy", new ImageIcon("src/Images/farroncross.png"));
		map[10][5][1] = new Room("Farron_Keep", false, true, false, true, false, false, "You may travel South and West, purple sludge surrounds your feet... you feel woozy", new ImageIcon("src/Images/farron.png"));
		map[11][5][1] = new Room("Farron_Keep", true, false, true, false, false, false, "You may travel North and East, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[11][6][1] = new Room("The_First_Candle", false, true, false, true, false, false, "There is a small candle... You may travel South and West", new ImageIcon("src/Images/farroncand.png"));
		map[12][6][1] = new Room("Farron_Keep", true, false, true, false, false, false, "You may travel North and East, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[12][7][1] = new Room("Farron_Keep", false, true, false, true, false, false, "You may travel South and East, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[13][7][1] = new Room("The_Second_Candle", true, false, false, false, false, false, "There is a small candle... You may travel North", map[11][6][1].getImage());
		map[10][3][1] = new Room("Farron_Keep", false, true, true, false, false, false, "You may travel East and South, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[11][3][1] = new Room("Farron_Keep", true, false, false, true, false, false, "You may travel North and West, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[11][2][1] = new Room("Farron_Keep", false, true, true, false, false, false, "You may travel South and East, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[12][2][1] = new Room("Farron_Keep", true, true, false, true, false, false, "You may travel North, South, and West", map[10][5][1].getImage());
		map[12][1][1] = new Room("The_Third_Candle", false, false, true, false, false, false, "You may travel East, purple sludge surrounds your feet... you feel woozy", map[11][6][1].getImage());
		map[13][2][1] = new Room("Farron_Keep", true, false, true, false, false, false, "You may travel North and East, purple sludge surrounds your feet... you feel woozy", map[10][5][1].getImage());
		map[13][3][1] = new Room("Farron_Keep_Exit", false, false, false, true, false, false, "You may travel East and West. There are three podiums, each with a small dimple, seemingly for candles.", map[10][5][1].getImage());
		
		map[13][4][1] = new Room("Watchdogs_of_Farron", false, false, false, false, false, false, "You enter the arena of the Abyss Watchers, those who forever engage in battle...", new ImageIcon("src/Images/abyss.png"));
		
		map[13][4][0] = new Room("Catacombs_of_Carthus", false, true, false, false, true, false, "You are at the entrance of the Catacombs of Carthus, There is an exit above you, you may travel deeper to the South", new ImageIcon("src/Images/catacomb.png"));
		map[14][4][0] = new Room("Catacombs_of_Carthus", true, true, false, true, false, false, "You may travel West, North, and South.", map[13][4][0].getImage());
		map[14][3][0] = new Room("Catacombs_of_Carthus", false, false, true, false, false, false, "You enter a small tomb with a Greatsword on the floor... You may move East.", map[13][4][0].getImage());
		map[15][4][0] = new Room("Carthus_Bridge", true, true, false, false, false, false, "You approach a decaying bridge... You may go North and South.", new ImageIcon("src/Images/catbridge.png"));
		
		map[16][4][0] = new Room("High_Lord_Wolnir", true, true, false, false, false, false, "You enter the Tomb of Wolnir, You see his enormous skeletal body limp on the floor.", new ImageIcon("src/Images/wolnir.png"));
		
		map[17][4][0] = new Room("Wolnir's_Passage", true, false, false, false, true, false, "You enter Wolnir's passage to Ithreal, It leads upwards and North.", new ImageIcon("src/Images/wolnirpas.png"));
		map[17][4][1] = new Room("Ithreal_View", false, false, true, false, false, true, "You are at the entrance to Ithreal, You see the entire castle ahead of you, with a bridge to the east and wolnir's tomb below you.", new ImageIcon("src/Images/ithveiw.png"));
		map[17][5][1] = new Room("Ithreal_Bridge", false, false, true, true, false, false, "You approach the bridge leading to Ithreal... you feel as though something is watching... you may travel East and West.", new ImageIcon("src/Images/ithbridge.png"));
		map[17][6][1] = new Room("Bridge_Barrier", false, false, false, true, false, false, "You find a barrier at the end of the bridge... it requires something to get through... you may travel east (With the key) and west.", new ImageIcon("src/Images/ithwall.png"));
		map[17][7][1] = new Room("Ithreal_Courtyard", true, false, false, true, false, false, "You enter the Ithreal Courtyard and can travel North and west.", new ImageIcon("src/Images/ithcourt.png"));
		map[16][7][1] = new Room("Church_of_Yorshka", true, true, false, false, false, false, "You enter the Church of Yorshka, there is a bonfire on the floor, and Exits to the North and South.", new ImageIcon("src/Images/yorksha.png"));
		map[15][7][1] = new Room("Central_Staircase", false, true, false, false, true, false, "You enter the central staircase of the Curch, you may travel Up or South.", new ImageIcon("src/Images/stair.png"));
		map[15][7][2] = new Room("Dual_Passage", true, false, false, true, false, true, "You enter the Dual passage, there is a broken bridge to the West, a pathway to the North, and a staircase leading downwards.", new ImageIcon("src/Images/duelpas.png"));
		
		map[14][7][2] = new Room("Aldrich", false, false, false, false, false, false, "You enter a large room filled with a black goo... Aldrich Rises from the ground with his bow drawn...", new ImageIcon("src/Images/aldrich.png"));
		
		map[15][6][2] = new Room("Invisible_Pathway", false, false, true, true, false, false, "You walk off of the broken bridge... but don't fall, you may go North, South, East, or West from here.", new ImageIcon("src/Images/invis.png"));
		map[14][6][2] = new Room("The_Fall", false, false, false, false, false, false, "You fall off of the Invisible pathway and fall to your death.", map[15][6][2].getImage());
		map[16][6][2] = new Room("The_Fall", false, false, false, false, false, false, "You fall off of the Invisible pathway and fall to your death.", map[15][6][2].getImage());
		map[15][5][2] = new Room("Blade_of_the_Darkmoon", false, false, true, false, false, false, "You arrive at the top of a tower, there is a small tome on the floor... you may travel east.", new ImageIcon("src/Images/darkmoon.png"));

		map[16][5][0] = new Room("Altar_of_the_First_Flame", false, false, true, false, false, false, "You arrive at the altar of the first flame, there is a bonfire on the floor and a large armored man standing to the East.", new ImageIcon("src/Images/altarpath.png"));
		
		map[16][6][0] = new Room("Altar_of_the_First_Flame", false, false, false, false, false, false, "You aproach the Lord of Cinder, ready for your final encounter.", new ImageIcon("src/Images/cinder.png"));
		
		map[16][7][0] = new Room("The_First_Flame", false, false, false, true, false, false, "You find the first flame, you may choose to either rekindle it, or let the age of fire die...", new ImageIcon("src/Images/firstflame.png"));

		initializeItems();
	}
	
	public static Room[][][] getMap()
	{
		return map;
	}
	
	public static Enemy getCurrentBoss()
	{
		for(Enemy e : enemies)
		{
			if(e.getRoom() == currentRoom)
			{
				return e;
			}
		}
		return null;
	}
	
	public static void updateRoom()
	{
		currentRoom = map[currentRoomY][currentRoomX][currentRoomZ];
		roomInventory.removeAll(allItems);
		for(Item i : allItems)
		{
			if(i.getRoom().equals(currentRoom))
				roomInventory.add(i);
		}
	}
	
	public static String getFirstWord(String input) 
	{
		input += " ";
		return input.substring(0, input.indexOf(" "));
	}

	public static String getRestOfSentence(String input) 
	{
		if (input.indexOf(" ") != -1) 
			return input.substring(input.indexOf(" "));
			
		else 
			return "";
	}
	
	public static Room getCurrentRoom() 
	{
		return currentRoom;
	}
	
	public static void setCurrentRoom(Room room)
	{
		currentRoom = room;
		currentRoomX = room.getX();
		currentRoomY = room.getY();
		currentRoomZ = room.getZ();
	}
	
	
	
	
	
	private static String up() 
	{
		if(currentRoom.getCanGoUp() == true) 
		{
			currentRoomZ++;
			updateRoom();
			return "You moved Upwards, \n\n" + currentRoom.getName() + currentRoom.getDescription();
		}else 
			return "You look upwards...\n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
	}
	
	private static String down() 
	{
		if(currentRoom.getCanGoDown() == true) 
		{
			currentRoomZ--;
			updateRoom();
			return "You moved Downwards. \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
		}else
			return "You hugged the floor...\n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
	}
	
	private static String north() 
	{
		if(currentRoom.getCanGoNorth() == true) 
		{
			currentRoomY--;
			updateRoom();
			return "You moved North. \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
		}else 
			return "You cannot move North.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
	}

	private static String south() 
	{
		if(currentRoom.getCanGoSouth() == true) 
		{
			currentRoomY++;
			updateRoom();
			return "You moved South.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
		}else 
			return "You cannot move South.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
	}
	
	private static String east() 
	{
		if(currentRoom.getCanGoEast() == true) 
		{
			currentRoomX++;
			updateRoom();
			return "You moved East.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
		}else 
			return "You cannot move East.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
	}
	
	private static String west() 
	{
		if(currentRoom.getCanGoWest() == true) 
		{
			currentRoomX--;
			updateRoom();
			return "You moved West.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
		}else 
			return "You cannot move West.  \n\n" + currentRoom.getName() + "\n" + currentRoom.getDescription();
	}
	
	
	
	
	private static String take(String itemName) 
	{
		try
		{
			Item item = getItem(itemName);
			if(!item.getName().equals("Bonfire") && !item.getClass().getName().equals("FarronCandle"))
			{
				if(item.getRoom().equals(currentRoom) && !item.getInInventory())
				{
					player.pickUp(item);
					item.setRoom(map[0][0][0]);
					item.setInInventory(true);
					return "You picked up the " + item.getName();
				}
			}
			if(item instanceof Weapon)
			{
				player.setWeapon((Weapon)item);
			}
		}
		catch(Exception e) { }
		return "The" + itemName + " is not here, or you cannot pick it up.";
	}
	
	private static String drop(String itemName) 
	{
		try 
		{
			Item item = player.getItem(itemName);
			if(item.getIsDroppable())
			{
				item.setRoom(currentRoom);
				item.setInInventory(false);
				getPlayer().getInventory().remove(item);
				getRoomInventory().add(item);
				return "You dropped the " + item.getName();
			}
			else 
				return "You cannot drop the" + item.getName();
		}
		catch(Exception e) { }
		return "You do not have the" + itemName;
		
	}
	
	public static Item getItem(String itemName)
	{
		for(Item i : roomInventory)
		{
			if(i.getName().toLowerCase().equals(itemName.substring(1).toLowerCase()))
				return i;
			
		}
		return null;
	}
	
	
	private static String look() 
	{
		String description = "You looked around. \n" + currentRoom.getName() + "\n\n" + currentRoom.getDescription();
		for(Item i : roomInventory)
		{
			if(i.getRoom().equals(currentRoom))
				description += "\nThere is a " + i.getName() + " on the ground.";
		}
		return description;
	}
	
	private static String inventory() 
	{
		String inventoryItems = "The current items in your inventory are: ";
		if(player.getInventory().size() == 0)
		{
			inventoryItems = "You currently have nothing in your inventory";
		}
		else 
		{
			for(Item i : player.getInventory())
			{
				inventoryItems += "\n" + i.getName() + "\t\t" + i.examine();
			}
		}
		
		return inventoryItems;
	}
	
	private static String examine(String itemName) 
	{
		return player.getItem(itemName).examine();
	}

	private static String eat(String itemName) 
	{
		Item item = player.getItem(itemName);
		try
		{
			if(item instanceof EdibleItem)
			{
				String eatMessage;
				if(((EdibleItem) item).getUses() > 0)
				{
					((EdibleItem) item).setUses(((EdibleItem) item).getUses() - 1);
					eatMessage = ((EdibleItem) item).use();
				}
				else if(((EdibleItem) item).getUses() == -1)
					eatMessage = ((EdibleItem) item).use();
				else
					eatMessage = "You do not have the " + item.getName();
				
				return eatMessage;
			}
			else
				throw new Exception();
		}
		catch(Exception e)
		{
			String eatMessage = "You cannot eat the " + itemName;
			return eatMessage;
		}
	}
	
	private static String use(String itemName) 
	{
		Item item = player.getItem(itemName);
		try
		{
			if(item instanceof FarronCandle)
			{
				if(!((FarronCandle)item).isUsed())
				{
					((FarronCandle)item).use();
					
					if(numCandlesUsed == 3)
					{
						getRoom(13, 3, 1).setCanGo("east");
					}
				}
			}
			if(item instanceof UsableItem)
			{
				String useMessage;
				if(((UsableItem) item).getUses() > 0)
				{
					((UsableItem) item).setUses(((UsableItem) item).getUses() - 1);
					useMessage = ((UsableItem) item).use();
				}
				else if(((UsableItem) item).getUses() == -1)
					useMessage = ((UsableItem) item).use();
				else
					useMessage = "You do not have the " + item.getName();
				
				return useMessage;
			}
			else
				throw new Exception();
			
		}
		catch(Exception e)
		{
			String useMessage;
			item = getItem(itemName);
			
			if(item instanceof Bonfire && item.getRoom().equals(currentRoom))
				useMessage = ((Bonfire) item).use();
			else
				useMessage = "You cannot use the " + itemName;
			return useMessage;
		}
	}

	private static String health() 
	{
		return player.getHealthPercentage() + player.getHealthBar();
	}

	private static String help(String number)
	{
		int num = Integer.parseInt(number);
		if(num == 1)
		{
			return "Command Name:\t Command example:\t Command Description:"
					+ "\n" + "North\t\t north\t\t\t Moves player North"
					+ "\n" + "South\t\t south\t\t\t Moves player South"
					+ "\n" + "East\t\t east\t\t\t Moves player East"
					+ "\n" + "West\t\t west\t\t\t Moves player West"
					+ "\n" + "UP \t\t up \t\t\t Moves player Up"
					+ "\n" + "Take\t\t take {item}\t\t\t Takes {item}" ;
		}
		else if(num == 2)
		{
			return "Drop\t\t drop {item}\t\t Drops {item}"
					+ "\n" + "Look\t\t look \t\t Looks around the area"
					+ "\n" + "Inventory\t\t inventory\t\t Checks items in inventory"
					+ "\n" + "Examine\t\t examine {item}\t Examines {item}"
					+ "\n" + "Eat\t\t eat {item}\t\t Eats {item} (if edible)"
					+ "\n" + "Use\t\t use {item}\t\t Uses {item} (if usable)"
					+ "\n" + "Quit\t\t quit\t\t Quits game";
		}
		else if (num == 3)
		{
			return 
					"Help\t\t help \t\t\t Opens command menu"
					+ "\n" + "Fight\t\t fight {enemy}\t\t Fights enemy in area"
					+ "\n" + "Health\t\t health\t\t\t Shows player's healthbar";
		}
		else
		{
			return "Either you did not specify which page or the specified page does not exist.";
		}
	}
		
	private static String quit() 
	{
		return "You have decided to quit the game.";
	}

	private static String invalid() 
	{
		return "I do not understand.";
	}
	
	
	
	public static String executeCommand(String command, String item) 
	{
		updateRoom();
		if(command.toLowerCase().equals("north")) 
			return north();
			
		else if(command.toLowerCase().equals("south")) 
			return south();
			
		else if(command.toLowerCase().equals("east")) 
			return east();
			
		else if (command.toLowerCase().equals("west")) 
			return west();
			
		else if (command.toLowerCase().equals("down")) 
			return down();
			
		else if (command.toLowerCase().equals("up")) 
			return up();
		
		else if (command.toLowerCase().equals("take")) 
			return take(item);
			
		else if (command.toLowerCase().equals("drop")) 
			return drop(item);
			
		else if (command.toLowerCase().equals("look")) 
			return look();
			
		else if(command.toLowerCase().equals("inventory")) 
			return inventory();
			
		else if(command.toLowerCase().equals("examine")) 
			return examine(item);
			
		else if(command.toLowerCase().equals("eat")) 
			return eat(item);
			
		else if(command.toLowerCase().equals("use")) 
			return use(item);
			
		else if(command.toLowerCase().equals("help")) 
			return help(item);
			
		else if(command.toLowerCase().equals("quit")) 
			return quit();
			
		else if (command.toLowerCase().equals("fight")) 
			return fight();
			
		else if (command.toLowerCase().equals("health")) 
			return health();
			
		else if(command.equals("2518alakazam"))
		{
			Weapon gg = new Weapon("God's wrath", currentRoom, true, "God's wrath but a sword.", 200000, 0.2);
			player.setHP(999999999);
			player.getInventory().add(gg);
			player.setWeapon(gg);
			return "... cheater";
		}
			return invalid();
			
	}

	
	
	public static void createPlayer() 
	{
		player = new Player();
	}
	public static boolean getPlayerIsAlive() 
	{
		if(player.getHP() <= 0) 
			return false;
		else 
			return true;
	}
	public static Player getPlayer() 
	{
		return player;
	}
	
	public static int getCandlesUsed()
	{
		return numCandlesUsed;
	}
	
	
	
	
	
	public static void setCandlesUsed(int candlesUsed)
	{
		numCandlesUsed = candlesUsed;
	}
	
	public static Room getRoom(int y, int x, int z)
	{
		return map[y][x][z];
	}
	
	public static ArrayList<Item> getRoomInventory()
	{
		return roomInventory;
	}
	

	public static void runGame() 
	{
		boolean done = false;
		fillRooms();
		createEnemies();
		createPlayer();
		System.out.println(gameIntro());
		
		while(!done) 
		{
			Scanner input = new Scanner(System.in);
			String firstWord = input.next();
			String restOfSentence = input.nextLine();

			
			if(firstWord.toLowerCase().equals("quit")) {
				player.setCured(true);
				currentRoom = map[0][0][0];
				done = true;
			}
			
			System.out.println(executeCommand(firstWord, restOfSentence));
			spawnEnemies();
			
			if(currentRoom.getName().equals("Farron_Keep") && player.getPoisonIsRunning() == false)
				player.runPoison();
			
		}
	}
}