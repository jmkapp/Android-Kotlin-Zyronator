package jkapp.zyronator.listenermix

import android.os.Parcel
import android.os.Parcelable

data class ListenerMixDisplay(
        val mixTitle : String,
        val lastListenedDate : String,
        val mixUrl : String,
        val discogsApiUrl: String,
        val discogsWebUrl : String,
        val comment : String,
        val selfUrl : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixDisplay> = object : Parcelable.Creator<ListenerMixDisplay>
        {
            override fun createFromParcel(source: Parcel): ListenerMixDisplay = ListenerMixDisplay(source)
            override fun newArray(size: Int): Array<ListenerMixDisplay?> = arrayOfNulls(size)
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

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(mixTitle)
        dest.writeString(lastListenedDate)
        dest.writeString(mixUrl)
        dest.writeString(discogsApiUrl)
        dest.writeString(discogsWebUrl)
        dest.writeString(comment)
        dest.writeString(selfUrl)
    }
}