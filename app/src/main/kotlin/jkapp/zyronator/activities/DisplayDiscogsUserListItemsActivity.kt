package jkapp.zyronator.activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.fragments.ListListener
import jkapp.zyronator.R
import jkapp.zyronator.api_calls.*
import jkapp.zyronator.api_calls.discogs.DiscogsUserListApi
import jkapp.zyronator.api_calls.discogs.DiscogsUserListApiCallback
import jkapp.zyronator.data.discogs.ListItem
import jkapp.zyronator.fragments.DiscogsUserListItemsFragment
import retrofit2.Call
import retrofit2.Response

internal class DisplayDiscogsUserListItemsActivity : AppCompatActivity(), ListListener, ReleaseUrlApiCallback, DiscogsUserListApiCallback
{
    private var _list = java.util.ArrayList<ListItem>()
    private val _discogsUserListTag = "discogsList"
    private val _detailsTag = "detailsFragment"

    companion object
    {
        val EXTRA_LIST_ID = "listId"
        val LIST_API_URL_EXTRA = "listApiUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_discogs_user_list_items)

        if(savedInstanceState == null)
        {
            val listFragment = DiscogsUserListItemsFragment()
            val fragmentManager = this.fragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.details_fragment_container, listFragment, _detailsTag)
            transaction.commit()


            val listDetailsApi = DiscogsUserListApi(
                    _userAgent = getString(R.string.app_name) + "/" + getString(R.string.version),
                    _listApiUrl = intent.getStringExtra(LIST_API_URL_EXTRA),
                    _callback = this)

            listDetailsApi.execute()
        }
        else
        {
            _list = savedInstanceState.getParcelableArrayList(_discogsUserListTag)
        }
    }

    override fun discogsUserListApiCallResponse(call: Call<ListDetailsApiResponse>, response: Response<ListDetailsApiResponse>)
    {
        if(response.isSuccessful)
        {
            _list = ArrayList<ListItem>(response.body().items)
            val listFragment = fragmentManager.findFragmentByTag(_detailsTag) as DiscogsUserListItemsFragment
            listFragment.setData(_list.toList())
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun discogsUserListApiCallFailed(call: Call<ListDetailsApiResponse>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun itemClicked(listId: Long)
    {
        val listFragment = fragmentManager.findFragmentByTag(_detailsTag) as DiscogsUserListItemsFragment
        val releaseId = listFragment.getReleaseId(listId)
        val userAgent : String = getString(R.string.app_name) + "/" + getString(R.string.version)

        val releaseUrlApi = ReleaseUrlApi(_userAgent = userAgent, _releaseId = releaseId, _callback = this)
        releaseUrlApi.execute()
    }

    override fun webUrlApiResponse(call: Call<ReleaseWebUrl>, response: Response<ReleaseWebUrl>)
    {
        if(response.isSuccessful)
        {
            val listWebUrl = response.body().uri
            val webPage = Uri.parse(listWebUrl)
            val intent: Intent = Intent(Intent.ACTION_VIEW, webPage)
            startActivity(intent)
        }
    }

    override fun webUrlApiCallFailure(call: Call<ReleaseWebUrl>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_discogsUserListTag, _list)
        super.onSaveInstanceState(outState)
    }
}
