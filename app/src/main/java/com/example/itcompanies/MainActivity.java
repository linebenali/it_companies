//package com.example.itcompanies;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.translation.TranslationResponse;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class MainActivity extends AppCompatActivity {
//    private RecyclerCompanyAdapter adapter;
//    private ArrayList<Company> companies = new ArrayList<>();
//    //private static final String TRANSLATOR_API_KEY = "B0o26MwGY9UfDWLcze40rC3LN1TFLcfqNnD4cWsYXWXiLAkmgzMFJQQJ99ALACYeBjFXJ3w3AAAbACOGrpYc";
//    //private static final String TRANSLATOR_REGION = "eastus";
//    // private String currentLanguage = "fr";  // Langue par défaut
//    //private Button Managebutton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Récupérer la langue depuis SharedPreferences
//        //currentLanguage = getLanguageFromPreferences();
//
//        //Managebutton = findViewById(R.id.Managebutton);
//
//
//        // Configurer le DrawerLayout et NavigationView
//        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//        NavigationView navigationView = findViewById(R.id.navigationView);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
////        // Vérifier la configuration du menu latéral
////        if (drawerLayout == null || navigationView == null) {
////            showError("Erreur dans la configuration du sidebar !");
////            return;
////        }
//
////        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
////        drawerLayout.addDrawerListener(toggle);
////        toggle.syncState();
//
//
//        // sidebar
////        navigationView.setNavigationItemSelectedListener(item -> {
////            if (item.getItemId() == R.id.nav_list) {
////                Toast.makeText(this, "List company selected", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(this, MainActivity.class);
////                saveLanguageToPreferences(currentLanguage);
////                startActivity(intent);
////            } else if (item.getItemId() == R.id.nav_profile) {
////                Toast.makeText(this, "Profil selected", Toast.LENGTH_SHORT).show();
////                // Démarrer l'activité Profil
////                Intent intent = new Intent(this, Profile_Activity.class); // Remplacez ProfilActivity par le nom de votre activité de profil
////                saveLanguageToPreferences(currentLanguage);
////                startActivity(intent);
////            } else if (item.getItemId() == R.id.nav_manage) {
////                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(this, Manage_companies.class);
////                saveLanguageToPreferences(currentLanguage);
////                startActivity(intent);
////            } else if (item.getItemId() == R.id.nav_logout) {
////                Toast.makeText(this, "Logout selected ", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(this, login.class); // Par exemple, revenir à l'écran de connexion
////                saveLanguageToPreferences(currentLanguage);
////                startActivity(intent);
////            }
////            drawerLayout.closeDrawers();
////            return true;
////        });
//
//
//        // RecyclerView setup
//        RecyclerView recyclerView = findViewById(R.id.recyclerView_companies);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Initialiser l'adaptateur
//        adapter = new RecyclerCompanyAdapter(this, companies, company -> openDetailActivity(company.getId()));
//        recyclerView.setAdapter(adapter);
//
//        // Charger les entreprises depuis la base de données
//        loadCompanies();
//
////        Managebutton.setOnClickListener(v -> {
////            // Sauvegarder la langue dans SharedPreferences
////            saveLanguageToPreferences(currentLanguage);
////            Intent intent = new Intent(MainActivity.this, Manage_companies.class);
////            startActivity(intent);
////        });
////
////        // FloatingActionButton pour changer la langue
////        FloatingActionButton fabTranslate = findViewById(R.id.fab_add_company);
////        fabTranslate.setOnClickListener(v -> {
////            currentLanguage = currentLanguage.equals("fr") ? "en" : "fr";  // Bascule entre français et anglais
////            translateAllTexts(currentLanguage); // Traduire tous les textes de l'interface
////        });
////    }
//
//        private void loadCompanies () {
//            DatabaseHelper dbHelper = new DatabaseHelper(this);
//            companies.clear();
//            companies.addAll(dbHelper.getAllCompanies());
//            adapter.notifyDataSetChanged();
//        }
//
////    private void translateAllTexts(String targetLanguage) {
////        // Préparer une liste de tous les éléments à traduire
////        List<String> textsToTranslate = new ArrayList<>();
////        textsToTranslate.add(((Button) findViewById(R.id.Managebutton)).getText().toString());
////        textsToTranslate.add(((FloatingActionButton) findViewById(R.id.fab_add_company)).getContentDescription().toString());
////
////        // Ajouter d'autres textes de votre interface utilisateur ici
////        // Par exemple, des TextView, EditText, etc.
////
////        if (textsToTranslate.isEmpty()) {
////            Toast.makeText(this, "Aucun texte à traduire", Toast.LENGTH_SHORT).show();
////            return;
////        }
//
////        // Préparer les données pour l'API de traduction
////        ArrayList<Map<String, String>> requestBody = new ArrayList<>();
////        for (String text : textsToTranslate) {
////            Map<String, String> textMap = new HashMap<>();
////            textMap.put("Text", text);
////            requestBody.add(textMap);
////        }
//
//        // Préparer les données pour les entreprises
//        ArrayList<Map<String, String>> requestBody2 = new ArrayList<>();
//        for (Company company : companies) {
//            Map<String, String> textMap = new HashMap<>();
//            textMap.put("Text", company.getName());
//            requestBody2.add(textMap);
//        }
//
////        // Configurer Retrofit
////        Retrofit retrofit = new Retrofit.Builder()
////                .baseUrl("https://api.cognitive.microsofttranslator.com/")
////                .addConverterFactory(GsonConverterFactory.create())
////                .build();
////
////        TranslatorApi translatorApi = retrofit.create(TranslatorApi.class);
////
////        // Appeler l'API pour traduire les éléments de l'interface et les noms des entreprises
////        Call<ArrayList<TranslationResponse>> call = translatorApi.translateText(
////                "3.0", // Version de l'API
////                targetLanguage,
////                TRANSLATOR_API_KEY,
////                TRANSLATOR_REGION,
////                requestBody
////        );
////
////        // Appel pour traduire les noms des entreprises
////        Call<ArrayList<TranslationResponse>> call2 = translatorApi.translateText(
////                "3.0", // Version de l'API
////                targetLanguage,
////                TRANSLATOR_API_KEY,
////                TRANSLATOR_REGION,
////                requestBody2
////        );
////
////        // Exécuter les appels en parallèle
////        call.enqueue(new Callback<ArrayList<TranslationResponse>>() {
////            @Override
////            public void onResponse(Call<ArrayList<TranslationResponse>> call, Response<ArrayList<TranslationResponse>> response) {
////                if (response.isSuccessful() && response.body() != null) {
////                    ArrayList<TranslationResponse> translations = response.body();
////
////                    // Mettre à jour les éléments traduits de l'interface
////                    for (int i = 0; i < textsToTranslate.size(); i++) {
////                        String translatedText = translations.get(i).getTranslations().get(0).getText();
////                        // Mettez à jour chaque élément en fonction de son type (Button, FloatingActionButton, etc.)
////                        if (i == 0) {
////                            ((Button) findViewById(R.id.Managebutton)).setText(translatedText);
////                        } else if (i == 1) {
////                            ((FloatingActionButton) findViewById(R.id.fab_add_company)).setContentDescription(translatedText);
////                        }
////                    }
////                } else {
////                    Log.e("TranslationAPI", "Erreur: " + response.errorBody());
////                    Toast.makeText(MainActivity.this, "Erreur lors de la traduction des éléments", Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<ArrayList<TranslationResponse>> call, Throwable t) {
////                Log.e("TranslationAPI", "Échec: " + t.getMessage());
////                Toast.makeText(MainActivity.this, "Échec de la traduction des éléments", Toast.LENGTH_SHORT).show();
////            }
////        });
////
////        // Exécuter l'appel pour traduire les noms des entreprises
////        call2.enqueue(new Callback<ArrayList<TranslationResponse>>() {
////            @Override
////            public void onResponse(Call<ArrayList<TranslationResponse>> call, Response<ArrayList<TranslationResponse>> response) {
////                if (response.isSuccessful() && response.body() != null) {
////                    ArrayList<TranslationResponse> translations = response.body();
////
////                    // Mettre à jour les noms des entreprises traduits
////                    for (int i = 0; i < companies.size(); i++) {
////                        String translatedText = translations.get(i).getTranslations().get(0).getText();
////                        companies.get(i).setName(translatedText);
////                    }
////
////                    // Notifier l'adaptateur que les données ont changé
////                    adapter.notifyDataSetChanged();
////                } else {
////                    Log.e("TranslationAPI", "Erreur: " + response.errorBody());
////                    Toast.makeText(MainActivity.this, "Erreur lors de la traduction des entreprises", Toast.LENGTH_SHORT).show();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<ArrayList<TranslationResponse>> call, Throwable t) {
////                Log.e("TranslationAPI", "Échec: " + t.getMessage());
////                Toast.makeText(MainActivity.this, "Échec de la traduction des entreprises", Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
//
//
////    private void openDetailActivity(int companyId) {
////        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
////        saveLanguageToPreferences(currentLanguage);
////        intent.putExtra("company_id", companyId);
////        startActivity(intent);
////    }
//
////    // Sauvegarder la langue dans SharedPreferences
////    private void saveLanguageToPreferences(String language) {
////        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
////        SharedPreferences.Editor editor = preferences.edit();
////        editor.putString("language", language);
////        editor.apply();
////    }
//
////    // Charger la langue depuis SharedPreferences
////    private String getLanguageFromPreferences() {
////        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
////        return preferences.getString("language", "fr");  // Langue par défaut : français
////    }
//
////    private void showError(String errorMessage) {
////        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
////    }
//
//    }
//}





