package com.example.healthyandfoodclean.entities
import com.google.gson.annotations.SerializedName

//khoi tao database cho repponsse
data class MealResponse(
    @SerializedName("meals")
    val mealsEntity: List<MealsEntity>
)

data class MealsEntity(
    @SerializedName("dateModified")
    val dateModified: Any, // null
    @SerializedName("idMeal")
    val idMeal: String, // 52772
    @SerializedName("strArea")
    val strArea: String, // Japanese
    @SerializedName("strCategory")
    val strCategory: String, // Chicken
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: Any, // null
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: Any, // null
    @SerializedName("strImageSource")
    val strImageSource: Any, // null
    @SerializedName("strIngredient1")
    val strIngredient1: String, // soy sauce
    @SerializedName("strIngredient10")
    val strIngredient10: String,
    @SerializedName("strIngredient11")
    val strIngredient11: String,
    @SerializedName("strIngredient12")
    val strIngredient12: String,
    @SerializedName("strIngredient13")
    val strIngredient13: String,
    @SerializedName("strIngredient14")
    val strIngredient14: String,
    @SerializedName("strIngredient15")
    val strIngredient15: String,
    @SerializedName("strIngredient16")
    val strIngredient16: Any, // null
    @SerializedName("strIngredient17")
    val strIngredient17: Any, // null
    @SerializedName("strIngredient18")
    val strIngredient18: Any, // null
    @SerializedName("strIngredient19")
    val strIngredient19: Any, // null
    @SerializedName("strIngredient2")
    val strIngredient2: String, // water
    @SerializedName("strIngredient20")
    val strIngredient20: Any, // null
    @SerializedName("strIngredient3")
    val strIngredient3: String, // brown sugar
    @SerializedName("strIngredient4")
    val strIngredient4: String, // ground ginger
    @SerializedName("strIngredient5")
    val strIngredient5: String, // minced garlic
    @SerializedName("strIngredient6")
    val strIngredient6: String, // cornstarch
    @SerializedName("strIngredient7")
    val strIngredient7: String, // chicken breasts
    @SerializedName("strIngredient8")
    val strIngredient8: String, // stir-fry vegetables
    @SerializedName("strIngredient9")
    val strIngredient9: String, // brown rice
    @SerializedName("strInstructions")
    val strInstructions: String, // Preheat oven to 350° F. Spray a 9x13-inch baking pan with non-stick spray.Combine soy sauce, ½ cup water, brown sugar, ginger and garlic in a small saucepan and cover. Bring to a boil over medium heat. Remove lid and cook for one minute once boiling.Meanwhile, stir together the corn starch and 2 tablespoons of water in a separate dish until smooth. Once sauce is boiling, add mixture to the saucepan and stir to combine. Cook until the sauce starts to thicken then remove from heat.Place the chicken breasts in the prepared pan. Pour one cup of the sauce over top of chicken. Place chicken in oven and bake 35 minutes or until cooked through. Remove from oven and shred chicken in the dish using two forks.*Meanwhile, steam or cook the vegetables according to package directions.Add the cooked vegetables and rice to the casserole dish with the chicken. Add most of the remaining sauce, reserving a bit to drizzle over the top when serving. Gently toss everything together in the casserole dish until combined. Return to oven and cook 15 minutes. Remove from oven and let stand 5 minutes before serving. Drizzle each serving with remaining sauce. Enjoy!
    @SerializedName("strMeal")
    val strMeal: String, // Teriyaki Chicken Casserole
    @SerializedName("strMealThumb")
    val strMealThumb: String, // https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg
    @SerializedName("strMeasure1")
    val strMeasure1: String, // 3/4 cup
    @SerializedName("strMeasure10")
    val strMeasure10: String,
    @SerializedName("strMeasure11")
    val strMeasure11: String,
    @SerializedName("strMeasure12")
    val strMeasure12: String,
    @SerializedName("strMeasure13")
    val strMeasure13: String,
    @SerializedName("strMeasure14")
    val strMeasure14: String,
    @SerializedName("strMeasure15")
    val strMeasure15: String,
    @SerializedName("strMeasure16")
    val strMeasure16: Any, // null
    @SerializedName("strMeasure17")
    val strMeasure17: Any, // null
    @SerializedName("strMeasure18")
    val strMeasure18: Any, // null
    @SerializedName("strMeasure19")
    val strMeasure19: Any, // null
    @SerializedName("strMeasure2")
    val strMeasure2: String, // 1/2 cup
    @SerializedName("strMeasure20")
    val strMeasure20: Any, // null
    @SerializedName("strMeasure3")
    val strMeasure3: String, // 1/4 cup
    @SerializedName("strMeasure4")
    val strMeasure4: String, // 1/2 teaspoon
    @SerializedName("strMeasure5")
    val strMeasure5: String, // 1/2 teaspoon
    @SerializedName("strMeasure6")
    val strMeasure6: String, // 4 Tablespoons
    @SerializedName("strMeasure7")
    val strMeasure7: String, // 2
    @SerializedName("strMeasure8")
    val strMeasure8: String, // 1 (12 oz.)
    @SerializedName("strMeasure9")
    val strMeasure9: String, // 3 cups
    @SerializedName("strSource")
    val strSource: Any, // null
    @SerializedName("strTags")
    val strTags: String, // Meat,Casserole
    @SerializedName("strYoutube")
    val strYoutube: String // https://www.youtube.com/watch?v=4aZr5hZXP_s
)