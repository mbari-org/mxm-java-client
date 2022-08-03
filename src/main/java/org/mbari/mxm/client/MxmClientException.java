package org.mbari.mxm.client;

/**
 * A directly detected exception is encapsulated with this class.
 */
public class MxmClientException extends RuntimeException {
  public MxmClientException(String message) {
    super(message);
  }
  
  public MxmClientException(String message, Throwable cause) {
    super(message, cause);
  }
}
