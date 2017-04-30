package jkapp.zyronator.listdetails

class ListOfDetails()
{
    val listOfDetails = ArrayList<jkapp.zyronator.listdetails.List>()

    init
    {
        val details1 = jkapp.zyronator.listdetails.List("comment1", "Title1")
        val details2 = jkapp.zyronator.listdetails.List("comment2", "Title2")
        listOfDetails.add(details1)
        listOfDetails.add(details2)
    }
}
