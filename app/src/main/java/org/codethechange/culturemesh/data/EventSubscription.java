package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Drew Gregory on 2/19/18.
 * This entity is to keep track of user subscriptions to events.
 */
@Entity
public class EventSubscription {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long userId;
    public long eventId;

    public EventSubscription() {

    }

    public EventSubscription(long userId, long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
}
