import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

  public Socket connectionSock = null;

  public String username = "";

  public int score = 0;



  Client(Socket sock, String username) {

    this.connectionSock = sock;

    this.username = username;

    this.score = score;

  }

  void addPoint() {
    ++score;
  }

 }