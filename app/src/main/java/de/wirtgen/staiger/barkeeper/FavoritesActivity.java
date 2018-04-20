package de.wirtgen.staiger.barkeeper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.Map;

public class FavoritesActivity extends HomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.nav_favorites);

        //remove Content from HomeActivity
        this.removeHomeView();

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_favorites, contentFrameLayout);


        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        LanguageManager.Language currentLang = LanguageManager.getCurrentLanguage();
        Log.d("BarkeeperApp", "Current Lang: " + currentLang.name());

        Map<Cocktail, String> m = Cocktail.getAllCoacktailsWithNamesASC(daoSession, currentLang.getId());

        RecyclerView rv = findViewById(R.id.content_recyclerView_favs);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        FavoriteAdapter fa = new FavoriteAdapter(m, this);
        rv.setLayoutManager(lm);
        rv.setAdapter(fa);
    }


    public void showCocktailDetail(Cocktail selectedCocktail){
        Log.d("BarkeeperApp", "Callback to Activity. Cocktail ID: " + selectedCocktail.getId());

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_cocktail, contentFrameLayout);

    }

}
