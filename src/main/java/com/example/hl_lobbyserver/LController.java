package com.example.hl_lobbyserver;

import java.util.ArrayList;

public class LController{

	public static void main(String[] args){
		LServerConnector serverConnector = new LServerConnector(); serverConnector.connect("localhost");
	}

	public void registerUser(String user_id, String password){
		// TODO implement here
	}

	public Boolean checkDuplicateUserID(String user_id, ArrayList<User> user_id_list){
		for(User user : user_id_list){
			if(user.user_id.equals(user_id)){
				return true;
			}
		} return false;
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


