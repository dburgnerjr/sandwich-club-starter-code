package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        String strMainName;
        String strPlaceOfOrigin;
        String strDescription;
        String strImage;

        JSONObject jsonObject = new JSONObject(json);
        JSONObject name = jsonObject.getJSONObject("name");
        strMainName = name.getString("mainName");
        strPlaceOfOrigin = name.getString("placeOfOrigin");
        strDescription = name.getString("description");
        strImage = name.getString("image");

        List<String> lstAKA = new ArrayList<String>();
        if (name.has("alsoKnownAs")) {
            JSONArray jsnaAKA = name.getJSONArray("alsoKnownAs");

            //Iterate through the array of jsnaAKA and add it to list
            for (int i = 0; i < jsnaAKA.length(); i++) {
                String strAlsoKnownAs = jsnaAKA.getString(i);
                lstAKA.add(strAlsoKnownAs);
            }
        }

        JSONArray ingredientArray = name.getJSONArray("ingredients");

        List<String> lstIngredients = new ArrayList<String>();
        for (int i = 0; i < ingredientArray.length(); i++) {
            lstIngredients.add(ingredientArray.getString(i));
        }

        Sandwich sandwich = new Sandwich(strMainName, lstAKA, strPlaceOfOrigin, strDescription, strImage, lstIngredients);
        return sandwich;

    }
}
