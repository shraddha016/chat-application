# 1.What is ChatApplication?
chat Application is a a simple client-server chat application using Java and Swing for the graphical user interface (GUI).
The project consists of two main classes: Server and Client, each representing a chat server and client respectively. The client-server communication is established over sockets.

# a.Client-Side
#### .The client GUI displays a heading, a text area to display messages, and an input field to type and send messages.
#### .When the client sends a message, it appears in the message area prefixed with "Me:".
#### .The client continuously listens for messages from the server.
#### .If the server terminates the chat or the client types "exit", the client's input field is disabled.
