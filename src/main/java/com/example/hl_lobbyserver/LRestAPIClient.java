package com.example.hl_lobbyserver;

import javax.websocket.ClientEndpoint;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

/**
 * ロビーサーバーのREST APIクライアント
 */
@ClientEndpoint
public class LRestAPIClient {

    private Client client = ClientBuilder.newClient();
    // http://localhost:8081/getsample
    private String lobbyServerUri = "http://localhost:8081";

    static int count = 0;
    static int id = 0;
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

        WebTarget target = client.target(lobbyServerUri).path("/checkRoomState");

        System.out.println(gson.toJson(message));
        count++;
        String result = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(gson.toJson(message), MediaType.APPLICATION_JSON), String.class);
        System.out.println("[RestClient] postExample: " + result);

        Boolean resultBoolean = gson.fromJson(result, Boolean.class);
        return resultBoolean;
    }
}