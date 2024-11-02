package com.todoLy.endpoints;

import com.todoLy.ApiRequestHandler;
import com.todoLy.client.RequestManager;
import com.todoLy.utils.PropertiesInfo;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

public class Project {
    // Configurar los headers
    private ResponseSpecification responseSpec;
    private String autUserName;
    private String autPassword;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Map<String, Object> projectObject;
    private ApiRequestHandler request;
    private String projectID;

    public Project(){
        request = new ApiRequestHandler();
        request = new ApiRequestHandler();
        autUserName = PropertiesInfo.getInstance().getAutUserName();
        autPassword = PropertiesInfo.getInstance().getAutPassword();

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
              .expectContentType(ContentType.JSON)
              .build();

        request.setBasicAuth(autUserName, autPassword);

        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        request.setHeaders(headers);
    }
    public Response createProject(String boardName) {
        request.setEndpoint("/projects.json");
        request.setProjectObject(boardName, 10);

        // Agregar el cuerpo de projectObject a la solicitud
//        request.setBody(projectObject);

        return RequestManager.post(request);
    }
}
