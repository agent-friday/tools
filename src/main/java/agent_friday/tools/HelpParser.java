/**
 *
 */
package agent_friday.tools;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author agent_friday
 *
 */
public class HelpParser extends DefaultHandler {
  private String option = "";
  private String parameter = "";
  private String description = "";
  private boolean param = false;
  private boolean desc = false;
  private boolean required = false;
  private String debugLevel = "";
  private Logger log;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {

    Logger log = getLogger();
    Level logLevel = log.getLevel();
    log.setLevel(Level.FINEST);

    if (qName.equals("Options")) {
      String programName = attributes.getValue("program");

      log.finest(String.format("%1s%n", ""));
      log.finest(String.format("%1s%n", "Usage: " + programName + " [options]"));
      log.finest(String.format("%1s%n", ""));
    }

    if (qName.equals("DebugLevels")) {
      log.finest(String.format("%n", ""));
      log.finest(String.format("%1s%n", "Debugging Levels:"));
      log.finest(String.format("%n", ""));
    }

    if (qName.equals("Option")) {
      option = attributes.getValue("name");

      if (attributes.getLength() > 1) {
        String req = attributes.getValue("required");
        if (req.equals("required")) {
          required = true;
        }
      }
    }

    if (qName.equals("Level")) {
      debugLevel = attributes.getValue("value");
      desc = true;
    }

    if (qName.equals("parameter")) {
      param = true;
    }

    if (qName.equals("description")) {
      desc = true;
    }

    log.setLevel(logLevel);
  } // End startElement(String, String, String, Attributes)

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    Logger log = getLogger();
    Level logLevel = log.getLevel();
    log.setLevel(Level.FINEST);

    if (qName.equals("Option")) {
      log.finest(String.format("     %1$-8s %2$-17s %3$s %4$-5s%n", option, parameter, description,
          required));

      option = "";
      parameter = "";
      description = "";
      required = false;
    }

    if (qName.equals("Level")) {
      log.finest(String.format("     %1$-5s %2$s%n", debugLevel, description));

      debugLevel = "";
      description = "";
      desc = false;
    }

    if (qName.equals("parameter")) {
      param = false;
    }

    if (qName.equals("description")) {
      desc = false;
    }

    if (qName.equals("DebugLevels")) {
      System.out.println();
    }
    log.setLevel(logLevel);
  } // End endElement(String, String, String)

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (desc) {
      description = new String(ch, start, length);
    }

    if (param) {
      parameter = "<" + new String(ch, start, length) + ">";
    }
  } // End characters(char[], int, int)

  private Logger getLogger() {
    if (log == null) {
      log = Logger.getLogger(this.getClass().getCanonicalName());
      StreamHandler sh = new StreamHandler(System.out, new Formatter() {

        @Override
        public String format(LogRecord record) {
          return record.getMessage();
        }

      });
      sh.setLevel(Level.FINEST);
      log.addHandler(sh);
    }
    return log;
  }
}
