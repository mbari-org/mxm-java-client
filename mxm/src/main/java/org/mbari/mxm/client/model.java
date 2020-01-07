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
  public static class Executor extends ModelBase {
    public String executorId;
    public String description;
    public String httpEndpoint;
    public String apiType;
    public boolean canValidate;
    public boolean usesSched;
    public boolean usesUnits;
  
    @SerializedName(value="missionTemplates", alternate={"missionTplsByExecutorIdList"})
    public List<MissionTemplate> missionTemplates;
  }
  
  /**
   * Information about a particular mission template.
   */
  public static class MissionTemplate extends ModelBase {
    public String missionTplId;
    public String description;
  
    @SerializedName(value="assetClasses", alternate={"missionTplAssetClassesByExecutorIdAndMissionTplIdList"})
    public List<AssetClass> assetClasses;
  
    @SerializedName(value="parameters", alternate={"parametersByExecutorIdAndMissionTplIdList"})
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
    public String executorId;
    public String missionTplId;
    public String missionId;
    public String assetId;
    public String description;
    
    @SerializedName(value="arguments", alternate={"argumentsByExecutorIdAndMissionTplIdAndMissionIdList"})
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
