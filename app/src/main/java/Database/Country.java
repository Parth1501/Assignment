package Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "country")
public class Country implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String flag;

    private String capital;

    private String region;

    private String subregion;

    private int population;

    private String languages;

    private String borders;

    public Country(String name, String flag, String capital, String region, String subregion, int population, String languages, String borders) {
        this.name = name;
        this.flag = flag;
        this.capital = capital;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.languages = languages;
        this.borders = borders;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getCapital() {
        return capital;
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

    public String getLanguages() {
        return languages;
    }

    public String getBorders() {
        return borders;
    }
}
