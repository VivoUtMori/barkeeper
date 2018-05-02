package de.wirtgen.staiger.barkeeper;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Map;

public class SearchActivity extends HomeActivity implements SearchAdapter.SearchAdapterListener {

    SearchAdapter sa;
    SearchView searchView;
    boolean showsSubContent;
    String filterString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.nav_search);

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

        //remove Content from HomeActivity
        this.removeHomeView();
        this.removeFrameContent();

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_search, contentFrameLayout);


        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        LanguageManager.Language currentLang = LanguageManager.getCurrentLanguage();
        Log.d("BarkeeperApp", "Current Lang: " + currentLang.name());

        Map<Cocktail, String> m = Cocktail.getAllCoacktailsWithNamesASC(daoSession, currentLang.getId());

        RecyclerView rv = findViewById(R.id.content_recyclerView_search);
        //whiteNotificationBar(rv);
        rv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager lm = new LinearLayoutManager(this);
        sa = new SearchAdapter(m, this);
        rv.setLayoutManager(lm);
        rv.setAdapter(sa);

        sa.notifyDataSetChanged();
        sa.getFilter().filter(filterString);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                sa.getFilter().filter(query);
                filterString = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                sa.getFilter().filter(query);
                filterString = query;
                return false;
            }
        });
        //sa.getFilter().filter("");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    @Override
    public void onSelected(String s) {
        Toast.makeText(getApplicationContext(), "Selected: " + s, Toast.LENGTH_LONG).show();
    }


    public void showCocktailDetail(Cocktail selectedCocktail){
        Log.d("BarkeeperApp", "Callback to Activity. Cocktail ID: " + selectedCocktail.getId());

        showsSubContent = true;

        this.removeHomeView();
        this.removeFrameContent();

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.content_cocktail, contentFrameLayout);


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