package com.example.itcompanies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerCompanyAdapter adapter;
    private ArrayList<Company> companies = new ArrayList<>();
    private Button Managebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//******************* DrawerLayout sidebar toolbar *************************************************************************//

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_list) {
                Toast.makeText(this, "List company selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(this, "Profil selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Profile_Activity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_manage) {
                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CompaniesManagementActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Logout selected ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawers();
            return true;
        });

//******************************************************************************************************************************//

        // Références aux vues
        RecyclerView recyclerView = findViewById(R.id.recyclerView_companies);
        TextView emptyTextView = findViewById(R.id.emptyTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation de l'adaptateur
        adapter = new RecyclerCompanyAdapter(this, companies, company -> openDetailActivity(company.getId()));
        recyclerView.setAdapter(adapter);

        // Charger les entreprises
        loadCompanies(recyclerView, emptyTextView);
    }

    private void loadCompanies(RecyclerView recyclerView, TextView emptyTextView) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        companies.clear();
        companies.addAll(dbHelper.getAllCompanies());
        adapter.notifyDataSetChanged();

        // Afficher ou masquer les vues selon la liste des entreprises
        if (companies.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }
    // Open detail activity for a company
    private void openDetailActivity(int companyId) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }

    // Show error message
    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}



//
//
//package com.example.itcompanies;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.Toast;
//
//import android.content.Intent;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationView;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    private RecyclerCompanyAdapter adapter;
//    private ArrayList<Company> companies = new ArrayList<>();
//    private Button Managebutton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
////******************* DrawerLayout sidebar toolbar *************************************************************************//
//
//        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//        NavigationView navigationView = findViewById(R.id.navigationView);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView.setNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.nav_list) {
//                Toast.makeText(this, "List company selected", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            } else if (item.getItemId() == R.id.nav_profile) {
//                Toast.makeText(this, "Profil selected", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, Profile_Activity.class);
//                startActivity(intent);
//            } else if (item.getItemId() == R.id.nav_manage) {
//                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, CompaniesManagementActivity.class);
//                startActivity(intent);
//            } else if (item.getItemId() == R.id.nav_logout) {
//                Toast.makeText(this, "Logout selected ", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, login.class);
//                startActivity(intent);
//            }
//            drawerLayout.closeDrawers();
//            return true;
//        });
//
////******************************************************************************************************************************//
//
//        // RecyclerView setup
//        RecyclerView recyclerView = findViewById(R.id.recyclerView_companies);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Adapter initialization
//        adapter = new RecyclerCompanyAdapter(this, companies, company -> openDetailActivity(company.getId()));
//        recyclerView.setAdapter(adapter);
//
//        // Load companies
//        loadCompanies();
//
//
////        Managebutton.setOnClickListener(v -> {
////            Intent intent = new Intent(MainActivity.this, AddUpdateCompanyActivity.class);
////            startActivity(intent);
////        });
//
//
//    }
//
//    // Load companies from the database
//    private void loadCompanies() {
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        companies.clear();
//        companies.addAll(dbHelper.getAllCompanies());
//        adapter.notifyDataSetChanged();
//    }
//
//    // Open detail activity for a company
//    private void openDetailActivity(int companyId) {
//        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//        intent.putExtra("company_id", companyId);
//        startActivity(intent);
//    }
//
//    // Show error message
//    private void showError(String errorMessage) {
//        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
//    }
//}
