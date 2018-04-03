package de.wirtgen.staiger.barkeeper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class IngredientsActivity extends HomeActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle("Ingredients");

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_ingredients, contentFrameLayout);
    }

}
