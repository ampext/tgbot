package telegram.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location
{
    @JsonProperty("longitude") private float longitude;
    @JsonProperty("latitude") private float latitude;

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }
}
