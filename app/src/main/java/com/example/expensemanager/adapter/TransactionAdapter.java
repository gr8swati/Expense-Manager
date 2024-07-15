package com.example.expensemanager.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.RowTransactionDetailBinding;
import com.example.expensemanager.models.Category;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.views.activites.MainActivity;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    RealmResults<Transaction> transactions;

    public TransactionAdapter(Context context, RealmResults<Transaction> transactions){
        this.context = context;
        this.transactions = transactions;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
       Transaction transaction = transactions.get(position);
       holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
       holder.binding.transactionlabel.setText(transaction.getAccount());

       holder.binding.dateOfTrans.setText(Helper.formatDate(transaction.getDate()));
       holder.binding.transactionCategory.setText(transaction.getCategory());

       holder.binding.transactionlabel.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction.getAccount())));

        Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());

        if (transactionCategory != null) {
            holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
            holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));
        } else {
            Log.e("TransactionAdapter", "Transaction category is null for: " + transaction.getCategory());
        }

        if (transaction.getType().equals(Constants.INCOME)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.green));
        }else if (transaction.getType().equals(Constants.EXPENSE)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.red));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deleteDailog = new AlertDialog.Builder(context).create();
                deleteDailog.setTitle("Delete Transaction");
                deleteDailog.setMessage("Are you sure to delete this transaction ?");
                deleteDailog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity)context).viewModel.deleteTransaction(transaction);
                    }
                });

                deleteDailog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDailog.dismiss();
                    }
                });
                deleteDailog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        RowTransactionDetailBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionDetailBinding.bind(itemView);
        }
    }
}
