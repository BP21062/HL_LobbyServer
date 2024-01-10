package com.example.hl_lobbyserver;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

public class Main {
	public static final String restUri = "http://localhost:8081";

	public static void main(String[] args) throws Exception{
		// サーバー接続
		Server server = new Server("localhost", 8080, "/app", null, LServerConnector.class);
		final ResourceConfig rc = new ResourceConfig().packages("");
		final HttpServer restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(restUri), rc);

		// 起動
		try {
			server.start();
			System.in.read();
		} finally {
			server.stop();
			restServer.shutdownNow();
		}

	}
}
