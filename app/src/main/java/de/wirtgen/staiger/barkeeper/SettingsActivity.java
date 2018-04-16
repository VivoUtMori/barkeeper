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

        //setTitle("Settings");
        setTitle(R.string.nav_settings);


        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_settings, contentFrameLayout);

        final Button buttonDE = findViewById(R.id.btn_languageGerman);
        buttonDE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("BarkeeperApp", "Button pushed");
                Log.d("BarkeeperApp", "Locale Language: " + getApplication().getBaseContext().getResources().getConfiguration().locale.getLanguage());
                LanguageManager lm = LanguageManager.getInstance();
                lm.setLanguage(getApplication(), LanguageManager.Language.GERMAN);
                Log.d("BarkeeperApp", "Language set to GERMAN");
                Log.d("BarkeeperApp", "Locale Language: " + getApplication().getBaseContext().getResources().getConfiguration().locale.getLanguage());
                refreshActivity();
            }
        });

        final Button buttonEN = findViewById(R.id.btn_languageEnglish);
        buttonEN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d("BarkeeperApp", "Button pushed");
                Log.d("BarkeeperApp", "Locale Language: " + getApplication().getBaseContext().getResources().getConfiguration().locale.getLanguage());
                LanguageManager lm = LanguageManager.getInstance();
                lm.setLanguage(getApplication(), LanguageManager.Language.ENGLISH);
                Log.d("BarkeeperApp", "Language set to ENGLISH");
                Log.d("BarkeeperApp", "Locale Language: " + getApplication().getBaseContext().getResources().getConfiguration().locale.getLanguage());
                refreshActivity();
            }
        });


    }

}