import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerExecHandler implements ServerRequestHandler {
  private boolean includeArgs;
  private String command;

  public ServerExecHandler(String command, boolean includeArgs) {
    this.includeArgs = includeArgs;
    this.command = command;
  }

  @Override
  public String handle(String args) {
    String command = this.command;
    if (includeArgs) {
      command = String.format("%s %s", command, args);
    }
    
    try {
      Process process = Runtime.getRuntime().exec(command);
      BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String result = "";
      String output;
      while ((output = br.readLine()) != null) {
        result += output + "\n";
      }
      return result;
    }
    catch (IOException e) {
      String message = String.format("Error while running \"%s\": %s\n", command, e.getMessage());
      System.err.println(message);
      return message;
    }
  }
}