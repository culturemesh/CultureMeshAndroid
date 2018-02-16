package org.codethechange.culturemesh.data;

import android.provider.BaseColumns;

/**
 * Created by Drew Gregory on 2/16/18.
 */

public class PostDbContract {
    private PostDbContract() {

    }
    public static class PostDbEntry implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        //The location info are all id's.
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_NETWORK_ID = "network_id";
        public static final String COLUMN_NAME_POST_DATE = "post_date";
        public static final String COLUMN_NAME_TEXT = "post_text";
        public static final String COLUMN_NAME_POST_CLASS = "post_class";
        public static final String COLUMN_NAME_POST_ORIGINAL = "post_original";
        public static final String COLUMN_NAME_VID_LINK = "vid_link";
        public static final String COLUMN_NAME_IMG_LINK = "img_link";
    }
}
