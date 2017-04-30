package jkapp.zyronator.listsummary

import android.app.ListFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import jkapp.zyronator.listdetails.ListOfDetails

class ListSummaryFragment : ListFragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val listOfDetails = ListOfDetails()
        val names = arrayOf(listOfDetails.listOfDetails.get(0).displayTitle, listOfDetails.listOfDetails.get(1).displayTitle)

        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context, android.R.layout.simple_list_item_1, names)

        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}

