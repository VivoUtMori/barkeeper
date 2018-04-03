package de.wirtgen.staiger.barkeeper;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class SettingsActivity extends HomeActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle("Settings");

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_settings, contentFrameLayout);

        final Button button = findViewById(R.id.btn_language);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("BarkeeperApp", "Button pushed");
            }
        });


    }

}