package com.example.android.animation_assignment4;

import android.provider.BaseColumns;

/**
 * Created by CuiCui on 5/2/2016.
 */
public class FdContract {
    public FdContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class FeedEntryC implements BaseColumns {
        public static final String DB_NAME="CC_Database.db";
        public static final String TABLE_NAME = "DB_ChidTable";
        public static final String COL_ID = "entryid";
        public static final String COL_USER = "username";
        public static final String COL_PWD= "password";
        public static final String COL_LEVEL="level";
        public static final String COL_STAGE="stage";
        public static final int DB_VERSION=1;
    }

    /* Inner class that defines the table contents */
    public static abstract class FeedEntryP implements BaseColumns {
        public static final String DB_NAME="CC_Database.db";
        public static final String TABLE_NAME = "DB_ParentTable";
        public static final String COL_ID = "entryid";
        public static final String COL_USER = "username";
        public static final String COL_PWD= "password";
        public static final int DB_VERSION=1;
    }
}
