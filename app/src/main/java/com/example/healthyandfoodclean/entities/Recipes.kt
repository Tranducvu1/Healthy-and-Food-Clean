package com.example.healthyandfoodclean.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
//khoi tao database cho recipoes
@Entity(tableName = "Recipes")
data class Recipes(
        @PrimaryKey(autoGenerate = true)
        var id:Int,

        @ColumnInfo(name = "dishName")
        var dishName:String
) : Serializable