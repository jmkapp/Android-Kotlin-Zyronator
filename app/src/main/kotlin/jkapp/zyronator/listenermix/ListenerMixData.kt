package jkapp.zyronator.listenermix

import android.os.Parcel
import android.os.Parcelable

data class ListenerMix(
        val mixTitle : String,
        val discogsApiUrl : String? = "",
        val discogsWebUrl: String? = "",
        val lastListened : String? = "",
        val comment : String? = "",
        val _links : Links) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMix> = object : Parcelable.Creator<ListenerMix>
        {
            override fun createFromParcel(source: Parcel): ListenerMix = ListenerMix(source)
            override fun newArray(size: Int): Array<ListenerMix?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Links>(Links::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(mixTitle)
        dest.writeString(discogsApiUrl)
        dest.writeString(discogsWebUrl)
        dest.writeString(lastListened)
        dest.writeString(comment)
        dest.writeParcelable(_links, 0)
    }
}

data class Links(
        val self : Self,
        val listenerMix: ListenerMixLink,
        val listener : ListenerLink,
        val mix : Mix) : Parcelable
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
            source.readParcelable<Self>(Self::class.java.classLoader),
            source.readParcelable<ListenerMixLink>(ListenerMixLink::class.java.classLoader),
            source.readParcelable<ListenerLink>(ListenerLink::class.java.classLoader),
            source.readParcelable<Mix>(Mix::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(listenerMix, 0)
        dest.writeParcelable(listener, 0)
        dest.writeParcelable(mix, 0)
    }
}

data class Self(
        val href : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Self> = object : Parcelable.Creator<Self>
        {
            override fun createFromParcel(source: Parcel): Self = Self(source)
            override fun newArray(size: Int): Array<Self?> = arrayOfNulls(size)
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

data class ListenerMixLink(
        val href : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixLink> = object : Parcelable.Creator<ListenerMixLink>
        {
            override fun createFromParcel(source: Parcel): ListenerMixLink = ListenerMixLink(source)
            override fun newArray(size: Int): Array<ListenerMixLink?> = arrayOfNulls(size)
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

data class ListenerLink(
        val href : String) : Parcelable
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

data class Mix(
        val href : String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Mix> = object : Parcelable.Creator<Mix>
        {
            override fun createFromParcel(source: Parcel): Mix = Mix(source)
            override fun newArray(size: Int): Array<Mix?> = arrayOfNulls(size)
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











