import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.*;


public class ZorkJFrame 
{
	
	private static JFrame jFrame;
	private static JTextField textField;
	private static ImageIcon currentImage; 
	private static JLabel background;
	private static JTextArea textArea = new JTextArea();
	private static boolean active = false;
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					ZorkJFrame window = new ZorkJFrame();
					window.jFrame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	public ZorkJFrame() 
	{
		initialize();
	}
	private static void initialize() 
	{
		active = true;
		jFrame = new JFrame();
		jFrame.setTitle("Dark Souls 3 Zork");
		jFrame.setResizable(false);
		jFrame.setBounds(0, 0, 1920, 1075);
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);
		
		
		textArea.setBounds(40, 40, 1840, 200);
		jFrame.getContentPane().add(textArea);
		textArea.setText(" " + Commands.gameIntro());
		
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setCaretColor(Color.WHITE);
		Font font = new Font("TimesRoman", Font.ITALIC, 20);
        textArea.setFont(font);
        textArea.setEditable(false);
		
		textField = new JTextField();
		textField.setFont(font);
		textField.setBounds(40, 250, 450, 30);
		jFrame.getContentPane().add(textField);
		textField.setColumns(10);
		
		Commands.fillRooms();
		Commands.createEnemies();
		Commands.createPlayer();
		
		Music gwynSong = new Music("src/audio/Gwyn.wav");
		gwynSong.loop();
	
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(font);
		submitButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String userInput = textField.getText();
				
				String command = Commands.getFirstWord(userInput);
				String item = Commands.getRestOfSentence(userInput);
				

				textArea.setText(Commands.executeCommand(command, item));
				textField.setText("");
				
				
				if (Commands.getFirstWord(userInput).toLowerCase().equals("quit")) 
				{
					active = false;
					jFrame.dispose();
				}
				updateFrame();
			}
		});
		JButton northButton = new JButton("North");
		northButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("north", ""));
				updateFrame();
			}
			
		});
		JButton southButton = new JButton("South");
		southButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("south", ""));
				updateFrame();
			}
			
		});
		JButton eastButton = new JButton("East");
		eastButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("east", ""));
				updateFrame();
			}
			
		});
		JButton westButton = new JButton("West");
		westButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("west", ""));
				updateFrame();
			}
			
		});
		JToggleButton fightButton = new JToggleButton("Fight");
		fightButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				testPlayerIsAlive();
				try
				{
					textArea.setText(Commands.executeCommand("fight", "") + "\n" + Commands.getCurrentBoss().getHP());
				}
				catch(Exception e)
				{
					textArea.setText(Commands.executeCommand("fight", ""));
				}
				
				updateFrame();
				
				try
				{
					Thread.sleep((long)Commands.getPlayer().getCurrentWeapon().getAttackSpeed() * 1000);
					fightButton.setSelected(false);
				}
				catch(Exception e)
				{ 
					try
					{
						Thread.sleep(250L);
						fightButton.setSelected(false);
					}
					catch(Exception e1) { e1.printStackTrace(); }
				}
			}
			
		});
		JButton upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("up", ""));
				updateFrame();
			}
				
		});
		JButton downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("down", ""));
				updateFrame();
			}
			
		});
		JButton inventoryButton = new JButton("Show Inventory");
		inventoryButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("inventory", ""));	
				updateFrame();
			}
			
		});
		
		JButton helpButton = new JButton("Help");
		helpButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("help", "1"));
				updateFrame();
			}
			
		});
		JButton helpButton2 = new JButton("Help 2");
		helpButton2.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("help", "2"));
				updateFrame();
			}
			
		});
		JButton helpButton3 = new JButton("Help 3");
		helpButton3.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("help", "3"));
				updateFrame();
			}
			
		});
		JButton lookButton = new JButton("Look");
		lookButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent arg0) 
			{
				textArea.setText(Commands.executeCommand("look", ""));
				updateFrame();
			}
			
		});
		Font buttonFont = new Font("TimesRoman", Font.ITALIC, 45);
		
		submitButton.setBounds(494, 250, 100, 30);
		jFrame.getContentPane().add(submitButton);
		jFrame.getRootPane().setDefaultButton(submitButton);
		
		northButton.setBounds(1180, 450, 200, 50);
		northButton.setFont(buttonFont);
		jFrame.getContentPane().add(northButton);
		
		southButton.setBounds(1180, 700, 200, 50);
		southButton.setFont(buttonFont);
		jFrame.getContentPane().add(southButton);
		
		eastButton.setBounds(1400, 576, 200, 50);
		eastButton.setFont(buttonFont);
		jFrame.getContentPane().add(eastButton);
		
		westButton.setBounds(960, 576, 200, 50);
		westButton.setFont(buttonFont);
		jFrame.getContentPane().add(westButton);
		
		fightButton.setBounds(1180, 518, 200, 163);
		fightButton.setFont(buttonFont);
		jFrame.getContentPane().add(fightButton);
		
		upButton.setBounds(1180, 380, 200, 50);
		upButton.setFont(buttonFont);
		jFrame.getContentPane().add(upButton);
		
		downButton.setBounds(1180, 770, 200, 50);
		downButton.setFont(buttonFont);
		jFrame.getContentPane().add(downButton);
		
		inventoryButton.setBounds(1080, 870, 400, 50);
		inventoryButton.setFont(buttonFont);
		jFrame.getContentPane().add(inventoryButton);
		
		helpButton.setBounds(1650, 930, 200, 50);
		helpButton.setFont(buttonFont);
		jFrame.getContentPane().add(helpButton);
		
		helpButton2.setBounds(1650, 860, 200, 50);
		helpButton2.setFont(buttonFont);
		jFrame.getContentPane().add(helpButton2);
		
		helpButton3.setBounds(1650, 790, 200, 50);
		helpButton3.setFont(buttonFont);
		jFrame.getContentPane().add(helpButton3);
		
		lookButton.setBounds(570, 770, 200, 50);
		lookButton.setFont(buttonFont);
		jFrame.getContentPane().add(lookButton);
		//keep on bottom
		updateFrame();
	
	}
	
	public static void updateFrame()
	{
		testPlayerIsAlive();
		Commands.updateRoom();
		Commands.spawnEnemies();
		jFrame.repaint();
		try
		{
			jFrame.remove(background);
			updateBackground();
			jFrame.add(background);
		}
		catch(Exception e) 
		{
			updateBackground();
			jFrame.add(background);
		}
	}
	
	private static void updateBackground()
	{
		currentImage = Commands.getCurrentRoom().getImage();
		background =  new JLabel(currentImage);
		background.setBounds(0, 0, 1920, 1075);
	}
	
	public static void setTextArea(String text)
	{
		textArea.setText(text);
	}
	
	public static boolean isActive()
	{
		return active;
	}
	
	private static void testPlayerIsAlive()
	{
		if (Commands.getPlayerIsAlive() == false ) 
		{
			Commands.setCurrentRoom(Commands.getPlayer().getLastBonfire());
			textArea.setText("You died and have respawned at the nearest bonfire!");
			Commands.getPlayer().setHP(Commands.getPlayer().getMaxHP());
		}
	}
	
}
	





	
	
	
	
	

