package org.mbari.mxm.client.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Convenience base class for the MXM model entities just to include
 * a human-friendly rendering in JSON format.
 */
public abstract class ModelBase {
  /**
   * A rendering of this model object in JSON format.
   */
  @Override
  public String toString() {
    return gson.toJson(this);
  }
  
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
}
