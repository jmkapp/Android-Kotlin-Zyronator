package jkapp.zyronator.data

import android.os.Parcel
import android.os.Parcelable

data class MixApiCallResultData(
        val _embedded : Embedded,
        val _links : Links) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<MixApiCallResultData> = object : Parcelable.Creator<MixApiCallResultData>
        {
            override fun createFromParcel(source: Parcel): MixApiCallResultData = MixApiCallResultData(source)
            override fun newArray(size: Int): Array<MixApiCallResultData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readParcelable<Embedded>(Embedded::class.java.classLoader),
    source.readParcelable<Links>(Links::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int)
    {
        dest.writeParcelable(_embedded, 0)
        dest.writeParcelable(_links, 0)
    }

    data class Embedded(
            val mixes : List<Mix>) : Parcelable
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
                source.createTypedArrayList(Mix.CREATOR)
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int)
        {
            dest.writeTypedList(mixes)
        }
    }

    data class Links(
            val self : Self) : Parcelable
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
                source.readParcelable<Self>(Self::class.java.classLoader)
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int)
        {
            dest.writeParcelable(self, 0)
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
}





