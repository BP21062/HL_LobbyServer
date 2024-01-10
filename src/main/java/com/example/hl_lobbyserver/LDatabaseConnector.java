package com.example.hl_lobbyserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

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

    // ルール取得
    public String getRule() {
        String rule = null;
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("target: " + target);

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
                System.out.println(rs.getString(1));
                rule = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rule;

    }

    // ユーザー一覧取得
    public ArrayList<User> getUserList() {
        ArrayList<User> user_list = new ArrayList<User>();
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("target: " + target);

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
                user_list.add(new User(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user_list;
    }

    // ユーザー登録
    public void registerUser(String user_id, String password) {
        try {

            // 接続先はこんな感じの文字列->jdbc:mysql://sql.yamazaki.se.shibaura-it.ac.jp:13307/データベース名
            String target = url + ":" + sqlServerPort + "/" + sqlDatabaseName;
            System.out.println("target: " + target);

            // 接続先情報と"MySQLへログインするための"ユーザIDとパスワードから接続を行う
            Connection connection = DriverManager.getConnection(target, sqlUserId, sqlPassword);
            // MySQLに問い合わせるためのStatementオブジェクトを構築する
            Statement stmt = connection.createStatement();
            // 実際にMySQLデータベースサーバに問い合わせるときのクエリメッセージを作る
            // ここはやりたい処理によって大きく変わることに注意
            String queryString = "INSERT INTO USER VALUES('" + user_id + "', '" + password + "');";

            // Statementオブジェクトとクエリメッセージを使い，実際に問い合わせて結果を得る
            ResultSet rs = stmt.executeQuery(queryString);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}