package com.bignerdranch.android.MyReceipts.database;

public class ReceiptDbSchema {
    public static final class ReceiptTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
