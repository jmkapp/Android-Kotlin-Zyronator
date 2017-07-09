package jkapp.zyronator.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import jkapp.zyronator.fragments.CurrentMixTextPressed
import jkapp.zyronator.activities.MainActivity
import jkapp.zyronator.fragments.MixButtonPressed
import jkapp.zyronator.R

class LastListenedScreenFragment() : Fragment(), View.OnClickListener
{
    private val START_NEXT_MIX_BUTTON_TAG = "startNextMix"
    private val CURRENT_MIX_TEXT_TAG = "currentMixText"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val view = inflater.inflate(R.layout.fragment_last_listened_screen, container, false)

        val lastListenedActivity = activity as MainActivity

        val currentMixNameView = view.findViewById(R.id.current_mix_text) as TextView
        currentMixNameView.text = lastListenedActivity.getCurrentMixName()
        val currentMixDateView = view.findViewById(R.id.current_mix_date) as TextView
        currentMixDateView.text = lastListenedActivity.getCurrentMixDate()

        val nextMixNameView = view.findViewById(R.id.next_mix_text) as TextView
        nextMixNameView.text = lastListenedActivity.getNextMixName()
        val nextMixDateView = view.findViewById(R.id.next_mix_date) as TextView
        nextMixDateView.text = lastListenedActivity.getNextMixDate()

        if(savedInstanceState == null)
        {
            val startNextMixButton = view.findViewById(R.id.start_next_mix_button) as Button
            startNextMixButton.setOnClickListener(this)
            startNextMixButton.tag = START_NEXT_MIX_BUTTON_TAG
            val findMixButton = view.findViewById(R.id.find_mix_button) as Button
            findMixButton.setOnClickListener(this)

            val currentMixText = view.findViewById(R.id.current_mix_text) as TextView
            currentMixText.tag = CURRENT_MIX_TEXT_TAG
            currentMixText.setOnClickListener(this)
        }

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
        val currentMixNameView = view.findViewById(R.id.current_mix_text) as TextView
        currentMixNameView.text = title
        val currentMixDateView = view.findViewById(R.id.current_mix_date) as TextView
        currentMixDateView.text = date
    }

    fun setNextMix(title : String, date: String)
    {
        val nextMixNameView = view.findViewById(R.id.next_mix_text) as TextView
        nextMixNameView.text = title
        val nextMixDateView = view.findViewById(R.id.next_mix_date) as TextView
        nextMixDateView.text = date
    }
}