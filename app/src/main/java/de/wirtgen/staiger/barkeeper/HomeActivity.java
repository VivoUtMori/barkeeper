package de.wirtgen.staiger.barkeeper;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.nav_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        List<LanguagesTexts> llt = daoSession.getLanguagesTextsDao().loadAll();

        for (LanguagesTexts lt : llt){
            Log.d("DaoDB", "LanguageText ID: " + lt.getId() + " IngredientID: " + lt.getIngredientID() + " text: " + lt.getText());
        }

        List<Ingredient> listIngredients = daoSession.getIngredientDao().loadAll();
        for (Ingredient i : listIngredients){
            Log.d("DaoDB","Readed Ingredient: "+ i.getId());
            String l = i.getIngredientsName((long) 1);
            Log.d("DaoDB","Readed Ingredient: "+ i.getId() + " name: " + l);
        }

        /*CocktailDao cocktailDao = daoSession.getCocktailDao();
        List<Cocktail> listOfCocktails = daoSession.getLanguagesTextsDao().queryBuilder().where(CocktailDao.Properties.Name.like("%Long%")).list();
        for (Cocktail c : listOfCocktails) {
            Log.d("HomeActivity", "Loaded Cocktail from DB" + c.getId());
            Map<Ingredient, Integer> m = c.getAllIngredientsWithUnits();
            Log.d("HomeActivity", "Ingredients for cocktail: " + c.getId());
            for (Map.Entry<Ingredient, Integer> e : m.entrySet()) {
                Log.d("HomeActivity", "Entry: " + e.getKey().getId() + ": " + e.getValue().toString());
            }
        }*/
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

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
        return false;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }

    public void refreshActivity(){
        recreate();
    }
}
