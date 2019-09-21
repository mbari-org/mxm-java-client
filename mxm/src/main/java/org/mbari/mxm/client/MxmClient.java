package org.mbari.mxm.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mbari.mxm.client.model.Executor;
import org.mbari.mxm.client.model.Mission;
import org.mbari.mxm.client.model.MissionTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * High-level MXM API Client.
 *
 * <br><br>
 * <b>Status: Preliminary</b>
 * <br><br>
 *
 * You can use this to retrieve information about available "executors"
 * (i.e., mission execution systems registered in the MXM service) including
 * associated mission templates, and their parameters and managed assets, as
 * well as missions
 *
 * Note that some fields in retrieved objects may be `null` or `Optional.empty()`
 * depending on the particular MxmClient method used to retrieve the object.
 * In general, more shallow information is provided when the operation is related
 * with a list of items, and more detailed when specifically requesting a particular item.
 */
public class MxmClient {
  
  /**
   * Returns the version of the mxm-client library.
   */
  public static String getVersion() {
    return BuildInfo.version;
  }
  
  /**
   * Creates an instance.
   *
   * @param endpoint  MXM API endpoint.
   */
  public MxmClient(String endpoint) {
    this.endpoint = endpoint;
  }
  
  /**
   * Gets basic info about the registered mission execution systems.
   */
  public List<Executor> getExecutors() {
    String query = getQuery("executors.gql");
    return getExecutors(null, query, null);
  }
  
