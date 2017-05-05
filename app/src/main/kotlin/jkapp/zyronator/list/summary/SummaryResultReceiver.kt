package jkapp.zyronator.list.summary

import android.annotation.SuppressLint
import android.os.*

@SuppressLint("ParcelCreator")
class SummaryResultReceiver(val receiver : SummaryReceiver) : ResultReceiver(Handler())
{
    override fun onReceiveResult(resultCode: Int, resultData: Bundle)
    {
        receiver.onReceiveSummaryResult(resultCode, resultData)
    }
}
