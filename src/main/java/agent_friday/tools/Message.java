/**
 *
 */
package agent_friday.tools;

/**
 * @author agent_friday
 *
 */
public interface Message {
  public void debug(char level, String message);

  public void verbose(String message);
}
