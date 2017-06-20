package jkapp.zyronator.listener.get.embedded

import android.os.Parcel
import android.os.Parcelable
import jkapp.zyronator.listener.Listener

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
        val self : Self,
        val listener: ListenerLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Links> = object : Parcelable.Creator<Links>
        {
            override fun createFromParcel(source: Parcel): Links = Links(source)
            override fun newArray(size: Int): Array<Links?> = arrayOfNulls(size)
        }
    }

    constructor(source : Parcel) : this(
            source.readParcelable<Self>(Self::class.java.classLoader),
            source.readParcelable<ListenerLink>(ListenerLink::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(listener, 0)
    }
}

data class Self(val href : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Self> = object : Parcelable.Creator<Self>
        {
            override fun createFromParcel(source: Parcel): Self = Self(source)
            override fun newArray(size: Int): Array<Self?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(href)
    }
}

data class ListenerLink(val href : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerLink> = object : Parcelable.Creator<ListenerLink>
        {
            override fun createFromParcel(source: Parcel): ListenerLink = ListenerLink(source)
            override fun newArray(size: Int): Array<ListenerLink?> = arrayOfNulls(size)
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
