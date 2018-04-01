package mrc.sad_project;

/**
 * Created by HP on 30-03-2018.
 */

public class Donor {
    String name;
    String phone;
    String blood_group;
    String latitude;
    String longitude;

    Donor()
    {

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Donor(String name, String phone, String blood_group, String latitude, String longitude) {

        this.name = name;
        this.phone = phone;
        this.blood_group = blood_group;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }


}
