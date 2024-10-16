package com.sio.apis;

import com.sio.models.Target;
import com.sio.tools.ConfigManager;
import com.sio.tools.HttpRequestBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MockChrevTzyonApiClient {
    private final ConfigManager cm = new ConfigManager();
    private final JSONParser parser = new JSONParser();

    /**
     * Get all targets from the API
     * @return ArrayList<JSONObject>
     */
    public ArrayList<JSONObject> getTargets() {

        try {
            HttpResponse<String> response = HttpRequestBuilder.get(cm.getProperty("api.url") + "/targets");
            JSONObject jsonItem = (JSONObject) parser.parse(response.body());
            ArrayList<JSONObject> targets = new ArrayList<>();
            JSONArray jsonTargets = (JSONArray) jsonItem.get("targets");
            for (Object item : jsonTargets) {
                JSONObject jsonT = (JSONObject) item;
                targets.add(jsonT);
            }
            return targets;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


        return null;
    }

    /**
     * Add a target to the API
     * @param target Target
     * @return boolean
     */
    public boolean addTarget(Target target) {
        //TODO : Implement this method
        return false;
    }

    /**
     * Delete a target from the API
     * @param target Target
     * @return boolean
     */
    public boolean deleteTarget(Target target) {
        //TODO : Implement this method
        return false;
    }

    /**
     * Build a JSON string from a Target object
     * @param t Target
     * @return String
     */
    private String buildJsonStringFromObject(Target t){
        //TODO : Implement this method
        return "string";
    }
}
