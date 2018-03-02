package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.Region;

/**
 * Created by Drew Gregory on 2/23/18.
 */

@Dao
public interface RegionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRegions(Region... Region);

    @Query("SELECT * FROM region WHERE id = :id")
    public Region getRegion(long id);
}
