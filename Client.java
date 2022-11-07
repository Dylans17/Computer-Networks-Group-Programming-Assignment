import java.io.*;
import java.net.*;

public class Client {
  public static void main(String[] args) {
    String hostName = "localhost";
    int portNumber = 4000;
    try (
      Socket socket = new Socket(hostName, portNumber);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    ) {
      request(in, out, "Date");
      request(in, out, "Uptime");
      request(in, out, "Memory");
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " +
        hostName);
      System.exit(1);
    }
  }

  public static void request(BufferedReader in, PrintWriter out, String messageString) throws IOException{
      System.out.printf("Sending \"%s\" to server\n", messageString);
      RequestHeaderHandler.writeMessage(out, messageString);
      String responseString = RequestHeaderHandler.readMessage(in);;
      System.out.println("Response:\n" + responseString);
  }
}
