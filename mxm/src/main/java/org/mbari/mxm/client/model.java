package org.mbari.mxm.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Set of model classes whose instances are provided by methods
 * in the MxmClient.
 */
public class model {
  
  public static abstract class ModelBase {
    /**
     * A rendering of this model object in JSON format.
     */
    @Override
    public String toString() {
      return MxmClient.gson.toJson(this);
    }
  }
  
  /**
   * Information about a registered mission execution system.
   */
  public static class Provider extends ModelBase {
    public String providerId;
    public String description;
    public String httpEndpoint;
    public String apiType;
    public boolean canValidate;
    public boolean usesSched;
    public boolean usesUnits;
  
    @SerializedName(value="missionTemplates", alternate={"missionTplsByProviderIdList"})
    public List<MissionTemplate> missionTemplates;
  }
  
  /**
   * Information about a particular mission template.
   */
  public static class MissionTemplate extends ModelBase {
    public String missionTplId;
    public String description;
  
    @SerializedName(value="assetClasses", alternate={"missionTplAssetClassesByProviderIdAndMissionTplIdList"})
    public List<AssetClass> assetClasses;
  
    @SerializedName(value="parameters", alternate={"parametersByProviderIdAndMissionTplIdList"})
    public List<Parameter> parameters;
  }
  
  /**
   * Information about a particular class of assets.
   */
  public static class AssetClass extends ModelBase {
    public String assetClassName;
    public String description;
  }
  
  /**
   * Information about a particular parameter in a mission template.
   */
  public static class Parameter extends ModelBase {
    public String paramName;
    public String type;
    public String defaultValue;
    public String defaultUnits;
    public boolean required;
    public String description;
  }
  
  /**
   * Information about a particular mission.
   */
  public static class Mission extends ModelBase {
    public String providerId;
    public String missionTplId;
    public String missionId;
    public String assetId;
    public String description;
    
    @SerializedName(value="arguments", alternate={"argumentsByProviderIdAndMissionTplIdAndMissionIdList"})
    public List<Argument> arguments;
  }
  
  /**
   * Information about an argument in a particular mission.
   */
  public static class Argument extends ModelBase {
    public String paramName;
    public String paramValue;
  }
  
  private model() {}
}
