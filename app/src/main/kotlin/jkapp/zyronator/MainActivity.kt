package jkapp.zyronator

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import jkapp.zyronator.listdetails.ListDetailsActivity
import jkapp.zyronator.listdetails.ListDetailsFragment
import jkapp.zyronator.listsummary.ListListener

class MainActivity : AppCompatActivity(), ListListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun itemClicked(listId: Long)
    {
        val fragmentContainer = findViewById(R.id.fragment_container)
        if(fragmentContainer != null)
        {
            val listDetails = ListDetailsFragment()
            val ft = fragmentManager.beginTransaction()
            listDetails.setListId(listId)
            ft.replace(R.id.fragment_container, listDetails)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }
        else
        {
            val intent = Intent(this, ListDetailsActivity::class.java)
            intent.putExtra(ListDetailsActivity.EXTRA_LIST_ID, listId)
            startActivity(intent)
        }
    }
}
