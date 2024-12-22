
//package com.example.itcompanies;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class login extends AppCompatActivity {
//
//    private EditText editTextEmail, editTextPassword;
//    private TextView signupText;
//    private Button loginButton;
//    private DatabaseHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // Initialisation des vues
//        editTextEmail = findViewById(R.id.email);
//        editTextPassword = findViewById(R.id.password);
//        signupText = findViewById(R.id.signupText);
//        loginButton = findViewById(R.id.loginButton);
//
//        // Initialisation de la base de données
//        dbHelper = new DatabaseHelper(this);
//
//        // when clic 3al bouton de connexion
//        loginButton.setOnClickListener(v -> handleLogin());
//
//        // when clic 3la texte sign up
//        signupText.setOnClickListener(v -> {
//            Intent intent = new Intent(login.this, creation_de_compte.class);
//            startActivity(intent);
//        });
//    }
//
//    // Fonction bech t géri le login
//    private void handleLogin() {
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();
//
//        // Vérifier que les champs ne sont pas vides
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Vérifier les informations ta3 l'utilisateur
//        int userId = dbHelper.checkUser(email, password);
//        if (userId != -1) {
//            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
//            // Redirection vers l'interface principale
//            Intent intent = new Intent(login.this, homepage.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Toast.makeText(this, "Invalid name or password", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
//

package com.example.itcompanies;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itcompanies.DatabaseHelper;
import com.example.itcompanies.R;

public class login2 extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView signupText;
    private Button loginButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signupText = findViewById(R.id.signupText);
        loginButton = findViewById(R.id.loginButton);

        dbHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> handleLogin());

        signupText.setOnClickListener(v -> startActivity(new Intent(login2.this, signup2.class)));
    }

    // Fonction de gestion du login
    private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Log.d("LOGIN_ID", "Utilisateur ID récupéré : " +email );

        // Validation des champs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        // verifier des infos de connexion
        int userId = checkLogin(email, password);
        if (userId != -1) {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

            GlobalState.getInstance().setUtilisateurId(userId);// save globalstate id
            Log.d("GlobalState", "Utilisateur ID global : " + GlobalState.getInstance().getUtilisateurId());


            startActivity(new Intent(login2.this, MainActivity2.class));
            finish();
        } else {
            Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    // Fonction pour vérifier les informations de connexion et retourner l'ID utilisateur
    @SuppressLint("Range")
    public int checkLogin(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int userId = -1;  // Retourner -1 si la connexion échoue

        try {
            cursor = db.query(
                    DatabaseHelper.TABLE_USERS,
                    new String[]{DatabaseHelper.COLUMN_ID_user},
                    DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                    new String[]{email, password},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_user));
            }
        } catch (Exception e) {
            Log.e("LOGIN_ERROR", "Erreur lors de la vérification du login utilisateur", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId;
    }




}
