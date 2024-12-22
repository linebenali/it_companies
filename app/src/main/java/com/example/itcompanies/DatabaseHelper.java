package com.example.itcompanies;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "companies.db";
    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_COMPANIES = "companies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_SERVICE = "service";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_WEB = "web";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_LOC = "loc";



    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID_user = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCompaniesTable = "CREATE TABLE " + TABLE_COMPANIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " BLOB NOT NULL, " +
                COLUMN_SERVICE + " TEXT NOT NULL, " +
                COLUMN_PHONE + " TEXT NOT NULL, " +
                COLUMN_WEB + " TEXT NOT NULL, " +
                COLUMN_MAIL + " TEXT, " +
                COLUMN_LOC + " TEXT)";
        db.execSQL(createCompaniesTable);

        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID_user + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PHOTO + " BLOB , " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void insertCompanyWithBlob(Company company, byte[] imageBlob) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, company.getName());
        values.put(COLUMN_PHONE, company.getPhone());
        values.put(COLUMN_WEB, company.getWebsite());
        values.put(COLUMN_MAIL, company.getMail());
        values.put(COLUMN_LOC, company.getLocation());
        values.put(COLUMN_SERVICE, String.join(",", company.getServices()));
        values.put(COLUMN_IMAGE, imageBlob);

        long result = db.insert(TABLE_COMPANIES, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error inserting company");
        }

        db.close();
    }



    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_COMPANIES, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    byte[] imageBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    String serviceString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE));
                    ArrayList<String> service = new ArrayList<>();
                    if (serviceString != null && !serviceString.isEmpty()) {
                        String[] servicesArray = serviceString.split(",");
                        for (String s : servicesArray) {
                            service.add(s.trim());
                        }
                    }
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                    String website = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEB));
                    String mail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAIL));
                    String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOC));

                    companies.add(new Company(id, name, imageBlob, service, phone, website, mail, location));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return companies;
    }



    public Company getCompanyById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Company company = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_COMPANIES + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String website = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEB));
                String mail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAIL)); // Utilisez COLUMN_MAIL, pas COLUMN_EMAIL
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOC));

                String servicesStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE));
                ArrayList<String> services = new ArrayList<>();
                if (servicesStr != null && !servicesStr.isEmpty()) {
                    services = new ArrayList<>(Arrays.asList(servicesStr.split(",")));
                }

                company = new Company(id, name, image, services, phone, website, mail, location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return company;
    }


    public void updateCompanyWithBlob(String oldName, String newName, String phone, String website, ArrayList<String> services, String mail, String location, byte[] imageBlob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_WEB, website);
        values.put(COLUMN_MAIL, mail);
        values.put(COLUMN_LOC, location);

        // Convertir la liste de services en une chaîne
        String serviceString = String.join(", ", services);
        values.put(COLUMN_SERVICE, serviceString);

        values.put(COLUMN_IMAGE, imageBlob);

        // Mise à jour de l'entreprise par son nom
        db.update(TABLE_COMPANIES, values, COLUMN_NAME + " = ?", new String[]{oldName});
        db.close();
    }

    public void deleteCompany(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPANIES, COLUMN_ID + "=?", new String[]{id});
        db.close();
    }

    public long addUser(String nom, String email, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Récupération de l'ID
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_ID_user + ") FROM " + TABLE_USERS, null);
        int id = 1; // Valeur par défaut
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0) + 1; // Incrémente l'ID max
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_user, id);
        values.put(COLUMN_USERNAME, nom);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, motDePasse);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }


    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, new String[]{DatabaseHelper.COLUMN_EMAIL},
                DatabaseHelper.COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    public Cursor getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USERNAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PHOTO +
                " FROM " + TABLE_USERS + " WHERE " + COLUMN_ID_user + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    public boolean updateUserProfile(int userId, String newName, String newEmail, ContentResolver contentResolver, Uri imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, newName);
        contentValues.put(COLUMN_EMAIL, newEmail);

        if (imageUri != null) {
            try {
                // Convertir l'image en tableau de bytes
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] photoBlob = byteArrayOutputStream.toByteArray();
                contentValues.put(COLUMN_PHOTO, photoBlob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // mettre a jour l utilisateur dans la DB
        int result = db.update(TABLE_USERS, contentValues, COLUMN_ID_user + " = ?", new String[]{String.valueOf(userId)});
        return result > 0;
    }



}
