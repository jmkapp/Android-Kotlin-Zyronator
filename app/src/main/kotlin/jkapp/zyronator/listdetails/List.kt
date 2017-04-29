package jkapp.zyronator.listdetails

import android.os.Parcel
import android.os.Parcelable

data class List(
        val comment : String = "",
        val displayTitle : String = "",
        val uri : String = "",
        val imageUrl : String = "",
        val resourceUrl : String = "",
        val type : String = "",
        val id : String = "") : Parcelable
{
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeString(comment)
        dest?.writeString(displayTitle)
        dest?.writeString(uri)
        dest?.writeString(imageUrl)
        dest?.writeString(resourceUrl)
        dest?.writeString(type)
        dest?.writeString(id)
    }

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<List> = object : Parcelable.Creator<List>
        {
            override fun createFromParcel(source: Parcel): List = List(source)
            override fun newArray(size: Int): Array<List?> = arrayOfNulls(size)
        }
    }
}

