package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Drew Gregory on 2/19/18.
 */
@Dao
public interface EventSubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSubscriptions(EventSubscription... subs);

    @Query("SELECT eventId FROM eventsubscription WHERE userId = :userId")
    public List<Long> getUserEventSubscriptions(long userId);

    @Query("SELECT userId FROM eventsubscription WHERE eventId = :eventId")
    public List<Long> getEventUsers(long eventId);

    @Delete
    public void unsubscribeFromEvent(EventSubscription... eventSubscriptions);
}
