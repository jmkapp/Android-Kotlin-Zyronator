package jkapp.zyronator.data

import android.os.Parcelable

interface Listener : Parcelable
{
    val listenerName : String
    val listenerUrl : String
}
