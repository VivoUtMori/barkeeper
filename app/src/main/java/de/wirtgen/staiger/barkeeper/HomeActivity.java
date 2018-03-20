package de.wirtgen.staiger.barkeeper;

import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        CocktailDao cocktailDao = daoSession.getCocktailDao();
        List<Cocktail> listOfCocktails = cocktailDao.queryBuilder().where(CocktailDao.Properties.Name.like("%Long%")).list();
        for (Cocktail c : listOfCocktails) {
            Log.d("HomeActivity", "Loaded Cocktail from DB" + c.getName());
            Map<Ingredient, Integer> m = c.getAllIngredientsWithUnits();
            Log.d("HomeActivity", "Ingredients for cocktail: " + c.getName());
            for (Map.Entry<Ingredient, Integer> e : m.entrySet()) {
                Log.d("HomeActivity", "Entry: " + e.getKey().getName() + ": " + e.getValue().toString());
            }
        }
    }

    @Override
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

        setTitle(item.getTitle());

        switch (id) {
            case R.id.nav_home:
                break;
            case R.id.nav_search:
                break;
            case R.id.nav_ingredients:
                break;
            case R.id.nav_favorites:
                break;
            case R.id.nav_settings:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
