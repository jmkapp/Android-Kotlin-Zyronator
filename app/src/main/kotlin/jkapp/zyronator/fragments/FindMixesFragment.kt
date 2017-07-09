package jkapp.zyronator.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import jkapp.zyronator.R
import jkapp.zyronator.activities.FindMixSearchText

internal class FindMixesFragment : Fragment(), View.OnClickListener
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view = inflater.inflate(R.layout.fragment_find_mixes, container, false)

        //initialise fragment contents
        val button = view.findViewById(R.id.search_mix_button_frag) as Button
        button.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View)
    {
        val editText = view.findViewById(R.id.search_mix_name_frag) as EditText
        val searchText = editText.text.toString()

        val activity = activity as FindMixSearchText
        activity.passSearchText(searchText)
    }
}