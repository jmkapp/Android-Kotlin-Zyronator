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
    private var _releaseId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_release, container, false)
    }

    override fun onStart()
    {
        super.onStart()
//        val view = view
//        if(view != null)
//        {
//            val titleView : TextView = view.findViewById(R.id.textTitle) as TextView
//            titleView.text = title
//            val descriptionView : TextView = view.findViewById(R.id.textDescription) as TextView
//
//        }
    }

    fun setRelease(releaseId : Long)
    {
        _releaseId = releaseId
    }

    fun setData(release : ReleaseApiCall)
    {
        val title : String = release.title
        val artist : Artist = release.artists.get(0)

        val view = view
        if(view != null)
        {
            val titleView : TextView = view.findViewById(R.id.textTitle) as TextView
            titleView.text = title
            val artistView : TextView = view.findViewById(R.id.textArtist) as TextView
            artistView.text = artist.name
        }
    }
}
