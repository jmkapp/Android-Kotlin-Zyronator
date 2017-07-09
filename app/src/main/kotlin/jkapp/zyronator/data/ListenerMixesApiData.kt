package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class ListenerMixesApiData(
        val _embedded : Embedded,
        val _links : ListenerMixesLinks) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixesApiData> = object : Parcelable.Creator<ListenerMixesApiData>
        {
            override fun createFromParcel(source: Parcel): ListenerMixesApiData = ListenerMixesApiData(source)
            override fun newArray(size: Int): Array<ListenerMixesApiData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<Embedded>(Embedded::class.java.classLoader),
            source.readParcelable<ListenerMixesLinks>(ListenerMixesLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(_embedded, 0)
        dest.writeParcelable(_links, 0)
    }
}

data class Embedded(
        val listenerMixes : List<ListenerMix>) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Embedded> = object : Parcelable.Creator<Embedded>
        {
            override fun createFromParcel(source: Parcel): Embedded = Embedded(source)
            override fun newArray(size: Int): Array<Embedded?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.createTypedArrayList(ListenerMix.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeTypedList(listenerMixes)
    }
}

data class ListenerMixesLinks(
        val self : HateoasLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<ListenerMixesLinks> = object : Parcelable.Creator<ListenerMixesLinks>
        {
            override fun createFromParcel(source: Parcel): ListenerMixesLinks = ListenerMixesLinks(source)
            override fun newArray(size: Int): Array<ListenerMixesLinks?> = arrayOfNulls(size)
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

