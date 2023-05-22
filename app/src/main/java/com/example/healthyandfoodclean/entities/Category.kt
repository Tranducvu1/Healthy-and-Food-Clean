package com.example.healthyandfoodclean.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.healthyandfoodclean.entities.converter.CategoryListConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "categoryItems")
    @Expose

    @TypeConverters(CategoryListConverter::class)
    @SerializedName("categories")
    val categorieitems: List<CategoryItems>
)
