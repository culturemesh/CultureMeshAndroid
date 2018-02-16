package org.codethechange.culturemesh.data;

import android.provider.BaseColumns;

/**
 * Created by Drew Gregory on 2/15/18.
 */

public class UserDbContract {
    private UserDbContract() {

    }

    public static class UserDbEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        //The location info are all id's.
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_REGISTER_DATE = "register_date";
        public static final String COLUMN_NAME_ROLE = "role";
        public static final String COLUMN_NAME_LAST_LOGIN = "last_login";
        public static final String COLUMN_NAME_GENDER = "gender";
        public static final String COLUMN_NAME_ABOUT_ME= "about_me";
        public static final String COLUMN_NAME_CONFIRMED = "confirmed";
        public static final String COLUMN_NAME_ACT_CODE = "act_code"; //What is this?
        public static final String COLUMN_NAME_IMAGE_LINK = "img_link";
        public static final String COLUMN_NAME_FP_CODE = "fp_code"; //What is this?
    }
}
