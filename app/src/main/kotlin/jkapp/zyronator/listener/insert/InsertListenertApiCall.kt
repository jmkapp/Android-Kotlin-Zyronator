package jkapp.zyronator.listener.insert

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class InsertListenertApiCall(
        private val activity : InsertListenerActivity,
        private val userAgent : String,
        private val baseUrl : String)
{
    internal fun execute()
    {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()


    }
}

interface InsertListenerApi
{
    //@POST("/userses")
    //fun getInsertListenerCall(@Header("listener-agent") userAgent : String) : Call<>
}

//data class