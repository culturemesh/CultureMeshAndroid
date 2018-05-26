package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.codethechange.culturemesh.models.DatabaseNetwork;

import java.util.List;

/**
 * Created by Drew Gregory on 2/19/18.
 */

//TODO: Consider getting subsets of columns: https://developer.android.com/training/data-storage/room/accessing-data.html

@Dao
public interface NetworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNetworks(DatabaseNetwork... networks);

    @Update
    public void updateNetworks(DatabaseNetwork... networks);

    @Delete
    public void deleteNetworks(DatabaseNetwork... networks);

    @Query("SELECT * FROM DatabaseNetwork WHERE id=:id")
    public List<DatabaseNetwork> getNetwork(long id);

    @Query("SELECT * FROM DatabaseNetwork WHERE languageId=:langID AND near_city_id=:nearCityID AND " +
            "near_region_id=:nearRegionID AND near_country_id=:nearCountryID")
    public DatabaseNetwork netFromLangAndHome(long langID, long nearCityID, long nearRegionID, long nearCountryID);

    @Query("SELECT * FROM DatabaseNetwork WHERE near_city_id=:fromCityID AND near_region_id=:fromRegionID AND " +
            "near_country_id=:fromCountryID AND near_city_id=:nearCityID AND " +
            "near_region_id=:nearRegionID AND near_country_id=:nearCountryID")
    public DatabaseNetwork netFromLocAndHome(long fromCityID, long fromRegionID, long fromCountryID,
                                     long nearCityID, long nearRegionID, long nearCountryID);
}
