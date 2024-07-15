package com.example.expensemanager.views.fragments;

import static com.example.expensemanager.utils.Constants.SELECTED_STATS_TYPE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.expensemanager.R;
import com.example.expensemanager.databinding.FragmentStatsBinding;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.viewmodels.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class StatsFragment extends Fragment {


    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentStatsBinding binding;
    public MainViewModel viewModel;
    Calendar calendar;
    /*
    0 = daily
    1 = monthly
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updateDate();

        binding.leftDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();
            }
        });

        binding.rightDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                } else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, 1);
                }
                updateDate();
            }
        });

        binding.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.income.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expense.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.income.setTextColor(getContext().getColor(R.color.textGreen));
                binding.expense.setTextColor(getContext().getColor(R.color.black));


                SELECTED_STATS_TYPE = Constants.INCOME;
                updateDate();
            }
        });

        binding.expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.expense.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.income.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expense.setTextColor(getContext().getColor(R.color.red));
                binding.income.setTextColor(getContext().getColor(R.color.black));

                SELECTED_STATS_TYPE = Constants.EXPENSE;
                updateDate();
            }
        });


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB_STATS = 1;
                    updateDate();
                } else if (tab.getText().equals("Daily")) {
                    Constants.SELECTED_TAB_STATS = 0;
                    updateDate();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        Pie pie = AnyChart.pie();

        viewModel.categoriesTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {

                if (transactions.size()>0){
                    List<DataEntry> data = new ArrayList<>();
                    binding.emptyTransactionList.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);

                    Map<String, Double> categoryMap = new HashMap<>();
                    for (Transaction transaction : transactions) {
                        String category = transaction.getCategory();
                        double amount = transaction.getAmount();

                        if (categoryMap.containsKey(category)) {
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal+= Math.abs(amount);
                            categoryMap.put(category, currentTotal);
                        }else{
                            categoryMap.put(category, Math.abs(amount));
                        }
                    }

                    for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
                        data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
                    }
                    pie.data(data);

                }else{
                    binding.emptyTransactionList.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);


//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Category channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

        binding.anyChart.setChart(pie);


        return binding.getRoot();
    }
    void updateDate(){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM , yyyy");

        if (Constants.SELECTED_TAB_STATS == Constants.DAILY){
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        }else if (Constants.SELECTED_TAB_STATS == Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);

    }
}