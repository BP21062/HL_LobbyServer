package com.example.hl_lobbyserver;

import java.util.ArrayList;
import java.util.Set;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashSet;
import java.io.IOException;

/**
 * 通信を行うクラス
 * <p>
 * ここからLControllerを呼び出して処理を行う
 * 
 * @param establishedSessions 接続中のセッションのset
 * 
 */
@ServerEndpoint("/")
public class LServerConnector {

	// 接続中のセッションのset
	private static Set<Session> establishedSessions = Collections.synchronizedSet(new HashSet<Session>());

	static Gson gson = new Gson();

	static LRestAPIClient restClient = new LRestAPIClient();

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
		System.out.println("[Lobby] onOpen: " + session.getId());
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
		System.out.println("[Lobby] onMessage from (session: " + session.getId() + ") msg: " + message);
		System.out.println(""); // 長いので空行挟む

		// 変換
		Message mes = gson.fromJson(message, Message.class);

		// メッセージの種類によって処理を分岐
		String order = mes.order;

		// 後で上書きするので、とりあえず空のメッセージを作っておく
		Message response = new Message("Debug", null);

		// メイン処理
		switch (order) {
			case "2": // registerUser
				// いったんコメントアウト
				// response = registerUser(mes.messageContent.user_id, mes.messageContent.password);
				response = new Message("2000", mes.messageContent.user_id);
				response.result = true;
				break;
			case "4": // login
				// いったんコメントアウト
				// response = login(mes.messageContent.user_id, mes.messageContent.password);
				response = new Message("2001", mes.messageContent.user_id);
				response.result = true;
				break;
			case "6": // checkRoomState
				response = checkRoomState(mes.messageContent.user_id, mes.messageContent.room_id);
				break;
			case "7": // logout
				establishedSessions.remove(session);
				session.close();

				// ここまで来ないけど一応
				return; // バグ防止
			case "8": // getScore
				// いったんコメントアウト
				// response = getScore(mes.messageContent.user_id);
				response = new Message("2003", mes.messageContent.user_id);
				break;
			case "9": // getRule
				// いったんコメントアウト
				// response = getRule(mes.messageContent.user_id);
				response = new Message("2004", mes.messageContent.user_id);
				break;
			default:
				break;
		}

		// レスポンスを送信
		sendMessage(session, response);

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
		System.out.println("[Lobby] onClose:" + session.getId());


		// 削除
		try {
			establishedSessions.remove(session);
			session.close();
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
		System.out.println("[Lobby] onError:" + session.getId());
		System.out.println("[Lobby] onError:" + error.getMessage());
		// 削除
		try {
			establishedSessions.remove(session);
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * メッセージを送信するメソッド
	 * @param session 送信先のセッション
	 * @param message 送信するMessageクラス
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	public void sendMessage(Session session, Message message) {
		// ログ
		System.out.println("[Lobby] sendMessage(): " +gson.toJson(message));
		System.out.println(); // 長いので空行挟む
		try {
			// 同期送信（sync）
			session.getBasicRemote().sendText(gson.toJson(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// public void disconnect() {
	// // たぶんonClose()でやってる
	// }

	/**
	 * ユーザー登録
	 * <p>
	 * 殆どの処理はLControllerに任せている
	 * 
	 * @param user_id  ユーザID
	 * @param password パスワード
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	public Message registerUser(String user_id, String password) {
		// 2000:ユーザー登録
		return LController.registerUser(user_id, password);
	}

	/**
	 * ログイン
	 * <p>
	 * 殆どの処理はLControllerに任せている
	 * 
	 * @param user_id  ユーザID
	 * @param password パスワード
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	public Message login(String user_id, String password) {
		// 2001:ログイン
		return LController.login(user_id, password);
	}

	// public void logout(String user_id) {
	// // たぶんonClose()でやってるけど

	// }

	/**
	 * ユーザー一覧取得
	 * <p>
	 * 殆どの処理はLControllerに任せている
	 * 
	 * @param なし
	 * @return ユーザ一覧
	 * @throws なし
	 * @author den3asphalt
	 */
	public ArrayList<User> getUserList() {
		return LController.getUserList();
	}

	/**
	 * 部屋の入室可否チェック
	 * <p>
	 * restClientからの返り値をMessageに格納して返す
	 * 
	 * @param message メッセージ
	 * @return なし
	 * @throws なし
	 * @author den3asphalt
	 */
	public Message checkRoomState(String user_id, int room_id) {

		// 通信規則が書いてない&必要ない単一なので、空
		Message post = new Message("", user_id);
		post.messageContent.room_id = room_id;
		Boolean result = restClient.checkRoomState(post);

		Message message = new Message("2002", user_id);
		message.result = result;

		return message;
	}

	/**
	 * 戦績取得
	 * <p>
	 * LDatabaseから取得して格納して返す order:2003
	 * 
	 * @param user_id ユーザID
	 * @return メッセージ
	 * @throws なし
	 * @author den3asphalt
	 */
	public Message getScore(String user_id) {
		Message message = new Message("2003", user_id);

		ArrayList<Integer> score = LDatabaseConnector.getScore(user_id);
		message.messageContent.num_plays_score = score.get(0);
		message.messageContent.num_wins_score = score.get(1);
		message.messageContent.num_hits_score = score.get(2);

		return message;
	}

	/**
	 * ルール取得
	 * <p>
	 * LDatabaseから取得して格納して返す order:2004
	 * 
	 * @param user_id ユーザID
	 * @return メッセージ
	 * @throws なし
	 * @author den3asphalt
	 */
	public Message getRule(String user_id) {
		Message message = new Message("2004", user_id);
		String rule = LDatabaseConnector.getRule();

		message.messageContent.image_data = rule;

		return message;
	}

}
