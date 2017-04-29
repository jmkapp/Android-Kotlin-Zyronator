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

        try
        {
            val frag = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
            frag.setListId(1)
        }
        catch(ex : Exception)
        {
            Log.i("Test", "Test")
        }
    }
}
