module com.example.hl_appserver {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javax.websocket.api;
	requires com.google.gson;
	requires tyrus.server;
	requires transitive java.ws.rs;
	requires jersey.server;
	requires grizzly.http.server;
	requires jersey.container.grizzly2.http;
	requires jersey.common;
	requires java.sql;

	opens com.example.hl_lobbyserver to javafx.fxml, com.google.gson;
	exports com.example.hl_lobbyserver;
}