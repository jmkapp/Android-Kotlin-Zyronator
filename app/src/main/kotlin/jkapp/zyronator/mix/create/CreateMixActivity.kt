package jkapp.zyronator.mix.create

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

import jkapp.zyronator.R
import retrofit2.Call
import retrofit2.Response

class CreateMixActivity : AppCompatActivity(), CreateMixActivityCallback
{
    private lateinit var _mixName : String
    private lateinit var _discogsApiUrl : String
    private lateinit var _discogsWebUrl : String

    companion object
    {
        //val MIX_RESULT = "mixResult"
        val MIX_NAME = "mixName"
        val DISCOGS_API_URL = "discogsApiUrl"
        val DISCOGS_WEB_URL = "discogsWebUrl"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_mix)
    }

    fun createMix(view : View)
    {
        val mixNameText = findViewById(R.id.create_mix_name) as EditText
        _mixName = mixNameText.text.toString()

        val discogsApiText = findViewById(R.id.create_discogs_api) as EditText
        _discogsApiUrl = discogsApiText.text.toString()

        val discogsWebText = findViewById(R.id.create_discogs_web) as EditText
        _discogsWebUrl = discogsWebText.text.toString()

        val createMixApi = CreateMixApiCall(activityCallBack = this, mixTitle = _mixName, discogsApiUrl = _discogsApiUrl, discogsWebUrl = _discogsWebUrl)
        createMixApi.execute()
    }

    override fun createMixApiResponse(call: Call<Void>, response: Response<Void>)
    {
        if(response.isSuccessful)
        {
            val intent = Intent()

            val bundle = Bundle()
            bundle.putString(MIX_NAME, _mixName)
            bundle.putString(DISCOGS_API_URL, _discogsApiUrl)
            bundle.putString(DISCOGS_WEB_URL, _discogsWebUrl)
            intent.putExtras(bundle)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        else
        {
            Toast.makeText(applicationContext, "Create Mix API call failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun createMixApiCallFailed(call: Call<Void>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Create Mix API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
