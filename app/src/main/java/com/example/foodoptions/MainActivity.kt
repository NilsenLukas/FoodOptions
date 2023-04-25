package com.example.foodoptions

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.foodoptions.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAmerican.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            intent.putExtra("category", "American")
            startActivity(intent)
        }

        binding.btnMexican.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            intent.putExtra("category", "Mexican")
            startActivity(intent)
        }

        binding.btnGreek.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            intent.putExtra("category", "Greek / Mediterranean")
            startActivity(intent)
        }

        binding.btnAsian.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            intent.putExtra("category", "Asian")
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_RESTAURANTS}", null)

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