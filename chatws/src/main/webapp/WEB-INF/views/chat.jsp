<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Chat using WebSocket</title>
		<script type="text/javascript" src="resources/js/websocket.js"></script>
	</head>
	<body>
		Username: <input type="text" id="username" value="${username}"/> </br>
		Message: <input type="text" id="message"/> <input type="submit" value="Submit" onclick="sendMessage()"/>
		</br>
		</br>
		</br>
		
		<div id="conversationArea"></div>
	</body>
</html>