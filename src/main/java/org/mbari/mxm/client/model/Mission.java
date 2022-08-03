package org.mbari.mxm.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Information about a particular mission.
 */
public class Mission extends ModelBase {
  /** ID of the provider */
  public String providerId;
  
  /** ID of the mission template this mission is an instance of. */
  public String missionTplId;

  /** ID of this mission instance. */
  public String missionId;

  /** ID of the asset instance associated with this mission. */
  public String assetId;

  /** Description of this mission. */
  public String description;
  
  /**
   * Associated arguments in this mission, that is, any overridden parameters.
   */
  @SerializedName(value="arguments", alternate={"argumentsByProviderIdAndMissionTplIdAndMissionIdList"})
  public List<Argument> arguments;
}
