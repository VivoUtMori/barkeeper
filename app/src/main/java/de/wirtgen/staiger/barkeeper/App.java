package de.wirtgen.staiger.barkeeper;

/**
 * Created by Staiger/Wirtgen on 14.03.2018.
 */

import android.app.Application;
import android.app.LoaderManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageManager.setLanguage(base, LanguageManager.getCurrentLanguage()));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LanguageManager.setLanguage(this, LanguageManager.getCurrentLanguage());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("APP", "Locale Language: " + this.getBaseContext().getResources().getConfiguration().locale.getLanguage());

        //Create/Load DB
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,  "cocktail-db-encrypted");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        DaoMaster dm = new DaoMaster(db);
        daoSession = dm.newSession();


        // DB leeren
        dm.dropAllTables(db, true);
        dm.createAllTables(db, true);

        //daoSession.getDaoCocktailDao().deleteAll();

        Languages german = new Languages();
        german.setName("de_DE");

        Languages english = new Languages();
        english.setName("en_EN");

        daoSession.getLanguagesDao().insert(german);
        daoSession.getLanguagesDao().insert(english);

        Log.d("DaoDB", "Language German has ID:  " + german.getId());
        Log.d("DaoDB", "Language English has ID: " + english.getId());


        Log.d("DaoDB", "Avaible Components: ");

        Components c1 = new Components();
        c1.setName("Cocktail_Name");

        Components c2 = new Components();
        c2.setName("Cocktail_Description");

        Components c3 = new Components();
        c3.setName("Cocktail_Preperation");


        daoSession.getComponentsDao().insert(c1);
        daoSession.getComponentsDao().insert(c2);
        daoSession.getComponentsDao().insert(c3);

        Log.d("DaoDB", "Comp: " + c1.getName() + " ID " + c1.getId());
        Log.d("DaoDB", "Comp: " + c2.getName() + " ID " + c2.getId());
        Log.d("DaoDB", "Comp: " + c3.getName() + " ID " + c3.getId());


        Ingredient rum = new Ingredient();
        rum.setIsAvailable(true);
        rum.setIsForbidden(false);

        Ingredient vodka = new Ingredient();
        vodka.setIsForbidden(false);
        vodka.setIsAvailable(true);


        daoSession.getIngredientDao().insert(rum);
        daoSession.getIngredientDao().insert(vodka);

        LanguagesTexts text_de_Rum = new LanguagesTexts();
        text_de_Rum.setText("Brauner Rum");
        text_de_Rum.setIngredientID(rum.getId());
        text_de_Rum.setLanguageID(german.getId());

        LanguagesTexts text_de_Vodka = new LanguagesTexts();
        text_de_Vodka.setText("Vodka");
        text_de_Vodka.setIngredientID(vodka.getId());
        text_de_Vodka.setLanguageID(german.getId());

        daoSession.getLanguagesTextsDao().insert(text_de_Rum);
        daoSession.getLanguagesTextsDao().insert(text_de_Vodka);

        Log.d("DaoDB", "Inserted Ingredient with ID: " + rum.getId() + " name: " + text_de_Rum.getText());
        Log.d("DaoDB", "Inserted Ingredient with ID: " + vodka.getId() + " name: " + text_de_Vodka.getText());


        Cocktail longIsland = new Cocktail();
        longIsland.setUrlPicture("/test.png");

        daoSession.getCocktailDao().insert(longIsland);

        Log.d("DaoDB", "Inserted Cocktail with ID and Name: " + longIsland.getId() + "Long Island Iced Tea");


        LanguagesTexts li_de_Description = new LanguagesTexts();
        li_de_Description.setLanguageID(german.getId());
        li_de_Description.setComponentID(c1.getId());
        li_de_Description.setCocktailID(longIsland.getId());
        li_de_Description.setText("Bester Cocktail der Welt!");

        LanguagesTexts li_de_Name = new LanguagesTexts();
        li_de_Name.setLanguageID(german.getId());
        li_de_Name.setComponentID(c2.getId());
        li_de_Name.setCocktailID(longIsland.getId());
        li_de_Name.setText("Long Island Iced Tea");


        LanguagesTexts li_de_Preperation = new LanguagesTexts();
        li_de_Preperation.setLanguageID(german.getId());
        li_de_Preperation.setComponentID(c3.getId());
        li_de_Preperation.setCocktailID(longIsland.getId());
        li_de_Preperation.setText("1) Alles zusammen kippen 2) Mixen 3) Saufen");


        daoSession.getLanguagesTextsDao().insert(li_de_Description);
        daoSession.getLanguagesTextsDao().insert(li_de_Name);
        daoSession.getLanguagesTextsDao().insert(li_de_Preperation);



        Recipes recipeLongIsland = new Recipes();
        recipeLongIsland.setCocktailID(longIsland.getId());
        recipeLongIsland.setIngredientID(rum.getId());
        recipeLongIsland.setUnits(30);

        Recipes recipeLongIsland2 = new Recipes();
        recipeLongIsland2.setCocktailID(longIsland.getId());
        recipeLongIsland2.setIngredientID(vodka.getId());
        recipeLongIsland2.setUnits(20);

        daoSession.getRecipesDao().insert(recipeLongIsland);
        daoSession.getRecipesDao().insert(recipeLongIsland2);


        Log.d("DaoDB", "Inserted RecipeEntry with: " + longIsland.getId() + " " + rum.getId() + " " + recipeLongIsland.getUnits());
        Log.d("DaoDB", "Inserted RecipeEntry with: " + longIsland.getId() + " " + vodka.getId() + " " + recipeLongIsland2.getUnits());


    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}