package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class LastListenedMixesOld(
        val currentListenerMix: LastListenedMix,
        val nextListenerMix: LastListenedMix,
        val _links : LastListenedLinks?) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedMixesOld> = object : Parcelable.Creator<LastListenedMixesOld>
        {
            override fun createFromParcel(source: Parcel): LastListenedMixesOld = LastListenedMixesOld(source)
            override fun newArray(size: Int): Array<LastListenedMixesOld?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readParcelable<LastListenedMix>(LastListenedMix::class.java.classLoader),
    source.readParcelable<LastListenedMix>(LastListenedMix::class.java.classLoader),
    source.readParcelable<LastListenedLinks>(LastListenedLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(currentListenerMix, 0)
        dest.writeParcelable(nextListenerMix, 0)
        dest.writeParcelable(_links, 0)
    }
}

data class LastListenedMix(
        val mixTitle : String,
        val recordedDate : String = "",
        val comment : String = "",
        val discogsApiUrl : String = "",
        val discogsWebUrl: String = "",
        val lastListenedDate : String = "",
        val _links : LastListenedMixLinks?) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedMix> = object : Parcelable.Creator<LastListenedMix>
        {
            override fun createFromParcel(source: Parcel): LastListenedMix = LastListenedMix(source)
            override fun newArray(size: Int): Array<LastListenedMix?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readParcelable<LastListenedMixLinks>(LastListenedMixLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(mixTitle)
        dest.writeString(recordedDate)
        dest.writeString(comment)
        dest.writeString(discogsApiUrl)
        dest.writeString(discogsWebUrl)
        dest.writeString(lastListenedDate)
        dest.writeParcelable(_links, 0)
    }
}

data class LastListenedLinks(
        val self : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedLinks> = object : Parcelable.Creator<LastListenedLinks>
        {
            override fun createFromParcel(source: Parcel): LastListenedLinks = LastListenedLinks(source)
            override fun newArray(size: Int): Array<LastListenedLinks?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
    }
}

data class LastListenedMixLinks(
        val self : HateoasLink,
        val listenerMix : HateoasLink,
        val listener : HateoasLink,
        val mix : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedMixLinks> = object : Parcelable.Creator<LastListenedMixLinks>
        {
            override fun createFromParcel(source: Parcel): LastListenedMixLinks = LastListenedMixLinks(source)
            override fun newArray(size: Int): Array<LastListenedMixLinks?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
    source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(listenerMix, 0)
        dest.writeParcelable(listener, 0)
        dest.writeParcelable(mix, 0)
    }
}
