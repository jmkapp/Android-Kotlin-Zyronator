package jkapp.zyronator.api_calls.discogs

import jkapp.zyronator.api_calls.ListDetailsApiResponse
import jkapp.zyronator.api_calls.ReleaseWebUrl
import jkapp.zyronator.data.discogs.DiscogsUserListsApiData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

object DiscogsApiAccess
{
    val apiCalls : DiscogsApiCalls

    init
    {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.discogs.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        apiCalls = retrofit.create(DiscogsApiCalls::class.java)
    }
}

interface DiscogsApiCalls
{
    @GET
    fun getUserList(@Url listUrl : String, @Header("user-agent") userAgent : String) : Call<ListDetailsApiResponse>

    @GET("/users/{user}/lists")
    fun getDiscogsUserListsCall(@Path("user") userName : String, @Header("user-agent") userAgent : String) : Call<DiscogsUserListsApiData>

    @GET("releases/{releaseId}")
    fun getReleaseWebUrl(@Header("user-agent") userAgent : String, @Path("releaseId") releaseId : String) : Call<ReleaseWebUrl>
}
