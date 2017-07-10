package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class ListenerDisplay(
        val name : String,
        val _links: ListenerDisplayLinks?) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerDisplay> = object : Parcelable.Creator<ListenerDisplay>
        {
            override fun createFromParcel(source: Parcel): ListenerDisplay = ListenerDisplay(source)
            override fun newArray(size: Int): Array<ListenerDisplay?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readParcelable<ListenerDisplayLinks>(ListenerDisplayLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(name)
        dest.writeParcelable(_links, 0)
    }
}


data class ListenerDisplayLinks(
        val self : HateoasLink,
        val listener: HateoasLink,
        val lastListened : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerDisplayLinks> = object : Parcelable.Creator<ListenerDisplayLinks>
        {
            override fun createFromParcel(source: Parcel): ListenerDisplayLinks = ListenerDisplayLinks(source)
            override fun newArray(size: Int): Array<ListenerDisplayLinks?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(listener, 0)
        dest.writeParcelable(lastListened, 0)
    }
}