  /**
   * Gets details about a registered mission execution system.
   * @param executorId
   */
  public Optional<Executor> getExecutor(String executorId) {
    String query = getQuery("executor.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("executorId", executorId);
    
    List<Executor> list = getExecutors("executor", query, variables);
    if (list.size() == 1) {
      return Optional.of(list.get(0));
    }
    else {
      assert (list.isEmpty());
      return Optional.empty();
    }
  }
  
  public Optional<MissionTemplate> getExecutorMissionTemplate(String executorId, String missionTplId) {
    String query = getQuery("missionTpl.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("executorId", executorId);
    variables.put("missionTplId", missionTplId);
    
    String s = getResponse(null, query, variables);

//    System.out.println("BARE RES:\n  | " + gson.toJson(gson.fromJson(s, Map.class)).replaceAll("\n", "\n  | "));
    
    GetMissionTemplateResponse res = gson.fromJson(s, GetMissionTemplateResponse.class);
    
    if (res.errors != null) {
      throw new MxmClientException("Errors reported: " + gson.toJson(res.errors));
    }
    
    if (res.data == null) {
      throw new MxmClientException("Expecting 'data' object member in response");
    }
    
    if (res.data.missionTplByExecutorIdAndMissionTplId != null) {
      return Optional.of(res.data.missionTplByExecutorIdAndMissionTplId);
    }
    else {
      return Optional.empty();
    }
  }
  
  /**
   * Gets basic info about the mission instances of a given mission template.
   * @param executorId
   * @param missionTplId
   */
  public List<Mission> getExecutorMissions(String executorId, String missionTplId) {
    String query = getQuery("missions.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("executorId", executorId);
    variables.put("missionTplId", missionTplId);
    String s = getResponse("missions", query, variables);

//    System.out.println("BARE RES:\n  | " + gson.toJson(gson.fromJson(s, Map.class)).replaceAll("\n", "\n  | "));
  
    GetExecutorMissionsResponse res = gson.fromJson(s, GetExecutorMissionsResponse.class);
  
    if (res.errors != null) {
      throw new MxmClientException("Errors reported: " + gson.toJson(res.errors));
    }
  
    if (res.data == null) {
      throw new MxmClientException("Expecting 'data' object member in response");
    }
  
    if (res.data.allMissionsList == null) {
      throw new MxmClientException("Expecting 'allMissionsList' list member in data.");
    }
  
    return res.data.allMissionsList;
  
  }
  
  /**
   * Gets details about a particular mission,
   * @param executorId
   * @param missionTplId
   * @param missionId
   */
  public Optional<Mission> getMission(String executorId, String missionTplId, String missionId) {
    String query = getQuery("mission.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("executorId", executorId);
    variables.put("missionTplId", missionTplId);
    variables.put("missionId", missionId);
  
    String s = getResponse("mission", query, variables);

//    System.out.println("BARE RES:\n  | " + gson.toJson(gson.fromJson(s, Map.class)).replaceAll("\n", "\n  | "));
  
    GetMissionResponse res = gson.fromJson(s, GetMissionResponse.class);
  
    if (res.errors != null) {
      throw new MxmClientException("Errors reported: " + gson.toJson(res.errors));
    }
  
    if (res.data == null) {
      throw new MxmClientException("Expecting 'data' object member in response");
    }
  
    if (res.data.missionByExecutorIdAndMissionTplIdAndMissionId != null) {
      return Optional.of(res.data.missionByExecutorIdAndMissionTplIdAndMissionId);
    }
    else {
      return Optional.empty();
    }
  }
  
  private List<Executor> getExecutors(String operationName, String query, Map<String, String> variables) {
    String s = getResponse(operationName, query, variables);
  
//    System.out.println("BARE RES:\n  | " + gson.toJson(gson.fromJson(s, Map.class)).replaceAll("\n", "\n  | "));
    
    GetExecutorsResponse res = gson.fromJson(s, GetExecutorsResponse.class);
    
    if (res.errors != null) {
      throw new MxmClientException("Errors reported: " + gson.toJson(res.errors));
    }

    if (res.data == null) {
      throw new MxmClientException("Expecting 'data' object member in response");
    }
  
    if (res.data.allExecutorsList == null) {
      throw new MxmClientException("Expecting 'allExecutorsList' list member in data.");
    }
    
    return res.data.allExecutorsList;
  }
  
  private String getResponse(String operationName, String query, Map<String, String> variables) {
    HttpRequestBase req = createRequest(operationName, query, variables);
    HttpResponse httpResponse = makeRequest(req);
    return getResponseContent(httpResponse);
  }
  
  private HttpRequestBase createRequest(String operationName, String query, Map<String, String> variables) {
    URI uri = makeQueryUri(query);
    HttpPost req = new HttpPost(uri);
    
    List<String> attrs = new ArrayList<>();
    
    if (operationName != null) {
      attrs.add("\"operationName\": \"" + operationName + "\"");
    }
    if (variables != null) {
      attrs.add("\"variables\": " + gson.toJson(variables).replaceAll("\n", "\n  "));
    }
    attrs.add("\"query\": \"" + query.replaceAll("\n", "\\\n") + "\"");
    
    String body = "{\n  " + String.join(",\n  ", attrs) + "\n}";

//    System.out.println("body:\n  " + body.replaceAll("\n", "\n  "));
    HttpEntity entity = new ByteArrayEntity(body.getBytes(StandardCharsets.UTF_8));
    req.setEntity(entity);
    return req;
  }
  
  private URI makeQueryUri(String query) {
    try {
      URIBuilder builder = new URIBuilder(endpoint);
      builder.setParameter("query", query);
      return builder.build();
    }
    catch (URISyntaxException ex) {
      throw new MxmClientException("unexpected", ex);
    }
  }
  
  private String getQuery(String resourceName) {
    ClassLoader cl = getClass().getClassLoader();
    String name = "org/mbari/mxm/client/" + resourceName;
    InputStream is = cl.getResourceAsStream(name);
    assert is != null;
    try {
      String query = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
      return query.replaceAll("\n", "\\\\n");
    }
    catch (IOException ex) {
      throw new MxmClientException("unexpected", ex);
    }
  }
  
  private final String endpoint;
  
  private static HttpResponse makeRequest(HttpRequestBase request) {
    request.setHeader("Content-type", "application/json");
    request.setHeader("Accept", "application/json");
    //debug("making request: " + request.getURI());
    int connectTimeout = 7 * 1000;
    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(connectTimeout)
        .build();
    HttpClient httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(config).build();
    try {
      return httpClient.execute(request);
    }
    catch (IOException ex) {
      throw new MxmClientException("error executing request", ex);
    }
  }
  
  private static String getResponseContent(HttpResponse httpResponse)  {
    try {
      final InputStream is = httpResponse.getEntity().getContent();
      final String content = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
      return content;
    }
    catch (Exception ex) {
      throw new MxmClientException("error processing response content", ex);
    }
  }
  
  private static class GetExecutorsResponse {
    Data data;
    Object errors;
    
    static class Data {
      List<Executor> allExecutorsList;
    }
  }
  
  private static class GetMissionTemplateResponse {
    Data data;
    Object errors;
    
    static class Data {
      MissionTemplate missionTplByExecutorIdAndMissionTplId;
    }
  }
  
  private static class GetExecutorMissionsResponse {
    Data data;
    Object errors;
    
    static class Data {
      List<Mission> allMissionsList;
    }
  }
  
  private static class GetMissionResponse {
    Data data;
    Object errors;
    
    static class Data {
      Mission missionByExecutorIdAndMissionTplIdAndMissionId;
    }
  }
  
  static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
}
