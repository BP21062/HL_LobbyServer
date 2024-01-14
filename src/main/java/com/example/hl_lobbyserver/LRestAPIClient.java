package com.example.hl_lobbyserver;

import jakarta.websocket.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;

import com.google.gson.Gson;

/**
 * ロビーサーバーのREST APIクライアント
 */
@ClientEndpoint
public class LRestAPIClient {

    private Client client = ClientBuilder.newClient();
    // http://localhost:8081/getsample
    private String appServerUri = "http://localhost:8081";
    static Gson gson = new Gson();

    /**
     * 部屋の状態を確認
     * <p>
     * Messageを送るだけ
     * 
     * @param message メッセージ
     * @return 部屋に入れるかどうか
     * @throws なし
     * @author den3asphalt
     */
    public Boolean checkRoomState(Message message) {

        // Appサーバへのリクエスト
        WebTarget target = client.target(appServerUri).path("/checkRoomState");

        // ログ
        System.out.println("[Lobby] checkRoomState send Message: " + gson.toJson(message));
        System.out.println("");

        // メッセージを送信
        String result = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(gson.toJson(message), MediaType.APPLICATION_JSON), String.class);

        // ログ
        System.out.println("[Lobby] checkRoomState: " + result);

        // 結果をBoolean型に変換
        Boolean resultBoolean = gson.fromJson(result, Boolean.class);
        return resultBoolean;
    }
}