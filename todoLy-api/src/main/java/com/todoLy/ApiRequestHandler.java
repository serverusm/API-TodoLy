package com.todoLy;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestHandler {
  private Map<String, String> headers;
  private Map<String, String> queryParams;
//  private Map<String, Object> body;
  private String body;
  private String baseUrl;
  private Map<String, Object> projectData;
  private String endpoint;

  public ApiRequestHandler() {
    headers = new HashMap<>();
    queryParams = new HashMap<>();
//    body = new HashMap<>();
    projectData = new HashMap<>();
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public void setQueryParam(Map<String, String> queryParam) {
    this.queryParams.putAll(queryParam);
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers.putAll(headers);
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public void setQueryParam(String key, String value) {
    this.queryParams.put(key, value);
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }

  public Map<String, ?> getQueryParams() {
    return this.queryParams;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

//  public void setBody(Map<String, Object> body) {
//    this.body = body;
//  }

  public void setBody(String bodyFormat) {
    this.body = bodyFormat;
  }
  public String getBody() {
    return body;
  }

//  public Map<String, Object> getBody() {
//    return body;
//  }

  // método para establecer el cuerpo del proyecto
//  public void setProjectObject(String content, int icon) {
//    projectData.put("Content", content);
//    projectData.put("Icon", icon);
//    setBody(projectData);
//  }

  // método para agregar autenticación básica
  public void setBasicAuth(String username, String password) {
    String auth = username + ":" + password;
    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
    headers.put("Authorization", "Basic " + encodedAuth);
  }
}
