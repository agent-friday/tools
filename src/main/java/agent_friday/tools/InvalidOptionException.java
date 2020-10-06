/**
 *
 */
package agent_friday.tools;

/**
 * @author agent_friday
 *
 */
public class InvalidOptionException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new exception with <code>null</code> as its detail message.
   */
  public InvalidOptionException() {
    this("Unknown option found.");
  }

  /**
   * Constructs a new exception with the specified detail message
   *
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   */
  public InvalidOptionException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified cause and a detail message of
   * <code>(cause==null ? null : cause.toString())</code> (which typically contains the class and
   * detail message of <code>cause</code>).
   *
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   */
  public InvalidOptionException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   */
  public InvalidOptionException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified detail message, cause, suppression enabled or
   * disabled, and writable stack trace enabled or disabled.
   *
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   * @param enableSuppression Whether or not suppression is enabled or disabled
   * @param writableStackTrace Whether or not the stack trace should be writable
   */
  public InvalidOptionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  } // End InvalidOption(String, Throwable, boolean, boolean)

} // End class InvalidOptionException
