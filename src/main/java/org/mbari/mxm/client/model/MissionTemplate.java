package org.mbari.mxm.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Information about a particular mission template.
 */
public class MissionTemplate extends ModelBase {
  /** ID of this mission template. */
  public String missionTplId;
  
  /** Description of this mission template. */
  public String description;
  
  /** Associated list of asset classes. */
  @SerializedName(value="assetClasses", alternate={"missionTplAssetClassesByProviderIdAndMissionTplIdList"})
  public List<AssetClass> assetClasses;
  
  /** Associated list of parameters. */
  @SerializedName(value="parameters", alternate={"parametersByProviderIdAndMissionTplIdList"})
  public List<Parameter> parameters;
}
