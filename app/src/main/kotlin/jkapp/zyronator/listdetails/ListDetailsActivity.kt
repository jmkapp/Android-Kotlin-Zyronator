package jkapp.zyronator.listdetails

import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jkapp.zyronator.R

class ListDetailsActivity : AppCompatActivity()
{
    private val _apiRequestCode = 1

    companion object
    {
        val EXTRA_LIST_ID = "listid"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        try
        {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_list_details)

            val pendingResult: PendingIntent = createPendingResult(_apiRequestCode, Intent(), 0)
            val newIntent = Intent(applicationContext, ListDetailsService::class.java)

            val userAgent : String = getString(R.string.app_name) + "/" + getString(R.string.version)
            val baseUrl : String = getString(R.string.base_url)
            val perPageDefault : String = getString(R.string.per_page_default)
            val listId = intent.getStringExtra(EXTRA_LIST_ID)

            newIntent.putExtra(ListDetailsService.EXTRA_PENDING_RESULT, pendingResult)
            newIntent.putExtra(ListDetailsService.EXTRA_BASE_URL, baseUrl)
            newIntent.putExtra(ListDetailsService.EXTRA_USER_AGENT, userAgent)
            newIntent.putExtra(ListDetailsService.EXTRA_LIST_ID, listId)
            newIntent.putExtra(ListDetailsService.EXTRA_PER_PAGE_DEFAULT, perPageDefault)

            startService(newIntent)
        }
        catch(ex : Exception)
        {
            Log.i("test", "test")
        }



        //val listDetailsFragment = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
        //val listId : String = intent.extras.get(EXTRA_LIST_ID) as String
        //listDetailsFragment.setListId(listId.toLong())
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data : Intent)
    {
        try
        {
            if(requestCode == _apiRequestCode)
            {
                val list = data.getParcelableArrayListExtra<jkapp.zyronator.listdetails.ListItem>(ListDetailsService.EXTRA_LIST_RESULT)

                val listDetailFragment = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
                listDetailFragment.setData(list)
            }
        }
        catch(ex : Exception)
        {
            Log.i("Test", "test")
        }


        super.onActivityResult(requestCode, resultCode, data)
    }
}
