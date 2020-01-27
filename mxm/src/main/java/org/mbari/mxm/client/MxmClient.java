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
import org.mbari.mxm.client.model.Provider;
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
 * <p>
 * Status: <b>Preliminary</b>. Only read-only operations at the moment.
 * </p>
 *
 * <p>
 * Currently, you can use this to retrieve information about available "providers"
 * (i.e., mission execution systems registered in the MXM service) including
 * associated mission templates, and their parameters and managed assets, as
 * well as missions.
 * </p>
 *
 * <p>
 * Note that some fields in retrieved objects may be <code>null</code>
 * or <code>Optional.empty()</code>
 * depending on the particular MxmClient method used to retrieve the object.
 * In general, more shallow information is provided when the operation is related
 * with a list of items, and more detailed when specifically requesting a particular item.
 * </p>
 */
public class MxmClient {
  
  /**
   * Returns the version of the mxm-client library.
   *
   * @return the version.
   */
  public static String getVersion() {
    return BuildInfo.getVersion();
  }
  
  /**
   * Creates an instance.
   *
   * @param endpoint  MXM API endpoint.
   *                  Example: <code>"http://mxm.shore.mbari.org/mxm-graphql"</code>
   */
  public MxmClient(String endpoint) {
    this.endpoint = endpoint;
  }
  
  /**
   * Gets basic info about the registered mission execution systems.
   *
   * @return List of providers.
   */
  public List<Provider> getProviders() {
    String query = getQuery("providers.gql");
    String s = getResponse(null, query, null);

    GetProvidersResponse res = gson.fromJson(s, GetProvidersResponse.class);
  
    if (res.errors != null) {
      throw new MxmClientException("Errors reported: " + gson.toJson(res.errors));
    }
  
    if (res.data == null) {
      throw new MxmClientException("Expecting 'data' object member in response");
    }
  
    if (res.data.allProvidersList == null) {
      throw new MxmClientException("Expecting 'allProvidersList' list member in data.");
    }
  
    return res.data.allProvidersList;
  }
  
  /**
   * Gets details about a registered mission execution system.
   *
   * @param providerId Provider ID
   * @return Provider or empty if not found
   */
  public Optional<Provider> getProvider(String providerId) {
    String query = getQuery("provider.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("providerId", providerId);
  
    String s = getResponse("provider", query, variables);
    GetProviderResponse res = gson.fromJson(s, GetProviderResponse.class);
    
    return Optional.ofNullable(res.data.providerByProviderId);
  }
  
  /**
   * Gets a particular mission template.
   *
   * @param providerId Provider ID
   * @param missionTplId Mission template ID
   * @return MissionTemplate or empty if not found
   */
  public Optional<MissionTemplate> getProviderMissionTemplate(String providerId, String missionTplId) {
    String query = getQuery("missionTpl.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("providerId", providerId);
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
    
    return Optional.ofNullable(res.data.missionTplByProviderIdAndMissionTplId);
  }
  
  /**
   * Gets basic info about the mission instances of a given mission template.
   *
   * @param providerId Provider ID
   * @param missionTplId Mission template ID
   * @return List of missions
   */
  public List<Mission> getProviderMissions(String providerId, String missionTplId) {
    String query = getQuery("missions.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("providerId", providerId);
    variables.put("missionTplId", missionTplId);
    String s = getResponse("missions", query, variables);

//    System.out.println("BARE RES:\n  | " + gson.toJson(gson.fromJson(s, Map.class)).replaceAll("\n", "\n  | "));
  
    GetProviderMissionsResponse res = gson.fromJson(s, GetProviderMissionsResponse.class);
  
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
   * Gets details about a particular mission.
   *
   * @param providerId Provider ID
   * @param missionTplId Mission template ID
   * @param missionId Mission ID
   * @return Mission or empty if not found
   */
  public Optional<Mission> getMission(String providerId, String missionTplId, String missionId) {
    String query = getQuery("mission.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("providerId", providerId);
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
  
    return Optional.ofNullable(res.data.missionByProviderIdAndMissionTplIdAndMissionId);
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
  
  private static class GetProvidersResponse {
    Data data;
    Object errors;
    
    static class Data {
      List<Provider> allProvidersList;
    }
  }
  
  private static class GetProviderResponse {
    Data data;
    Object errors;
    
    static class Data {
      Provider providerByProviderId;
    }
  }
  
  private static class GetMissionTemplateResponse {
    Data data;
    Object errors;
    
    static class Data {
      MissionTemplate missionTplByProviderIdAndMissionTplId;
    }
  }
  
  private static class GetProviderMissionsResponse {
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
      Mission missionByProviderIdAndMissionTplIdAndMissionId;
    }
  }
  
  static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
}
