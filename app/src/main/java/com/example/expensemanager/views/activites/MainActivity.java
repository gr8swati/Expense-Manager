package com.example.expensemanager.views.activites;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanager.adapter.TransactionAdapter;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.viewmodels.MainViewModel;
import com.example.expensemanager.views.fragments.AddTransactionFragment;
import com.example.expensemanager.R;
import com.example.expensemanager.databinding.ActivityMainBinding;
import com.example.expensemanager.views.fragments.StatsFragment;
import com.example.expensemanager.views.fragments.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

   public MainViewModel viewModel;
    Calendar calendar;
    /*
    0 = daily
    1 = monthly
    2 = calendar
    3 = summary
    4 = notes
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        binding setup
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(this, "Created by Swati", Toast.LENGTH_SHORT).show();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        mvvm setup
       viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Transactions");

        Constants.setCategories();

//        calendar setup
        calendar = Calendar.getInstance();



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, new TransactionFragment());
        fragmentTransaction.commit();


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == R.id.transaction){
                    getSupportFragmentManager().popBackStack();
                }else if (item.getItemId() == R.id.stats){
                    fragmentTransaction.replace(R.id.content, new StatsFragment());
                    fragmentTransaction.addToBackStack(null);
                }else if (item.getItemId() == R.id.adding){
                    new AddTransactionFragment().show(getSupportFragmentManager(),null);
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
                return true;
            }
        });

    }
    public void getTransactions(){
        viewModel.getTransactions(calendar);
    }

    //    top menu method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}