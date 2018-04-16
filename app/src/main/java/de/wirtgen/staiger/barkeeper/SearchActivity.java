package de.wirtgen.staiger.barkeeper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SearchActivity extends HomeActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.nav_search);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_search, contentFrameLayout);
    }

}
