/**
 *
 */
package agent_friday.tools;

/**
 * @author agent_friday
 *
 */
public interface IsOption {
  public boolean isOption(String s);

  public String getLongName();

  public IsOption getHelp();

  public IsOption getNull();

  public String getShortName();

  @Override
  public String toString();

  public IsOption[] getValueArray();
} // End interface IsOption
