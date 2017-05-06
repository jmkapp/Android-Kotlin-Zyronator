package jkapp.zyronator.releasedetails

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import jkapp.zyronator.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ReleaseActivity : AppCompatActivity(), Callback<Release>
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
            val userAgent: String = getString(R.string.app_name) + "/" + getString(R.string.version)
            val baseUrl: String = getString(R.string.base_url)
            val releaseId = intent.getStringExtra(EXTRA_RELEASE_ID)

            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            val releaseApi = retrofit.create(ReleaseApi::class.java)
            val releaseCall : Call<Release> = releaseApi.getReleaseCall(releaseId, userAgent)
            releaseCall.enqueue(this)
        }
    }

    override fun onResponse(call: Call<Release>, response: Response<Release>)
    {
        val release = response.body()

        val releaseFragment = fragmentManager.findFragmentById(R.id.release_frag) as ReleaseFragment
        releaseFragment.setData(release)
    }

    override fun onFailure(call: Call<Release>, t: Throwable)
    {
        Toast.makeText(applicationContext, "Api Call Failed: " + t.message, Toast.LENGTH_LONG).show()
    }
}
