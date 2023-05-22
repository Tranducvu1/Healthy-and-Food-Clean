package com.example.healthyandfoodclean


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query

import com.example.healthyandfoodclean.adapters.MainCategoryAdapter
import com.example.healthyandfoodclean.adapters.SubCategoryAdapter
import com.example.healthyandfoodclean.data.RecipeDatabase
import com.example.healthyandfoodclean.entities.CategoryItems
import com.example.healthyandfoodclean.entities.Meal
import com.example.healthyandfoodclean.entities.MealsItems

import com.example.healthyandfoodclean.fragment.DetailActivity
import kotlinx.coroutines.launch


class EatCleanActivity : Base1Activity(), SearchView.OnQueryTextListener {
    private lateinit var rv_main_category: RecyclerView
    private lateinit var rv_sub_category: RecyclerView
    private lateinit var tvCategory: TextView

    private lateinit var searchview: SearchView
    private var arrMainCategory = ArrayList<CategoryItems>()
    private var arrSubCategory = ArrayList<MealsItems>()

    private val mainCategoryAdapter = MainCategoryAdapter()
    private val subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eat_clean)

        tvCategory = findViewById(R.id.tvCategory)
        rv_main_category = findViewById(R.id.rv_main_category)
        searchview = findViewById(R.id.search_view)
        rv_sub_category = findViewById(R.id.rv_sub_category)


        mainCategoryAdapter.setClickListener(onCLicked)
        subCategoryAdapter.setClickListener(onCLickedSubItem)

        getDataFromDb()

        searchview.setOnQueryTextListener(this)
    }
//lay du lieu khi click
    private val onCLicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }
//anh xa khi click mot item
    private val onCLickedSubItem  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent(this@EatCleanActivity,DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }

//phan hoi tim kiem
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }
//thuc thi tim kiem
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            filterList(newText)
        }
        return true
    }

//truy xuat danh muc JSON hien thi
    private fun getDataFromDb() {
        launch {
            val cat = RecipeDatabase.getDatabase(this@EatCleanActivity).recipeDao().getAllCategory()
            arrMainCategory = cat as ArrayList<CategoryItems>
            arrMainCategory.reverse()
            if (arrMainCategory.isNotEmpty()) {
                getMealDataFromDb(arrMainCategory[0].strCategory)
                mainCategoryAdapter.setData(arrMainCategory)
                rv_main_category.layoutManager = LinearLayoutManager(this@EatCleanActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_main_category.adapter = mainCategoryAdapter
            }
        }
    }
//truy xuat danh muc nho hien thi
    private fun getMealDataFromDb(categoryName: String) {
        tvCategory.text = "$categoryName Category"
        launch {
            val cat = RecipeDatabase.getDatabase(this@EatCleanActivity).recipeDao().getSpecificMealList(categoryName)
            arrSubCategory = cat as ArrayList<MealsItems>
            subCategoryAdapter.setData(arrSubCategory)
            rv_sub_category.layoutManager = LinearLayoutManager(this@EatCleanActivity, LinearLayoutManager.HORIZONTAL, false)
            rv_sub_category.adapter = subCategoryAdapter
        }
    }
//loc danh sach tim kiem theo ten
    private fun filterList(text: String) {
        val filterList = ArrayList<CategoryItems>()
        for (category in arrMainCategory) {
            if (category.strCategory.contains(text, ignoreCase = true)) {
                filterList.add(category)
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
        } else {
            mainCategoryAdapter.setFilterList(filterList)
        }
    }
}


