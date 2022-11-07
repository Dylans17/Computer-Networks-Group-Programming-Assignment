import java.io.IOException;
import java.io.PrintWriter;

public interface ServerRequestHandler {
  public void handle(String args, PrintWriter out) throws IOException;
}