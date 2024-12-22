package com.example.itcompanies;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddUpdateCompanyActivity extends Activity {

    private EditText nameInput, phoneInput, websiteInput, mailInput, locationInput, serviceInput;
    private ImageView companyImageView;
    private Button saveButton;
    private String selectedImagePath = null;
    private DatabaseHelper dbHelper;
    private String oldCompanyName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_company);

        nameInput = findViewById(R.id.etCompanyName);
        phoneInput = findViewById(R.id.etCompanyPhone);
        websiteInput = findViewById(R.id.etCompanyWebsite);
        mailInput = findViewById(R.id.etCompanyMail);
        locationInput = findViewById(R.id.etCompanyLocation);
        serviceInput = findViewById(R.id.etCompanyService);
        companyImageView = findViewById(R.id.ivSelectedImage);
        saveButton = findViewById(R.id.btnSaveCompany);

        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de l'entreprise
        Intent intent = getIntent();
        int companyId = intent.getIntExtra("company_id", -1);

        if (companyId != -1) {
            // Charger les données de l'entreprise depuis la base
            Company company = dbHelper.getCompanyById(companyId);

            if (company != null) {
                // Remplir les champs avec les données de l'entreprise
                nameInput.setText(company.getName());
                phoneInput.setText(company.getPhone());
                websiteInput.setText(company.getWebsite());
                mailInput.setText(company.getMail());
                locationInput.setText(company.getLocation());
                serviceInput.setText(String.join(", ", company.getServices()));

                // Convertir le blob en Bitmap et afficher l'image
                Bitmap bitmap = BitmapFactory.decodeByteArray(company.getImage(), 0, company.getImage().length);
                companyImageView.setImageBitmap(bitmap);

                // Stocker l'ancien nom pour la mise à jour
                oldCompanyName = company.getName();
            }
        }

        companyImageView.setOnClickListener(v -> selectImage());

        saveButton.setOnClickListener(v -> saveCompany());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            selectedImagePath = FileUtils.getPath(this, imageUri);

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                companyImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Image loading failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveCompany() {
        String name = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String website = websiteInput.getText().toString();
        String mail = mailInput.getText().toString();
        String location = locationInput.getText().toString();
        String serviceString = serviceInput.getText().toString();
        ArrayList<String> services = new ArrayList<>();

        // Validation des champs vides
        if (name.isEmpty() || phone.isEmpty() || website.isEmpty() || mail.isEmpty() || location.isEmpty() || serviceString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation du numéro de téléphone (exactement 8 caractères)
        if (phone.length() != 8) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!serviceString.isEmpty()) {
            String[] serviceArray = serviceString.split(",");
            for (String s : serviceArray) {
                services.add(s.trim());
            }
        }

        if (companyImageView.getDrawable() == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        companyImageView.buildDrawingCache();
        Bitmap bitmap = companyImageView.getDrawingCache();
        byte[] imageBlob = dbHelper.convertBitmapToByteArray(bitmap);

        if (oldCompanyName == null) {
            // Ajout d'une nouvelle entreprise
            Company newCompany = new Company(name, null, services, phone, website, mail, location);
            dbHelper.insertCompanyWithBlob(newCompany, imageBlob);
            Toast.makeText(this, "Company added successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Mise à jour d'une entreprise existante
            dbHelper.updateCompanyWithBlob(oldCompanyName, name, phone, website, services, mail, location, imageBlob);
            Toast.makeText(this, "Company updated successfully", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }


    public static class FileUtils {

        public static String getPath(Activity activity, Uri uri) {
            String path = null;

            // Si l'URI est de type "content" (image choisie depuis la galerie)
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    path = cursor.getString(columnIndex);
                    cursor.close();
                }
            }
            // Si l'URI est de type "file" (fichier local)
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                path = uri.getPath();
            }

            return path;
        }
    }
}
