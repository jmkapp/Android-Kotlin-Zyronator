package jkapp.zyronator.list.details

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ListDetailsApi
{
    @GET("/lists/{listid}")
    fun getListDetailsCall(
            @Path("listid") listId : String,
            @Header("user-agent") userAgent : String) : Call<ListDetailsApiCall>
}

data class ListDetailsApiCall(
        val items : java.util.List<jkapp.zyronator.list.details.ListItem>)
