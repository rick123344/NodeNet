package Tic_Tac_Toe;

import java.util.*;
import java.net.URI;
import javax.net.ssl.SSLContext;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

class socket implements IOCallback{
	
	private SocketIO sockets;
	private String sname="";
	private MenuBar bar;
	private board board;
	socket(MenuBar bar,board board) throws Exception{
		//System.out.println("hi");
		this.bar = bar;
		this.board = board;
		SocketIO.setDefaultSSLSocketFactory(SSLContext.getDefault());
		sockets = new SocketIO();
        sockets.connect("http://localhost:5001/",this);//https://shrouded-falls-99718.herokuapp.com/  http://localhost:5001/
		sockets.emit("NewGuess",new JSONObject().put("data", "Rick"));
	}
	public void sendRequest(String jsons){
		sockets.emit("MSG","["+jsons+"]");
	}
	public void stopSocket(){
		//sockets.emit("disconnect","");
		sockets.disconnect();
	}
	public void refreshBar(){
		sockets.emit("ReFresh","");
	}
	@Override
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		try {
			System.out.println("Server said:" + json.toString(2));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(String data, IOAcknowledge ack) {
		System.out.println("Server said: " + data);
	}

	@Override
	public void onError(SocketIOException socketIOException) {
		System.out.println("an Error occured");
		socketIOException.printStackTrace();
	}

	@Override
	public void onDisconnect() {
		System.out.println("Connection terminated.");
	}

	@Override
	public void onConnect() {
		System.out.println("Connection established");
	}

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		//System.out.println("Server triggered event '" + event + "'");
		/*for(Object s:args){
			System.out.println("Args --- "+s.toString());
		}*/
		
		switch(event){
			case "NewGuess":
				//GuessName = ack[0].toString();
				try{
					//get this client name
					JSONObject json = new JSONObject(args[0].toString());
					
					this.sname = json.getString("data");
					//this.sname = args[0].toString();
					System.out.println(this.sname);
					
				}catch(Exception e){
					System.out.println("Get Name Failed");
				}
				
			break;
			case "resetBar":
				try{
					//get all guess name list
					JSONObject json = new JSONObject(args[0].toString());
					JSONArray data = new JSONArray(json.getString("data"));
					/*for(int i=0;i<data.length();i++){
						System.out.println("Hello "+data.getString(i));
					}*/
					//additem(json.getString("data"));
					bar.additem(json.getString("data"));
				}catch(Exception e){
					System.out.println("ERROR "+e);
				}
				//System.out.println(args[0].toString());
			break;
			case "EachMessage":
				try{
					JSONObject json = new JSONObject(args[0].toString());
					JSONArray data = new JSONArray(json.getString("data"));
					//System.out.println(data.toString().substring(1,data.toString().length()-1));
					board.ChangeArray(data.toString().substring(1,data.toString().length()-1));
				}catch(Exception e){
					System.out.println("ERROR "+e);
				}
				
			break;
			default:
				for(Object s:args){
					System.out.println("Args --- "+s.toString());
				}
			break;
		}
		
		
	}
	public String getName(){
		return this.sname;
	}
	/*public static void main(String args[]){
		try{
			new socket();
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}