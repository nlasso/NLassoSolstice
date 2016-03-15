package com.solstice.contactList;

import org.json.JSONException;
import org.json.JSONObject;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.repacked.antlr.v4.runtime.atn.LexerATNSimulator;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;


public class User extends BaseObservable implements Serializable{

    private String NAME_KEY = "name";
    private String ID_KEY = "employeeId";
    private String COMPANY_KEY = "company";
    private String DETAILS_URL_KEY = "detailsURL";
    private String IMAGE_KEY = "smallImageURL";
    private String BIRTHDATE_KEY = "birthdate";
    private String PHONE_KEY = "phone";
    private String ADDRESS_KEY = "address";
    private String FAVORITE_KEY = "favorite";
    private String LARGE_IMAGE_KEY = "largeImageURL";
    private String EMAIL_KEY = "email";
    private String WEBSITE_KEY = "website";

    // Properties from User Class
    private String Name;
    private int Id;
    private String Company;
    private String DetailsUrl;
    private String ImageUrl;
    private Date BirthDate;
    private Phone phones;
    private Boolean IsFavorite;
    private String largeImageURL;
    private String Email;
    private String WebSite;
    private Address address;
    private Boolean hasDetails;

    // Phone class define.
    public static class Phone extends BaseObservable implements Serializable{
        //PHONE KEYS
        private static String PHONE_WORK_KEY = "work";
        private static String PHONE_HOME_KEY = "home";
        private static String PHONE_MOBILE_KEY = "mobile";

        private String Work;
        private String Home;
        private String Mobile;

        public Phone(String work, String home, String mobile){
            Work = work;
            Home = home;
            Mobile = mobile;
        }

        public Phone(JSONObject obj) throws JSONException{
            Work = obj.has(PHONE_WORK_KEY) ? obj.getString(PHONE_WORK_KEY) : null;
            Home = obj.has(PHONE_HOME_KEY) ? obj.getString(PHONE_HOME_KEY) : null;
            Mobile = obj.has(PHONE_MOBILE_KEY) ? obj.getString(PHONE_MOBILE_KEY) : null;
        }

        @Bindable
        public String getWork() {
            return Work;
        }

        public void setWork(String work) {
            Work = work;
        }

        @Bindable
        public String getHome() {
            return Home;
        }

        public void setHome(String home) {
            Home = home;
        }

        @Bindable
        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }
    }

    // Address Class.
    public static class Address extends BaseObservable implements Serializable{
        //ADDRESS KEYS
        private String STREET_KEY = "street";
        private String CITY_KEY = "city";
        private String STATE_KEY = "state";
        private String COUNTRY_KEY = "country";
        private String ZIP_KEY = "zip";
        private String LATITUDE_KEY = "latitude";
        private String LONGITUDE_KEY = "longitude";
        //PROPERTIES
        private String Street;
        private String City;
        private String State;
        private String Country;
        private String Zip;
        private double Latitude;
        private double Longitude;

        //JSON Constructor
        public Address(JSONObject obj) throws JSONException{
            Street = obj.has(STREET_KEY) ? obj.getString(STREET_KEY) : null;
            City = obj.has(CITY_KEY) ? obj.getString(CITY_KEY) : null;
            State = obj.has(STATE_KEY) ? obj.getString(STATE_KEY) : null;
            Country = obj.has(COUNTRY_KEY) ? obj.getString(COUNTRY_KEY) : null;
            Zip = obj.has(ZIP_KEY) ? obj.getString(ZIP_KEY) : null;
            Latitude = obj.has(LATITUDE_KEY) ? obj.getDouble(LATITUDE_KEY) : null;
            Longitude = obj.has(LONGITUDE_KEY) ? obj.getDouble(LONGITUDE_KEY) : null;
        }

        @Bindable
        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }

        @Bindable
        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }
        @Bindable
        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }
        @Bindable
        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }
        @Bindable
        public String getZip() {
            return Zip;
        }

        public void setZip(String zip) {
            Zip = zip;
        }
        @Bindable
        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }
        @Bindable
        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }
    }



    public  User(String name, int id, String company, String detailsUrl, String imageUrl, String birthdate){
        Name = name;
        Id = id;
        Company = company;
        DetailsUrl = detailsUrl;
        ImageUrl = imageUrl;
        BirthDate = new Date(Long.getLong(birthdate));
        phones = null;
    }

    public User(JSONObject obj) throws JSONException{
        Name = obj.has(NAME_KEY) ? obj.getString(NAME_KEY) : null;
        Id = obj.has(ID_KEY) ? obj.getInt(ID_KEY) : null;
        Company = obj.has(COMPANY_KEY) ? obj.getString(COMPANY_KEY) : null;
        DetailsUrl = obj.has(DETAILS_URL_KEY) ? obj.getString(DETAILS_URL_KEY) : null;
        ImageUrl = obj.has(IMAGE_KEY) ? obj.getString(IMAGE_KEY) : null;
        BirthDate = new Date(obj.has(BIRTHDATE_KEY) ? obj.getLong(BIRTHDATE_KEY) : null);
        phones = obj.has(PHONE_KEY) ? new Phone(obj.getJSONObject(PHONE_KEY)) : null;
        hasDetails = false;
    }

    public void updateUser(JSONObject obj) throws JSONException{
        address = new Address(obj.getJSONObject(ADDRESS_KEY));
        IsFavorite = obj.has(FAVORITE_KEY) ? obj.getBoolean(FAVORITE_KEY) : null;
        largeImageURL = obj.has(LARGE_IMAGE_KEY) ? obj.getString(LARGE_IMAGE_KEY) : null;
        Email = obj.has(EMAIL_KEY) ? obj.getString(EMAIL_KEY) : null;
        WebSite = obj.has(WEBSITE_KEY) ? obj.getString(WEBSITE_KEY) : null;

    }

    @Bindable
    public String getName() {return Name;}

    public void setName(String name) {Name = name;}

    @Bindable
    public int getId() {return Id;}

    public void setId(int id) {Id = id;}

    @Bindable
    public String getCompany() {return Company;}

    public void setCompany(String company) {Company = company;}

    @Bindable
    public String getDetailsUrl() {return DetailsUrl;}

    public void setDetailsUrl(String detailsUrl) {DetailsUrl = detailsUrl;}

    @Bindable
    public String getImageUrl() {return ImageUrl;}

    public void setImageUrl(String imageUrl) {ImageUrl = imageUrl;}

    @Bindable
    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }

    @Bindable
    public Boolean getIsFavorite() {return IsFavorite;}

    public void setIsFavorite(Boolean isFavorite) {IsFavorite = isFavorite;}

    @Bindable
    public Phone getPhones() {return phones;}

    public void setPhones(Phone phones) {this.phones = phones;}

    @Bindable
    public String getLargeImageURL() {return largeImageURL;}

    public void setLargeImageURL(String largeImageURL) {this.largeImageURL = largeImageURL;}

    @Bindable
    public String getEmail() {return Email;}

    public void setEmail(String email) {Email = email;}

    @Bindable
    public String getWebSite() {return WebSite;}

    public void setWebSite(String webSite) {WebSite = webSite;}

    @Bindable
    public Address getAddress() {return address;}

    public void setAddress(Address address) {this.address = address;}

    @Bindable
    public Boolean getHasDetails() {return hasDetails;}

    public void setHasDetails(Boolean hasDetails) {this.hasDetails = hasDetails;}
}
