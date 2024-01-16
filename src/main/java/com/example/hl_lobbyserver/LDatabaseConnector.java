package com.example.hl_lobbyserver;

import java.util.ArrayList;
import java.sql.*;

import com.mysql.jdbc.Driver;

/**
 * データベースとの通信を行うクラス
 * <p>
 * 認証・メッセージ作成などは行わない
 */
public class LDatabaseConnector {
    private static final String sqlDriverName = "com.mysql.jdbc.Driver";

    // SQLサーバの指定
    private static final String url = "jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp";
    private static final String sqlServerPort = "13308";

    // 以下は班ごとに違うことに注意
    private static final String sqlDatabaseName = "db_group_b";
    private static final String sqlUserId = "group_b";
    private static final String sqlPassword = "group_b";

    LDatabaseConnector() {
        try {
            Class.forName(sqlDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * ルール取得
     * <p>
     * アプリケーションサーバから移動してきた処理
     * 
     * @param なし
     * @return String型のルール全文
     * @throws なし
     * @author den3asphalt
     */
    public String getRule() {
        String rule = null;
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("[Lobby] getRule: Access to " + target);

            // 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            // MySQLに問い合わせるためのStatementオブジェクトを構築する
            Statement stmt = connection.createStatement();
            // 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
            // ここはやりたい処理によって大きく変わることに注意
            String queryString = "SELECT rule FROM RULE;";

            // Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
            ResultSet rs = stmt.executeQuery(queryString);

            // 得られた結果の集合から必要なデータを取り出す
            while (rs.next()) {
                System.out.println("[Lobby] getRule(): Done");
                rule = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rule;

    }

    /**
     * ユーザ一覧取得
     * <p>
     * SQLサーバに接続し，ユーザ一覧を取得する
     * 
     * @param なし
     * @return ユーザ一覧
     * @throws なし
     * @author den3asphalt
     */
    public ArrayList<User> getUserList() {
        ArrayList<User> user_list = new ArrayList<User>();
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("[Lobby] getuserList: Access to " + target);

            // 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            // MySQLに問い合わせるためのStatementオブジェクトを構築する
            Statement stmt = connection.createStatement();
            // 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
            // ここはやりたい処理によって大きく変わることに注意
            String queryString = "SELECT user_id, password FROM USER;";

            // Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
            ResultSet rs = stmt.executeQuery(queryString);

            // 得られた結果の集合から必要なデータを取り出す
            while (rs.next()) {
                // Userクラスのインスタンスを作成し，ユーザ一覧に追加する
                user_list.add(new User(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user_list;
    }

    /**
     * ユーザ登録
     * <p>
     * SQLサーバに接続し，ユーザを登録する
     * 
     * @param user_id  ユーザID
     * @param password パスワード
     * @return なし
     * @throws なし
     * @author den3asphalt
     */
    public void registerUser(String user_id, String password) {
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("[Lobby] registerUser: Access to " + target);

            // 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            // MySQLに問い合わせるためのStatementオブジェクトを構築する
            Statement stmt = connection.createStatement();
            // 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
            // ここはやりたい処理によって大きく変わることに注意
            String queryString = "INSERT INTO USER VALUES('" + user_id + "', '" + password + "');";

            // Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
            stmt.executeUpdate(queryString);

            queryString = "INSERT INTO SCORE VALUES('" + user_id + "', 0, 0, 0);";

            stmt.executeUpdate(queryString);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * スコアの取得
     * @param user_id ユーザID
     * @return スコアのリスト
     * @throws なし
     * @author den3asphalt
     */
    public ArrayList<Integer> getScore(String user_id) {
        ArrayList<Integer> scoreDataList = null;
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("[Lobby] getScore: Access to " + target);

            // 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            // MySQLに問い合わせるためのStatementオブジェクトを構築する
            Statement stmt = connection.createStatement();
            // 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
            // ここはやりたい処理によって大きく変わることに注意
            String queryString = "SELECT * FROM SCORE WHERE user_id = '" + user_id + "';";

            // Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
            ResultSet rs = stmt.executeQuery(queryString);

            // 得られた結果の集合から必要なデータを取り出す
            scoreDataList = new ArrayList<>();
            // 0→plays 1→hits 2→wins

            while (rs.next()) {
                System.out.println(rs.getString(1));
                scoreDataList.add(Integer.parseInt(rs.getString(2)));
                scoreDataList.add(Integer.parseInt(rs.getString(3)));
                scoreDataList.add(Integer.parseInt(rs.getString(4)));
                // textDataList.add(rs.getString(1));
            }
            System.out.println("List Elements:");
            for (int element : scoreDataList) {
                System.out.println(element);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return scoreDataList;
    }

}