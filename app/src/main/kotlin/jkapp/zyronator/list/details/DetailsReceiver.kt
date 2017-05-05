package jkapp.zyronator.list.details

import android.os.Bundle

interface DetailsReceiver
{
    fun onReceiveDetailResult(resultCode: Int, resultData: Bundle)
}