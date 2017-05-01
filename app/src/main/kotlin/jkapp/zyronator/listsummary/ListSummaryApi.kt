package jkapp.zyronator.listsummary

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ListSummaryApi
{
    @GET("/users/{user}/lists")
    fun getListSummaryCall(@Path("user") user : String, @Header("user-agent") userAgent : String) : Call<ListSummaryApiCall>
}

data class ListSummaryApiCall(
        val pagination : Pagination,
        val lists: java.util.List<jkapp.zyronator.listsummary.List>)
