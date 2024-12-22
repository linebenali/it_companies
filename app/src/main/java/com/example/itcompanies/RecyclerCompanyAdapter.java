
//bech ya3mel liste des companies fel home page en utilisant item_company_card design.

package com.example.itcompanies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerCompanyAdapter extends RecyclerView.Adapter<RecyclerCompanyAdapter.CompanyViewHolder> {
    private final Context context;
    private final ArrayList<Company> companies;
    private final OnItemClickListener listener;

    // Interface pour gérer les clics sur les éléments
    public interface OnItemClickListener {
        void onItemClick(Company company);
    }

    // Constructeur de l'adaptateur
    public RecyclerCompanyAdapter(Context context, ArrayList<Company> companies, OnItemClickListener listener) {
        this.context = context;
        this.companies = companies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Charger le layout pour chaque élément
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_card, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companies.get(position);

        // Afficher le nom de l'entreprise
        holder.name.setText(company.getName());

        // Convertir le blob en Bitmap et afficher l'image
        Bitmap bitmap = getImageFromBlob(company.getImage());
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        } else {
            // Image par défaut en cas d'erreur ou d'absence d'image
            holder.image.setImageResource(R.drawable.placeholder);
        }

        // Gestion des clics sur un élément
        holder.itemView.setOnClickListener(v -> listener.onItemClick(company));
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    // Méthode pour convertir un blob en Bitmap
    private Bitmap getImageFromBlob(byte[] imageBlob) {
        if (imageBlob != null) {
            return BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
        }
        return null;
    }

    // Classe interne pour gérer les vues des éléments
    static class CompanyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_company);
            name = itemView.findViewById(R.id.name_company);
        }
    }
}
