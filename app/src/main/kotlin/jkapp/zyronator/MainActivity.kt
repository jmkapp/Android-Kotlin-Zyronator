package jkapp.zyronator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frag = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
        frag.setListId(1)
    }
}
