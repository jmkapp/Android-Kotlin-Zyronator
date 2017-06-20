package jkapp.zyronator.listener

import android.os.Parcelable

interface Listener : Parcelable
{
    val listenerName : String
    val listenerUrl : String
}
