package jkapp.zyronator.fragments

import android.os.Bundle
import android.app.ListFragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import jkapp.zyronator.data.discogs.ListItem

class DiscogsUserListItemsFragment : ListFragment()
{
    private val _listIdString = "listid"
    private val _listsTag = "names"
    private var _listId : Long = 0
    private var _listener: ListListener? = null
    private var _listOfItems = listOf<ListItem>()

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        this._listener = context as ListListener
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null)
        {
            _listId = savedInstanceState.getLong(_listIdString)
            val arrayList : ArrayList<ListItem> = savedInstanceState.getParcelableArrayList(_listsTag)
            _listOfItems = arrayList.toList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context,
                android.R.layout.simple_list_item_1, arrayListOf<String>())

        listAdapter = adapter

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

    fun setData(data : kotlin.collections.List<ListItem>)
    {
        _listOfItems = data
        refresh()
    }

    private fun refresh()
    {
        val names = mutableListOf<String>()
        for(listItem : ListItem in _listOfItems)
        {
            names.add(listItem.display_title)
        }

        if(listAdapter != null && _listOfItems.isNotEmpty())
        {
            val adapter : ArrayAdapter<String> = listAdapter as ArrayAdapter<String>
            adapter.clear()
            adapter.addAll(names)
            adapter.notifyDataSetChanged()
        }
    }

    fun getReleaseId(listIndex : Long) : String
    {
        return _listOfItems.get(listIndex.toInt()).id
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        savedInstanceState.putLong(_listIdString, _listId)
        savedInstanceState.putParcelableArrayList(_listsTag, ArrayList<ListItem>(_listOfItems))
        super.onSaveInstanceState(savedInstanceState)
    }
}
