package jkapp.zyronator.listenermix.create

import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import jkapp.zyronator.R
import jkapp.zyronator.mix.find.*
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Response

class CreateListenerMixActivity : AppCompatActivity(), FindMixSearchText, FindMixesActivityCallback,
        MixesListListener, CreateListenerMixActivityCallback
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_listener_mix)

        val findMixFragment = FindMixesFragment()
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.create_listener_mix_activity, findMixFragment,"findMixFragment")
        transaction.commit()
    }

    override fun passSearchText(searchText: String)
    {
        val findMixApiCall = FindMixesApiCall(activityCallBack = this, searchText = searchText)
        findMixApiCall.execute()
    }

    override fun findMixesApiResponse(response: Response<MixApiCallResultData>)
    {
        if(response.isSuccessful)
        {
            val mixes = response.body()._embedded.mixes

            val mixListFragment = MixListFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.create_listener_mix_activity, mixListFragment)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()

            mixListFragment.setData(mixes)
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

    override fun mixListItemClicked(mix: Mix)
    {
        val mixUrl = mix._links.self.href

        val listenerUrl = getString(R.string.default_listener_url)

        val apiCall = CreateListenerMixApiCall(activityCallback = this, listenerUrl = listenerUrl, mixUrl = mixUrl)
        apiCall.execute()
    }

    override fun CreateListenerMixApiResponse(call: Call<Void>, response: Response<Void>)
    {
        Log.i("test", "test")

        fragmentManager.popBackStack()

        Log.i("test", "test")
    }

    override fun CreateListenerMixApiCallFailed(call: Call<Void>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
