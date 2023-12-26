package com.example.hl_lobbyserver;

import java.util.ArrayList;
import java.util.List;


public class MessageContent{

	String user_id;
	String password;
	int num_plays_score;
	int num_hits_score;
	int num_wins_score;
	//カードおよびルールの画像ファイル(base64)格納用のリスト
	List<String> textDataList = new ArrayList<>();
	String error;

}