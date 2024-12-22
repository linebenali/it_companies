//package com.example.itcompanies;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class creation_de_compte extends AppCompatActivity {
//
//    private EditText editTextNom,editTextEmail, editTextPassword;
//    private Button registerButton;
//    private DatabaseHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_creation_de_compte);
//
//        // Initialisation des vues
//        editTextNom = findViewById(R.id.name);
//        editTextEmail = findViewById(R.id.email);
//        editTextPassword = findViewById(R.id.password);
//        registerButton = findViewById(R.id.registerButton);
//
//        // Initialisation de la base de données
//        dbHelper = new DatabaseHelper(this);
//
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nom = editTextNom.getText().toString().trim();
//                String email = editTextEmail.getText().toString().trim();
//                String password = editTextPassword.getText().toString().trim();
//
//                if (nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(creation_de_compte.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Enregistrer l'utilisateur seulement si l'email n'existe pas
//                    long result = dbHelper.addUser(nom, email, password);
//                    if (result == -1) {
//                        Toast.makeText(creation_de_compte.this, "Email already exists", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(creation_de_compte.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(creation_de_compte.this, login.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }
//        });
//
//
////        registerButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String nom = editTextNom.getText().toString().trim();
////                String password = editTextPassword.getText().toString().trim();
////
////                if (nom.isEmpty() || password.isEmpty()) {
////                    Toast.makeText(creation_de_compte.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
////                } else {
////                    long result = dbHelper.addUser(nom, password);
////                    if (result != -1) {
////                        Toast.makeText(creation_de_compte.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(creation_de_compte.this, login.class);
////                        startActivity(intent);
////                        finish();
////                    } else {
////                        Toast.makeText(creation_de_compte.this, "Registration Failed", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////        });
//
//    }
//}




package com.example.itcompanies;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {

    private EditText editTextNom , editTextEmail, editTextPassword;
    private Button registerButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextNom = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        dbHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = editTextNom.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (nom.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(signup.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else {
                    // Vérifier si l'email existe déjà
                    if (dbHelper.isEmailExists(email)) {
                        Toast.makeText(signup.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    } else {

                        long result = dbHelper.addUser(nom , email, password);
                        if (result != -1) {
                            Toast.makeText(signup.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(signup.this, login.class);
                            intent.putExtra("role", "user"); // Passer le rôle pour la connexion
                            startActivity(intent);
                            finish(); // Fermer l'activité actuelle
                        } else {
                            Toast.makeText(signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}