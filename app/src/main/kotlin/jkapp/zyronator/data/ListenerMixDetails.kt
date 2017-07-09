package jkapp.zyronator.data

interface ListenerMixDetails
{
    fun getCurrentMixName() : String

    fun getCurrentMixDate() : String

    fun getNextMixName() : String

    fun getNextMixDate() : String
}