package com.example.expensemanager.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.example.expensemanager.R;
import com.example.expensemanager.adapter.AccountAdapter;
import com.example.expensemanager.adapter.CategoryAdapter;
import com.example.expensemanager.databinding.FragmentAddTransactionBinding;
import com.example.expensemanager.databinding.ListDialogBinding;
import com.example.expensemanager.models.Account;
import com.example.expensemanager.models.Category;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.views.activites.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddTransactionBinding binding;
    Transaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater);

        transaction = new Transaction();

        binding.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.income.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expense.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.income.setTextColor(getContext().getColor(R.color.textGreen));
                binding.expense.setTextColor(getContext().getColor(R.color.black));

//                        fragment me click karne se data add hoga main recycler me
                transaction.setType(Constants.INCOME);
            }
        });

        binding.expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.expense.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.income.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expense.setTextColor(getContext().getColor(R.color.red));
                binding.income.setTextColor(getContext().getColor(R.color.black));

//                        fragment me click karne se data add hoga main recycler me
                transaction.setType(Constants.EXPENSE);
            }
        });

        binding.selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH , datePicker.getDayOfMonth());
                        calendar.set(Calendar.MONTH , datePicker.getMonth());
                        calendar.set(Calendar.YEAR, datePicker.getYear());

//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM , yyyy");
                        String dateToShow = Helper.formatDate(calendar.getTime());
                        binding.selectDate.setText(dateToShow);

//                        fragment me click karne se data add hoga main recycler me
                        transaction.setDate(calendar.getTime());
                        transaction.setId(calendar.getTime().getTime());
                    }
                });
                datePickerDialog.show();

            }
        });

        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);
                AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
                categoryDialog.setView(listDialogBinding.getRoot());



                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListener() {
                    @Override
                    public void onCategoryClickListener(Category category) {
                        binding.category.setText(category.getCategoryName());

//                        fragment me click karne se data add hoga main recycler me
                        transaction.setCategory(category.getCategoryName());
                        categoryDialog.dismiss();
                    }
                });
                listDialogBinding.listRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
                listDialogBinding.listRecyclerView.setAdapter(categoryAdapter);
                categoryDialog.show();
            }
        });

        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);
                AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
                accountDialog.setView(listDialogBinding.getRoot());

                ArrayList<Account> accounts = new ArrayList<>();
                accounts.add(new Account(0,"Bank"));
                accounts.add(new Account(0,"Check"));
                accounts.add(new Account(0,"Card"));
                accounts.add(new Account(0,"Cash"));
                accounts.add(new Account(0,"Google Pay"));
                accounts.add(new Account(0,"Offline Wallet( like Paytm...etc)"));
                accounts.add(new Account(0,"Paytm"));
                accounts.add(new Account(0,"PhonePe"));
                accounts.add(new Account(0,"UPI"));
                accounts.add(new Account(0,"WhatsApp"));
                accounts.add(new Account(0,"Other"));

                AccountAdapter accountAdapter = new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountClickListener() {
                    @Override
                    public void onAccountClickListener(Account account) {
                        binding.account.setText(account.getAccountName());

            //  fragment me click karne se data add hoga main recycler me
                        transaction.setAccount(account.getAccountName());
                        accountDialog.dismiss();
                    }
                });
                listDialogBinding.listRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                listDialogBinding.listRecyclerView.setAdapter(accountAdapter);
                accountDialog.show();
            }
        });

        binding.saveTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = Double.parseDouble(binding.amount.getText().toString());

                if (transaction.getType().equals(Constants.EXPENSE)){
                    transaction.setAmount(amount*-1);
                }else {
                    transaction.setAmount(amount);
                }

                ((MainActivity)getContext()).viewModel.addTransaction(transaction);
                ((MainActivity)getContext()).getTransactions();
                dismiss();
            }
        });
        return binding.getRoot();
    }
}