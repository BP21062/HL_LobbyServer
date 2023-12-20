module com.example.hl_lobbyserver {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.google.gson;
	requires javax.websocket.api;


	opens com.example.hl_lobbyserver to javafx.fxml;
	exports com.example.hl_lobbyserver;
}