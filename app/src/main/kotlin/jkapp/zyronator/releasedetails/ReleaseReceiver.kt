package jkapp.zyronator.releasedetails

import android.os.Bundle

interface ReleaseReceiver
{
    fun onReceiveDetailResult(resultCode: Int, resultData: Bundle)
}