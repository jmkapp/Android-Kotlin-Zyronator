package jkapp.zyronator.listdetails

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListDetailsService : IntentService(_name)
{
    companion object
    {
        private val _name: String = ListDetailsService::class.java.simpleName
        val EXTRA_PENDING_RESULT: String = "pend"
        val EXTRA_BASE_URL: String = "url"
        val EXTRA_USER_AGENT = "agent"
        val EXTRA_LIST_ID = "listid"
        val EXTRA_PER_PAGE_DEFAULT = "perpage"
        val EXTRA_LIST_RESULT = "result"
        val RESULT_CODE: Int = 0
    }

    override fun onHandleIntent(intent: Intent)
    {
        try
        {
            val reply : PendingIntent = intent.getParcelableExtra(EXTRA_PENDING_RESULT)

            val baseUrl : String = intent.getStringExtra(EXTRA_BASE_URL)
            val userAgent : String = intent.getStringExtra(EXTRA_USER_AGENT)
            val listId : String = intent.getStringExtra(EXTRA_LIST_ID)

            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            val listDetailsApi = retrofit.create(ListDetailsApi::class.java)
            val listDetailsApiCall : Call<ListDetailsApiCall> = listDetailsApi.getListDetailsCall(listId, userAgent)
            val response = listDetailsApiCall.execute()

            if(response.isSuccessful)
            {
                val list: java.util.List<jkapp.zyronator.listdetails.ListItem> = response.body().items

                val result = Intent()
                result.putParcelableArrayListExtra(EXTRA_LIST_RESULT, java.util.ArrayList<jkapp.zyronator.listdetails.ListItem>(list))

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
        catch(ex : Exception)
        {
            Log.i("test", "test")
        }
    }
}
