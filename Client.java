import java.io.*;
import java.net.*;

public class Client {
  public static void main(String[] args) {
    String hostName = "localhost";
    int portNumber = 4000;
    request(hostName, portNumber, "Date");
  }

  public static void request(String hostName, int portNumber, String messageString) {
    try (
      Socket socket = new Socket(hostName, portNumber);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    ) {
      System.out.printf("Sending \"%s\" to server\n", messageString);
      String responseString = sendReqString(out, in, messageString);
      System.out.println("Response: " + responseString);
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " +
        hostName);
      System.exit(1);
    }
  }

  public static String sendReqString(PrintWriter socketOut, BufferedReader socketIn, String messageString) throws IOException {
    socketOut.println(messageString);
    String result = "";
    String id = socketIn.readLine();
    String answer;
    while ((answer = socketIn.readLine()) != null) {
      if (answer.contains(id)) {

      }
      result += answer;
    }
    return result;
  }
}
