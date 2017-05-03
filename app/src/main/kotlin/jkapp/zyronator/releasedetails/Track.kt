package jkapp.zyronator.releasedetails

import android.os.Parcel
import android.os.Parcelable

data class Track(
        val title : String = "") : Parcelable {

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Track> = object : Parcelable.Creator<Track>
        {
            override fun createFromParcel(source: Parcel): Track = Track(source)
            override fun newArray(size: Int): Array<Track?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeString(title)
    }
}
