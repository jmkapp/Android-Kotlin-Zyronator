package jkapp.zyronator.releasedetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver

@SuppressLint("ParcelCreator")
class ReleaseResultReceiver(val receiver : ReleaseReceiver) : ResultReceiver(Handler())
{
    override fun onReceiveResult(resultCode: Int, resultData: Bundle)
    {
        receiver.onReceiveDetailResult(resultCode, resultData)
    }
}
