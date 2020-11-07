package agent_friday.tools;

public interface HasOptions<T extends IsOption> {
  public void getOpts(String[] args) throws MissingRequiredOptionException, InvalidOptionException;

  public void getOpts(String[] args, Message messages)
      throws MissingRequiredOptionException, InvalidOptionException;

  public void help();

  public void setCallback(T option, ActionCallback callback);

  public void setHelpFile(String helpFile);

  public void setOptions(T option);
}
