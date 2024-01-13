package com.example.hl_lobbyserver;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
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
		Server server = new Server("ws", 8080, "/lobby", null, LServerConnector.class);

		// 起動
		try {
			server.start();
			System.in.read();
		} finally {
			server.stop();

		}

	}
}
