import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServerExecHandler implements ServerRequestHandler {
  private boolean includeArgs;
  private String command;

  public ServerExecHandler(String command, boolean includeArgs) {
    this.includeArgs = includeArgs;
    this.command = command;
  }

  @Override
  public void handle(String args, PrintWriter out) throws IOException {
    String command = this.command;
    if (includeArgs) {
      command = String.format("%s %s", command, args);
    }
    Process process = Runtime.getRuntime().exec(command);
    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String output;
    while ((output = br.readLine()) != null) {
      out.println(output);
    }
  }
}
