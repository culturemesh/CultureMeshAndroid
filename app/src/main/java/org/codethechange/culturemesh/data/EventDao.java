package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.codethechange.culturemesh.models.Event;

import java.util.List;

/**
 * Created by Drew Gregory on 2/19/18.
 */
@Dao
public interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addEvent(Event... events);

    @Query("SELECT * FROM event WHERE id=:id")
    public Event getEvent(long id);

    @Query("SELECT * FROM event WHERE networkId=:id")
    public List<Event> getNetworkEvents(long id);
}
