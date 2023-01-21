import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClientHandler.java
 *
 * <p>This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */
public class ClientHandler implements Runnable {
  private Client myClient = null;
  // ArrayList of Client Objects
  private ArrayList<Client> clientList;
 

  //Modify ClientHandler 
  ClientHandler(Client myClient, ArrayList<Client> clientList) {
    this.myClient = myClient;
    this.clientList = clientList;  // Keep reference to master list
  }

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      System.out.println("Connection made with socket " + myClient.connectionSock);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(myClient.connectionSock.getInputStream()));
      DataOutputStream myClientOutput = new DataOutputStream(
          myClient.connectionSock.getOutputStream());
      String clientText;
      myClientOutput.writeBytes("Please enter \"host\" if you would like to be host. \n");
      while (myClient.username.equals("")) {
        int error = 0;
        clientText = clientInput.readLine();
        for (int i = 0; i < clientList.size(); ++i) {
          if (clientList.get(i).username.equals(clientText)) {
            System.out.println("Name error");
            myClientOutput.writeBytes("Name Taken. Please Enter Unique Username: \n");
            error += 1;
          }
        }
        if (error == 0) {
          myClient.username = clientText;
          myClientOutput.writeBytes("Username accepted \n");
          myClientOutput.writeBytes("List of commands: \n");
          myClientOutput.writeBytes("QUIT \n");
          myClientOutput.writeBytes("Who? \n");
          if (myClient.username.equals("host")) {
            myClientOutput.writeBytes("SCORES \n");
            myClientOutput.writeBytes("POINT \n");
            myClientOutput.writeBytes("Directions: \n");
            myClientOutput.writeBytes("Type in a question \n");
            myClientOutput.writeBytes("If a user enters the correct answer,");
            myClientOutput.writeBytes(" type in POINT to award point. \n");
            myClientOutput.writeBytes("Then enter the username. \n");
            
          }
        } else {
          continue;
        }
      }
      while (true) {
        // Get data sent from a client
        clientText = clientInput.readLine();
        //The user decides to type in QUIT
        if (clientText.equals("QUIT")) {
          // Connection was lost
          System.out.println("Closing connection for socket " + myClient.connectionSock);
          for (Client c : clientList) {
            if (c.connectionSock != myClient.connectionSock) {
              DataOutputStream clientOutput = new DataOutputStream(
                  c.connectionSock.getOutputStream());
              clientOutput.writeBytes(myClient.username + " left the chat\n");
            }
          }
          // Remove from arraylist
          clientList.remove(myClient);
          myClient.connectionSock.close();
          break;
        // The user decides to type in "Who?"
        } else if (clientText.equals("Who?")) {
          for (Client c : clientList) {
            if (c != myClient) {
              myClientOutput.writeBytes(c.username + "\n");
            }
          }
        } else if ((clientText.equals("SCORES")) && (myClient.username.equals("host"))) {
          for (Client c : clientList) {
            if (c != myClient) {
              myClientOutput.writeBytes(c.username + ": " + c.score + "\n");
            }
          }
        } else if ((clientText.equals("POINT")) && (myClient.username.equals("host"))) {
          myClientOutput.writeBytes("Enter the username that earned the point. \n");
          clientText = clientInput.readLine();
          for (Client c : clientList) {
            if (clientText.equals(c.username)) {
              c.addPoint();
            }
          }
          for (Client c : clientList) {
            if (c.connectionSock != myClient.connectionSock) {
              DataOutputStream clientOutput = new DataOutputStream(
                  c.connectionSock.getOutputStream());
              clientOutput.writeBytes(clientText + " earned a point \n");
            }
          }
        } else if (clientText != null) {
          System.out.println("Received: " + clientText);
          // Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          for (Client c : clientList) {
            if (c.connectionSock != myClient.connectionSock) {
              DataOutputStream clientOutput = new DataOutputStream(
                  c.connectionSock.getOutputStream());
              clientOutput.writeBytes(myClient.username + ": " + clientText + "\n");
            }
          }

        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + myClient.connectionSock);
          // Remove from arraylist
          clientList.remove(myClient);
          myClient.connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      clientList.remove(myClient);
    }
  }
} // ClientHandler for MtServer.java
