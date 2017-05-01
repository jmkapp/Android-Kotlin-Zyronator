package jkapp.zyronator.listdetails


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jkapp.zyronator.R
import jkapp.zyronator.listdetails.ListOfDetails

class ListDetailsFragment : Fragment()
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart()
    {
        super.onStart()
        val view = view
        if (view != null)
        {
            val title = view.findViewById(R.id.textTitle) as TextView
            val description = view.findViewById(R.id.textDescription) as TextView

            val listOfDetaile = ListOfDetails().listOfDetails
            val listDetails = listOfDetaile.get(_listId.toInt())

            title.setText(listDetails.displayTitle)
            description.setText(listDetails.comment)
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

}// Required empty public constructor
