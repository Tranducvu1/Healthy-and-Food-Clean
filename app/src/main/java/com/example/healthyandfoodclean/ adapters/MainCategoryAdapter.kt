package com.example.healthyandfoodclean.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule

import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.entities.CategoryItems


private lateinit var tv_dish_name:TextView
private lateinit var img_dish:ImageView
//hien thi dah sach ten, anh
class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {


    var listener: OnItemClickListener? = null
    var ctx: Context? = null
    var arrMainCategory = ArrayList<CategoryItems>()

    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

//gan gia tri danh sach cho category
    fun setData(arrData : List<CategoryItems>){
        arrMainCategory = arrData as ArrayList<CategoryItems>
    }
//lang nghe khi click
    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        ctx = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false)
        tv_dish_name = view.findViewById(R.id.tv_dish_name)
        img_dish = view.findViewById(R.id.img_dish)

        return RecipeViewHolder(view)
    }
//tra ve so luong item
    override fun getItemCount(): Int {
        return arrMainCategory.size
    }
//khoi tao hien thi
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        tv_dish_name.text = arrMainCategory[position].strCategory

        Glide.with(ctx!!).load(arrMainCategory[position].strCategoryThumb).into(img_dish)

        holder.itemView.rootView.setOnClickListener {

            listener!!.onClicked(arrMainCategory[position].strCategory)
        }
    }
//cap nhat danh sach hen thi
    fun setFilterList(filterList: ArrayList<CategoryItems>) {
            this.arrMainCategory = filterList
            notifyDataSetChanged()
        }

//click tung mục duoc chon
    interface OnItemClickListener{
        fun onClicked(categoryName:String)

    }
}