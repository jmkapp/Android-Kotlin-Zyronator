package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class Mix(
        val title : String,
        val recorded : String? = "",
        val comment : String? = "",
        val discogsApiUrl : String? = "",
        val discogsWebUrl : String? = "",
        val _links : MixLinks) : Parcelable
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
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readParcelable<MixLinks>(MixLinks::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeString(title)
        dest.writeString(recorded)
        dest.writeString(comment)
        dest.writeString(discogsApiUrl)
        dest.writeString(discogsWebUrl)
        dest.writeParcelable(_links, 0)
    }
}

data class MixLinks(
        val self : Self,
        val mix: MixLink) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<MixLinks> = object : Parcelable.Creator<MixLinks>
        {
            override fun createFromParcel(source: Parcel): MixLinks = MixLinks(source)
            override fun newArray(size: Int): Array<MixLinks?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<Self>(Self::class.java.classLoader),
            source.readParcelable<MixLink>(MixLink::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(self, 0)
        dest.writeParcelable(mix, 0)
    }
}

data class Self(
        val href: String) : Parcelable
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

data class MixLink(
        val href: String) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<MixLink> = object : Parcelable.Creator<MixLink>
        {
            override fun createFromParcel(source: Parcel): MixLink = MixLink(source)
            override fun newArray(size: Int): Array<MixLink?> = arrayOfNulls(size)
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






