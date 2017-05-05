package jkapp.zyronator

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jkapp.zyronator.list.details.*
import jkapp.zyronator.list.summary.*

class MainActivity : AppCompatActivity(), ListListener, SummaryReceiver, DetailsReceiver
{
    private var _currentDetailsList = listOf<jkapp.zyronator.list.details.ListItem>()
    private val _savedList = "savedlist"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null)
        {
            val list : ArrayList<jkapp.zyronator.list.details.ListItem> = savedInstanceState.getParcelableArrayList(_savedList)
            _currentDetailsList = list.toList()
        }
        else
        {
            val intent = Intent(applicationContext, ListSummaryService::class.java)

            val userAgent : String = getString(R.string.app_name) + "/" + getString(R.string.version)
            val user : String = getString(R.string.default_user)
            val baseUrl : String = getString(R.string.base_url)
            val perPageDefault : String = getString(R.string.per_page_default)

            val resultReceiver = SummaryResultReceiver(this)

            intent.putExtra(ListSummaryService.EXTRA_RESULT_RECEIVER, resultReceiver)
            intent.putExtra(ListSummaryService.EXTRA_BASE_URL, baseUrl)
            intent.putExtra(ListSummaryService.EXTRA_USER_AGENT, userAgent)
            intent.putExtra(ListSummaryService.EXTRA_USER, user)
            intent.putExtra(ListSummaryService.EXTRA_PER_PAGE_DEFAULT, perPageDefault)

            startService(intent)
        }
    }

    override fun onReceiveSummaryResult(resultCode: Int, resultData: Bundle)
    {
        val listSummary : ListSummaryApiCall = resultData.getParcelable<ListSummaryApiCall>(ListSummaryService.EXTRA_LIST_SUMMARY_RESULT)
        val lists = listSummary.lists
        val fragmentContainer = findViewById(R.id.fragment_container)
        if(fragmentContainer != null)
        {
            val listSummaryFragment = fragmentManager.findFragmentById(R.id.summary_frag) as ListSummaryFragment
            listSummaryFragment.setData(lists)
        }
        else
        {
            val listSummaryFragment = fragmentManager.findFragmentById(R.id.list_frag) as ListSummaryFragment
            listSummaryFragment.setData(lists)
        }
    }

    override fun itemClicked(listId: Long)
    {
        val fragmentContainer = findViewById(R.id.fragment_container)
        if(fragmentContainer != null)
        {
            val listSummaryFragment = fragmentManager.findFragmentById(R.id.summary_frag) as ListSummaryFragment
            fetchDetailsData(listSummaryFragment.getListId(listId))
        }
        else
        {
            val listSummaryFragment = fragmentManager.findFragmentById(R.id.list_frag) as ListSummaryFragment

            val intent = Intent(this, ListDetailsActivity::class.java)
            intent.putExtra(ListDetailsActivity.EXTRA_LIST_ID, listSummaryFragment.getListId(listId))
            startActivity(intent)
        }
    }

    private fun fetchDetailsData(listId : String)
    {
        val newIntent = Intent(applicationContext, ListDetailsService::class.java)

        val userAgent : String = getString(R.string.app_name) + "/" + getString(R.string.version)
        val baseUrl : String = getString(R.string.base_url)
        val perPageDefault : String = getString(R.string.per_page_default)

        val resultReceiver = DetailsResultReceiver(this)

        newIntent.putExtra(ListDetailsService.EXTRA_RESULT_RECEIVER, resultReceiver)
        newIntent.putExtra(ListDetailsService.EXTRA_BASE_URL, baseUrl)
        newIntent.putExtra(ListDetailsService.EXTRA_USER_AGENT, userAgent)
        newIntent.putExtra(ListDetailsService.EXTRA_LIST_ID, listId)
        newIntent.putExtra(ListDetailsService.EXTRA_PER_PAGE_DEFAULT, perPageDefault)

        startService(newIntent)
    }

    override fun onReceiveDetailResult(resultCode: Int, resultData: Bundle)
    {
        val listDetails = resultData.getParcelableArrayList<jkapp.zyronator.list.details.ListItem>(ListDetailsService.EXTRA_LIST_DETAILS_RESULT)
        _currentDetailsList = listDetails.toList()

        val _detailsFragment = ListDetailsFragment()
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, _detailsFragment)
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()

        _detailsFragment.setData(_currentDetailsList)
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelableArrayList(_savedList, ArrayList<jkapp.zyronator.list.details.ListItem>(_currentDetailsList))
        super.onSaveInstanceState(outState)
    }
}
