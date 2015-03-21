package hu.bute.daai.amorg.bata.cards.model;

public class City
{
    String name;
    int imageRes;

    public City()
    {
    }

    public City(String name, int imageRes)
    {
        this.name = name;
        this.imageRes = imageRes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getImageRes()
    {
        return imageRes;
    }

    public void setImageRes(int imageRes)
    {
        this.imageRes = imageRes;
    }
}
