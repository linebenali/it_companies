
package com.example.itcompanies;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerCompanyAdapter adapter;
    private ArrayList<Company> companies = new ArrayList<>();
    private Button Managebutton;

    private FloatingActionButton fabTranslate;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //***************************************************************************************//

        fabTranslate = findViewById(R.id.fab_translate);
        fabTranslate.setOnClickListener(v -> showLanguageDialog());
        //*******************************************************************************************//



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
                Intent intent = new Intent(this, Profile_Activity2.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_manage) {
                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CompaniesManagementActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Logout selected ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, login2.class);
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

        //*********************************************//
        fabTranslate.setOnClickListener(v -> {
            showLanguageDialog();
        });
        //******************************************************//
    }

    private void loadCompanies(RecyclerView recyclerView, TextView emptyTextView) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        companies.clear();
        companies.addAll(dbHelper.getAllCompanies());
        adapter.notifyDataSetChanged();


        if (companies.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    private void openDetailActivity(int companyId) {
        Intent intent = new Intent(MainActivity2.this, DetailActivity.class);
        intent.putExtra("company_id", companyId);
        startActivity(intent);
    }


    //*******************************************************************/
    private void showLanguageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setTitle(R.string.choose_language)
                .setItems(new CharSequence[]{getString(R.string.language_english), getString(R.string.language_french)}, (dialog, which) -> {
                    if (which == 0) {
                        navigateToMainActivity();
                    } else if (which == 1) {
                        navigateToMainActivity2();
                    }
                })
                .create()
                .show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToMainActivity2() {
        Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
        startActivity(intent);
    }




    //**********************************************************************/


}


