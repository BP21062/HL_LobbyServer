package com.example.hl_lobbyserver;

import java.util.ArrayList;
import javax.websocket.*;
import com.google.gson.Gson;

public class LServerConnector{
	String server_name;
	String send_db;
	String receive_db;

	public void connect(String server_name){
		// TODO implement here
	}

	public void disconnect(){
		// TODO implement here
	}

	public void registerUser(String user_id, String password){
		// TODO implement here
	}

	public void login(String user_id, String password){
		ArrayList<User> user_id_list = getUserList();
	}

	public void logout(String user_id){
		// TODO implement here
	}

	public ArrayList<User> getUserList(){
		// TODO implement here
		return null;
	}

	public String getPassword(String user_id){
		// TODO implement here
		return null;
	}

	public Boolean sendUserInfo(String user_id, String password){
		// TODO implement here
		return null;
	}

	public String getErrorInfo(){
		// TODO implement here
		return "";
	}

	public Message sendMessage(Message message){
		// TODO implement here
		return null;
	}

	public Boolean checRoomState(String room_id){
		// TODO implement here
		return null;
	}

}
