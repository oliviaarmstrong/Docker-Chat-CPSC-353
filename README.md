# dockerchat

This repo contains programs to implement a multi-threaded TCP chat server and client

* MtClient.java handles keyboard input from the user.
* ClientListener.java receives responses from the server and displays them
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java receives messages from a client and relays it to the other clients.
* Class.java contains Socket and username of client

## Identifying Information

* Name: Olivia Armstrong, Kevin Le, Thomas Gooding
* Student ID: 2346715, 2406054, 2373468
* Email: oarmstrong@chapman.edu, kevle@chapman.edu, tgooding@chapman.edu
* Course: CPSC 353
* Assignment: PA04 Chat Submission 2

## Source Files

* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java
* Client.java

## References

* N/A

## Known Errors

* Submission 2: Exiting with Control-C from Client side causes NullPointerException. Exiting with Control-C from Server side causes infinite printing of null on Client terminal.

## Build Instructions

* javac *.java

## Execution Instructions

* java MtServer
* java MtClient

## Contributions
* Submission 1:
    * Olivia - Modified ClientHandler.java and MtServer.java to include client and client lists, updated README.md
    * Kevin - Modified MtClient.java so that the server adds username to the beginning of every message from every client
    * Thomas - Changed port number, assisted with modifying files, created Client.java File

* Submission 2: 
    * Olivia - Added "Who?" command that outputs all usernames of clients connected
    * Kevin - Added "QUIT" command that allows clients to disconnect from server without Control-C, updated README.md
    * Thomas - Added Username Duplicate Verification function that checks and prompts users again if username already in use

* Submission 3:
    * Olivia - Prompted that the username entered was accepted. Prompted Clients the possible commands. Prompted Host their commands and instruction on how to ask questions and award points. 
    * Kevin - Implemented the scoring/point system
    * Thomas - Added SCORES command for host, implemented scoring/point system
