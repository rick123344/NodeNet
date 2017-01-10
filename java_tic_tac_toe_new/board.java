package Tic_Tac_Toe;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
enum Status{
	Null,
	O,
	X,
	XWin,	// X win
	OWin,	// O win
	NoWin,	// continue game
	EndGame	// all column are fill , end game
}
class board extends JPanel implements ActionListener,MouseListener{
	config c = new config();
	private AllArray arr;
	private boolean is_self = false;
	private int[][] WinCon=new int[][]{
		{0,1,2},{3,4,5},{6,7,8},
		{0,3,6},{1,4,7},{8,5,2},
		{0,4,8},{2,4,6}
	};
	private JLabel label;
	private JLabel fighter;
	board(JLabel label,JLabel fighter){
		arr = new AllArray();
		arr.is_end = false;
		arr.myturn = false;
		arr.winLine = -1;
		this.label = label;
		this.fighter = fighter;
		addMouseListener(this);
		setPreferredSize(new Dimension(c.WPanel,c.WPanel));
		int tmp=0;
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				arr.point[tmp] = new Point(i*c.Weight,j*c.Weight);
				arr.platform[tmp] = Status.Null;
				tmp++;
			}
		}
		if((int)(Math.random()*2) +1 == 1){
			arr.XorO = Status.O;
		}else{
			arr.XorO = Status.X;
		}
		setLabel(arr.XorO);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color colors[]={new Color(0,0,0),new Color(255,69,7),new Color(18,2,255)};
		Color color = colors[0];
		
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setStroke(new BasicStroke(c.fontWeight));
		g2.setColor(color);
		//int wx = getPreferredSize().width;
		
		g2.drawLine(c.Weight,0,c.Weight,c.WPanel);
		g2.drawLine(c.Weight*2,0,c.Weight*2,c.WPanel);
		g2.drawLine(0,c.Weight,c.WPanel,c.Weight);
		g2.drawLine(0,c.Weight*2,c.WPanel,c.Weight*2);
		int r = c.Weight/2;
		for(int i=0; i<arr.platform.length; i++){
			if(arr.platform[i]!=Status.Null){
				g2.setColor(colors[arr.platform[i].ordinal()]);
				if(arr.platform[i]==Status.O){
					g2.drawOval(arr.point[i].x+(r/2), arr.point[i].y+(r/2),r,r);
				}else{
					g2.drawLine(arr.point[i].x+(r*2/3), arr.point[i].y+(r*2/3), arr.point[i].x+(int)(r*1.5), arr.point[i].y+(int)(r*1.5));
					g2.drawLine(arr.point[i].x+(int)(r*1.5), arr.point[i].y+(r*2/3), arr.point[i].x+(r*2/3), arr.point[i].y+(int)(r*1.5));
				}
			}
		}
		
		if(arr.winLine != -1){
			g2.setColor(new Color(72,237,47));
			switch(arr.winLine){
				case 0: case 1: case 2:
					g2.drawLine(arr.point[0].x+(r)+(arr.winLine*2*r), 0, arr.point[0].x+(r)+(arr.winLine*2*r), c.WPanel);
				break;
				case 3: case 4: case 5:
					g2.drawLine(0, arr.point[0].y+(r)+((arr.winLine-3)*2*r), c.WPanel, arr.point[0].y+(r)+((arr.winLine-3)*2*r));
				break;
				case 6:
					g2.drawLine(0,0,c.WPanel,c.WPanel);
				break;
				case 7:
					g2.drawLine(0,c.WPanel,c.WPanel,0);
				break;
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e){
		//System.out.println("8795656");
	}
	/*
	*  MouseListener need to override all func
	*/
	@Override
	public void mousePressed(MouseEvent e){

		int x = e.getX();
		int y = e.getY();
		if(!arr.is_end && arr.myturn){
			for(int i=0; i<arr.point.length; i++){
				if(x > arr.point[i].x+c.fontWeight && x < arr.point[i].x+c.Weight-c.fontWeight && y > arr.point[i].y+c.fontWeight && y < arr.point[i].y+c.Weight-c.fontWeight){
					//System.out.println(x+" ----- "+y+" In "+arr.point[i].x+" -- "+arr.point[i].y);
					if(arr.platform[i] == Status.Null){
						arr.platform[i] = arr.XorO;
						//arr.XorO = (arr.XorO==Status.O)?Status.X:Status.O;
						if(arr.XorO == Status.O){
							arr.XorO = Status.X;
						}else{
							arr.XorO = Status.O;
						}
						setLabel(arr.XorO);
						Process();
						DoConnect("","Change Turn");
					}
					break;
				}
			}
		}
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	private void Process(){
		Status tmp = is_win();
		if(tmp != Status.NoWin){
			arr.is_end = true;
			setLabel(tmp);
		}
	}
	private Status is_win(){
		Status tmp = Status.Null;
		int count;
		for(int i=0; i<WinCon.length; i++){
			count = 0;
			for(int j=0; j<WinCon[i].length; j++){
				if(j == 0){
					tmp = arr.platform[WinCon[i][j]];
					if(tmp == Status.Null){
						break;
					}
				}
				if(arr.platform[WinCon[i][j]] == tmp){
					count ++;
				}else{
					break;
				}
			}
			if(count == 3){
				//System.out.println("tmp = "+tmp);
				arr.winLine = i;
				if(tmp == Status.O){
					return Status.OWin;
				}else{
					return Status.XWin;
				}
			}
		}
		if(! Arrays.asList(arr.platform).contains(Status.Null)){
			return Status.EndGame;
		}
		return Status.NoWin;
	}
	void reset(){
		for(int i=0; i<arr.platform.length; i++){
			arr.platform[i] = Status.Null;
		}
		arr.is_end = false;
		arr.winLine = -1;
		setLabel(arr.XorO);
		repaint();
	}
	void resetGame(){
		reset();
		DoConnect("","Reset Game");
	}
	void changeTurn(String target){
		if(target.equals("MySelf")){
			arr.myturn = true;
			is_self = true;
			arr.oppo = "";
			arr.Target_ip = "";
			arr.RequestType = "";
			arr.ResponseMsg = "";
			resetGame();
		}
	}
	void _ChangeTurn(boolean turn, String oppo){
		if(turn){
			arr.myturn = false;
			this.fighter.setText("Fighte With "+oppo+", Please Wait For Opponent Action!");
		}else{
			arr.myturn = true;
			this.fighter.setText("Fighte With "+oppo+", Now is Your Turn!!!");
		}
	}
	void FailTurn(){
		is_self = false;
		arr.myturn = false;
		arr.oppo = "";
		arr.Target_ip = "";
	}
	void DoConnect(String msg,String Type){
		if(!is_self){
			if(Type.equals("Change Turn")){
				_ChangeTurn(arr.myturn,arr.oppo);
			}
			arr.Target_ip = msg;
			arr.RequestType = Type;
			arr.GuessName = Main.GuessName;
			String json = MenuBar.gson.toJson(arr);
			//System.out.println(json);
			if(Main.socket!=null){
				Main.socket.sendRequest(json);
			}else{
				this.fighter.setText("Not Connect... Please Retry");
			}
		}
	}
	void setLabel(Status status){
		switch(status){
			case X:
				label.setText("Next => X");
			break;
			case O:
				label.setText("Next => O");
			break;
			case OWin:
				label.setText("O is Win");
			break;
			case XWin:
				label.setText("X is Win");
			break;
			case EndGame:
				label.setText("No One Win");
			break;
		}
	}
	void ChangeArray(String newJson){
		System.out.println("Get Here");
		//System.out.println(newJson);
		try{
			AllArray newarr = MenuBar.gson.fromJson(newJson,AllArray.class);
			if(newarr.GuessName.equals(arr.GuessName)){
				System.out.println("break my request");
				return;
			}
			if(!arr.GuessName.equals(newarr.Target_ip)&&!arr.GuessName.equals(newarr.oppo)&&!newarr.RequestType.equals("Return Fight Response")){
				System.out.println("Break , Fight");
				return;
			}
			//System.out.println(newJson);
			switch(newarr.RequestType){
				case "Send Fight Request":
					if(!arr.oppo.isEmpty() && !arr.is_end){
						arr.ResponseMsg = "Fighting";
						DoConnect(newarr.GuessName,"Return Fight Response");
						return;
					}
					if(showDialog(newarr.GuessName)){
						String tmp = "";
						if((int)(Math.random()*2) +1 == 1){
							arr.myturn = true;
							tmp = ", Now is Your Turn!!!";
						}else{
							arr.myturn = false;
							tmp = ", Please Wait For Opponent Action!";
						}
						this.fighter.setText("Fight With "+newarr.GuessName+tmp);
						arr.ResponseMsg = "Yes";
						reset();
					}else{
						arr.ResponseMsg = "No";
					}
					DoConnect(newarr.GuessName,"Return Fight Response");
				break;
				case "Return Fight Response":
					if(!arr.GuessName.equals(newarr.Target_ip)){
						System.out.println("Not My Business");
						return;
					}
					switch(newarr.ResponseMsg){
						case "Yes":
							System.out.println("Yes");
							this.fighter.setText("Fight With "+newarr.GuessName);
							arr.oppo = newarr.GuessName;
							_ChangeTurn(newarr.myturn,newarr.GuessName);
							reset();
						break;
						case "No":
							System.out.println("No");
							this.fighter.setText("Refuse.... Please Choose A Opponent...");
						break;
						case "Fighting":
							System.out.println("Fighting");
							this.fighter.setText("Please Choose A Opponent...");
							JOptionPane.showMessageDialog(this,
							"Opponent is Fighting With Other People~",
							"Warning",
							JOptionPane.WARNING_MESSAGE);
						break;
					}
				break;
				case "Change Turn":
					arr.platform = newarr.platform;
					arr.point = newarr.point;
					arr.XorO = newarr.XorO;
					setLabel(arr.XorO);
					arr.is_end = newarr.is_end;
					//System.out.println(arr.myturn+"   ---   "+newarr.myturn);
					_ChangeTurn(newarr.myturn,newarr.GuessName);
					arr.winLine = newarr.winLine;
					repaint();
				break;
				case "Reset Game":
					arr.platform = newarr.platform;
					arr.point = newarr.point;
					arr.XorO = newarr.XorO;
					setLabel(arr.XorO);
					arr.is_end = newarr.is_end;
					//System.out.println(arr.myturn+"   ---   "+newarr.myturn);
					_ChangeTurn(newarr.myturn,newarr.GuessName);
					arr.winLine = newarr.winLine;
					repaint();
				break;
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	boolean showDialog(String oppo_ip){
		int dialogResult = JOptionPane.showConfirmDialog (this, oppo_ip+" Want Play With You, Are You Agree?","Message",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			arr.oppo = oppo_ip;
			return true;
		}
		return false;
	}
	class AllArray{
		//record every column is O or X
		Status[] platform = new Status[9];
		//record all coordinate
		Point[] point = new Point[9];
		//current status
		Status XorO;
		//if game is end
		boolean is_end;
		//if choose opponent
		boolean myturn;
		//work with WinCon
		int winLine;
		//current ip
		String GuessName="";
		String oppo = "";
		String Target_ip = "";
		String RequestType = "";
		String ResponseMsg = "";
	}
}

//love on the brain
//all time low
//may we all
