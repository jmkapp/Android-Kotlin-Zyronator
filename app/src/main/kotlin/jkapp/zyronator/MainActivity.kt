package jkapp.zyronator

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.list.details.*
import jkapp.zyronator.list.summary.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class MainActivity : AppCompatActivity()
{
    private var _currentDetailsList = listOf<jkapp.zyronator.list.details.ListItem>()
    private val _savedList = "savedlist"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ListSummaryActivity::class.java)
        startActivity(intent)

        finish()
    }
}
