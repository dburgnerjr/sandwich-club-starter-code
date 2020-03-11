package com.udacity.sandwichclub;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static int nPosition;

    private ImageView ivSandwich;
    private TextView tvIngredients;
    private TextView tvDescription;
    private TextView tvPlaceOfOrigin;
    private TextView tvPlaceOfOriginLabel;
    private TextView tvAlsoKnownAs;
    private TextView tvAlsoKnownAsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivSandwich = findViewById(R.id.image_iv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvDescription = findViewById(R.id.description_tv);
        tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);

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

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ivSandwich);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        tvPlaceOfOriginLabel = findViewById(R.id.origin_tv_label);
        tvAlsoKnownAsLabel = findViewById(R.id.also_known_tv_label);
        setTitle(sandwich.getMainName());

        if (sandwich.getAlsoKnownAs().size() == 0) {
            tvAlsoKnownAsLabel.setVisibility(View.GONE);
            tvAlsoKnownAs.setVisibility(View.GONE);
        } else {
            tvAlsoKnownAsLabel.setVisibility(View.VISIBLE);
            tvAlsoKnownAs.setVisibility(View.VISIBLE);
        }

        for (int nI = 0; nI < sandwich.getAlsoKnownAs().size(); nI++) {
            if (nI != (sandwich.getAlsoKnownAs().size() - 1)) {
                tvAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(nI) + ", ");
            } else {
                tvAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(nI));
            }
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            tvPlaceOfOriginLabel.setVisibility(View.GONE);
            tvPlaceOfOrigin.setVisibility(View.GONE);
        }
        tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());


        for (int nJ = 0; nJ < sandwich.getIngredients().size(); nJ++) {
            if (nJ != (sandwich.getIngredients().size() - 1)) {
                tvIngredients.append(sandwich.getIngredients().get(nJ) + ", ");
            } else {
                tvIngredients.append(sandwich.getIngredients().get(nJ) );
            }
        }

        tvDescription.setText(sandwich.getDescription());

    }
}
