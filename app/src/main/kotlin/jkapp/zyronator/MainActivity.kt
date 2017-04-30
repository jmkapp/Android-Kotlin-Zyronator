package jkapp.zyronator

import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jkapp.zyronator.listsummary.ListListener

class MainActivity : AppCompatActivity(), ListListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val frag = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
        //frag.setListId(1)
    }

    override fun itemClicked(listId: Long)
    {
        val listDetails = ListDetailsFragment()
        val ft = fragmentManager.beginTransaction()
        listDetails.setListId(listId)
        ft.replace(R.id.fragment_container, listDetails)
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
    }
}
