package jkapp.zyronator.discogs.discogs_user_lists.data

import android.os.Parcel
import android.os.Parcelable

data class DiscogsUserListsApiData(
        val pagination : Pagination,
        val lists: kotlin.collections.List<DiscogsUserList>) : Parcelable
{
    companion object
    {
        @JvmField val CREATOR: Parcelable.Creator<DiscogsUserListsApiData> = object : Parcelable.Creator<DiscogsUserListsApiData>
        {
            override fun createFromParcel(source: Parcel): DiscogsUserListsApiData = DiscogsUserListsApiData(source)
            override fun newArray(size: Int): Array<DiscogsUserListsApiData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<Pagination>(Pagination::class.java.classLoader),
            source.createTypedArrayList(DiscogsUserList.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int)
    {
        dest?.writeParcelable(pagination, 0)
        dest?.writeTypedList(lists)
    }
}
