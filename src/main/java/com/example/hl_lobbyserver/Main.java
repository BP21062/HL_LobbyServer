package com.example.hl_lobbyserver;

import org.glassfish.tyrus.server.Server;

/**
 * ロビーサーバーのメインクラス
 * <p>
 * これを起動することで動かす
 * 
 * @param restUri サーバーのURI
 */
public class Main {
	public static final String restUri = "http://localhost:8081";

	public static void main(String[] args) throws Exception{
		// サーバー接続
		Server server = new Server("localhost", 8080, "/lobby", null, LServerConnector.class);

		// 起動
		try {
			server.start();
			System.in.read();
		} finally {
			server.stop();
		}

	}
}
