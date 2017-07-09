package jkapp.zyronator.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import jkapp.zyronator.R
import jkapp.zyronator.data.ListenerMixDetails

class MainFragment() : Fragment(), View.OnClickListener
{
    private val START_NEXT_MIX_BUTTON_TAG = "startNextMix"
    private val CURRENT_MIX_TEXT_TAG = "currentMixText"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val mainActivity = activity as ListenerMixDetails

        val currentMixNameView = view.findViewById(R.id.main_current_mix_text) as TextView
        currentMixNameView.text = mainActivity.getCurrentMixName()
        val currentMixDateView = view.findViewById(R.id.main_current_mix_date) as TextView
        currentMixDateView.text = mainActivity.getCurrentMixDate()

        val nextMixNameView = view.findViewById(R.id.main_next_mix_text) as TextView
        nextMixNameView.text = mainActivity.getNextMixName()
        val nextMixDateView = view.findViewById(R.id.main_next_mix_date) as TextView
        nextMixDateView.text = mainActivity.getNextMixDate()

        val startNextMixButton = view.findViewById(R.id.main_start_next_mix_button) as Button
        startNextMixButton.setOnClickListener(this)
        startNextMixButton.tag = START_NEXT_MIX_BUTTON_TAG
        val findMixButton = view.findViewById(R.id.main_find_mix_button) as Button
        findMixButton.setOnClickListener(this)

        val currentMixText = view.findViewById(R.id.main_current_mix_text) as TextView
        currentMixText.tag = CURRENT_MIX_TEXT_TAG
        currentMixText.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View)
    {
        if(v.tag == CURRENT_MIX_TEXT_TAG)
        {
            val activity = activity as CurrentMixTextPressed
            activity.currentMixTextPressed()
        }
        else
        {
            val activity = activity as MixButtonPressed

            if(v.tag == START_NEXT_MIX_BUTTON_TAG)
            {
                activity.startNextMixButtonPressed()
            }
            else
            {
                activity.findMixButtonPressed()
            }
        }
    }

    fun setCurrentMix(title : String, date: String)
    {
        val currentMixNameView = view.findViewById(R.id.main_current_mix_text) as TextView
        currentMixNameView.text = title
        val currentMixDateView = view.findViewById(R.id.main_current_mix_date) as TextView
        currentMixDateView.text = date
    }

    fun setNextMix(title : String, date: String)
    {
        val nextMixNameView = view.findViewById(R.id.main_next_mix_text) as TextView
        nextMixNameView.text = title
        val nextMixDateView = view.findViewById(R.id.main_next_mix_date) as TextView
        nextMixDateView.text = date
    }

//    override fun onSaveInstanceState(outState: Bundle)
//    {
//        outState.putString(CURRENT_MIX_NAME, _currentMixName)
//        outState.putString(CURRENT_MIX_DATE, _currentMixDate)
//        outState.putString(NEXT_MIX_NAME, _nextMixName)
//        outState.putString(NEXT_MIX_DATE, _nextMixDate)
//
//        super.onSaveInstanceState(outState)
//    }
}

interface MixButtonPressed
{
    fun startNextMixButtonPressed()
    fun findMixButtonPressed()
}

interface CurrentMixTextPressed
{
    fun currentMixTextPressed()
}