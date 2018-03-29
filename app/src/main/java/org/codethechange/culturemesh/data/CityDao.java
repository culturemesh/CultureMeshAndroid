package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.City;
import org.codethechange.culturemesh.models.Country;

import java.util.List;

/**
 * Created by Drew Gregory on 2/23/18.
 */

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCities(City... cities);

    @Query("SELECT * FROM city WHERE id = :id")
    public City getCity(long id);

    @Query("SELECT * FROM City WHERE NAME LIKE :query")
    public List<City> autoCompleteCities(String query);
}
