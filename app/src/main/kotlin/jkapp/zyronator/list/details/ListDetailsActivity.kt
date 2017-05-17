package jkapp.zyronator.list.details

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import jkapp.zyronator.ListListener
import jkapp.zyronator.R
import jkapp.zyronator.releasedetails.ReleaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListDetailsActivity : AppCompatActivity(), ListListener, Callback<ListDetailsApiCall>
{
    private var _list = java.util.ArrayList<jkapp.zyronator.list.details.ListItem>()
    private val _detailsFragment = ListDetailsFragment()
    private val _detailsTag = "detailsFragment"

    companion object
    {
        val EXTRA_LIST_ID = "listid"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_details)

        val fragmentManager = this.fragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.details_fragment_container, _detailsFragment, _detailsTag)
        transaction.commit()

        if(savedInstanceState == null)
        {
            val userAgent : String = getString(R.string.app_name) + "/" + getString(R.string.version)
            val baseUrl : String = getString(R.string.base_url)
            val listId = intent.getStringExtra(EXTRA_LIST_ID)

            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            val listDetailsApi = retrofit.create(ListDetailsApi::class.java)
            val listDetailsApiCall : Call<ListDetailsApiCall> = listDetailsApi.getListDetailsCall(listId, userAgent)
            listDetailsApiCall.enqueue(this)
        }
    }

    override fun onResponse(call: Call<ListDetailsApiCall>, response: Response<ListDetailsApiCall>)
    {
        if(response.isSuccessful)
        {
            val listDetails = response.body().items

            //val listDetailFragment = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
            _detailsFragment.setData(listDetails.toList())
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

    override fun itemClicked(listId: Long)
    {
        val intent = Intent(this, ReleaseActivity::class.java)
        intent.putExtra(ReleaseActivity.EXTRA_RELEASE_ID, _detailsFragment.getListId(listId))
        startActivity(intent)
        //val fragmentContainer = findViewById(R.id.fragment_container)
        //if(fragmentContainer != null)
        //{
            /*val listDetails = ListDetailsFragment()
            val ft = fragmentManager.beginTransaction()
            listDetails.setListId(listId)
            ft.replace(R.id.fragment_container, listDetails)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()*/
        //}
        //else
        //{
            //val detailsFragment = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment

           // val intent = Intent(this, ReleaseActivity::class.java)
            //intent.putExtra(ReleaseActivity.EXTRA_RELEASE_ID, detailsFragment.getListId(listId))
            //startActivity(intent)
        //}
    }
}
