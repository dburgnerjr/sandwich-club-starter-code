package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static int nPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivSandwich = findViewById(R.id.image_iv);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);
        TextView tvDescription = findViewById(R.id.description_tv);
        TextView tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        TextView tvAlsoKnownAs = findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        nPosition = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (nPosition == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[nPosition];
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI();
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ivSandwich);

            setTitle(sandwich.getMainName());
            tvIngredients.setText(sandwich.getIngredients().toString());
            tvAlsoKnownAs.setText(sandwich.getAlsoKnownAs().toString());
            tvDescription.setText(sandwich.getDescription());
            tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
