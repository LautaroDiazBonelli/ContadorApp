package com.example.contador

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cargar data
        loadData()

        //Buscar por id
        val masUnoButton : Button = findViewById(R.id.btnMasUno)
        val menosUnoButton : Button = findViewById(R.id.btnMenosUno)
        val contadorTextView : TextView = findViewById(R.id.tvContador)
        val fechaTextView : TextView = findViewById(R.id.tvFecha)

        //Conseguir la fecha del user
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm")

        //funcionalidad botones
        masUnoButton.setOnClickListener {
            contadorTextView.text = (contadorTextView.text.toString().toInt()+1).toString()
            val currentDateAndTime: String = simpleDateFormat.format(Date())
            fechaTextView.text = currentDateAndTime
            fechaTextView.setTextColor(Color.GRAY)
            saveData()
        }

        menosUnoButton.setOnClickListener {
            if (contadorTextView.text.toString().toInt()>0){
                contadorTextView.text = (contadorTextView.text.toString().toInt()-1).toString()
                val currentDateAndTime: String = simpleDateFormat.format(Date())
                fechaTextView.text = currentDateAndTime
                fechaTextView.setTextColor(Color.RED)
                }
            saveData()
        }

    }

    private fun saveData(){
        //textviews
        val contadorTextView : TextView = findViewById(R.id.tvContador)
        val fechaTextView : TextView = findViewById(R.id.tvFecha)

        //save and edit preferences
        val sharedPreferences  = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("contador_key",contadorTextView.text.toString())
            putString("fecha_key", fechaTextView.text.toString())
            if (fechaTextView.currentTextColor == Color.GRAY){
                putBoolean("fechaColor_key",true)
            }else{
                putBoolean("fechaColor_key",false)
            }
        }.apply()
    }

    private fun loadData(){
        //get data
        val sharedPreferences  = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedContador : String? = sharedPreferences.getString("contador_key", "0")
        val savedFecha : String? = sharedPreferences.getString("fecha_key", null)
        val savedFechaColor : Boolean = sharedPreferences.getBoolean("fechaColor_key", true)

        //put data in text views
        val contadorTextView : TextView = findViewById(R.id.tvContador)
        val fechaTextView : TextView = findViewById(R.id.tvFecha)

        contadorTextView.text = savedContador
        fechaTextView.text = savedFecha
        if (savedFechaColor){
            fechaTextView.setTextColor(Color.GRAY)
        }else{
            fechaTextView.setTextColor(Color.RED)
        }
    }
}