# 1.What is ChatApplication?
chat Application is a a simple client-server chat application using Java and Swing for the graphical user interface (GUI).
The project consists of two main classes: Server and Client, each representing a chat server and client respectively. The client-server communication is established over sockets.

# a.Client-Side
#### .The client GUI displays a heading, a text area to display messages, and an input field to type and send messages.
#### .When the client sends a message, it appears in the message area prefixed with "Me:".
#### .The client continuously listens for messages from the server.
#### .If the server terminates the chat or the client types "exit", the client's input field is disabled.
![Screenshot (32)](https://github.com/shraddha016/chat-application/assets/145293064/731c7416-cb33-4c43-a624-5d77f5262bc0)

# b.Server-Side
#### .The server GUI displays similar components as the client.
#### .When the server receives a message from the client, it appears in the message area prefixed with "Server:".
##### .The server continuously listens for messages from the client.
#### .If the client terminates the chat or the server types "exit", the server's input field is disabled.
![Screenshot (31)](https://github.com/shraddha016/chat-application/assets/145293064/03378751-21e9-434e-822e-68c9290c6fb2)

