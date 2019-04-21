package com.karenjar.demo;

public class MainActivity extends BaseTabListAty{
    private static final String TAG = "MainActivity";
    private static final String LIST_CATEGORY_STR = "com.karenjar.demo.category.DEMO";

    @Override
    public String getListCategoryStr(){
        return LIST_CATEGORY_STR;
    }
}
