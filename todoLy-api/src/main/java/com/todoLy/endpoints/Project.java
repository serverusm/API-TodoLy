package com.todoLy.endpoints;

import com.todoLy.ApiRequestHandler;
import com.todoLy.client.RequestManager;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Project {
    // Configurar los headers
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Map<String, Object> projectObject;
    private ApiRequestHandler request;

    public Project(){
        // Configurar los headers
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Configurar los parámetros de autenticación
        queryParams = new HashMap<>();
        queryParams.put("UserName", "sergiomamani2014@gmail.com");
        queryParams.put("Password", "Krattosmamani9/");

        // Configurar el cuerpo del proyecto (projectObject)
        projectObject = new HashMap<>();
        projectObject.put("Content", "API Project from Intellij");
        projectObject.put("Icon", 3);

        // Inicializar la solicitud
        request = new ApiRequestHandler();
        request.setHeaders(headers);
        request.setQueryParam(queryParams);
    }
    public Response createProject(String boardName) {
        request.setQueryParam("name", boardName);
        request.setEndpoint("/project/");

        // Agregar el cuerpo de projectObject a la solicitud
        request.setBody(projectObject);

        return RequestManager.post(request);
    }
}
