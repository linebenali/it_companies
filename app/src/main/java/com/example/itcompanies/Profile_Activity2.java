//package com.example.itcompanies;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//
//import com.google.android.material.imageview.ShapeableImageView;
//import com.google.android.material.navigation.NavigationView;
//
//public class Profile_Activity extends AppCompatActivity {
//
//    private ShapeableImageView profileImageView;
//    private Button nameButton, emailButton , editButton;
//    private DatabaseHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        // Initialisation des vues
//        profileImageView = findViewById(R.id.imageView2);
//        nameButton = findViewById(R.id.name);
//        emailButton = findViewById(R.id.email);
//        editButton = findViewById(R.id.button);
//
//        // Initialisation du DatabaseHelper
//        dbHelper = new DatabaseHelper(this);
//
//
////****************************** DrawerLayout sidebar toolbar **************************************//
//
//        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
//        NavigationView navigationView = findViewById(R.id.navigationView);
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
//                startActivity(new Intent(this, MainActivity.class));
//            } else if (item.getItemId() == R.id.nav_profile) {
//                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, Profile_Activity.class));
//            } else if (item.getItemId() == R.id.nav_manage) {
//                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, CompaniesManagementActivity.class));
//            } else if (item.getItemId() == R.id.nav_logout) {
//                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, login.class));
//            }
//            drawerLayout.closeDrawers();
//            return true;
//        });
//
////*************************************************************************************************************************//
//
//
//        // Récupération de l'ID utilisateur depuis GlobalState
//        int userId = GlobalState.getInstance().getUtilisateurId();
//        Log.d("Profile_Activity", "User ID récupéré : " + userId);
//
//        // Vérification si l'ID utilisateur est valide
//        if (userId != -1) {
//            Log.d("Profile_Activity", "ID utilisateur valide, chargement du profil...");
//            loadUserProfile(userId);
//        } else {
//            Log.e("Profile_Activity", "Erreur : utilisateur non identifié");
//            Toast.makeText(this, "Erreur : utilisateur non identifié", Toast.LENGTH_SHORT).show();
//        }
//
//        editButton.setOnClickListener(v -> {
//            Intent intent = new Intent(Profile_Activity.this, EditProfile_Activity.class);
//            startActivity(intent);
//        });
//    }
//
//    // Fonction pour charger les informations de l'utilisateur
//    private void loadUserProfile(int userId) {
//        Log.d("Profile_Activity", "Tentative de récupération des informations pour l'utilisateur ID : " + userId);
//
//        Cursor cursor = dbHelper.getUserById(userId);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            Log.d("Profile_Activity", "Utilisateur trouvé, récupération des données...");
//
//            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
//            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
//
//            // Récupérer l'image sous forme de tableau d'octets
//            byte[] photoBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHOTO));
//
//            // Vérifier si une photo existe et la définir
//            if (photoBlob != null && photoBlob.length > 0) {
//                Bitmap photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
//                profileImageView.setImageBitmap(photoBitmap);
//            } else {
//                profileImageView.setImageResource(R.drawable.default_profile_image); // Image par défaut
//            }
//
//            // Mettre à jour le nom et l'email
//            nameButton.setText(name);
//            emailButton.setText(email);
//
//            cursor.close();
//            Log.d("Profile_Activity", "Profil utilisateur chargé avec succès.");
//        } else {
//            Log.e("Profile_Activity", "Aucun profil trouvé pour l'utilisateur ID : " + userId);
//            Toast.makeText(this, "Aucun profil trouvé", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
//
//
//
//
//






package com.example.itcompanies;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

public class Profile_Activity2 extends AppCompatActivity {

    private ShapeableImageView profileImageView;
    private Button nameButton, emailButton , editButton;
    private Button helpButton; // Ajouter référence pour le bouton Help
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        // Initialisation des vues
        profileImageView = findViewById(R.id.imageView2);
        nameButton = findViewById(R.id.name);
        emailButton = findViewById(R.id.email);
        editButton = findViewById(R.id.button);
        helpButton = findViewById(R.id.help);

        // Initialisation du DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        //****************************** DrawerLayout sidebar toolbar **************************************//
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
                startActivity(new Intent(this, MainActivity2.class));
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Profile_Activity2.class));
            } else if (item.getItemId() == R.id.nav_manage) {
                Toast.makeText(this, "Manage company selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CompaniesManagementActivity.class));
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, login2.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });

        //*************************************************************************************************************************//

        // Récupération de l'ID utilisateur depuis GlobalState
        int userId = GlobalState.getInstance().getUtilisateurId();
        Log.d("Profile_Activity", "User ID récupéré : " + userId);

        // Vérification si l'ID utilisateur est valide
        if (userId != -1) {
            Log.d("Profile_Activity", "ID utilisateur valide, chargement du profil...");
            loadUserProfile(userId);
        } else {
            Log.e("Profile_Activity", "Erreur : utilisateur non identifié");
            Toast.makeText(this, "Erreur : utilisateur non identifié", Toast.LENGTH_SHORT).show();
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Activity2.this, EditProfile_Activity2.class);
            startActivity(intent);
        });

        // Ajout du gestionnaire de clic pour le bouton Help
        helpButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Activity2.this, HelpActivity.class); // Intent pour HelpActivity
            startActivity(intent); // Démarre HelpActivity
        });
    }

    // Fonction pour charger les informations de l'utilisateur
    private void loadUserProfile(int userId) {
        Log.d("Profile_Activity", "Tentative de récupération des informations pour l'utilisateur ID : " + userId);

        Cursor cursor = dbHelper.getUserById(userId);

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Profile_Activity", "Utilisateur trouvé, récupération des données...");

            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));

            // Récupérer l'image sous forme de tableau d'octets
            byte[] photoBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHOTO));

            // Vérifier si une photo existe et la définir
            if (photoBlob != null && photoBlob.length > 0) {
                Bitmap photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
                profileImageView.setImageBitmap(photoBitmap);
            } else {
                profileImageView.setImageResource(R.drawable.default_profile_image); // Image par défaut
            }

            // Mettre à jour le nom et l'email
            nameButton.setText(name);
            emailButton.setText(email);

            cursor.close();
            Log.d("Profile_Activity", "Profil utilisateur chargé avec succès.");
        } else {
            Log.e("Profile_Activity", "Aucun profil trouvé pour l'utilisateur ID : " + userId);
            Toast.makeText(this, "Aucun profil trouvé", Toast.LENGTH_SHORT).show();
        }
    }
}
