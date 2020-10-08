package agent_friday.tools;

public class MissingRequiredOptionException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   *
   */
  public MissingRequiredOptionException() {
    super();
  }

  /**
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   */
  public MissingRequiredOptionException(String message) {
    super(message);
  }

  /**
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   */
  public MissingRequiredOptionException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message The detail message. The detail message is saved for later retrieval by the
   *        Throwable.getMessage() method.
   * @param cause The cause (which is saved for later retrieval by the Throwable.getCause() method).
   *        (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
   */
  public MissingRequiredOptionException(String message, Throwable cause) {
    super(message, cause);
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
  }
} // End class MissingRequiredOptionException
