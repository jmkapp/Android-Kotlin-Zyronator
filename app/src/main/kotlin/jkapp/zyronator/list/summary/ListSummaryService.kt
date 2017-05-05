package jkapp.zyronator.list.summary

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListSummaryService : IntentService(_name)
{
    companion object
    {
        private val _name: String = ListSummaryService::class.java.simpleName
        val EXTRA_RESULT_RECEIVER = "resrec"
        val EXTRA_BASE_URL: String = "url"
        val RESULT_CODE: Int = 0

        val EXTRA_USER_AGENT = "agent"
        val EXTRA_USER = "user"
        val EXTRA_PER_PAGE_DEFAULT = "perpage"
        val EXTRA_LIST_SUMMARY_RESULT = "listsum"
    }

    override fun onHandleIntent(intent: Intent)
    {
        val baseUrl : String = intent.getStringExtra(EXTRA_BASE_URL)
        val userAgent : String = intent.getStringExtra(EXTRA_USER_AGENT)
        val user : String = intent.getStringExtra(EXTRA_USER)

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val listSummaryApi = retrofit.create(ListSummaryApi::class.java)
        val listSummaryApiCall : Call<ListSummaryApiCall> = listSummaryApi.getListSummaryCall(user, userAgent)
        val response = listSummaryApiCall.execute()

        if(response.isSuccessful)
        {
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_LIST_SUMMARY_RESULT, response.body())
            val resultReceiver = intent.getParcelableExtra<ResultReceiver>(EXTRA_RESULT_RECEIVER)
            resultReceiver.send(RESULT_CODE, bundle)
        }
    }
}