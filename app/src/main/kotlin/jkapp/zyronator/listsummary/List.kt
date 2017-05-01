package jkapp.zyronator.listsummary

import android.os.Parcel
import android.os.Parcelable

data class List(
        val public : Boolean,
        val name : String = "",
        val date_changed : String = "",
        val date_added : String = "",
        val resource_url : String = "",
        val uri : String = "",
        val id : String = "",
        val description : String = "") : Parcelable
{
    constructor(source: Parcel) : this(
            1.equals(source.readInt()),
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
        dest?.writeInt((if (public) 1 else 0))
        dest?.writeString(name)
        dest?.writeString(date_changed)
        dest?.writeString(date_added)
        dest?.writeString(resource_url)
        dest?.writeString(uri)
        dest?.writeString(id)
        dest?.writeString(description)
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
