package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class ListenerMix(
        val mixTitle : String,
        val discogsApiUrl : String? = "",
        val discogsWebUrl: String? = "",
        val lastListenedDate: String? = "",
        val comment : String? = "",
        val _links : ListenerMixDataLinks) : Parcelable
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
        source.readParcelable<ListenerMixDataLinks>(ListenerMixDataLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(mixTitle)
        dest.writeString(discogsApiUrl)
        dest.writeString(discogsWebUrl)
        dest.writeString(lastListenedDate)
        dest.writeString(comment)
        dest.writeParcelable(_links, 0)
    }
}

data class ListenerMixDataLinks(
        val self : HateoasLink,
        val listenerMix: HateoasLink,
        val listener : HateoasLink,
        val mix : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixDataLinks> = object : Parcelable.Creator<ListenerMixDataLinks>
        {
            override fun createFromParcel(source: Parcel): ListenerMixDataLinks = ListenerMixDataLinks(source)
            override fun newArray(size: Int): Array<ListenerMixDataLinks?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
            source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
            source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader),
            source.readParcelable<HateoasLink>(HateoasLink::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(listenerMix, 0)
        dest.writeParcelable(listener, 0)
        dest.writeParcelable(mix, 0)
    }
}











