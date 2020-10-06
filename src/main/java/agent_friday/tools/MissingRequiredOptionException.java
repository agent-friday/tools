package agent_friday.tools;

public class MissingRequiredOptionException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   *
   */
  public MissingRequiredOptionException() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   */
  public MissingRequiredOptionException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   */
  public MissingRequiredOptionException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   */
  public MissingRequiredOptionException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   * @param enableSuppression Whether or not suppression is enabled or disabled
   * @param writableStackTrace Whether or not the stack trace should be writable
   */
  public MissingRequiredOptionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    // TODO Auto-generated constructor stub
  }
} // End class MissingRequiredOptionException
