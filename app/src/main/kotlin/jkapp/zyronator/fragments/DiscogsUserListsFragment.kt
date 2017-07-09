package jkapp.zyronator.fragments

import android.app.ListFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import jkapp.zyronator.data.discogs.DiscogsUserList
import java.util.*

class DiscogsUserListsFragment : ListFragment()
{
    private var _listener: ListListener? = null
    private var _listEntries = listOf<DiscogsUserList>()
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
            _listEntries = savedInstanceState.getParcelableArrayList<DiscogsUserList>(_listTag).toList()
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

    fun setData(resultsData : kotlin.collections.List<DiscogsUserList>)
    {
        _listEntries = resultsData
        refresh()
    }

    private fun refresh()
    {
        val names = ArrayList<String>()
        for(list : DiscogsUserList in _listEntries)
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

    fun getDiscogsUserList(listIndex : Long) : DiscogsUserList
    {
        return _listEntries.get(listIndex.toInt())
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_listTag, ArrayList<DiscogsUserList>(_listEntries))
        super.onSaveInstanceState(outState)
    }
}

