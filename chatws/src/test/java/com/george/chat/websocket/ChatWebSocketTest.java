package com.george.chat.websocket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

public class ChatWebSocketTest{
	
	ChatWebSocket websocket = new ChatWebSocket();
	
	Session session1 = Mockito.mock(Session.class);
	Session session2 = Mockito.mock(Session.class);
	
	@Before
	public void init(){
		when(session1.getId()).thenReturn("1");
		when(session2.getId()).thenReturn("2");
		
		RemoteEndpoint.Basic basicRemoteMock = Mockito.mock(RemoteEndpoint.Basic.class);
		Mockito.when(session1.getBasicRemote()).thenReturn(basicRemoteMock);
		Mockito.when(session2.getBasicRemote()).thenReturn(basicRemoteMock);
	}
	
	@Test
	public void testChatMessage() throws Exception{
		JsonObject jsonDataTest1 = Json.createObjectBuilder()
									   .add("username", "user")
									   .add("message", "test msg 1").build();
		String msgTest1 = jsonDataTest1.toString();

		JsonObject jsonDataTest2 = Json.createObjectBuilder()
									   .add("username", "user_2")
									   .add("message", "test msg 2").build();
		String msgTest2 = jsonDataTest2.toString();
		
		websocket.onOpen(session1);
		websocket.onOpen(session2);
		
		websocket.onMessage(msgTest1, session1);
		websocket.onMessage(msgTest2, session2);
		
		websocket.onError(session1,new RuntimeException());
		
		websocket.onClose(session1);
		websocket.onClose(session2);
		
		session1.close();
		session2.close();
		Assert.assertTrue("session1 closed", !session1.isOpen());
		Assert.assertTrue("session2 closed", !session2.isOpen());
	}
	
}