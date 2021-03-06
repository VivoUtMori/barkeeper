package de.wirtgen.staiger.barkeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    public void onResume(){
        super.onResume();
        Log.d("BarkeeperApp", "HOME ACT onResume");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = pref.getString("language_key", "");
        Log.d("BarkeeperApp", "HOME ACT onResume LangKey: " + lang);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BarkeeperApp", "HOME ACT onCreate");
        setContentView(R.layout.activity_home);
        setTitle(R.string.nav_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.back

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_cocktail, contentFrameLayout);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Log.d("BarkeeperApp", "Lang: " + LanguageManager.getCurrentLanguage().name());


        LanguageManager.Language currentLang = LanguageManager.getCurrentLanguage();
        Log.d("BarkeeperApp", "Lang: " + currentLang.name());
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        List<Cocktail> allCocktails = daoSession.getCocktailDao().loadAll();
        int i = allCocktails.size();
        Log.d("BarkeeperApp", "All Cocktail Size: " + i);
        int randomID = Helper.getRandomNumber(1, i+1);

        Log.d("BarkeeperApp", "Random Cocktail ID: " + randomID);

        QueryBuilder<Cocktail> qb = daoSession.getCocktailDao().queryBuilder();
        qb.where(CocktailDao.Properties.Id.eq(randomID));
        List<Cocktail> lt = qb.list();
        Cocktail cocktailOfTheDay = lt.get(0);
        int imageID = getResources().getIdentifier(cocktailOfTheDay.getUrlPicture(), "drawable", getPackageName());

        Log.d("BarkeeperApp", "Packagename: " + getPackageName());

        String cocktailName;
        String cocktailDescription;
        String cocktailPreperation;

        QueryBuilder<LanguagesTexts> qb2 = daoSession.getLanguagesTextsDao().queryBuilder();
        qb2.where(LanguagesTextsDao.Properties.CocktailID.eq(randomID));
        qb2.where(LanguagesTextsDao.Properties.LanguageID.eq(currentLang.getId()));
        qb2.where(LanguagesTextsDao.Properties.ComponentID.eq(Helper.Component.COCKTAILNAME.getId()));
        cocktailName = qb2.list().get(0).getText();

        qb2 = daoSession.getLanguagesTextsDao().queryBuilder();
        qb2.where(LanguagesTextsDao.Properties.CocktailID.eq(randomID));
        qb2.where(LanguagesTextsDao.Properties.LanguageID.eq(currentLang.getId()));
        qb2.where(LanguagesTextsDao.Properties.ComponentID.eq(Helper.Component.COCKTAILDESCRIPTION.getId()));
        cocktailDescription = qb2.list().get(0).getText();

        qb2 = daoSession.getLanguagesTextsDao().queryBuilder();
        qb2.where(LanguagesTextsDao.Properties.CocktailID.eq(randomID));
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

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem item = menu.findItem(R.id.button_favorite);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.button_favorite:
                if (item.isChecked()) {
                    item.setChecked(false);
                    item.setIcon(getResources().getDrawable(R.drawable.button_favorite_deactivated));
                } else {
                    item.setChecked(true);
                    item.setIcon(getResources().getDrawable(R.drawable.button_favorite_activated));
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.d("BarkeeperApp", "NavigationItem: " + id);

        switch (id) {
            case R.id.nav_home:
                //setContentView(R.layout.activity_home);
                Intent intent_Home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent_Home);
                drawer.closeDrawers();
                break;
            case R.id.nav_search:
                //setContentView(R.layout.activity_search);
                Intent intent_Search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent_Search);
                break;
            case R.id.nav_ingredients:
                //setContentView(R.layout.activity_ingredients);
                Intent intent_Ingredients = new Intent(getApplicationContext(), IngredientsActivity.class);
                startActivity(intent_Ingredients);
                break;
            case R.id.nav_favorites:
                //setContentView(R.layout.activity_favorites);
                Intent intent_Favorites = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(intent_Favorites);
                break;
            case R.id.nav_settings:
                //setContentView(R.layout.activity_settings);
                Intent intent_Seetings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent_Seetings);
                drawer.closeDrawers();
                break;
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*setTitle(item.getTitle());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;*/
        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }

    public void refreshActivity(){
        recreate();
    }

    public void removeHomeView(){
        ConstraintLayout lr = findViewById(R.id.content_home);
        CoordinatorLayout cl = findViewById(R.id.app_bar_coordinaterLayout);
        cl.removeView(lr);
    }

    public void removeFrameContent(){
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        contentFrameLayout.removeAllViews();
    }

    public void setupBackButtonActionBar(){
        // update the actionbar
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BarkeeperApp", "Back Button pushed");
                Intent intent_Home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent_Home);
                drawer.closeDrawers();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageManager.setLanguage(base, LanguageManager.getCurrentLanguage()));
        Log.d("BarkeeperApp", "HOME ACT attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageManager.setLanguage(this, LanguageManager.getCurrentLanguage());
        Log.d("BarkeeperApp", "HOME ACT Config has changed");
    }
}
