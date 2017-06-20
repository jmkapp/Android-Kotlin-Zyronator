package jkapp.zyronator.discogs.discogs_user_lists.data

import android.os.Parcel
import android.os.Parcelable

data class Urls(
        val next : String = "",
        val previous : String = "",
        val last : String = "",
        val first : String = "") : Parcelable
{
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeString(next)
        dest?.writeString(previous)
        dest?.writeString(last)
        dest?.writeString(first)
    }

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Urls> = object : Parcelable.Creator<Urls>
        {
            override fun createFromParcel(source: Parcel): Urls = Urls(source)
            override fun newArray(size: Int): Array<Urls?> = arrayOfNulls(size)
        }
    }
}
