package jkapp.zyronator.discogs.discogs_user_lists.update

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.ListListener

import jkapp.zyronator.R
import jkapp.zyronator.discogs.discogs_user_lists.data.DiscogsUserListsApiData
import jkapp.zyronator.discogs.discogs_user_lists.DiscogsUserListsFragment
import jkapp.zyronator.discogs.discogs_user_lists.DiscogsUserListsApiCallback
import jkapp.zyronator.discogs.discogs_user_lists.DiscogsUserListsApiCall
import jkapp.zyronator.listenermix.GetListenerMixByUrlApiCall
import jkapp.zyronator.listenermix.GetListenerMixByUrlApiCallback
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.listenermix.ListenerMixDisplay
import jkapp.zyronator.listenermix.update.UpdateMixApiCall
import jkapp.zyronator.listenermix.update.UpdateMixApiCallCallback
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Response

internal class UpdateDiscogsUserListApiUrlActivity : AppCompatActivity(), ListListener, DiscogsUserListsApiCallback,
        UpdateMixApiCallCallback, GetListenerMixByUrlApiCallback
{
    private val LISTS_FRAGMENT = "listsFragment"

    companion object
    {
        val LISTENER_MIX_DISPLAY = "listenerMix"
        val NEW_LISTENER_MIX_OBJECT = "newListenerMix"
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

        val listenerMix = intent.getParcelableExtra<ListenerMixDisplay>(LISTENER_MIX_DISPLAY)
        val mix = listenerMix.mixUrl

        val apiCall = UpdateMixApiCall(_activity = this, _listenerMixUrl = mix, _discogsApiUrl = userList.resource_url)
        apiCall.execute()
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
            val listenerMix = intent.getParcelableExtra<ListenerMixDisplay>(LISTENER_MIX_DISPLAY)

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

    override fun getListenerMixByUrlApiResponse(listenerMix : ListenerMixDisplay, tag : String)
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
