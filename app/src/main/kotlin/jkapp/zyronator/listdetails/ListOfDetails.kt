package jkapp.zyronator.listdetails

class ListOfDetails()
{
    val listOfDetails = ArrayList<jkapp.zyronator.listdetails.ListItem>()

    init
    {
        val details1 = jkapp.zyronator.listdetails.ListItem("comment1", "Title1")
        val details2 = jkapp.zyronator.listdetails.ListItem("comment2", "Title2")
        listOfDetails.add(details1)
        listOfDetails.add(details2)
    }
}
