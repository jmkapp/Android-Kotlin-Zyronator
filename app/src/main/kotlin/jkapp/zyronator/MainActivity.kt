package jkapp.zyronator

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.list.details.*
import jkapp.zyronator.list.summary.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class MainActivity : AppCompatActivity(), ListListener, Callback<ListDetailsApiCall>
{
    private var _currentDetailsList = listOf<jkapp.zyronator.list.details.ListItem>()
    private val _savedList = "savedlist"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null)
        {
            val list : ArrayList<jkapp.zyronator.list.details.ListItem> = savedInstanceState.getParcelableArrayList(_savedList)
            _currentDetailsList = list.toList()
        }
        else
        {
            val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
            val discogsUser = getString(R.string.default_user)
            val baseUrl = getString(R.string.base_url)

            val apiCall = SummaryApiCall(activity = this, baseUrl = baseUrl, userAgent = userAgent, discogsUser = discogsUser)
            apiCall.execute()
        }
    }

    internal fun summaryApiResponse(response: Response<ListSummaryApiCall>)
    {
        if(response.isSuccessful)
        {
            val lists = response.body().lists
            val fragmentContainer = findViewById(R.id.fragment_container)
            if(fragmentContainer != null)
            {
                val listSummaryFragment = fragmentManager.findFragmentById(R.id.summary_frag) as ListSummaryFragment
                listSummaryFragment.setData(lists)
            }
            else
            {
                val listSummaryFragment = fragmentManager.findFragmentById(R.id.list_frag) as ListSummaryFragment
                listSummaryFragment.setData(lists)
            }
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    internal fun summaryApiCallFailed(call: Call<ListSummaryApiCall>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun itemClicked(listId: Long)
    {
        val fragmentContainer = findViewById(R.id.fragment_container)
        if(fragmentContainer != null)
        {
            val listSummaryFragment = fragmentManager.findFragmentById(R.id.summary_frag) as ListSummaryFragment

            val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
            val baseUrl = getString(R.string.base_url)

            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            val listDetailsApi = retrofit.create(ListDetailsApi::class.java)
            val listDetailsApiCall : Call<ListDetailsApiCall> = listDetailsApi.getListDetailsCall(listSummaryFragment.getListId(listId), userAgent)
            listDetailsApiCall.enqueue(this)
        }
        else
        {
            val listSummaryFragment = fragmentManager.findFragmentById(R.id.list_frag) as ListSummaryFragment

            val intent = Intent(this, ListDetailsActivity::class.java)
            intent.putExtra(ListDetailsActivity.EXTRA_LIST_ID, listSummaryFragment.getListId(listId))
            startActivity(intent)
        }
    }

    override fun onResponse(call: Call<ListDetailsApiCall>, response: Response<ListDetailsApiCall>)
    {
        if(response.isSuccessful)
        {
            val listDetails = response.body().items
            _currentDetailsList = listDetails.toList()

            val _detailsFragment = ListDetailsFragment()
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, _detailsFragment)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()

            _detailsFragment.setData(_currentDetailsList)
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onFailure(call: Call<ListDetailsApiCall>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_savedList, ArrayList<jkapp.zyronator.list.details.ListItem>(_currentDetailsList))
        super.onSaveInstanceState(outState)
    }
}
