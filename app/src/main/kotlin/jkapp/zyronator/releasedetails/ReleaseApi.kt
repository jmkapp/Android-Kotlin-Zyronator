package jkapp.zyronator.releasedetails

import android.os.Parcel
import android.os.Parcelable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ReleaseApi
{
    @GET("/releases/{releaseid}")
    fun getReleaseCall(
            @Path("releaseid") releaseId : String,
            @Header("user-agent") userAgent : String) : Call<ReleaseApiCall>
}

data class ReleaseApiCall(
        val id : String = "",
        val styles : List<String> = listOf<String>(),
        val genres : List<String> = listOf<String>(),
        val labels : List<Label>,
        val artists : List<Artist>,
        val title : String = "",
        val masterurl : String? = "",
        val tracklist : List<Track>,
        val country : String = "") : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ReleaseApiCall> = object : Parcelable.Creator<ReleaseApiCall>
        {
            override fun createFromParcel(source: Parcel): ReleaseApiCall = ReleaseApiCall(source)
            override fun newArray(size: Int): Array<ReleaseApiCall?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.createStringArrayList(),
    source.createStringArrayList(),
    source.createTypedArrayList(Label.CREATOR),
    source.createTypedArrayList(Artist.CREATOR),
    source.readString(),
    source.readString(),
    source.createTypedArrayList(Track.CREATOR),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeString(id)
        dest?.writeStringList(styles)
        dest?.writeStringList(genres)
        dest?.writeTypedList(labels)
        dest?.writeTypedList(artists)
        dest?.writeString(title)
        dest?.writeString(masterurl)
        dest?.writeTypedList(tracklist)
        dest?.writeString(country)
    }
}