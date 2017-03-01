package com.company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kevinlee on 1/1/17.
 */

public class QueryUtils {

    private QueryUtils() {
    }

    public static void postFoodImages(String JSON_DATA) {
        try {
            JSONArray data = new JSONArray(JSON_DATA);
            for (int i = 0; i < data.length(); i++) {
                JSONObject currentFood = data.optJSONObject(i);
                int id = currentFood.getInt(DBConfig.TAG_FOOD_ID);
                String name = currentFood.getString(DBConfig.TAG_FOOD_NAME);
                String imageURL = currentFood.optString(DBConfig.TAG_FOOD_IMAGE);

                // ADDING IMAGES TO THE DATABASE
                if(imageURL.equals("null") || imageURL.equals("")) {
                    System.out.println("Adding food image for " + name + " with current url of " + imageURL + "...");
                    HashMap<String, String> params = new HashMap<>();
                    RequestHandler rh = new RequestHandler();
                    String bingImageJSON = rh.sendGetRequestBingImageAPI(name);
                    String bingImageURL = extractFirstImageUrl(bingImageJSON);
                    if (bingImageURL.equals("")) {
                        System.out.println("Couldn't find image for " + name);
                    } else {
                        params.put(DBConfig.KEY_FOOD_ID, String.valueOf(id));
                        params.put(DBConfig.KEY_FOOD_IMAGE, bingImageURL);
                        String result = rh.sendPostRequest(DBConfig.URL_UPDATE_IMAGE, params);
                        System.out.println(result);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String extractFirstImageUrl(String JSON_DATA) {
        try {
            JSONObject root = new JSONObject(JSON_DATA);
            JSONArray value = root.optJSONArray(DBConfig.TAG_BING_VALUE);
            if(value.length() != 0) {
                JSONObject current = value.optJSONObject(0);
                String url = current.optString(DBConfig.TAG_BING_CONTENT_URL);
                return url;
            } else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> extractASPCFoodList(String JSON_DATA) {
        ArrayList<String> foodList = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(JSON_DATA);
            for(int i = 0; i < root.length(); i++) {
                JSONObject currentMeal = root.optJSONObject(i);
                JSONArray food_items = currentMeal.optJSONArray(DBConfig.ASPC_FOOD_ITEMS);
                for(int j = 0; j < food_items.length(); j++) {
                    foodList.add(food_items.getString(j));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodList;
    }

}
