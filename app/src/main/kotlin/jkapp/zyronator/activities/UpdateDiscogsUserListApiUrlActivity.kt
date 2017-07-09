package jkapp.zyronator.activities

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.fragments.ListListener

import jkapp.zyronator.R
import jkapp.zyronator.data.discogs.DiscogsUserListsApiData
import jkapp.zyronator.fragments.DiscogsUserListsFragment
import jkapp.zyronator.api_calls.discogs.DiscogsUserListsApiCallback
import jkapp.zyronator.api_calls.discogs.DiscogsUserListsApiCall
import jkapp.zyronator.api_calls.GetListenerMixByUrlApiCall
import jkapp.zyronator.api_calls.GetListenerMixByUrlApiCallback
import jkapp.zyronator.data.ListenerMix
import jkapp.zyronator.data.ListenerMixDisplayOld
import jkapp.zyronator.listenermix.update.UpdateMixApiCallCallback
import jkapp.zyronator.data.Mix
import retrofit2.Call
import retrofit2.Response

internal class UpdateDiscogsUserListApiUrlActivity : AppCompatActivity(), ListListener, DiscogsUserListsApiCallback,
        UpdateMixApiCallCallback, GetListenerMixByUrlApiCallback
{
    private val LISTS_FRAGMENT = "listsFragment"

    companion object
    {
        val LISTENER_MIX_DISPLAY = "listenerMix"
        val LISTENER_MIX_URL = "listenerMixUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_discogs_user_list_api_url)

        if(savedInstanceState == null)
        {
            val transaction = fragmentManager.beginTransaction()
            val fragment = DiscogsUserListsFragment()
            transaction.add(R.id.discogs_user_lists, fragment, LISTS_FRAGMENT)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()

            val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
            val discogsUser = getString(R.string.discogs_default_user_name)

            val apiCall = DiscogsUserListsApiCall(activity = this, userAgent = userAgent, discogsUserName = discogsUser)
            apiCall.execute()
        }
    }

    override fun itemClicked(listId: Long)
    {

        val fragment = fragmentManager.findFragmentByTag(LISTS_FRAGMENT) as DiscogsUserListsFragment
        val userList = fragment.getDiscogsUserList(listId)

        val listenerMixUrl = intent.getStringExtra(LISTENER_MIX_URL)

//        val apiCall = UpdateMixApiCall(_activity = this, _listenerMixUrl = listenerMixUrl, _discogsApiUrl = userList.resource_url)
//        apiCall.execute()
    }

    override fun discogsUserListsApiResponse(call: Call<DiscogsUserListsApiData>, response: Response<DiscogsUserListsApiData>)
    {
        if(response.isSuccessful)
        {
            val lists = response.body().lists
            val listsFragment = fragmentManager.findFragmentByTag(LISTS_FRAGMENT) as DiscogsUserListsFragment
            listsFragment.setData(lists)
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

    override fun updateMixApiResponse(call: Call<Mix>, response: Response<Mix>)
    {
        if(response.isSuccessful)
        {
            val listenerMix = intent.getParcelableExtra<ListenerMixDisplayOld>(LISTENER_MIX_DISPLAY)

            val apiCall = GetListenerMixByUrlApiCall(this, listenerMix.selfUrl, "")
            apiCall.execute()
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun updateMixApiCallFailed(call: Call<Mix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun getListenerMixByUrlApiResponse(listenerMix : ListenerMixDisplayOld, tag : String)
    {
        val intent = Intent()


//        if(response.isSuccessful)
//        {
//            val listenerMix = response.body()
//
//            intent.putExtra(NEW_LISTENER_MIX_OBJECT, listenerMix)
//            setResult(Activity.RESULT_OK, intent)
//        }
//        else
//        {
//            setResult(Activity.RESULT_CANCELED, intent)
//        }
//
//        finish()
    }

    override fun getListenerMixByUrlApiCallFailed(call: Call<ListenerMix>, t: Throwable, tag : String)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
