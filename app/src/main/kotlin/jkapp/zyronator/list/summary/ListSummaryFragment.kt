package jkapp.zyronator.list.summary

import android.app.ListFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import jkapp.zyronator.ListListener
import java.util.*

class ListSummaryFragment : ListFragment()
{
    private var _listener: ListListener? = null
    private var _listEntries = listOf<jkapp.zyronator.list.summary.List>()
    private val _listTag = "list"

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        this._listener = context as ListListener
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        if(savedInstanceState != null)
        {
            _listEntries = savedInstanceState.getParcelableArrayList<jkapp.zyronator.list.summary.List>(_listTag).toList()
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context,
                android.R.layout.simple_list_item_1, arrayListOf<String>())

        listAdapter = adapter as ListAdapter?

        refresh()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long)
    {
        if (_listener != null)
        {
            _listener!!.itemClicked(id)
        }
    }

    fun setData(resultsData : kotlin.collections.List<jkapp.zyronator.list.summary.List>)
    {
        _listEntries = resultsData
        refresh()
    }

    private fun refresh()
    {
        val names = ArrayList<String>()
        for(list : List in _listEntries)
        {
            names.add(list.name)
        }

        val adapter : ArrayAdapter<String> = listAdapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(names)
        adapter.notifyDataSetChanged()
    }

    fun getListId(listIndex : Long) : String
    {
        return _listEntries.get(listIndex.toInt()).id
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_listTag, ArrayList<jkapp.zyronator.list.summary.List>(_listEntries))
        super.onSaveInstanceState(outState)
    }
}

