package org.codethechange.culturemesh.models;

import java.io.Serializable;

/**
 * Created by nathaniel on 11/10/17.
 */

public class Network extends NetworkLite implements Serializable {
    //TODO: Figure out Room deals with bigints.

    /**
     * We don't have posts, events, and users fields because they are so large and often unnecessary.
     * Instead, pass the id into an API.Get.networkUsers(), API.Get.networkEvents(), or
     * or API.networkPosts() (asynchronously of course) to get these fields.
     * */

    public Network(Place nearLocation, Place fromLocation, long id) {
        super(nearLocation, fromLocation, id);
    }

    public Network(Place nearLocation, Language lang, long id) {
        super(nearLocation, lang, id);
    }

    //No override of toString() because getResources() requires an activity
    //We want to use string xml resources for localization.
    //For more info, check out
    //https://developer.android.com/guide/topics/resources/providing-resources.html
}
