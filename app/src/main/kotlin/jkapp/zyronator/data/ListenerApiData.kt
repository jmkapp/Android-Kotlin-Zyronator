package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class ListenerApiData(
        val name : String,
        val password : String,
        val enabled : Boolean,
        val _links: Links) : Listener, Parcelable
{
    override val listenerName: String
    get() = name

    override val listenerUrl: String
    get() = _links.listener.href

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerApiData> = object : Parcelable.Creator<ListenerApiData>
        {
            override fun createFromParcel(source: Parcel): ListenerApiData = ListenerApiData(source)
            override fun newArray(size: Int): Array<ListenerApiData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    1 == source.readInt(),
    source.readParcelable<Links>(Links::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(name)
        dest.writeString(password)
        dest.writeInt((if (enabled) 1 else 0))
        dest.writeParcelable(_links, 0)
    }
}

data class Links(
        val self : HateoasLink,
        val listener: HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Links> = object : Parcelable.Creator<Links>
        {
            override fun createFromParcel(source: Parcel): Links = Links(source)
            override fun newArray(size: Int): Array<Links?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(listener, 0)
    }
}
