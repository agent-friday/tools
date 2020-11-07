/**
 *
 */
package agent_friday.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author agent_friday
 *
 */
public class OptionsParser implements HasOptions {

  private static OptionsParser instance = new OptionsParser();
  private String helpFile = "Help.xml";
  private IsOption nullOpt = null;
  private IsOption helpOpt = null;
  protected Map<IsOption, List<ActionCallback>> callbacks = new HashMap<>();
  protected Map<IsOption, Boolean> requiredOptions = new HashMap<>();
  private Map<IsOption, List<String>> setOptions = new HashMap<>();

  private OptionsParser() {
    // Intentionally blank
  } // End constructor

  public static OptionsParser getInstance() {
    return instance;
  } // End getInstance

  /**
   * Parses <code>args</code> and notifies any classes passed into <code>setCallback(...)</code> of
   * the values for the arguments/flags they are interested in.<br>
   * <br>
   * To describe the parsing algorithm, we define two types of elements in <code>args</code>:
   * <ol>
   * <li>Values - elements with out a leading '-' character.</li>
   * <li>Arguments (or flags) - elements with a leading '-' character.</li>
   * </ol>
   * Argument/flag elements are described by all values between it(the argument/flag) and the next
   * argument/flag or the end of user input. For example, we have the following command line
   * <p>
   * <code>command -arg1 value11 value12 -arg2 value21 -arg3 -arg4</code>
   * </p>
   * The values associated with <code>arg1</code> will be <code>value11</code> and
   * <code>value12</code>. While <code>arg3</code> and <code>arg4</code> will have no values
   * associated with them.
   *
   * @param args The array passed in by the <code>main</code> method.
   * @throws InvalidOptionException Thrown if the user supplies an argument that doesn't match any
   *         possible <code>Options</code> values.
   * @throws MissingRequiredOptionException Thrown when the user does not give a required option.
   */
  @Override
  public void getOpts(String[] args, Message messages)
      throws MissingRequiredOptionException, InvalidOptionException {
    // List of values associated with the current command line argument/flag. For example, command
    // -argument value1 value2 ... where value1 and value2 would be placed into paramsForOption
    List<String> paramsForOption = new ArrayList<>();
    // The classes that need to be passed in the values from paramsForOption
    List<ActionCallback> localCallbacks = new ArrayList<>();
    // The current command line argument/flag. For example, command -argument value1 value2 ...
    // where the OptionsEnum equivalent of "argument" would be placed into curOption. The
    // equivalence is given through the paramToEnum() method.
    IsOption currentOption = nullOpt;

    getRequiredOptions();

    for (String arg : args) {
      // Assuming that the user will not put a "-" in front of any values
      // they pass in.
      if (isArgFlag(arg)) {
        // Run the previous list of callbacks with the values collected. Only run the callbacks if
        // there are callbacks to run AND if the user has specified an argument/flag value. This
        // will only be true after the first flag and value is parsed. For example, command -arg1
        // value1 -arg2 value2 ... where arg1 and value1 will be used when "-arg2" is encountered.
        if (currentOption != nullOpt) {
          doCallbacks(currentOption, localCallbacks, paramsForOption);
          paramsForOption = new ArrayList<>();
          localCallbacks = new ArrayList<>();
        } // End if callbacks set, and have option

        // The next argument/flag is a function of arg[i] minus the leading "-".
        currentOption = paramToEnum(arg.substring(1));

        if (currentOption == helpOpt) {
          help();
        }

        // Make sure that a valid flag was found arg[i]
        if (currentOption != nullOpt) {
          // Update the callback list
          localCallbacks = callbacks.getOrDefault(currentOption, new ArrayList<>());

          checkIsRequired(currentOption);
        } // End if CurrentOptions not null
        // End if argument
      } else { // If arg[i] is not an argument/flag, then it must be value.
        paramsForOption.add(arg);
      } // End if argument parameter

    } // End foreach arg

    doCallbacks(currentOption, localCallbacks, paramsForOption);

    checkRequiredOptions();

    debugSetOptions(messages);
  } // End getOpts(String[])

  private void checkIsRequired(IsOption option) {
    if (requiredOptions.containsKey(option)) {
      requiredOptions.put(option, Boolean.TRUE);
    }
  } // End checkIsRequired(IsOption)

  private boolean isArgFlag(String arg) {
    return arg != null && !arg.isEmpty() && (arg.charAt(0) == '-' || arg.charAt(0) == '/');
  } // End isArgFlag(String)

  private void doCallbacks(IsOption currentOption, List<ActionCallback> localCallbacks,
      List<String> paramsForOption) {
    if (localCallbacks != null && currentOption != nullOpt && !localCallbacks.isEmpty()) {
      String[] params = paramsForOption.toArray(new String[] {""});
      Iterator<ActionCallback> it = localCallbacks.iterator();

      while (it.hasNext()) {
        it.next().callback(currentOption, params);
      }

      setOptions.put(currentOption, paramsForOption);

      List<IsOption> keys = new ArrayList<>(requiredOptions.keySet());
      for (IsOption key : keys) {
        if (key == currentOption) {
          requiredOptions.put(currentOption, true);
        }
      }
    }
  } // End doCallbacks(IsOption, List<ActionCallback>, List<String>)

