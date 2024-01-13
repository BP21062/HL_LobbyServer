package com.example.hl_lobbyserver;

import java.util.ArrayList;

/**
 * ロビーサーバーのコントローラー
 * <p>
 * staticに使い、インスタンス化はしない
 */
public class LController {

	/**
	 * ユーザー登録
	 * <p>
	 * 殆どの処理はLDatabaseConnectorに任せている
	 * order:2000
	 * 
	 * @param user_id  ユーザID
	 * @param password パスワード
	 * @return 成否についてのメッセージ
	 * @throws なし
	 * @author den3asphalt
	 */
	public static Message registerUser(String user_id, String password) {
		Boolean result = checkDuplicateUserID(user_id, getUserList());
		Message message = new Message("2000", user_id);

		message.result = result;

		if (!result) {
			// false=重複していない
			LDatabaseConnector.registerUser(user_id, password);
		}

		return message;
	}

	/**
	 * ユーザ一覧取得
	 * <p>
	 * 殆どの処理はLDatabaseConnectorに任せている
	 * 
	 * @param なし
	 * @return ユーザ一覧
	 * @throws なし
	 * @author deh3asphalt
	 */
	public static ArrayList<User> getUserList() {
		return LDatabaseConnector.getUserList();
	}

	/**
	 * ユーザID重複チェック
	 * <p>
	 * 重複していたらtrueを返す
	 * 
	 * @param user_id   ユーザID
	 * @param user_list ユーザ一覧
	 * @return 重複しているかどうか
	 * @throws なし
	 */
	public static Boolean checkDuplicateUserID(String user_id, ArrayList<User> user_list) {
		for (User user : user_list) {
			if (user.user_id.equals(user_id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ログイン処理
	 * <p>
	 * Messageに結果を格納して返す order:2001
	 * @param user_id ユーザID
	 * @param password パスワード
	 * @return Messageクラス
	 * @throws なし
	 * @author den3asphalt
	 */
	public static Message login(String user_id, String password) {
		Boolean result = verifyUserIDAndPassword(user_id, password);
		Message message = new Message("2001", user_id);
		message.result = result;

		return message;
	}
	/**
	 * 認証処理
	 * <p>
	 * ログイン処理の内部はこっちで行う
	 * @param user_id ユーザID
	 * @param password パスワード
	 * @return 認証結果
	 * @throws なし
	 * @author den3asphalt
	 */
	public static Boolean verifyUserIDAndPassword(String user_id, String password) {
		ArrayList<User> user_list = getUserList();
		for (User user : user_list) {
			if (user.user_id.equals(user_id) && user.password.equals(password)) {
				return true;
			}
		}

		return false;
	}

	// public void logout(String user_id) {
	// 		// たぶんonClose()でやってるけど
	// }
}
