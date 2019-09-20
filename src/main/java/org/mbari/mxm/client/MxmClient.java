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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * High-level MXM API Client.
 * This is initially intended to retrieve information about available
 * "executors" including associated mission templates (and their parameters)
 * and assets.
 * All of this functionality while hiding some of the details about the
 * underlying GraphQL interface.
 * <br/><br/>
 *
 * <b>Status: Preliminary.</b>
 *
 * <br/>
 * <br/>
 * No all model classes are reflected yet.
 * When that's the case, the member in the response will be of generic type
 *    <code>Map&lt;?, ?></code>
 * or
 *    <code>List&lt;Map&lt;?, ?>></code>.
 */
public class MxmClient {
  
  static class GetAllExecutorsResponse {
    Data data;
    Object errors;
    
    static class Data {
      List<Executor> allExecutorsList;
    }
  }
  
  /**
   * Creates an instance.
   *
   * @param endpoint  MXM API endpoint.
   */
  public MxmClient(String endpoint) {
    this.endpoint = endpoint;
  }
  
  public Optional<Executor> getExecutor(String executorId) {
    String query = getQuery("executor.gql");
    Map<String, String> variables = new HashMap<>();
    variables.put("executorId", executorId);
    
    List<Executor> list = _getAllExecutors("executor", query, variables);
    if (list.size() == 1) {
      return Optional.of(list.get(0));
    }
    else {
      assert (list.isEmpty());
      return Optional.empty();
    }
  }
  
  public List<Executor> getAllExecutors() {
    String query = getQuery("executors.gql");
    return _getAllExecutors(null, query, null);
  }
  
  private List<Executor> _getAllExecutors(String operationName, String query, Map<String, String> variables) {
    HttpRequestBase req = createRequest(operationName, query, variables);
    HttpResponse httpResponse = makeRequest(req);
    String s = getResponseContent(httpResponse);
  
//    System.out.println("BARE RES:\n  | " + gson.toJson(gson.fromJson(s, Map.class)).replaceAll("\n", "\n  | "));
    
    GetAllExecutorsResponse res = gson.fromJson(s, GetAllExecutorsResponse.class);
    
    if (res.errors != null) {
      throw new RuntimeException("Errors reported: " + gson.toJson(res.errors));
    }

    if (res.data == null) {
      throw new RuntimeException("Expecting 'data' object member in response");
    }
  
    if (res.data.allExecutorsList == null) {
      throw new RuntimeException("Expecting 'allExecutorsList' list member in data.");
    }
    
    return res.data.allExecutorsList;
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
      throw new RuntimeException(ex);
    }
  }
  
  private HttpResponse makeRequest(HttpRequestBase request) {
    request.setHeader("Content-type", "application/json");
    request.setHeader("Accept", "application/json");
    //debug("making request: " + request.getURI());
    int connectTimeout = 3 * 1000;
    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(connectTimeout)
        .build();
    HttpClient httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(config).build();
    try {
      return httpClient.execute(request);
    }
    catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  private String getQuery(String resourceName) {
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
      assert is != null;
      String query = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
      return query.replaceAll("\n", "\\\\n");
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
  
  private final String endpoint;
  
  private static String getResponseContent(HttpResponse httpResponse)  {
    try {
      final InputStream is = httpResponse.getEntity().getContent();
      final String content = IOUtils.toString(is);
      IOUtils.closeQuietly(is);
      return content;
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
  
  static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
}
