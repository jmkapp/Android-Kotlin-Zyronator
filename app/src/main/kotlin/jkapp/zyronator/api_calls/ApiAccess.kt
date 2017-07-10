package jkapp.zyronator.api_calls

import jkapp.zyronator.data.LastListenedMixes
import jkapp.zyronator.data.ListenerDisplay
import jkapp.zyronator.data.ListenerMixesApiData
import jkapp.zyronator.data.ListenerMix
import jkapp.zyronator.data.ListenerMixDisplay
import jkapp.zyronator.data.MixApiCallResultData
import jkapp.zyronator.data.Mix
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object ApiAccess
{
    private val baseUrl : String = "http://jsbr.us-west-2.elasticbeanstalk.com"
    private val builder : Retrofit.Builder
    lateinit var apiCalls : ApiCalls

    init
    {
        builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
    }

    fun login(userName : String, password : String)
    {
        val token : String = Credentials.basic(userName, password)

        val interceptor = AuthenticationInterceptor(token)

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(interceptor)

        builder.client(okHttpClient.build())

        val retrofit = builder.build()
        apiCalls = retrofit.create(ApiCalls::class.java)
    }
}

interface ApiCalls
{
    // listeners
    @GET("listeners/search/findByName")
    fun findListenerByName(@Query("name") name : String) : Call<ListenerDisplay>

    @GET
    fun lastListened(@Url url : String) : Call<LastListenedMixes>

    // mixes
    @GET
    fun getMix(@Url mixUrl : String) : Call<Mix>

    @PATCH
    fun updateDiscogsApiUrl(@Url mixUrl : String, @Body updates : Map<String, String>) : Call<Mix>

    @GET("mixes/search/findByTitleContainingIgnoreCase")
    fun findMixes(@Query("text") searchText : String) : Call<MixApiCallResultData>

    @GET("mixes/search/findByTitleAndDiscogsApiUrlAndDiscogsWebUrl")
    fun findMix(@Query("title") title : String, @Query("discogsApiUrl") discogsApiUrl : String, @Query("discogsWebUrl") discogsWebUrl : String) : Call<Mix>

    @POST("mixes")
    fun createMix(@Body values : Map<String, String>) : Call<Void>

    // listenerMixes
    @GET
    fun getListenerMix(@Url listenerMixUrl : String) : Call<ListenerMix>

    @GET("listenerMixes/search/findTopByListenerOrderByLastListenedDesc")
    fun findLatestListenerMix(@Query("listener") listenerUrl : String) : Call<ListenerMix>

    @GET("listenerMixes/search/findTopByListenerOrderByLastListenedAsc")
    fun findEarliestListenerMix(@Query("listener") listenerUrl : String) : Call<ListenerMix>

    @GET("listenerMixes/search/findByListenerName")
    fun findMixesByListenerName(@Query("name") name : String) : Call<ListenerMixesApiData>

    @GET("listenerMixes/search/findByMixAndListener")
    fun findListenerMix(@Query("mix") mixUrl : String, @Query("listener") listenerUrl : String) : Call<ListenerMix>

    @POST("listenerMixes")
    fun createListenerMix(@Body values : Map<String, String>) : Call<ListenerMixDisplay>

    @PATCH
    fun updateLastListened(@Url mixUrl : String, @Body updates : Map<String, String>) : Call<LastListenedMixes>
}

internal class AuthenticationInterceptor(private val token : String) : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response
    {
        val original = chain.request()

        val builder = original.newBuilder()
                .header("Authorization", token)

        val request = builder.build()
        return chain.proceed(request)
    }
}
