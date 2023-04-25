package com.example.foodoptions

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodoptions.databinding.ActivityRestaurantDetailBinding

class RestaurantDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = intent.getStringExtra("restaurant")
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_RESTAURANTS} WHERE ${DatabaseHelper.COLUMN_RESTAURANT_NAME} = ?",
            arrayOf(restaurant)
        )

        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RESTAURANT_NAME))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RESTAURANT_DESCRIPTION))
        val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RESTAURANT_LATITUDE))
        val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RESTAURANT_LONGITUDE))
        cursor.close()

        val nameTextView = findViewById<TextView>(R.id.restaurantName)
        val descriptionTextView = findViewById<TextView>(R.id.restaurantDescription)
        nameTextView.text = name
        descriptionTextView.text = description

        val showOnMapButton = findViewById<Button>(R.id.btn_show_map)
        showOnMapButton.setOnClickListener {
            // Start the map activity with the latitude and longitude
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            startActivity(intent)
        }
    }
}




