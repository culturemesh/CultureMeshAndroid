package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.codethechange.culturemesh.models.Network;

import java.util.List;

/**
 * Created by Drew Gregory on 2/19/18.
 */

//TODO: Consider getting subsets of columns: https://developer.android.com/training/data-storage/room/accessing-data.html

@Dao
public interface NetworkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNetworks(Network... networks);

    @Update
    public void updateNetworks(Network... networks);

    @Delete
    public void deleteNetworks(Network... networks);

    @Query("SELECT * FROM network WHERE id=:id")
    public List<Network> getNetwork(long id);

    @Query("SELECT * FROM network WHERE language_id=:langID AND near_city_id=:nearCityID AND " +
            "near_region_id=:nearRegionID AND near_country_id=:nearCountryID")
    public Network netFromLangAndHome(long langID, long nearCityID, long nearRegionID, long nearCountryID);

    @Query("SELECT * FROM network WHERE from_city_id=:fromCityID AND from_region_id=:fromRegionID AND " +
            "from_country_id=:fromCountryID AND near_city_id=:nearCityID AND " +
            "near_region_id=:nearRegionID AND near_country_id=:nearCountryID")
    public Network netFromLocAndHome(long fromCityID, long fromRegionID, long fromCountryID,
                                     long nearCityID, long nearRegionID, long nearCountryID);
}
