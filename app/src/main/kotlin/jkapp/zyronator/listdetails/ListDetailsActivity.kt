package jkapp.zyronator.listdetails

import android.app.FragmentTransaction
import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jkapp.zyronator.ListListener
import jkapp.zyronator.R
import jkapp.zyronator.releasedetails.ReleaseActivity

class ListDetailsActivity : AppCompatActivity(), ListListener
{
    private val _apiRequestCode = 1
    private var _list = java.util.ArrayList<jkapp.zyronator.listdetails.ListItem>()

    companion object
    {
        val EXTRA_LIST_ID = "listid"
    }

    override fun onCreate(savedInstanceState: Bundle?)
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

    override fun onActivityResult(requestCode: Int, resultCode : Int, data : Intent)
    {
        if(requestCode == _apiRequestCode)
        {
            _list = data.getParcelableArrayListExtra<jkapp.zyronator.listdetails.ListItem>(ListDetailsService.EXTRA_LIST_RESULT)

            val listDetailFragment = fragmentManager.findFragmentById(R.id.detail_frag) as ListDetailsFragment
            listDetailFragment.setData(_list)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun itemClicked(listId: Long)
    {
        val fragmentContainer = findViewById(R.id.fragment_container)
        if(fragmentContainer != null)
        {
            /*val listDetails = ListDetailsFragment()
            val ft = fragmentManager.beginTransaction()
            listDetails.setListId(listId)
            ft.replace(R.id.fragment_container, listDetails)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()*/
        }
        else
        {
            val intent = Intent(this, ReleaseActivity::class.java)
            intent.putExtra(ReleaseActivity.EXTRA_RELEASE_ID, _list.get(listId.toInt()).id)
            startActivity(intent)
        }
    }
}
