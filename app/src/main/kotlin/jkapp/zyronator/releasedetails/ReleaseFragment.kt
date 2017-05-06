package jkapp.zyronator.releasedetails

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import jkapp.zyronator.R

class ReleaseFragment : Fragment()
{
    //private var _releaseId: Long = 0
    private val _releaseTag = "rel"
    private var _release : Release? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_release, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        if(savedInstanceState != null)
        {
            _release = savedInstanceState.getParcelable(_releaseTag)
            refresh()
        }
        super.onActivityCreated(savedInstanceState)
    }

    fun setData(release : Release)
    {
        _release = release

        refresh()
    }

    private fun refresh()
    {
        val title : String = _release?.title ?: ""
        val artist : Artist = _release?.artists?.get(0) as Artist

        val view = view
        if(view != null && artist != null)
        {
            val titleView : TextView = view.findViewById(R.id.textTitle) as TextView
            titleView.text = title
            val artistView : TextView = view.findViewById(R.id.textArtist) as TextView
            artistView.text = artist.name
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        savedInstanceState.putParcelable(_releaseTag, _release)
        super.onSaveInstanceState(savedInstanceState)
    }
}
