package jkapp.zyronator.listsummary

import android.app.ListFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import jkapp.zyronator.listdetails.ListOfDetails

class ListSummaryFragment : ListFragment()
{
    private var _listener: ListListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val listOfDetails = ListOfDetails()
        val names = arrayOf(listOfDetails.listOfDetails.get(0).displayTitle, listOfDetails.listOfDetails.get(1).displayTitle)

        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context, android.R.layout.simple_list_item_1, names)

        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        this._listener = context as ListListener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long)
    {
        if (_listener != null)
        {
            _listener!!.itemClicked(id)
        }
    }
}

