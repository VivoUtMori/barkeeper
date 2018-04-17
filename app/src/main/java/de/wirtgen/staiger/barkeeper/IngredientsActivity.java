package de.wirtgen.staiger.barkeeper;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.Map;

public class IngredientsActivity extends HomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.nav_ingredients);

        //remove Content from HomeActivity
        this.removeHomeView();

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        LanguageManager.Language currentLang = LanguageManager.getCurrentLanguage();
        Log.d("BarkeeperApp", "Current Lang: " + currentLang.name());

        Map<Ingredient, String> m = Ingredient.getAllIngredientsWithLanguageASC(daoSession, currentLang.getId());


        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_ingredients, contentFrameLayout);

        RecyclerView rv = findViewById(R.id.content_recyclerView_ingredients);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        IngredientAdapter ia = new IngredientAdapter(m);
        rv.setLayoutManager(lm);
        rv.setAdapter(ia);

    }



}
