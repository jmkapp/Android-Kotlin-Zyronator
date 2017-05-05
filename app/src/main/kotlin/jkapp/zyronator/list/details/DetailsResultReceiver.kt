package jkapp.zyronator.list.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

@SuppressLint("ParcelCreator")
class DetailsResultReceiver(val receiver : DetailsReceiver) : ResultReceiver(Handler())
{
    override fun onReceiveResult(resultCode: Int, resultData: Bundle)
    {
        receiver.onReceiveDetailResult(resultCode, resultData)
    }
}