package org.mbari.mxm.client.model;

/**
 * Information about an argument (that is, an overridden parameter) in a particular mission.
 */
public class Argument extends ModelBase {
  /** Name of the parameter overridden by this argument. */
  public String paramName;

  /** Value for the parameter. */
  public String paramValue;

  /** Units of measure (if any) for the parameter. */
  public String paramUnits;
}
