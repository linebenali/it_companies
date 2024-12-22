

package com.example.itcompanies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private TextView companyName, phoneText, websiteText, mailText, locationText;
    private ImageView companyImage, phoneButton, websiteButton, mailButton, locationButton, calendrierIcon;
    private RatingBar ratingBar;
    private ListView serviceListView;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialisations

        dbHelper = new DatabaseHelper(this);

        initializeViews();

        // Récupérer l'ID de l'entreprise
        Intent intent = getIntent();
        int companyId = intent.getIntExtra("company_id", -1);

        if (companyId != -1) {
            loadCompanyDetails(companyId);
        } else {
            Toast.makeText(this, "Invalid company ID", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité si l'ID est invalide
        }

//******************* DrawerLayout sidebar toolbar *************************************************************************//

        //  DrawerLayout et NavigationView
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
    }
//***************************************************************************************************************//

    private void initializeViews() {
        companyName = findViewById(R.id.company_name);
        phoneText = findViewById(R.id.phone_text);
        websiteText = findViewById(R.id.website_text);
        mailText = findViewById(R.id.mail_text);
        //locationText = findViewById(R.id.location_text);

        companyImage = findViewById(R.id.detail_image);
        phoneButton = findViewById(R.id.phoneIcon);
        websiteButton = findViewById(R.id.webIcon);
        mailButton = findViewById(R.id.emailIcon);
        locationButton = findViewById(R.id.locationIcon);

        ratingBar = findViewById(R.id.ratingBar);
        serviceListView = findViewById(R.id.detail_services);
        calendrierIcon = findViewById(R.id.calendrierIcon);
    }

    private void loadCompanyDetails(int companyId) {
        Company company = dbHelper.getCompanyById(companyId);

        if (company != null) {
            // Mettre à jour les vues avec les données de l'entreprise
            companyName.setText(company.getName());
            phoneText.setText(company.getPhone());
            websiteText.setText(company.getWebsite());
            mailText.setText(company.getMail());
            //locationText.setText(company.getLocation());

            // Afficher l'image
            Bitmap bitmap = getImageFromBlob(company.getImage());
            if (bitmap != null) {
                companyImage.setImageBitmap(bitmap);
            } else {
                companyImage.setImageResource(R.drawable.placeholder);
            }

            // Afficher les services dans un ListView
            ArrayList<String> services = company.getServices();
            ServiceAdapter serviceAdapter = new ServiceAdapter(this, services);
            serviceListView.setAdapter(serviceAdapter);

            // Configurer les boutons pour les actions
            setupActionButtons(company);
        } else {
            Toast.makeText(this, "Company not found", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité si l'entreprise n'est pas trouvée
        }
    }

    private void setupActionButtons(Company company) {
        phoneButton.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + company.getPhone()));
            startActivity(phoneIntent);
        });

        websiteButton.setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( company.getWebsite()));
            startActivity(webIntent);
        });

        mailButton.setOnClickListener(v -> {
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + company.getMail()));
            startActivity(mailIntent);
        });

        locationButton.setOnClickListener(v -> {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( company.getLocation()));
            startActivity(mapIntent);
        });

        calendrierIcon.setOnClickListener(v -> {
            Intent calendarIntent = new Intent(DetailActivity.this, CalendarActivity.class);
            startActivity(calendarIntent);
        });

    }

    private Bitmap getImageFromBlob(byte[] imageBlob) {
        if (imageBlob != null) {
            return BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
        }
        return null;
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, "Erreur : " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
