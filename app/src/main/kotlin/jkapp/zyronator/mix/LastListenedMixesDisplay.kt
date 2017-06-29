package jkapp.zyronator.mix

import android.os.Parcel
import android.os.Parcelable

data class LastListenedMixesDisplay(
        val currentMixTitle : String = "",
        val currentMixRecorded : String = "",
        val currentMixComment : String = "",
        val currentMixDiscogsApiUrl : String = "",
        val currentMixDiscogsWebUrl : String = "",
        val currentMixLastListenedDate : String = "",
        val currentListenerMixSelf: String  = "",
        val currentMixSelfUrl: String = "",
        val nextMixTitle : String = "",
        val nextMixRecorded : String = "",
        val nextMixComment : String = "",
        val nextMixDiscogsApiUrl : String = "",
        val nextMixDiscogsWebUrl : String = "",
        val nextMixLastListenedDate : String = "",
        val nextListenerMixSelf : String = "",
        val nextMixSelfUrl: String = "") : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedMixesDisplay> = object : Parcelable.Creator<LastListenedMixesDisplay>
        {
            override fun createFromParcel(source: Parcel): LastListenedMixesDisplay = LastListenedMixesDisplay(source)
            override fun newArray(size: Int): Array<LastListenedMixesDisplay?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
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
        dest.writeString(currentMixTitle)
        dest.writeString(currentMixRecorded)
        dest.writeString(currentMixComment)
        dest.writeString(currentMixDiscogsApiUrl)
        dest.writeString(currentMixDiscogsWebUrl)
        dest.writeString(currentMixLastListenedDate)
        dest.writeString(currentListenerMixSelf)
        dest.writeString(currentMixSelfUrl)
        dest.writeString(nextMixTitle)
        dest.writeString(nextMixRecorded)
        dest.writeString(nextMixComment)
        dest.writeString(nextMixDiscogsApiUrl)
        dest.writeString(nextMixDiscogsWebUrl)
        dest.writeString(nextMixLastListenedDate)
        dest.writeString(nextListenerMixSelf)
        dest.writeString(nextMixSelfUrl)
    }
}