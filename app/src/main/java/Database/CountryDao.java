package Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM country where 1=1")
    List<Country> getAll();

    @Insert()
    void insert (Country db);

    @Query("DELETE FROM country")
    void delete();


}
