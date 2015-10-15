package com.george.chat.websocket;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.george.chat.controller.ChatController;

@ServerEndpoint("/ws/chat")
public class ChatWebSocket{
	private static final Map<Session,String> SESSIONS = Collections.synchronizedMap(new HashMap<Session,String>());
	
	@OnOpen
	public void onOpen(Session session){
		System.out.println("session open ("+session.getId()+")");
		SESSIONS.put(session, ChatController.DEFAULT_USERNAME);
	}
	
	@OnClose
	public void onClose(Session session){
		System.out.println("session closed ("+session.getId()+")");
		JsonObject jsonData = Json.createObjectBuilder()
							      .add("username", SESSIONS.get(session))
							      .add("event", "disconnected").build();
		String message = jsonData.toString();
		SESSIONS.remove(session);
		broadcast(message);
	}
	
	@OnError
	public void onError(Session session, Throwable thwroable){
		System.out.println("Error on session "+session.getId()+" ("+SESSIONS.get(session)+"):");
		thwroable.printStackTrace();
	}
	
	@OnMessage
	public void onMessage(String jsonText, Session session){
		System.out.println(jsonText);
		
		JsonReader jsonReader = Json.createReader(new StringReader(jsonText));
		JsonObject jsonData = jsonReader.readObject();
		String currentName = SESSIONS.get(session);
		String newName = jsonData.getString("username");
		if (!newName.equals(currentName)){
			JsonObject jsonDataNameChanged = Json.createObjectBuilder()
				      							 .add("old_username", currentName)
				      							 .add("new_username", newName).build();
			String messageNameChanged = jsonDataNameChanged.toString();
			broadcast(messageNameChanged);
			SESSIONS.put(session, jsonData.getString("username"));
		}
		
		broadcast(jsonText);
	}
	
	private void broadcast(String message){
		for (Session openSession : SESSIONS.keySet()){
			try {
				openSession.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}