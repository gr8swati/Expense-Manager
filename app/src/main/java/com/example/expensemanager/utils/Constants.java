package com.example.expensemanager.utils;

import com.example.expensemanager.R;
import com.example.expensemanager.models.Category;

import java.util.ArrayList;

public class Constants {
    public static String EXPENSE = "EXPENSE";
    public static String INCOME = "INCOME";

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;
    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;
    public static ArrayList<Category> categories;
    public static void setCategories(){
        categories = new ArrayList<>();
        categories.add(new Category("Salary" , R.drawable.salary, R.color.green));
        categories.add(new Category("Bill" ,R.drawable.bills , R.color.red));
        categories.add(new Category("Recharge" ,R.drawable.recharge,R.color.blue));
        categories.add(new Category("Repairs" ,R.drawable.repair,R.color.blue));
        categories.add(new Category("Rent" ,R.drawable.rent , R.color.lightGreen));
        categories.add(new Category("Business" ,R.drawable.business, R.color.orange));
        categories.add(new Category("Shopping" ,R.drawable.shopping, R.color.orange));
        categories.add(new Category("People" ,R.drawable.people, R.color.yellow));
        categories.add(new Category("Investment" ,R.drawable.investment, R.color.lightBlue));
        categories.add(new Category("Loan", R.drawable.loan, R.color.lightOrange));
        categories.add(new Category("Bank", R.drawable.bank, R.color.veryBlue));
        categories.add(new Category("Travel", R.drawable.travel, R.color.veryBlue));
        categories.add(new Category("Grocery", R.drawable.grocery, R.color.brown));
        categories.add(new Category("Food", R.drawable.food, R.color.pink));
        categories.add(new Category("Other", R.drawable.newmore, R.color.pink));
    }

    public static Category getCategoryDetails(String categoryName){
        for (Category cat:
             categories) {
            if (cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }

    public static int getAccountsColor(String accountName){
        int color = 0;
        switch (accountName){
            case "Salary" :
                return R.color.green;
            case "Bill" :
                return R.color.red;
            case "Recharge" :
                return R.color.blue;
            case "Rent" :
                return R.color.lightGreen;
            case "Business" :
                return R.color.orange;
            case "Bank" :
                return R.color.veryBlue;
            case "People" :
                return R.color.yellow;
            case "Investment" :
                return R.color.lightBlue;
             case "Loan" :
                return R.color.lightOrange;
             case "Card" :
                return R.color.brown;
            default:
                return R.color.pink;
        }
    }
}
