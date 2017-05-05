package jkapp.zyronator.list.summary

import android.os.Parcel
import android.os.Parcelable
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
        val lists: kotlin.collections.List<jkapp.zyronator.list.summary.List>) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListSummaryApiCall> = object : Parcelable.Creator<ListSummaryApiCall>
        {
            override fun createFromParcel(source: Parcel): ListSummaryApiCall = ListSummaryApiCall(source)
            override fun newArray(size: Int): Array<ListSummaryApiCall?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readParcelable<Pagination>(Pagination::class.java.classLoader),
        source.createTypedArrayList(List.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeParcelable(pagination, 0)
        dest?.writeTypedList(lists)
    }
}
