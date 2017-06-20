package jkapp.zyronator.listenermix.find

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import jkapp.zyronator.R
import jkapp.zyronator.listenermix.create.CreateAndReturnListenerMixApiCall
import jkapp.zyronator.listenermix.create.CreateAndReturnListenerMixApiCallback
import jkapp.zyronator.listenermix.ListenerMix
import jkapp.zyronator.mix.find.FindMixesActivity
import jkapp.zyronator.mix.find.SelectOrCreateMixActivity
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Response

class FindOrCreateListenerMixActivity : AppCompatActivity(), CreateAndReturnListenerMixApiCallback
{
    private val FIND_MIX = 5
    private val SELECT_OR_CREATE_MIX = 6

    private lateinit var _listenerUrl: String

    companion object
    {
        val LISTENER_MIX_OBJECT = "listenerMix"
        val LISTENER_URL = "listenerUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_find_or_create_listener_mix)

//        val mix = intent.getParcelableExtra<Mix>(MIX_OBJECT)
//
//        val baseUrl = getString(R.string.base_listener_url)
//        val userAgent = getString(R.string.app_name) + "/" + getString(R.string.version)
//
//        val tempMixUrl = "http://jsbr.us-west-2.elasticbeanstalk.com/mixes/7"
//        val listenerUrl = getString(R.string.default_listener_url)

        _listenerUrl = intent.getStringExtra(LISTENER_URL)

        val findMixIntent = Intent(this, FindMixesActivity::class.java)
        startActivityForResult(findMixIntent, FIND_MIX)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

//        if(requestCode == FIND_MIX && resultCode == Activity.RESULT_OK)
//        {
//            val mixList = data.getParcelableArrayListExtra<Mix>(FindMixesActivity.LIST_OF_MIXES)
//
//            val intent = Intent(this, SelectOrCreateMixActivity::class.java)
//            intent.putParcelableArrayListExtra(SelectOrCreateMixActivity.LIST_OF_MIXES, mixList)
//            startActivityForResult(intent, SELECT_OR_CREATE_MIX)
//        }
//        else if(requestCode == SELECT_OR_CREATE_MIX && resultCode == Activity.RESULT_OK)
//        {
//            val mix = data.getParcelableExtra<Mix>(SelectOrCreateMixActivity.MIX_RESULT)
//            val mixCreated = data.getBooleanExtra(SelectOrCreateMixActivity.MIX_CREATED, false)
//
//             val mixUrl = mix._links.self.href
//
//            if(mixCreated)
//            {
//                val apiCall = CreateAndReturnListenerMixApiCall(_callback = this, _listenerUrl = _listenerUrl, _mixUrl = mixUrl)
//                apiCall.execute()
//            }
//            else
//            {
//                val apiCall = FindOrCreateListenerMixApiCall(_activity = this, _listenerUrl = _listenerUrl, _mixUrl = mixUrl)
//                apiCall.execute()
//            }
//        }
    }

    override fun createAndReturnListenerMixApiResponse(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        finishOk(call, response)
    }

    override fun createAndReturnListenerMixApiCallFailed(call: Call<ListenerMix>, t: Throwable)
    {
        finishFail(call, t)
    }

    private fun finishOk(call: Call<ListenerMix>, response: Response<ListenerMix>)
    {
        val intent = Intent()

        if(response.isSuccessful)
        {
            val listenerMix = response.body()

            intent.putExtra(LISTENER_MIX_OBJECT, listenerMix)
            setResult(Activity.RESULT_OK, intent)
        }
        else
        {
            setResult(Activity.RESULT_CANCELED, intent)
        }

        finish()
    }

    private fun finishFail(call: Call<ListenerMix>, t: Throwable)
    {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }
}


