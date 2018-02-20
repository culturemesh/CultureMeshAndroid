package org.codethechange.culturemesh.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.codethechange.culturemesh.models.Network;

/**
 * Created by Drew Gregory on 2/19/18.
 * TODO: Check out LiveData: https://developer.android.com/reference/android/arch/lifecycle/LiveData.html
 */
@Database(version = 3, entities = {Network.class})
abstract public class CMDatabase extends RoomDatabase{


    abstract public NetworkDao networkDao();
    abstract public UserDao userDao();
    abstract public EventDao eventDao();
    abstract public PostDao postDao();
    abstract public EventSubscriptionDao eventSubscriptionDao();
    abstract public NetworkSubscriptionDao networkSubscriptionDao();

}
