package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Country  implements Parcelable {
    String name;
    String capital;
    String flag;
    String region;
    String subregion;
    int population;
    ArrayList<String> borders;
    ArrayList<Languages> languages;

    public Country(String name, String capital, String flag, String region, String subregion, int population, ArrayList<String> borders, ArrayList<Languages> languages) {
        this.name = name;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    protected Country(Parcel in) {
        name = in.readString();
        capital = in.readString();
        flag = in.readString();
        region = in.readString();
        subregion = in.readString();
        population = in.readInt();
        borders = in.createStringArrayList();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(capital);
        parcel.writeString(flag);
        parcel.writeString(region);
        parcel.writeString(subregion);
        parcel.writeInt(population);
        parcel.writeStringList(borders);
    }

    public static class Languages {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public ArrayList<Languages> getLanguages() {
        return languages;
    }
}

