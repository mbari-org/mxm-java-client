package org.mbari.mxm.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Information about a registered mission execution system.
 */
public class Provider extends ModelBase {
  /** ID of the provider. */
  public String providerId;

  /** Description of the provider. */
  public String description;

  /** URL of this provider's API for MXM purposes. */
  public String httpEndpoint;

  /** Type of this provider's API. */
  public String apiType;

  /** Can the provider validate missions (i.e., as a separate operation not necessarily for submission)? */
  public boolean canValidate;

  /** Does the provider handle scheduling options for missions? */
  public boolean usesSched;

  /** Do mission parameters need to include units of measure? */
  public boolean usesUnits;
  
  /** Associated list of mission templates. */
  @SerializedName(value="missionTemplates", alternate={"missionTplsByProviderIdList"})
  public List<MissionTemplate> missionTemplates;
}
