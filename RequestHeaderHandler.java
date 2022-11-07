import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestHeaderHandler {
  public static void writeMessage(PrintWriter out, String message) {
    out.printf("%d\n", message.length());
    out.print(message);
    out.flush();
  }
  public static String readMessage(BufferedReader in) throws IOException {
    String headerLine = in.readLine();
    if (headerLine == null) {return headerLine;}
    int length = Integer.parseInt(headerLine);
    char data[] = new char[length];
    for (int i = 0; i < length; i++) {
      data[i] = (char) in.read();
    }
    return String.valueOf(data);
  }
}
