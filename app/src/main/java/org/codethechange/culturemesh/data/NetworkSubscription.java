package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigInteger;

/**
 * Created by Drew Gregory on 2/19/18.
 * This database will allow us to get a list of users subscribed to a network and a list of networks
 * that a user is subscribed to.
 */

@Entity
public class NetworkSubscription {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long userId;
    public long networkId;

    public NetworkSubscription(long userId, long networkId) {
        this.userId = userId;
        this.networkId = networkId;
    }
}
