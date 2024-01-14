package com.example.hl_lobbyserver;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

/**
 * 使わない
 */
@Path("/")
public class LRestAPIServer {

    static Gson gson = new Gson();

    @Path("/getsample")
    @GET
    @Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
    public String getSample(){
        return "GET Sample";
    }


    @Path("/postsample")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSample(String requestBody) {

		try{

			// リクエストメッセージの処理
			Message rxMsg = gson.fromJson(requestBody, Message.class);

			// レスポンスメッセージの生成
			Message respMsg = new Message("", null);
			return Response.ok().entity(gson.toJson(respMsg)).build();

		} catch (Exception e) {

			e.printStackTrace();
			int status = 400;
			return Response.status(status).build();

		}
	}
}
