package jkapp.zyronator

import jkapp.zyronator.discogs.discogs_user_list_items.ListDetailsApiResponse
import jkapp.zyronator.discogs.discogs_user_list_items.ReleaseWebUrl
import jkapp.zyronator.discogs.discogs_user_lists.data.DiscogsUserListsApiData
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
