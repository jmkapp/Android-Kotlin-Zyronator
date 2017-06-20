package jkapp.zyronator.discogs.discogs_user_lists

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.ListListener

import jkapp.zyronator.R
import jkapp.zyronator.discogs.discogs_user_list_items.DisplayDiscogsUserListItemsActivity
//import jkapp.zyronator.discogs.discogs_user_list_items.ListDetailsApiCall
import jkapp.zyronator.discogs.discogs_user_list_items.ListDetailsApiResponse
import jkapp.zyronator.discogs.discogs_user_lists.data.DiscogsUserListsApiData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DiscogsUserListsActivity : AppCompatActivity(), ListListener, DiscogsUserListsApiCallback
{
    private val _summaryTag = "summaryFragment"
    private val _summaryFragment = DiscogsUserListsFragment()
    private var _currentDetailsList = listOf<jkapp.zyronator.discogs.discogs_user_list_items.ListItem>()
    private val _savedList = "savedlist"
    private var _doubleContainer = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_summary)

        _doubleContainer = findViewById(R.id.summary_fragment_container_left) != null

        val containerViewId = if(_doubleContainer) R.id.summary_fragment_container_left else R.id.summary_fragment_container

        val fragmentManager = this.fragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerViewId, _summaryFragment, _summaryTag)
        transaction.commit()

        if(savedInstanceState != null)
        {
            val list : ArrayList<jkapp.zyronator.discogs.discogs_user_list_items.ListItem> = savedInstanceState.getParcelableArrayList(_savedList)
            _currentDetailsList = list.toList()
        }
        else
        {
            val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
            val discogsUser = getString(R.string.discogs_default_user_name)

            val apiCall = DiscogsUserListsApiCall(activity = this, userAgent = userAgent, discogsUserName = discogsUser)
            apiCall.execute()
        }
    }

    internal fun summaryApiResponse(response: Response<DiscogsUserListsApiData>)
    {
        if(response.isSuccessful)
        {
            val lists = response.body().lists
            val summaryFragment = fragmentManager.findFragmentByTag(_summaryTag) as DiscogsUserListsFragment
            summaryFragment.setData(lists)
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    internal fun summaryApiCallFailed(call: Call<DiscogsUserListsApiData>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun itemClicked(listId: Long)
    {
        if(_doubleContainer)
        {
            val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
            val baseUrl = getString(R.string.discogs_api_url)

            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            //val listDetailsApi = retrofit.create(ListDetailsApiCall::class.java)
            //val listDetailsApiCall : Call<ListDetailsApiResponse> = listDetailsApi.getListDetailsCall(_summaryFragment.getListId(listId), userAgent)
            //listDetailsApiCall.enqueue(this)
        }
        else
        {
            val intent = Intent(this, DisplayDiscogsUserListItemsActivity::class.java)
            intent.putExtra(DisplayDiscogsUserListItemsActivity.EXTRA_LIST_ID, _summaryFragment.getListId(listId))
            startActivity(intent)
        }
    }

    override fun discogsUserListsApiResponse(call: Call<DiscogsUserListsApiData>, response: Response<DiscogsUserListsApiData>)
    {
        if(response.isSuccessful)
        {
//            val listDetails = response.body().lists
//            _currentDetailsList = listDetails.toList()
//
//            val _detailsFragment = DiscogsUserListItemsFragment()
//            val ft = fragmentManager.beginTransaction()
//            ft.replace(R.id.summary_fragment_container_right, _detailsFragment)
//            ft.addToBackStack(null)
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            ft.commit()
//
//            _detailsFragment.setData(_currentDetailsList)
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun discogsUserListsApiCallFailed(call: Call<DiscogsUserListsApiData>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_savedList, ArrayList<jkapp.zyronator.discogs.discogs_user_list_items.ListItem>(_currentDetailsList))
        super.onSaveInstanceState(outState)
    }
}
