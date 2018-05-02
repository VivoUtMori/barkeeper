package de.wirtgen.staiger.barkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Map;

public class FavoritesActivity extends HomeActivity {

    boolean showsSubContent;
    Bundle savedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.nav_favorites);

        showsSubContent = false;
        savedState = savedInstanceState;

        showContent();


        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BarkeeperApp", "Back Button pushed");
                if(showsSubContent){
                    showContent();
                }
                else{
                    Intent intent_Home = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent_Home);
                }
                drawer.closeDrawers();
            }
        });



    }


    private void showContent(){
        showsSubContent = false;

        this.removeHomeView();
        this.removeFrameContent();

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_favorites, contentFrameLayout);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        LanguageManager.Language currentLang = LanguageManager.getCurrentLanguage();
        Log.d("BarkeeperApp", "Current Lang: " + currentLang.name());

        Map<Cocktail, String> m = Cocktail.getAllFavoriteCocktails(daoSession, currentLang.getId());

        RecyclerView rv = findViewById(R.id.content_recyclerView_favs);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        FavoriteAdapter fa = new FavoriteAdapter(m, this);
        rv.setLayoutManager(lm);
        rv.setAdapter(fa);
    }

    public void showCocktailDetail(Cocktail selectedCocktail){
        Log.d("BarkeeperApp", "Callback to Activity. Cocktail ID: " + selectedCocktail.getId());

        this.removeHomeView();
        this.removeFrameContent();

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_cocktail, contentFrameLayout);

        showsSubContent = true;

        LanguageManager.Language currentLang = LanguageManager.getCurrentLanguage();
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        int imageID = getResources().getIdentifier(selectedCocktail.getUrlPicture(), "drawable", getPackageName());


        String cocktailName;
        String cocktailDescription;
        String cocktailPreperation;

        QueryBuilder<LanguagesTexts> qb2 = daoSession.getLanguagesTextsDao().queryBuilder();
        qb2.where(LanguagesTextsDao.Properties.CocktailID.eq(selectedCocktail.getId()));
        qb2.where(LanguagesTextsDao.Properties.LanguageID.eq(currentLang.getId()));
        qb2.where(LanguagesTextsDao.Properties.ComponentID.eq(Helper.Component.COCKTAILNAME.getId()));
        cocktailName = qb2.list().get(0).getText();

        qb2 = daoSession.getLanguagesTextsDao().queryBuilder();
        qb2.where(LanguagesTextsDao.Properties.CocktailID.eq(selectedCocktail.getId()));
        qb2.where(LanguagesTextsDao.Properties.LanguageID.eq(currentLang.getId()));
        qb2.where(LanguagesTextsDao.Properties.ComponentID.eq(Helper.Component.COCKTAILDESCRIPTION.getId()));
        cocktailDescription = qb2.list().get(0).getText();

        qb2 = daoSession.getLanguagesTextsDao().queryBuilder();
        qb2.where(LanguagesTextsDao.Properties.CocktailID.eq(selectedCocktail.getId()));
        qb2.where(LanguagesTextsDao.Properties.LanguageID.eq(currentLang.getId()));
        qb2.where(LanguagesTextsDao.Properties.ComponentID.eq(Helper.Component.COCKTAILPREPERATION.getId()));
        cocktailPreperation = qb2.list().get(0).getText();

        TextView textviewCocktailTitle = findViewById(R.id.cocktail_name);
        textviewCocktailTitle.setText(cocktailName);

        TextView textviewCocktailDescription = findViewById(R.id.cocktail_description);
        textviewCocktailDescription.setText(cocktailDescription);

        TextView textviewCocktailPreperation = findViewById(R.id.cocktail_preperation);
        textviewCocktailPreperation.setText(cocktailPreperation);

        ImageView ivCocktail = findViewById(R.id.cocktail_picture);
        ivCocktail.setImageDrawable(getResources().getDrawable(imageID));

    }

}
