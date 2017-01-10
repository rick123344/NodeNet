package Tic_Tac_Toe;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
class MenuBar extends JMenuBar implements ActionListener{
	public static Gson gson = new Gson();
	JMenu bar;
	MenuBar(){
		bar = new JMenu("All Online");
		add(bar);
		addcomputer();
	}
	void additem(String online){
		bar.removeAll();
		JMenuItem computer = new JMenuItem("MySelf");
		//System.out.println(online);
		if(online != ""){
			/*Type type = new TypeToken<List<Map<String, String>>>() {}.getType();
			List<Map<String, String>> map = gson.fromJson(online, type);
			for(Map<String, String> maps:map){
				for(Map.Entry<String,String> val:maps.entrySet()){
					//System.out.println(val.getKey());
					//System.out.println(val.getValue());
					if(val.getKey().equals("ip") && !val.getValue().equals(Main.cur_ip)){
						JMenuItem tmp = new JMenuItem(val.getValue());
						tmp.addActionListener(this);
						bar.add(tmp);
					}
				}
			}*/
			Gson gson = new Gson();
			Type type = new TypeToken<List<String>>() {}.getType();
			List<String> map = gson.fromJson(online, type);
			for(String s:map){
				if(!s.equals(Main.GuessName)&&Main.GuessName!=""){
					JMenuItem tmp = new JMenuItem(s);
					tmp.addActionListener(this);
					bar.add(tmp);
				}
			}
		}
		computer.addActionListener(this);
		bar.add(computer);
		add(bar);
	}
	void addcomputer(){
		bar.removeAll();
		JMenuItem computer = new JMenuItem("MySelf");
		//System.out.println(online);
		computer.addActionListener(this);
		bar.add(computer);
		add(bar);
	}
	public void actionPerformed(ActionEvent e){
		JMenuItem item = (JMenuItem)e.getSource();
		//System.out.println(e.getActionCommand());
		switch(e.getActionCommand()){
			case "MySelf":
				Main.fighter.setText("Fight MySelf");
				Main.board.changeTurn("MySelf");
			break;
			default:
				if(Main.GuessName==""){
					Main.fighter.setText("You're Not Connect... Try Restart...");
					return;
				}
				Main.board.FailTurn();
				Main.board.DoConnect(e.getActionCommand(),"Send Fight Request");
				Main.fighter.setText("Wait For Response.........");
			break;
		}
	}
}
//all time low