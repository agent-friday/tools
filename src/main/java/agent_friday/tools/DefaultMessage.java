package agent_friday.tools;

/**
 * A default implementation of the {@linkplain Message} interface. Assumes {@code -verbose=true} and
 * {@code -debug=all}. All messages passed to the {@code debug} and {@code verbose} methods will be
 * printed, regardless of the options that may have been set on the commandline
 *
 * @author agent_friday
 *
 */
public class DefaultMessage implements Message {

  private volatile static DefaultMessage instance;

  private DefaultMessage() {
    // Intentionally blank.
  }

  public static DefaultMessage getInstance() {
    if (instance == null) {
      synchronized (DefaultMessage.class) {
        if (instance == null) {
          instance = new DefaultMessage();
        }
      }
    }
    return instance;
  }

  @Override
  public void debug(char level, String message) {
    System.out.println("DEBUG[" + level + "]: " + message);
  }

  @Override
  public void verbose(String message) {
    System.out.println(message);
  }

  @Override
  public void error(String message) {
    System.err.println(message);
  }

}
