package jkapp.zyronator.list.summary

import android.os.Bundle

interface SummaryReceiver
{
    fun onReceiveSummaryResult(resultCode: Int, resultData: Bundle)
}
