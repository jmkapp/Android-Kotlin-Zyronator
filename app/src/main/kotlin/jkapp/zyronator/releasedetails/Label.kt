package jkapp.zyronator.releasedetails

import android.os.Parcel
import android.os.Parcelable

data class Label(
        val name : String = "",
        val entityType : String = "",
        val catNo : String = "",
        val resourceUrl : String = "",
        val id : String = "",
        val entityTypeName : String = "") : Parcelable {

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Label> = object : Parcelable.Creator<Label>
        {
            override fun createFromParcel(source: Parcel): Label = Label(source)
            override fun newArray(size: Int): Array<Label?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
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
        dest?.writeString(name)
        dest?.writeString(entityType)
        dest?.writeString(catNo)
        dest?.writeString(resourceUrl)
        dest?.writeString(id)
        dest?.writeString(entityTypeName)
    }
}
