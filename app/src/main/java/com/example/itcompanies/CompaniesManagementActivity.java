
//pour afficher la liste li jaya mel companyAdapter

package com.example.itcompanies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class CompaniesManagementActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView companiesRecyclerView;
    private FloatingActionButton addCompanyButton;
    private CompanyAdapter companyAdapter;
    private ArrayList<Company> companies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_management);

//****************************** DrawerLayout sidebar toolbar **************************************//

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (drawerLayout == null || navigationView == null) {
            showError("Erreur dans la configuration du menu latéral !");
            return;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_list) {
                Toast.makeText(this, "List company selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Profile_Activity.class));
            } else if (item.getItemId() == R.id.nav_manage) {
                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CompaniesManagementActivity.class));
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, login.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });

//*************************************************************************************************************************//

        dbHelper = new DatabaseHelper(this);
        companiesRecyclerView = findViewById(R.id.manage_recyclerView_companies);
        companiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // addCompanyButton
        addCompanyButton = findViewById(R.id.fab_add_company); // Correct assignment
        addCompanyButton.setOnClickListener(v -> {
            Intent intent = new Intent(CompaniesManagementActivity.this, AddUpdateCompanyActivity.class);
            startActivity(intent);
        });

        // Load companies in a background
        new Thread(() -> {
            companies = dbHelper.getAllCompanies();
            runOnUiThread(() -> {
                companyAdapter = new CompanyAdapter(CompaniesManagementActivity.this, companies);
                companiesRecyclerView.setAdapter(companyAdapter);
            });
        }).start();
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_LONG).show();
    }
}

