package org.mbari.mxm.client.model;

/**
 * Information about a particular parameter in a mission template.
 */
public class Parameter extends ModelBase {
  /** Name of the parameter. */
  public String paramName;
  
  /** Type of the parameter. */
  public String type;
  
  /** Default value if any. */
  public String defaultValue;
  
  /** Default units of measure if any. */
  public String defaultUnits;
  
  /** Is this parameter required?. */
  public boolean required;
  
  /** Description of this parameter. */
  public String description;
}
