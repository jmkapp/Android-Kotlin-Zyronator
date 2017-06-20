package jkapp.zyronator.mix.find

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import jkapp.zyronator.R
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Response

class FindMixesActivity :
        AppCompatActivity(),
        FindMixSearchText,
        FindMixesActivityCallback
{
    companion object
    {
        val MIX_RESULT = "mixResult"
        val LISTENER_URL = "listenerUrl"
    }

    private val SELECT_OR_CREATE_MIX = 45

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_mixes)

        if(savedInstanceState == null)
        {
            val findMixFragment = FindMixesFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.find_mix_activity, findMixFragment,"findMixFragment")
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

    override fun passSearchText(searchText: String)
    {
        if(!searchText.isBlank())
        {
            val intent = Intent(this, SelectOrCreateMixActivity::class.java)
            intent.putExtra(SelectOrCreateMixActivity.SEARCH_TEXT, searchText)
            startActivityForResult(intent, SELECT_OR_CREATE_MIX)
//            val findMixApiCall = FindMixesApiCall(activityCallBack = this, searchText = searchText)
//            findMixApiCall.execute()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null)
        {
            if(requestCode == SELECT_OR_CREATE_MIX)
            {
                val intent = Intent()

                if(resultCode == Activity.RESULT_OK)
                {
                    val mix = data.getParcelableExtra<Mix>(SelectOrCreateMixActivity.MIX_RESULT)
                    intent.putExtra(MIX_RESULT, mix)
                    setResult(Activity.RESULT_OK, intent)
                }
                else
                {
                    setResult(Activity.RESULT_CANCELED)
                }

                finish()
            }
        }

    }

    override fun findMixesApiResponse(response: Response<MixApiCallResultData>)
    {
        if(response.isSuccessful)
        {
//            val mixes = response.body()._embedded.mixes
//
//            val intent = Intent()
//            intent.putParcelableArrayListExtra(LIST_OF_MIXES, java.util.ArrayList(mixes))
//            setResult(Activity.RESULT_OK, intent)
//            finish()
        }
        else
        {
            Toast.makeText(applicationContext, "Api Call Failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun findMixesApiCallFailed(call: Call<MixApiCallResultData>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
