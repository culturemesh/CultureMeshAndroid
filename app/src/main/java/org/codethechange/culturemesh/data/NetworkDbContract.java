package org.codethechange.culturemesh.data;

import android.provider.BaseColumns;

/**
 * Created by Drew Gregory on 2/12/18.
 */

public final class NetworkDbContract {
    private NetworkDbContract() {

    }

    public static class NetworkDbEntry implements BaseColumns {
        public static final String TABLE_NAME = "networks";
        public static final String COLUMN_NAME_CM_ID = "culturemesh_id";
        //The location info are all id's.
        public static final String COLUMN_NAME_LOCATION_CURR_COUNTRY = "location_current_country";
        public static final String COLUMN_NAME_LOCATION_CURR_REGION = "location_current_region";
        public static final String COLUMN_NAME_LOCATION_CURR_CITY = "location_current_city";
        public static final String COLUMN_NAME_LOCATION_ORIGIN_COUNTRY = "location_origin_country";
        public static final String COLUMN_NAME_LOCATION_ORIGIN_REGION = "location_origin_region";
        public static final String COLUMN_NAME_LOCATION_ORIGIN_CITY = "location_origin_city";
        public static final String COLUMN_NAME_LANGUAGE_ORIGIN_ID = "language_origin_id";
        public static final String COLUMN_NAME_NETWORK_CLASS = "network_class";
        public static final String COLUMN_NAME_DATE_ADDED = "date_added";
        public static final String COLUMN_NAME_IMAGE_LINK = "image_link";


    }
}
