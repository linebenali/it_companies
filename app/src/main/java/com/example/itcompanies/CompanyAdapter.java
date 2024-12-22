// //bech ya3mel liste des companies m3a l edit w delete button en utilisant design de item_company.xml

package com.example.itcompanies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.itcompanies.AddUpdateCompanyActivity;
import com.example.itcompanies.Company;
import com.example.itcompanies.DatabaseHelper;
import com.example.itcompanies.DetailActivity;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private Context context;
    private ArrayList<Company> companies;

    public CompanyAdapter(Context context, ArrayList<Company> companies) {
        this.context = context;
        this.companies = companies;
    }
    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.companyName.setText(company.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("company_id", company.getId()); // Transmettre uniquement l'ID
            context.startActivity(intent);
        });

        // DELETE une entreprise
        holder.deleteButton.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            dbHelper.deleteCompany(String.valueOf(company.getId()));
            companies.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, companies.size());
        });

        //  EDIT une entreprise
        holder.updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddUpdateCompanyActivity.class);
            intent.putExtra("company_id", company.getId()); // Transmettre uniquement l'ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        public TextView companyName;
        public ImageView deleteButton, updateButton;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.company_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
            updateButton = itemView.findViewById(R.id.update_button);
        }
    }
}
