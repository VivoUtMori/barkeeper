package de.wirtgen.staiger.barkeeper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class FavoritesActivity extends HomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.nav_favorites);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_favorites, contentFrameLayout);
    }

}
