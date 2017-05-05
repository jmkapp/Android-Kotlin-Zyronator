package jkapp.zyronator.list.summary

import android.os.Parcel
import android.os.Parcelable

data class Pagination(
        val perPage: Int,
        val items : Int,
        val page : Int,
        val urls : Urls,
        val pages : Int) : Parcelable
{
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readParcelable<Urls>(Urls::class.java.classLoader),
            source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeInt(perPage)
        dest?.writeInt(items)
        dest?.writeInt(page)
        dest?.writeParcelable(urls, 0)
        dest?.writeInt(pages)
    }

    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<Pagination> = object : Parcelable.Creator<Pagination>
        {
            override fun createFromParcel(source: Parcel): Pagination = Pagination(source)
            override fun newArray(size: Int): Array<Pagination?> = arrayOfNulls(size)
        }
    }
}
