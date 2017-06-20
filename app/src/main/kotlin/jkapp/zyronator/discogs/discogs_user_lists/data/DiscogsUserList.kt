package jkapp.zyronator.discogs.discogs_user_lists.data

import android.os.Parcel
import android.os.Parcelable

data class DiscogsUserList(
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
        @JvmField val CREATOR: Parcelable.Creator<DiscogsUserList> = object : Parcelable.Creator<DiscogsUserList>
        {
            override fun createFromParcel(source: Parcel): DiscogsUserList = DiscogsUserList(source)
            override fun newArray(size: Int): Array<DiscogsUserList?> = arrayOfNulls(size)
        }
    }
}
