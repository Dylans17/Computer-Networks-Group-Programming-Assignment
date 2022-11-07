import java.net.*;
import java.io.*;

public class Server {
  public static void main(String[] args) throws IOException {
    int portNumber = 4000;

    ServerRequestManager mngr = new ServerRequestManager(); 
    mngr.addHandler("Date", new ServerExecHandler("date", false));
    mngr.addHandler("Uptime", new ServerExecHandler("uptime", false));
    mngr.addHandler("Memory", new ServerExecHandler("free", false));
    try (
      ServerSocket serverSocket = new ServerSocket(portNumber);
    ) {
      while(true) {
        System.out.println("Accepting Sockets:");
        mngr.handleRequest(serverSocket.accept());
    }
    } catch (IOException e) {
      System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
      System.out.println(e.getMessage());
    }
  }
}