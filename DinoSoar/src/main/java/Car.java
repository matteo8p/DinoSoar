public class Car
{
    String make;
    String model;
    String year;
    String distance;

    public Car(String make, String model, String year, String distance)
    {
        this.make = make;
        this.model = model;
        this.year = year;
        this.distance = distance;
    }

    public String getMake()
    {return this.make;}

    public String getModel()
    {return this.model;}

    public String getYear()
    {return this.year;}

    public double getDistance()
    {return Double.parseDouble(this.distance);}

}
