package jkapp.zyronator.mix.find

import android.R
import android.app.ListFragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import jkapp.zyronator.mix.mixapidata.Mix

internal class MixListFragment : ListFragment()
{
    private var _listListener : MixesListListener? = null
    private var _listEntries = listOf<Mix>()
    private val _listTag = "list"

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        _listListener = context as MixesListListener
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        if(savedInstanceState != null)
        {
            _listEntries = savedInstanceState.getParcelableArrayList<Mix>(_listTag).toList()
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(inflater.context,
                R.layout.simple_list_item_1, arrayListOf<String>())

        listAdapter = adapter as ListAdapter?

        refresh()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long)
    {
        if (_listListener != null)
        {
            val mix = _listEntries.get(id.toInt())
            _listListener!!.mixListItemClicked(mix)
        }
    }

    fun setData(resultsData : List<Mix>)
    {
        _listEntries = resultsData
        if(view != null)
        {
            refresh()
        }
    }

    private fun refresh()
    {
        val names = ArrayList<String>()
        for (mix: Mix in _listEntries)
        {
            names.add(mix.title)
        }

        val adapter: ArrayAdapter<String> = listAdapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(names)
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_listTag, java.util.ArrayList<Mix>(_listEntries))
        super.onSaveInstanceState(outState)
    }
}
