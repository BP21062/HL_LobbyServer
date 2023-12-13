module com.example.hl_lobbyserver {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.hl_lobbyserver to javafx.fxml;
	exports com.example.hl_lobbyserver;
}