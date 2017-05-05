package jkapp.zyronator.list.details

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ListDetailsService : IntentService(_name)
{
    companion object
    {
        private val _name: String = ListDetailsService::class.java.simpleName
        val EXTRA_RESULT_RECEIVER = "resrec"
        val EXTRA_BASE_URL: String = "url"
        val EXTRA_USER_AGENT = "agent"
        val EXTRA_LIST_ID = "listid"
        val EXTRA_PER_PAGE_DEFAULT = "perpage"
        val EXTRA_LIST_DETAILS_RESULT = "result"
        val RESULT_CODE: Int = 0
    }

    override fun onHandleIntent(intent: Intent)
    {
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
            val list: java.util.List<jkapp.zyronator.list.details.ListItem> = response.body().items

            val bundle = Bundle()
            bundle.putParcelableArrayList(EXTRA_LIST_DETAILS_RESULT, ArrayList(list))
            val resultReceiver = intent.getParcelableExtra<ResultReceiver>(EXTRA_RESULT_RECEIVER)
            resultReceiver.send(RESULT_CODE, bundle)
        }
    }
}
