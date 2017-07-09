package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class ListenerMixDisplay(
        val mixTitle : String = "",
        val recordedDate : String = "",
        val mixComment : String = "",
        val discogsApiUrl : String = "",
        val discogsWebUrl : String = "",
        val lastListenedDate : String = "",
        val listenerMixComment : String = "",
        val _links : ListenerMixDisplayLinks? = null) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixDisplay> = object : Parcelable.Creator<ListenerMixDisplay>
        {
            override fun createFromParcel(source: Parcel): ListenerMixDisplay = ListenerMixDisplay(source)
            override fun newArray(size: Int): Array<ListenerMixDisplay?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<ListenerMixDisplayLinks>(ListenerMixDisplayLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(mixTitle)
        dest.writeString(recordedDate)
        dest.writeString(mixComment)
        dest.writeString(discogsApiUrl)
        dest.writeString(discogsWebUrl)
        dest.writeString(lastListenedDate)
        dest.writeString(listenerMixComment)
        dest.writeParcelable(_links, 0)
    }
}


data class ListenerMixDisplayLinks(
        val self : HateoasLink,
        val listenerMix : HateoasLink,
        val listener : HateoasLink,
        val mix : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixDisplayLinks> = object : Parcelable.Creator<ListenerMixDisplayLinks>
        {
            override fun createFromParcel(source: Parcel): ListenerMixDisplayLinks = ListenerMixDisplayLinks(source)
            override fun newArray(size: Int): Array<ListenerMixDisplayLinks?> = arrayOfNulls(size)
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