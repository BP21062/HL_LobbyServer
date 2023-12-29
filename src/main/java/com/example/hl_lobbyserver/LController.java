package com.example.hl_lobbyserver;

import java.util.ArrayList;

import org.glassfish.tyrus.server.Server;


public class LController{

	public static void main(String[] args){

		// サーバー接続
		Server server = new Server("localhost", 8080, "/app", null, LServerConnector.class);

		// メッセージ待機


	}

	public void registerUser(String user_id, String password){
		// TODO implement here
	}

	public Boolean checkDuplicateUserID(String user_id, ArrayList<User> user_id_list){
		for(User user : user_id_list){
			if(user.user_id.equals(user_id)){
				return true;
			}
		}
		return false;
	}

	public void login(String user_id, String password){
		// TODO implement here
	}

	public Boolean verifyUserIDAndPassword(){
		// TODO implement here
		return null;
	}

	public void logout(String user_id, String password){
		// TODO implement here
	}

	public String generateSuccessMessage(){
		// TODO implement here
		return "";
	}

	public String generateErrorMessage(){
		// TODO implement here
		return "";
	}
}


