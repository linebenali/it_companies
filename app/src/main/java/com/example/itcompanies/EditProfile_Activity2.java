package com.example.itcompanies;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import java.io.IOException;

public class EditProfile_Activity2 extends AppCompatActivity {

    private ImageView profileImageView;
    private AppCompatEditText nameEditText, emailEditText;
    private Button saveButton;
    private DatabaseHelper dbHelper;
    private Uri selectedImageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);

        profileImageView = findViewById(R.id.imageView2);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        saveButton = findViewById(R.id.save_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        dbHelper = new DatabaseHelper(this);

        int userId = GlobalState.getInstance().getUtilisateurId();
        Log.d("Profile_Activity", "User ID récupéré : " + userId);

        if (userId != -1) {
            Log.d("Profile_Activity", "ID utilisateur valide, chargement du profil...");
            loadUserProfile(userId);
        } else {
            Log.e("Profile_Activity", "Erreur : utilisateur non identifié");
            Toast.makeText(this, "Erreur : utilisateur non identifié", Toast.LENGTH_SHORT).show();
        }

        saveButton.setOnClickListener(v -> saveUserProfile(userId));

        profileImageView.setOnClickListener(v -> openGallery());

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfile_Activity2.this, Profile_Activity2.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserProfile(int userId) {
        Log.d("Profile_Activity", "Tentative de récupération des informations pour l'utilisateur ID : " + userId);

        Cursor cursor = dbHelper.getUserById(userId);

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Profile_Activity", "Utilisateur trouvé, récupération des données...");

            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            byte[] photoBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHOTO));

            // Initialiser la variable photoBitmap à null
            Bitmap photoBitmap = null;

            // Vérification si une photo est présente dans la base de données
            if (photoBlob != null && photoBlob.length > 0) {
                photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
            } else {
                // Image par défaut si aucune photo n'est présente
                photoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_image);
            }

            // Affichage de l'image du profil
            profileImageView.setImageBitmap(photoBitmap);

            // Mise à jour des champs nom et email
            nameEditText.setText(name);
            emailEditText.setText(email);

            cursor.close();
            Log.d("Profile_Activity", "Profil utilisateur chargé avec succès.");
        } else {
            Log.e("Profile_Activity", "Aucun profil trouvé pour l'utilisateur ID : " + userId);
            Toast.makeText(this, "Aucun profil trouvé", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveUserProfile(int userId) {
        String newName = nameEditText.getText().toString();
        String newEmail = emailEditText.getText().toString();

        // Pass the ContentResolver along with the image URI to updateUserProfile
        boolean isUpdated = dbHelper.updateUserProfile(userId, newName, newEmail, getContentResolver(), selectedImageUri);

        if (isUpdated) {
            Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfile_Activity2.this, Profile_Activity2.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erreur lors de la mise à jour du profil", Toast.LENGTH_SHORT).show();
        }
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

