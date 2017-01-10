package Tic_Tac_Toe;
//import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.net.InetAddress;
//product jar.....    
//jar cvfm XXX.jar manifest.mf package_name\*.class
//manifest.mf=>Main-Class: Tic_Tac_Toe/Main    Class-path: gson.jar
class Main extends JFrame implements ActionListener{
	public static String GuessName = "";
	public static JLabel fighter = new JLabel("Please Choose Opponent");
	public static board board;
	public static socket socket;
	private Timer t;
	MenuBar bar;
	Main(){
		setTitle("Connecting... Please Wait...");
		bar = new MenuBar();
		setJMenuBar(bar);
		JLabel label = new JLabel("123");
		board = new board(label,fighter);
		
		Thread sT= new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					socket = new socket(bar,board);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		//set Name
		Thread setName= new Thread(new Runnable(){
			@Override
			public void run(){
				while(GuessName==""){
					try{
						GuessName = socket.getName();
						if(GuessName!=""){
							System.out.println("Title=> "+GuessName);
							setTitle("Tic Tac Toe : Hello "+ GuessName);
						}
						Thread.sleep(500);
					}catch(Exception e){
						try{
							Thread.sleep(500);
						}catch(Exception err){
							
						}
					}
				}
			}
		});
		sT.start();
		setName.start();
		
		t=new Timer(10000,this);//execute actionPerformed every 10 seconds
		t.start();
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			System.out.println("Error With Improve Button");
		}
		config c = new config();
		setLayout(new BorderLayout());
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1,2));
		
		JButton Btn = new JButton("Reset");
		
		jp.add(label);
		jp.add(Btn);
		add(jp,BorderLayout.SOUTH);
		
		
		JLabel west = new JLabel();
		west.setPreferredSize(new Dimension(c.BorderWidth-(c.BorderWidth/5),0));
		add(west, BorderLayout.WEST);
		
		//JLabel top = new JLabel();
		fighter.setPreferredSize(new Dimension(0,c.BorderWidth));
		add(fighter, BorderLayout.NORTH);
		
		add(board,BorderLayout.CENTER);
		Btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				board.resetGame();
			}
		});
		
		setSize(c.SWidth,c.SHeight);
		setResizable(false);
		
		//setTitle("Tic Tac Toe "+ GuessName);
		//set offline before close
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.out.println("Close click");
				if(socket != null){
					socket.stopSocket();
				}
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e){
		if(socket != null){
			try{
				socket.refreshBar();
			}catch(Exception err){
				
			}
		}
	}
	public static void main(String args[]){
		LogManager.getLogManager().reset();
		try{
			InetAddress add = InetAddress.getLocalHost();
			String cip=add.getHostAddress();
			//cur_ip = cip;
			//System.out.println(cur_ip);
		}catch(Exception e){
			System.out.println(e.toString());
		}
		//cur_ip = args[0];
		Main main = new Main();
		main.setVisible(true);
	}
}
