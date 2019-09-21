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
    public final String executorId;
    public final String description;
  
    @SerializedName(value="missionTemplates", alternate={"missionTplsByExecutorIdList"})
    public final List<MissionTemplate> missionTemplates;
  
    public Executor(String executorId, String description, List<MissionTemplate> missionTemplates) {
      this.executorId = executorId;
      this.description = description;
      this.missionTemplates = missionTemplates;
    }
  }
  
  /**
   * Information about a particular mission template.
   */
  public static class MissionTemplate extends ModelBase {
    public final String missionTplId;
    public final String description;
  
    @SerializedName(value="assetClasses", alternate={"missionTplAssetClassesByExecutorIdAndMissionTplIdList"})
    public final List<AssetClass> assetClasses;
  
    @SerializedName(value="parameters", alternate={"parametersByExecutorIdAndMissionTplIdList"})
    public final List<Parameter> parameters;
  
    public MissionTemplate(String missionTplId, String description, List<AssetClass> assetClasses, List<Parameter> parameters) {
      this.missionTplId = missionTplId;
      this.description = description;
      this.assetClasses = assetClasses;
      this.parameters = parameters;
    }
  }
  
  /**
   * Information about a particular class of assets.
   */
  public static class AssetClass extends ModelBase {
    public final String assetClassName;
    public final String description;
  
    public AssetClass(String assetClassName, String description) {
      this.assetClassName = assetClassName;
      this.description = description;
    }
  }
  
  /**
   * Information about a particular parameter in a mission template.
   */
  public static class Parameter extends ModelBase {
    public final String paramName;
    public final String type;
    public final String defaultValue;
    public final boolean required;
    public final String description;
  
    public Parameter(String paramName, String type, String defaultValue, boolean required, String description) {
      this.paramName = paramName;
      this.type = type;
      this.defaultValue = defaultValue;
      this.required = required;
      this.description = description;
    }
  }
  
  /**
   * Information about a particular mission.
   */
  public static class Mission extends ModelBase {
    public final String executorId;
    public final String missionTplId;
    public final String missionId;
    public final String assetId;
    public final String description;
    
    @SerializedName(value="arguments", alternate={"argumentsByExecutorIdAndMissionTplIdAndMissionIdList"})
    public final List<Argument> arguments;
  
    public Mission(String executorId, String missionTplId, String missionId, String assetId, String description, List<Argument> arguments) {
      this.executorId = executorId;
      this.missionTplId = missionTplId;
      this.missionId = missionId;
      this.assetId = assetId;
      this.description = description;
      this.arguments = arguments;
    }
  }
  
  /**
   * Information about an argument in a particular mission .
   */
  public static class Argument extends ModelBase {
    public final String paramName;
    public final String paramValue;
  
    public Argument(String paramName, String paramValue) {
      this.paramName = paramName;
      this.paramValue = paramValue;
    }
  }
  
  private model() {}
}
