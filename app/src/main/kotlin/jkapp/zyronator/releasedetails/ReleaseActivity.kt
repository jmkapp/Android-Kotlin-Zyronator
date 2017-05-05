package jkapp.zyronator.releasedetails

import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import jkapp.zyronator.R

class ReleaseActivity : AppCompatActivity(), ReleaseReceiver
{
    private val _apiRequestCode = 1

    companion object
    {
        val EXTRA_RELEASE_ID = "relid"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release)

        if(savedInstanceState == null)
        {
            val pendingResult: PendingIntent = createPendingResult(_apiRequestCode, Intent(), 0)
            val newIntent = Intent(applicationContext, ReleaseService::class.java)

            val userAgent: String = getString(R.string.app_name) + "/" + getString(R.string.version)
            val baseUrl: String = getString(R.string.base_url)
            val listId = intent.getStringExtra(EXTRA_RELEASE_ID)

            val resultReceiver = ReleaseResultReceiver(this)

            newIntent.putExtra(ReleaseService.EXTRA_RESULT_RECEIVER, resultReceiver)
            newIntent.putExtra(ReleaseService.EXTRA_PENDING_RESULT, pendingResult)
            newIntent.putExtra(ReleaseService.EXTRA_BASE_URL, baseUrl)
            newIntent.putExtra(ReleaseService.EXTRA_USER_AGENT, userAgent)
            newIntent.putExtra(ReleaseService.EXTRA_RELEASE_ID, listId)

            startService(newIntent)
        }
    }

    override fun onReceiveDetailResult(resultCode: Int, resultData: Bundle)
    {
        val release = resultData.getParcelable<ReleaseApiCall>(ReleaseService.EXTRA_RELEASE_RESULT)

        val releaseFragment = fragmentManager.findFragmentById(R.id.release_frag) as ReleaseFragment
        releaseFragment.setData(release)
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data : Intent)
    {
        if(requestCode == _apiRequestCode)
        {

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
