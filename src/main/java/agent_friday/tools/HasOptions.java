package agent_friday.tools;

public interface HasOptions {
  public void getOpts(String[] args, Message messages)
      throws MissingRequiredOptionException, InvalidOptionException;

  public void help();

  public void setCallback(IsOption option, ActionCallback callback);

  public void setHelpFile(String helpFile);

  public void setOptions(IsOption option);
}
