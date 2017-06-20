package jkapp.zyronator.mix.find

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

import jkapp.zyronator.R
import jkapp.zyronator.mix.create.CreateMixActivity
import jkapp.zyronator.mix.mixapidata.Mix
import retrofit2.Call
import retrofit2.Response

class SelectOrCreateMixActivity :
        AppCompatActivity(),
        FindMixesActivityCallback,
        MixesListListener,
        View.OnClickListener,
        FindMixActivityCallback
{
    companion object
    {
        val LIST_OF_MIXES: String = "listOfMixes"
        val MIX_RESULT = "resultMix"
        val CREATE_MIX = 7
        val MIX_CREATED = "mixCreated"

        val SEARCH_TEXT = "searchText"
    }

    private var _mixCreated = false
    private val MIX_LIST_FRAGMENT = "mixListFragment"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_or_create_mix)

        if(savedInstanceState == null)
        {
            val myView = findViewById(R.id.select_or_create_mix_activity) as LinearLayout
            val createButton = Button(this)
            createButton.tag = "createMixButton"
            createButton.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            createButton.text = "Create Mix"
            createButton.setOnClickListener(this)
            myView.addView(createButton)

            val mixListFragment = MixListFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.add(R.id.select_or_create_mix_activity, mixListFragment, MIX_LIST_FRAGMENT)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()

            val searchText = intent.getStringExtra(SEARCH_TEXT)
            val findMixApiCall = FindMixesApiCall(activityCallBack = this, searchText = searchText)
            findMixApiCall.execute()
        }
    }

    override fun findMixesApiResponse(response: Response<MixApiCallResultData>)
    {
        val mixListFragment = fragmentManager.findFragmentByTag(MIX_LIST_FRAGMENT) as MixListFragment

        val mixes = response.body()._embedded.mixes
        mixListFragment.setData(mixes)
    }

    override fun findMixesApiCallFailed(call: Call<MixApiCallResultData>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Find Mixes API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }

    override fun mixListItemClicked(mix: Mix)
    {
        val intent = Intent()
        intent.putExtra(MIX_RESULT, mix)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onClick(v: View)
    {
        val intent = Intent(this, CreateMixActivity::class.java)
        startActivityForResult(intent, CREATE_MIX)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    {
        if(requestCode == CREATE_MIX && resultCode == Activity.RESULT_OK)
        {
            _mixCreated = true
            val bundle = data.extras

            val mixName = bundle.get(CreateMixActivity.MIX_NAME) as String
            val discogsApiUrl = bundle.get(CreateMixActivity.DISCOGS_API_URL) as String
            val discogsWebUrl = bundle.get(CreateMixActivity.DISCOGS_WEB_URL) as String

            val apiCall = FindMixApiCall(_activity = this, _mixTitle = mixName, _discogsApiUrl = discogsApiUrl, _discogsWebUrl = discogsWebUrl)
            apiCall.execute()
        }
    }

    override fun findMixApiResponse(call: Call<Mix>, response: Response<Mix>)
    {
        if(response.isSuccessful)
        {
            val intent = Intent()
            intent.putExtra(MIX_RESULT, response.body())
            intent.putExtra(MIX_CREATED, _mixCreated)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        else
        {
            Toast.makeText(applicationContext, "Find Mix API call failed: " + response.message(), Toast.LENGTH_LONG).show()
        }
    }

    override fun findMixApiCallFailed(call: Call<Mix>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Find Mix API call failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
