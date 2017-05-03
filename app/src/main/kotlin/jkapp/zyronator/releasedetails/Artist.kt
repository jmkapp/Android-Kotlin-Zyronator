package jkapp.zyronator.releasedetails

import android.os.Parcel
import android.os.Parcelable

data class Artist(
        val join : String = "",
        val name : String = "",
        val anv : String = "",
        val tracks : String = "",
        val role : String = "",
        val resourceUrl : String = "",
        val id : String = "") : Parcelable {

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Artist> = object : Parcelable.Creator<Artist>
        {
            override fun createFromParcel(source: Parcel): Artist = Artist(source)
            override fun newArray(size: Int): Array<Artist?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeString(join)
        dest?.writeString(name)
        dest?.writeString(anv)
        dest?.writeString(tracks)
        dest?.writeString(role)
        dest?.writeString(resourceUrl)
        dest?.writeString(id)
    }
}
