package com.example.foodoptions

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.foodoptions.databinding.ActivityOptionsBinding
import android.view.Menu
import android.view.MenuItem
import kotlin.random.Random


class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category") ?: ""
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_RESTAURANTS} WHERE ${DatabaseHelper.COLUMN_CATEGORY_ID} = (SELECT ${DatabaseHelper.COLUMN_CATEGORY_ID} FROM ${DatabaseHelper.TABLE_CATEGORIES} WHERE ${DatabaseHelper.COLUMN_CATEGORY_NAME} = ?)", arrayOf(category))

        val restaurantList = ArrayList<String>()

        if (cursor.moveToFirst()) { // Move the cursor to the first row before accessing the data
            do {
                val columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT_NAME)
                if (columnIndex != -1) {
                    val restaurantName = cursor.getString(columnIndex)
                    restaurantList.add(restaurantName)
                }
            } while (cursor.moveToNext())
        }

        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurantList)
        binding.listViewOptions.adapter = adapter

        binding.listViewOptions.setOnItemClickListener { _, _, position, _ ->
            val restaurant = restaurantList[position]
            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra("restaurant", restaurant)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_random_restaurant -> {
                goToRandomRestaurant()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun goToRandomRestaurant() {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_RESTAURANTS} WHERE ${DatabaseHelper.COLUMN_CATEGORY_ID} = (SELECT ${DatabaseHelper.COLUMN_CATEGORY_ID} FROM ${DatabaseHelper.TABLE_CATEGORIES} WHERE ${DatabaseHelper.COLUMN_CATEGORY_NAME} = ?)", arrayOf(category))

        if (cursor.count > 0) {
            val randomIndex = Random.nextInt(cursor.count)
            cursor.moveToPosition(randomIndex)
            val restaurantName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RESTAURANT_NAME))
            cursor.close()

            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra("restaurant", restaurantName)
            startActivity(intent)
        } else {
            cursor.close()
        }
    }

}