  private void checkRequiredOptions() throws MissingRequiredOptionException {
    for (Entry<IsOption, Boolean> entry : requiredOptions.entrySet()) {
      if (!entry.getValue().booleanValue()) {
        String optionName = entry.getKey().toString();
        throw new MissingRequiredOptionException("Required option: " + optionName + " not set.");
      }
    } // End check for missing required options
  } // End checkRequiredOptions()

  private void debugSetOptions(Message message) {
    StringBuilder opts = new StringBuilder(String.format("%1s%n", "Options set:"));
    for (Entry<IsOption, List<String>> entry : setOptions.entrySet()) {
      opts.append("\t" + entry.getKey().toString() + ":");

      if (!entry.getValue().isEmpty()) {
        for (String param : entry.getValue()) {
          opts.append(" " + param);
        }
      } // End if option has parameters
      else {
        opts.append(" True");
      } // End if option is flag.

      opts = new StringBuilder(String.format("%1s%n", opts.toString()));
    } // End foreach set option
    message.debug('0', opts.toString());
  } // End debugSetOptions(Message)

  private void getRequiredOptions() {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      RequiredOptionsHandler handler = new RequiredOptionsHandler();
      saxParser.parse(helpFile, handler);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } // End getRequiredOptions

  /**
   * Accepts a command line argument/flag and returns the corresponding <code>Options</code>
   * constant. If an <code>Options</code> constant cannot be found, an
   * <code>InvalidOptionException</code> is thrown. <br>
   * <strong>Note:</strong> Case will be ignored in the case of full <code>Options</code> constant
   * names, but will not be ignored for their shorthand equivalent.
   *
   * @param param The command line argument/flag that needs to be converted into an existing
   *        <code>Options</code> constant.
   *
   * @return The <code>Options</code> equivalent of <code>param</code>.
   *
   * @throws InvalidArgumentException Thrown if <code>param</code> doesn't match any possible
   *         OptionsEnum values.
   */
  private IsOption paramToEnum(String param) throws InvalidOptionException {
    IsOption[] availableOptions = nullOpt.getValueArray();

    for (IsOption option : availableOptions) {
      if (option != nullOpt && option.isOption(param)) {
        return option;
      } // end if match
    } // end foreach AvailableOption

    throw new InvalidOptionException("Invalid argument: " + param);
  } // end ParamToEnum

  @Override
  public void setHelpFile(String helpFile) {
    this.helpFile = helpFile;
  } // End setHelpFile(String)

  @Override
  public void setOptions(IsOption option) {
    nullOpt = option.getNull();
    helpOpt = option.getHelp();
  } // End setOptions(IsOption)

  /**
   * Prints out the help message contained in the help file.
   */
  @Override
  public void help() {
    if (helpFile != null) {
      try {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        HelpParser handler = new HelpParser();
        saxParser.parse(helpFile, handler);
        System.exit(0);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  } // End help

  /**
   * Adds a class that implements <code>Callback</code> interface to the list of classes that should
   * be notified of the values for argument/flag <code>option</code>. When the <code>option</code>
   * argument has been encountered and its values parsed, <code>callback</code> will be notified of
   * those values. <br>
   * <br>
   * A list of <code>Callback</code> implementations is maintained in case more than one class
   * should be notified of the same argument/flag and its values. For example, two different classes
   * may need to know the same file path.
   *
   * @param option The argument/flag that <code>Callback</code> needs values for.
   * @param callback The class that is notified of the values in <code>Option</code>.
   */
  @Override
  public void setCallback(IsOption option, ActionCallback callback) {
    if (nullOpt != null && option.getClass() != nullOpt.getClass()) {
      return;
    }

    List<ActionCallback> classes = callbacks.getOrDefault(option, new ArrayList<>());
    classes.add(callback);
    callbacks.put(option, classes);
  } // End setCallback(IsOption, Callback)

  class RequiredOptionsHandler extends DefaultHandler {
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      if (qName.equals("Option")) {
        String name = attributes.getValue("name");

        if (attributes.getLength() > 1) {
          String required = attributes.getValue("required");
          if (required.equals("required")) {
            // This should always return true
            try {
              // Found a required option, put it in the map and set it to false. When the option and
              // value are read in, this will get set to true signifying that we have the required
              // option.
              requiredOptions.put(paramToEnum(name), Boolean.FALSE);
            } catch (InvalidOptionException e) {
              e.printStackTrace();
            }
          } // End if option is required
        } // End if option tag has more than one attribute
      } // End if have option tag
    } // End startElement(String, String, String, Attributes)
  } // End class RequiredOptionsHandler
} // End class OptionsParser
