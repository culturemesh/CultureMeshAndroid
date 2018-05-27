package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Drew Gregory on 2/19/18.
 * This database will allow us to get a list of users subscribed to a network and a list of networks
 * that a user is subscribed to.
 */
@Dao
public interface NetworkSubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSubscriptions(NetworkSubscription... subs);

    @Query("SELECT networkId FROM networksubscription WHERE userId = :userId")
    public List<Long> getUserNetworks(long userId);

    @Query("SELECT userId FROM networksubscription WHERE networkId = :networkId")
    public List<Long> getNetworkUsers(long networkId);

    @Delete
    public void deleteNetworkSubscriptions(NetworkSubscription... networkSubscriptions);
}

