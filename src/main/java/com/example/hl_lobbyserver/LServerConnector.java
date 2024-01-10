package com.example.hl_lobbyserver;

import java.util.ArrayList;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashSet;
import java.io.IOException;

/**
 * 通信を行うクラス
 * <p>
 * 
 * @param server_name         サーバー名
 * @param establishedSessions 接続中のセッションのset
 * 
 */
@ServerEndpoint("/")
public class LServerConnector {
	private String server_name = ""; // サーバー名

	private LController lController;

	public LServerConnector(LController lController) {
		this.lController = lController;
	}

	// 接続中のセッションのset
	private static Set<Session> establishedSessions = Collections.synchronizedSet(new HashSet<Session>());

	static Gson gson = new Gson();

	/**
	 * セッションが確立したときに呼び出されるメソッド
	 * <p>
	 * 接続が確立したときに得るsessionをセットに追加することで操作できるようにしている
	 * 
	 * @param session セッション
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	@OnOpen
	public void onOpen(Session session) {
		establishedSessions.add(session); // sessionをセットに追加

		// ログ
		System.out.println("[WebSocketServerSample] onOpen:" + session.getId());
	}

	/**
	 * メッセージを受信したときに呼び出されるメソッド
	 * <p>
	 * 受信したメッセージはString型のjson形式なので、
	 * gsonを使ってMessage型に変換している
	 * 
	 * @param message 受信したメッセージ
	 * @param session セッション
	 * @return なし
	 * @throws IOException
	 * @author den3asphalt
	 */
	@OnMessage
	public void onMessage(final String message, final Session session) throws IOException {
		// ログ
		System.out.println("[WebSocketServerSample] onMessage from (session: " + session.getId() + ") msg: " + message);

		// 変換
		Message mes = gson.fromJson(message, Message.class);

		// メッセージの種類によって処理を分岐

	}

	/**
	 * セッションが切断されたときに呼び出されるメソッド
	 * <p>
	 * セッションが切断されたときにセットから削除することで操作できないようにしている
	 * 
	 * @param session セッション
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	@OnClose
	public void onClose(Session session) {
		// ログ
		System.out.println("[WebSocketServerSample] onClose:" + session.getId());

		// 削除
		try {
			session.close();
			establishedSessions.remove(session);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * エラーが発生したときに呼び出されるメソッド
	 * <p>
	 * 発生したエラーをキャッチし、セットから削除する。
	 * 
	 * @param session セッション
	 * @param error   エラー
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("[WebSocketServerSample] onError:" + session.getId());
		// 削除
		try {
			session.close();
			establishedSessions.remove(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(Session session, String message) {
		System.out.println("[WebSocketServerSample] sendMessage(): " + message);
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connect(String server_name) {
		if (server_name == "Client") {
			// TODO implement here
		} else {
			System.out.println("サーバー名が不正です");
			return;
		}
		this.server_name = server_name;

	}

	public void disconnect() {
		// たぶんonClose()でやってる
	}

	public void registerUser(String user_id, String password) {
		this.lController.registerUser(user_id, password);
	}

	public void login(String user_id, String password) {

	}

	public void logout(String user_id) {
		// TODO implement here
	}

	public ArrayList<User> getUserList() {
		// TODO implement here
		return null;
	}

	public String getPassword(String user_id) {
		// TODO implement here
		return null;
	}

	public Boolean sendUserInfo(String user_id, String password) {
		// TODO implement here
		return null;
	}

	public String getErrorInfo() {
		// TODO implement here
		return "";
	}

	public Message sendMessage(Message message) {
		// TODO implement here
		return null;
	}

	public Boolean checRoomState(String room_id) {
		// TODO implement here
		return null;
	}

}
