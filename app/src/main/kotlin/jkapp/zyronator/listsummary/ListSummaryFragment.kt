package jkapp.zyronator.listsummary

import android.app.ListFragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import jkapp.zyronator.listdetails.ListOfDetails

class ListSummaryFragment : ListFragment()
{
    private var _listener: ListListener? = null

    override fun onAttach(context: Context)
    {
        Log.i(ListSummaryFragment::class.java.simpleName, "onAttach()")
        super.onAttach(context)
        this._listener = context as ListListener
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.i(ListSummaryFragment::class.java.simpleName, "onCreate()")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context,
                android.R.layout.simple_list_item_1, arrayListOf<String>())

        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        Log.i(ListSummaryFragment::class.java.simpleName, "onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart()
    {
        Log.i(ListSummaryFragment::class.java.simpleName, "onStart()")
        super.onStart()
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long)
    {
        if (_listener != null)
        {
            _listener!!.itemClicked(id)
        }
    }

    public fun setData(data : java.util.ArrayList<jkapp.zyronator.listsummary.List>)
    {
        val names = ArrayList<String>();
        for(list : List in data)
        {
            names.add(list.name)
        }

        val adapter : ArrayAdapter<String> = listAdapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(names)
        adapter.notifyDataSetChanged()
    }
}

