package my.edu.utar.periodtracker;

import android.provider.BaseColumns;

public final class SymptomContract {

    private SymptomContract() {} // private constructor to prevent accidental instantiation

    public static class SymptomEntry implements BaseColumns {
        public static final String TABLE_SYMPTOMS = "symptom";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_SEVERITY = "severity";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DURATION = "duration";

        // Define data types for each column
        public static final String TEXT_TYPE = " TEXT";
        public static final String INTEGER_TYPE = " INTEGER";
        public static final String COMMA_SEP = ",";

        // Define SQL statement to create the table
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_SYMPTOMS + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_IMAGE + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_SEVERITY + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_NOTES + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_DURATION + INTEGER_TYPE + ")";

        // Define SQL statement to delete the table
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_SYMPTOMS;
    }
}
