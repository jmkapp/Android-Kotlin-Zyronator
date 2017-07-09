package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class LastListenedMixes(
        val currentListenerMix : ListenerMixDisplay,
        val nextListenerMix : ListenerMixDisplay,
        val _links : LastListenedMixesLinks) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedMixes> = object : Parcelable.Creator<LastListenedMixes>
        {
            override fun createFromParcel(source: Parcel): LastListenedMixes = LastListenedMixes(source)
            override fun newArray(size: Int): Array<LastListenedMixes?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<ListenerMixDisplay>(ListenerMixDisplay::class.java.classLoader),
            source.readParcelable<ListenerMixDisplay>(ListenerMixDisplay::class.java.classLoader),
            source.readParcelable<LastListenedMixesLinks>(LastListenedMixesLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(currentListenerMix, 0)
        dest.writeParcelable(nextListenerMix, 0)
        dest.writeParcelable(_links, 0)
    }
}

data class LastListenedMixesLinks(
        val self : HateoasLink,
        val lastListened : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<LastListenedMixesLinks> = object : Parcelable.Creator<LastListenedMixesLinks>
        {
            override fun createFromParcel(source: Parcel): LastListenedMixesLinks = LastListenedMixesLinks(source)
            override fun newArray(size: Int): Array<LastListenedMixesLinks?> = arrayOfNulls(size)
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
        dest.writeParcelable(lastListened, 0)
    }
}
