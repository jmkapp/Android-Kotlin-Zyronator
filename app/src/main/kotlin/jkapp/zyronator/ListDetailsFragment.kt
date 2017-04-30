package jkapp.zyronator


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListDetailsFragment : Fragment()
{
    private var _listId : Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_details, container, false)
    }

    override fun onStart()
    {
        super.onStart()
        val view = view
        if (view != null)
        {
            val title = view.findViewById(R.id.textTitle) as TextView
            //val workout = Workout.workouts[workoutId.toInt()]
            //title.setText(workout.name)
            title.setText("title testing 1.......")
            val description = view.findViewById(R.id.textDescription) as TextView
            //description.setText(workout.description)
            description.setText("description testing 1....")
        }
    }

    public fun setListId(id : Long)
    {
        _listId = id
    }

}// Required empty public constructor
