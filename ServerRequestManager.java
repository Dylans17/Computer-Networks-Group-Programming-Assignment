import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ServerRequestManager {
  private HashMap<String, ServerRequestHandler> handlerMap = new HashMap<String, ServerRequestHandler>();
  public boolean logging = true;

  public boolean addHandler(String reqString, ServerRequestHandler handler) {
    if (handlerMap.containsKey(reqString)) {
      return false;
    }
    handlerMap.put(reqString, handler);
    return true;
  }

  public boolean removeHandler(String reqString) {
    return handlerMap.remove(reqString) != null;
  }

  public void handleRequest(Socket clientSocket) throws IOException {
    if (logging) {
      System.out.println("Accepted a new connection!");
    }
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String message;
    while ((message = RequestHeaderHandler.readMessage(in)) != null) {
      if (logging) {
        System.out.println("Got message from socket:\n" + message);
      }
      String serverResponse = handleRequestMessage(message);
      RequestHeaderHandler.writeMessage(out, serverResponse);
    }
    if (logging) {
      System.out.println("Socket Closed");
    }
  }

  private String handleRequestMessage(String input) {
    //line parsing
    int space = input.indexOf(' ');
    String handlerName;
    String args;
    if (space != -1) {
      handlerName = input.substring(0, space);
      args = input.substring(space);
    }
    else {
      handlerName = input;
      args = "";
    }
    ServerRequestHandler handler = handlerMap.get(handlerName);
    if (handler == null) {
      return String.format("Server Error: Command \"%s\" not found!\n", handlerName);
    }
    else {
      return handler.handle(args);
    }
  }
}