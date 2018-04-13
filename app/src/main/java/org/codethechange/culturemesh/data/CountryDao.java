package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.Country;

import java.util.List;


/**
 * Created by Drew Gregory on 2/23/18.
 */

@Dao
public interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCountries(Country... countries);

    @Query("SELECT * FROM country WHERE id = :id")
    public Country getCountry(long id);

    @Query("SELECT * FROM country WHERE NAME LIKE :query")
    public List<Country> autoCompleteCountries(String query);
}
