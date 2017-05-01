package jkapp.zyronator.listdetails

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import jkapp.zyronator.R

class ListDetailsActivity : AppCompatActivity()
{
    companion object
    {
        val EXTRA_LIST_ID = "listid"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_details)

        val listDetailsFragment = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
        val listId : Long = intent.extras.get(EXTRA_LIST_ID) as Long
        listDetailsFragment.setListId(listId)
    }
}
