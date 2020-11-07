/**
 *
 */
package agent_friday.tools;

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

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {

    if (qName.equals("Options")) {
      String programName = attributes.getValue("program");

      System.out.println();
      System.out.println("Usage: " + programName + " [options]");
      System.out.println();
    }

    if (qName.equals("DebugLevels")) {
      System.out.println();
      System.out.println("Debugging Levels:");
      System.out.println();
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

  } // End startElement(String, String, String, Attributes)

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equals("Option")) {
      System.out.println(String.format("     %1$-8s %2$-17s %3$s %4$-5s", option, parameter,
          description, required));

      option = "";
      parameter = "";
      description = "";
      required = false;
    }

    if (qName.equals("Level")) {
      System.out.println(String.format("     %1$-5s %2$s", debugLevel, description));

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

}
