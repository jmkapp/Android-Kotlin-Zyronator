package jkapp.zyronator.listdetails

import android.os.Parcel
import android.os.Parcelable

data class ListItem(
        val comment : String = "",
        val display_title: String = "",
        val uri : String = "",
        val image_url: String = "",
        val resource_url: String = "",
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
        dest?.writeString(display_title)
        dest?.writeString(uri)
        dest?.writeString(image_url)
        dest?.writeString(resource_url)
        dest?.writeString(type)
        dest?.writeString(id)
    }

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListItem> = object : Parcelable.Creator<ListItem>
        {
            override fun createFromParcel(source: Parcel): ListItem = ListItem(source)
            override fun newArray(size: Int): Array<ListItem?> = arrayOfNulls(size)
        }
    }
}

