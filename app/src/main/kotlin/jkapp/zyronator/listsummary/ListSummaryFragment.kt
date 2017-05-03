package jkapp.zyronator.listsummary

import android.app.ListFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import jkapp.zyronator.ListListener

class ListSummaryFragment : ListFragment()
{
    private var _listener: ListListener? = null

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        this._listener = context as ListListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context,
                android.R.layout.simple_list_item_1, arrayListOf<String>())

        listAdapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
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

