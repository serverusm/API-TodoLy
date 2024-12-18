package com.todoLy.utils;

import com.jayway.jsonpath.PathNotFoundException;
import org.json.simple.JSONObject;

import java.util.List;

public class JsonPath {
    /**
     * Defaul private constructor.
     */
    private JsonPath() {
    }

    /**
     * Gets of filter result.
     *
     * @param jsonContent    the json object.
     * @param jsonPathFilter Filter expression. Expression must evaluate to a boolean value.
     * @return filter result.
     */
    public static List<String> getResults(final JSONObject jsonContent, final String jsonPathFilter) {
        return com.jayway.jsonpath.JsonPath.parse(jsonContent).read(jsonPathFilter);
    }

    /**
     * Gets of filter result
     *
     * @param jsonContent  the json string.
     * @param jsonPathFile Filter expression.
     * @return filter result
     */
    public static <T> T getResult(final String jsonContent, final String jsonPathFile) {
        try {
            return com.jayway.jsonpath.JsonPath.parse(jsonContent).read(jsonPathFile);
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
            throw new PathNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * Gets of filter result.
     *
     * @param jsonContent   the json string
     * @param jsonPathFiler Filter expression.
     * @return filter result.
     */
    public static List<?> getResultList(final String jsonContent, final String jsonPathFiler) {
        return com.jayway.jsonpath.JsonPath.parse(jsonContent).read(jsonPathFiler);
    }

    /**
     * Gets new Json string with the new value
     *
     * @param jsonContent   the json object.
     * @param jsonPathFiler Filter expression.
     * @param valueToSet    to set.
     * @return new Json string with the new value.
     */
    public static String setValue(final JSONObject jsonContent, final String jsonPathFiler, final Object valueToSet) {
        return com.jayway.jsonpath.JsonPath.parse(jsonContent).set(jsonPathFiler, valueToSet).jsonString();
    }
}
