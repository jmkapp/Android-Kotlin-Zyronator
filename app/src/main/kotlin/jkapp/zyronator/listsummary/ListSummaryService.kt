package jkapp.zyronator.listsummary

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListSummaryService : IntentService(_name)
{
    companion object
    {
        private val _name: String = ListSummaryService::class.java.simpleName
        val EXTRA_PENDING_RESULT: String = "pend"
        val EXTRA_BASE_URL: String = "url"
        val PAGINATION_RESULT: String = "pag"
        val LISTS_RESULT: String = "lists"
        val EXTRA_BUNDLE_RESULT: String = "bundle"
        val RESULT_CODE: Int = 0

        val EXTRA_USER_AGENT = "agent"
        val EXTRA_USER = "user"
        val EXTRA_PER_PAGE_DEFAULT = "perpage"
    }

    override fun onHandleIntent(intent: Intent)
    {
        Log.i(_name, "onHandleIntent()")
        val reply : PendingIntent = intent.getParcelableExtra(EXTRA_PENDING_RESULT)

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
            val pagination : Pagination = response.body().pagination
            val lists: java.util.List<jkapp.zyronator.listsummary.List> = response.body().lists

            val result = Intent()

            val bundle : Bundle = Bundle()
            bundle.putParcelable(PAGINATION_RESULT, pagination)
            bundle.putParcelableArrayList(LISTS_RESULT, ArrayList<jkapp.zyronator.listsummary.List>(lists))

            result.putExtra(EXTRA_BUNDLE_RESULT, bundle)

            try
            {
                reply.send(this, RESULT_CODE, result)
            }
            catch(ce : PendingIntent.CanceledException)
            {
                Log.i(_name, "reply cancelled", ce)
            }
        }
    }
}