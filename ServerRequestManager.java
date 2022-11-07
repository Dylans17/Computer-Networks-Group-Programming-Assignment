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
    String input = in.readLine();
    String inputline;
    while ((inputline = in.readLine()) != null) {
      inputline = inputline.strip();
      input += "\n" + inputline;
    }
    if (logging) {
      System.out.println("Got request from socket:\n" + inputline);
    }
    handleInput(input, out);
    clientSocket.close();
    if (logging) {
      System.out.println("Socket Closed");
    }
  }

  private void handleInput(String input, PrintWriter out) throws IOException {
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
      out.printf("Error: Command \"%s\" not found!\n", handlerName);
    }
    else {
      handler.handle(args, out);
    }
  }
}