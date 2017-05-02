package jkapp.zyronator.listdetails


import android.os.Bundle
import android.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class ListDetailsFragment : ListFragment()
{
    private val _listIdString = "listid"
    private var _listId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null)
        {
            _listId = savedInstanceState.getLong(_listIdString)
        }
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
        super.onActivityCreated(savedInstanceState)
    }

    //redundant now
    override fun onStart()
    {
        super.onStart()
        val view = view
        if (view != null)
        {
           // val title = view.findViewById(R.id.textTitle) as TextView
            //val description = view.findViewById(R.id.textDescription) as TextView

            val listOfDetaile = ListOfDetails().listOfDetails
            val listDetails = listOfDetaile.get(_listId.toInt())

            //title.setText(listDetails.display_title)
            //description.setText(listDetails.comment)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        savedInstanceState.putLong(_listIdString, _listId)
    }

    public fun setListId(id : Long)
    {
        _listId = id
    }

    public fun setData(data : java.util.ArrayList<jkapp.zyronator.listdetails.ListItem>)
    {
        val names = ArrayList<String>();
        for(listItem : ListItem in data)
        {
            names.add(listItem.display_title)
        }

        val adapter : ArrayAdapter<String> = listAdapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(names)
        adapter.notifyDataSetChanged()
    }
}
