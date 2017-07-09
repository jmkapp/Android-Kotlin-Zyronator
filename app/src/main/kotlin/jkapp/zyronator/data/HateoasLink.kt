package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class HateoasLink(
        val href : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<HateoasLink> = object : Parcelable.Creator<HateoasLink>
        {
            override fun createFromParcel(source: Parcel): HateoasLink = HateoasLink(source)
            override fun newArray(size: Int): Array<HateoasLink?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(href)
    }
}
