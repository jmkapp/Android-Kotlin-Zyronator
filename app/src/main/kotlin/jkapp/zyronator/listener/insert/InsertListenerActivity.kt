package jkapp.zyronator.listener.insert

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import jkapp.zyronator.R

class InsertListenerActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val button = findViewById(R.id.insert_button)
        Log.i("Text", "Text")
    }

    fun insertMixName(view: View)
    {
        val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
        val baseUrl = getString(R.string.base_listener_url)


    }

}
