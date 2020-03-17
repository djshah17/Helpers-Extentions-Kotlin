package com.example.helperfunctionssample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apphelper.extentions.get
import com.example.apphelper.extentions.put
import com.example.apphelper.extentions.showTimePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
