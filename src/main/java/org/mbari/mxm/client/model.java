package org.mbari.mxm.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class model {
  
  public static class Executor {
    public String executorId;
    public String description;
  
    @SerializedName(value="missionTemplates", alternate={"missionTplsByExecutorIdList"})
    public List<MissionTemplate> missionTemplates;
  
    @Override
    public String toString() {
      return MxmClient.gson.toJson(this);
    }
  }
  
  public static class MissionTemplate {
    public String missionTplId;
    public String description;
  
    @SerializedName(value="assetClasses", alternate={"missionTplAssetClassesByExecutorIdAndMissionTplIdList"})
    public List<AssetClass> assetClasses;
  }
  
  public static class AssetClass {
    public String assetClassName;
    public String description;
  }
  
  private model() {}
}
