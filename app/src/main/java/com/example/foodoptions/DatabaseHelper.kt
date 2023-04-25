package com.example.foodoptions

import android.app.DownloadManager.COLUMN_ID
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "restaurants.db"

        const val TABLE_CATEGORIES = "categories"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_CATEGORY_NAME = "category_name"

        const val TABLE_RESTAURANTS = "restaurants"
        const val COLUMN_RESTAURANT_ID = "restaurant_id"
        const val COLUMN_RESTAURANT_NAME = "restaurant_name"
        const val COLUMN_RESTAURANT_DESCRIPTION = "restaurant_description"


        const val COLUMN_RESTAURANT_LATITUDE = "latitude"
        const val COLUMN_RESTAURANT_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoriesTable = "CREATE TABLE $TABLE_CATEGORIES (" +
                "$COLUMN_CATEGORY_ID INTEGER PRIMARY KEY," +
                "$COLUMN_CATEGORY_NAME TEXT)"

        val createRestaurantsTable = "CREATE TABLE $TABLE_RESTAURANTS (" +
                "$COLUMN_RESTAURANT_ID INTEGER PRIMARY KEY," +
                "$COLUMN_RESTAURANT_NAME TEXT," +
                "$COLUMN_RESTAURANT_DESCRIPTION TEXT," +
                "$COLUMN_CATEGORY_ID INTEGER," +
                "$COLUMN_RESTAURANT_LATITUDE REAL," + // Add latitude column
                "$COLUMN_RESTAURANT_LONGITUDE REAL," + // Add longitude column
                "FOREIGN KEY($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORIES($COLUMN_CATEGORY_ID))"

        db.execSQL(createCategoriesTable)
        db.execSQL(createRestaurantsTable)
        initializeData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RESTAURANTS")
        onCreate(db)
    }

    private fun initializeData(db: SQLiteDatabase) {
        // Add categories
        val categories = arrayOf("American", "Mexican", "Greek / Mediterranean", "Asian")
        for (category in categories) {
            val values = ContentValues()
            values.put(COLUMN_CATEGORY_NAME, category)
            db.insert(TABLE_CATEGORIES, null, values)
        }

        // Add restaurants
        val restaurants = arrayOf(
            arrayOf("McDonald's", "A fast-food giant known for its burgers and fries. They also serve breakfast items, chicken sandwiches, and desserts.", 1, 42.505270, -83.106250),
            arrayOf("Buffalo Wild Wings", "A casual dining restaurant and sports bar specializing in wings. They offer a variety of sauces and seasonings, as well as burgers, wraps, and salads.", 1, 42.486060, -83.144520),
            arrayOf("Applebee's", "A popular American chain restaurant with a diverse menu. They offer appetizers, steaks, pasta, and a variety of signature dishes.", 1, 42.446289, -83.120300),
            arrayOf("Red Robin", "A family-friendly restaurant known for its gourmet burgers. They also serve sandwiches, salads, and bottomless fries.", 1, 42.526680, -83.107550),
            arrayOf("Chipotle", "A Mexican fast-casual eatery offering customizable burritos, tacos, and bowls. They focus on fresh, high-quality ingredients and sustainable practices.", 2, 42.489460, -83.147000),
            arrayOf("Qdoba", "A fast-casual Mexican chain known for its made-to-order burritos, tacos, and nachos. They also offer a variety of toppings and salsas.", 2, 42.516070, -83.183330),
            arrayOf("Taco Bell", "A fast-food chain specializing in tacos, burritos, and other Tex-Mex dishes. They are known for their creative and budget-friendly menu items.", 2, 42.490520, -83.125360),
            arrayOf("Moe’s Southwest Grill", "A fast-casual restaurant offering customizable Mexican dishes like burritos, tacos, and quesadillas. They provide a friendly atmosphere with a wide variety of toppings.", 2, 42.244820, -83.757650),
            arrayOf("Mr. Kabob", "A Mediterranean restaurant serving a variety of kabobs, shawarma, and other Middle Eastern dishes. Their menu also includes vegetarian options and fresh salads.", 3, 42.561740, -83.175250),
            arrayOf("KouZina", "A fast-casual Greek street food restaurant offering customizable pitas, bowls, and salads. They use fresh ingredients and serve a variety of house-made sauces.", 3, 42.490530, -83.144690),
            arrayOf("Princess Mediterranean", "A family-owned Mediterranean restaurant known for its authentic dishes. They serve a variety of kebabs, gyros, and other traditional Middle Eastern cuisine.", 3, 42.515950, -83.185220),
            arrayOf("Red Olive", "A Mediterranean-American fusion restaurant offering a diverse menu. They serve a mix of traditional Mediterranean dishes, as well as burgers, sandwiches, and breakfast items.", 3, 42.533560, -83.164290),
            arrayOf("Panda Express", "A fast-food chain specializing in American Chinese cuisine. They serve dishes like Orange Chicken, Kung Pao Chicken, and Chow Mein.", 4, 42.503920, -83.118690),
            arrayOf("China King", "A Chinese take-out restaurant offering a wide variety of dishes. They serve classics like General Tso's Chicken, Sweet and Sour Pork, and a variety of fried rice options.", 4, 42.523830, -82.945060),
            arrayOf("Wing Hong Express", "A Chinese take-out restaurant offering a variety of dishes like lo mein, fried rice, and chop suey. They also serve a selection of appetizers and soups.", 4, 42.590290, -83.447510),
            arrayOf("Boon Kai", "A family-owned Chinese restaurant known for its authentic cuisine. They offer a variety of dishes, including stir-fries, noodle dishes, and traditional Chinese favorites.", 4, 42.543390, -83.477220)
        )

        for (restaurant in restaurants) {
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_RESTAURANT_NAME, restaurant[0] as String)
                put(DatabaseHelper.COLUMN_RESTAURANT_DESCRIPTION, restaurant[1] as String)
                put(DatabaseHelper.COLUMN_CATEGORY_ID, restaurant[2] as Int)
                put(DatabaseHelper.COLUMN_RESTAURANT_LATITUDE, restaurant[3] as Double) // Add latitude
                put(DatabaseHelper.COLUMN_RESTAURANT_LONGITUDE, restaurant[4] as Double) // Add longitude
            }
            db.insert(DatabaseHelper.TABLE_RESTAURANTS, null, values)
        }
    }
}