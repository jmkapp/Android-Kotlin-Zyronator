package jkapp.zyronator.list.details

import android.os.Bundle
import android.app.ListFragment
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import jkapp.zyronator.ListListener

class ListDetailsFragment : ListFragment()
{
    private val _listIdString = "listid"
    private val _listsTag = "names"
    private var _listId : Long = 0
    private var _listener: ListListener? = null
    private var _listOfItems = listOf<jkapp.zyronator.list.details.ListItem>()

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
            _listOfItems = savedInstanceState.getParcelableArrayList(_listsTag)
        }
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

//    override fun onActivityCreated(savedInstanceState: Bundle?)
//    {
//        super.onActivityCreated(savedInstanceState)
//    }
//
//    override fun onStart()
//    {
//        super.onStart()
//        refresh()
//    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long)
    {
        if (_listener != null)
        {
            _listener!!.itemClicked(id)
        }
    }

    fun setData(data : kotlin.collections.List<jkapp.zyronator.list.details.ListItem>)
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

    fun getListId(listIndex : Long) : String
    {
        return _listOfItems.get(listIndex.toInt()).id
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        savedInstanceState.putLong(_listIdString, _listId)
        savedInstanceState.putParcelableArrayList(_listsTag, ArrayList<jkapp.zyronator.list.details.ListItem>(_listOfItems))
        super.onSaveInstanceState(savedInstanceState)
    }
}
