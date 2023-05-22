package com.example.healthyandfoodclean.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthyandfoodclean.R
import com.example.healthyandfoodclean.entities.MealsItems

private lateinit var tv_dish_name: TextView
private lateinit var img_dish: ImageView
//hien thi dah sach ten, anh
class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    var listener: SubCategoryAdapter.OnItemClickListener? = null
    var ctx: Context? = null
    var arrSubCategory = ArrayList<MealsItems>()

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
    //gai gia tri danh sach cho Meal
    fun setData(arrData: List<MealsItems>) {
        arrSubCategory = arrData as ArrayList<MealsItems>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_sub_category, parent, false)
        tv_dish_name = view.findViewById(R.id.tv_dish_name)
        img_dish = view.findViewById(R.id.img_dish)
        return RecipeViewHolder(view)
    }
    //tra ve so luong item
    override fun getItemCount(): Int {
        return arrSubCategory.size
    }
    //lang nghe khi click
    fun setClickListener(listener1: SubCategoryAdapter.OnItemClickListener) {
        listener = listener1
    }
    //khoi tao hien thi
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        tv_dish_name.text = arrSubCategory[position].strMeal

        Glide.with(ctx!!)
            .load(arrSubCategory[position].strMealThumb)
            .into(img_dish)

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClicked(arrSubCategory[position].idMeal)
        }
    }

    interface OnItemClickListener {
        fun onClicked(id: String)
    }
}
