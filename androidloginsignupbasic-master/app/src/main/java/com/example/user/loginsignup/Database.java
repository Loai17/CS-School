package com.example.user.loginsignup;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Database";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create user table
        String CREATE_MANAGER_TABLE = "CREATE TABLE manager ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fullName TEXT, "+
                "username TEXT, "+
                "company TEXT, "+
                "email TEXT, "+
                "password TEXT )";

        String CREATE_JOB_TABLE = "CREATE TABLE job ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "companyId INTEGER, "+ // Secondary---------------------
                "name TEXT, "+
                "location TEXT, "+
                "description TEXT, "+
                "startDate TEXT, "+ //Date instead of TEXT
                "endDate TEXT )"; //Date instead of TEXT

        String CREATE_DAY_TABLE = "CREATE TABLE day ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "companyId INTEGER, "+
                "jobId INTEGER, "+
                "date TEXT, "+ //Date instead of TEXT
                "name TEXT, "+
                "description TEXT )";

        String CREATE_WORKER_TABLE = "CREATE TABLE worker ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "companyId INTEGER, "+
                "name TEXT, "+
                "dob TEXT, "+ //Date instead of TEXT
                "username TEXT, "+
                "password TEXT, "+
                "experience TEXT, "+
                "payment REAL, "+ //Check that REAL = double
                "paymentMethod TEXT, "+
                "photo TEXT )";

        String CREATE_DAYIMAGE_TABLE = "CREATE TABLE dayImage ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dayId INTEGER, "+
                "image TEXT, "+
                "description TEXT )";

        String CREATE_SUPPLY_TABLE = "CREATE TABLE supply ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "image TEXT, "+
                "description TEXT )";

        String CREATE_DAYSUPPLY_TABLE = "CREATE TABLE daySupply ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dayId INTEGER, "+
                "supplyId INTEGER, "+
                "count INTEGER )";

        String CREATE_DAYWORKER_TABLE = "CREATE TABLE dayWorker ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dayId INTEGER, "+
                "workerId INTEGER )";

        String CREATE_COMPANY_TABLE = "CREATE TABLE company ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT )";

        // create userss table
        db.execSQL(CREATE_MANAGER_TABLE);
        db.execSQL(CREATE_JOB_TABLE);
        db.execSQL(CREATE_DAY_TABLE);
        db.execSQL(CREATE_WORKER_TABLE);
        db.execSQL(CREATE_DAYIMAGE_TABLE);
        db.execSQL(CREATE_SUPPLY_TABLE);
        db.execSQL(CREATE_DAYSUPPLY_TABLE);
        db.execSQL(CREATE_DAYWORKER_TABLE);
        db.execSQL(CREATE_COMPANY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS manager");
        db.execSQL("DROP TABLE IF EXISTS job");
        db.execSQL("DROP TABLE IF EXISTS day");
        db.execSQL("DROP TABLE IF EXISTS worker");
        db.execSQL("DROP TABLE IF EXISTS dayImage");
        db.execSQL("DROP TABLE IF EXISTS supply");
        db.execSQL("DROP TABLE IF EXISTS daySupply");
        db.execSQL("DROP TABLE IF EXISTS dayWorker");
        db.execSQL("DROP TABLE IF EXISTS company");

        // create fresh users table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) user + get all users + delete all users
     */

    // users table name
    private static final String TABLE_MANAGER = "manager";

    // users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static final String[] MANAGER_COLUMNS = {KEY_ID,KEY_FULLNAME,KEY_USERNAME,KEY_COMPANY,KEY_EMAIL,KEY_PASSWORD};

    public void addManager(Manager manager){
        Log.d("addManager", manager.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, manager.getFullName()); // get title
        values.put(KEY_USERNAME, manager.getUsername()); // get title
        values.put(KEY_COMPANY, manager.getCompany()); // get title
        values.put(KEY_EMAIL, manager.getEmail()); // get title
        values.put(KEY_PASSWORD, manager.getPassword()); // get title

        // 3. insert
        db.insert(TABLE_MANAGER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Manager getManager(String username){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MANAGER, // a. table
                        MANAGER_COLUMNS, // b. column names
                        " username = ?", // c. selections
                        new String[] { String.valueOf(username) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build user object

        if (cursor.getCount() > 0) {
            Manager manager = new Manager();
            manager.setId(Integer.parseInt(cursor.getString(0)));
            manager.setFullName(cursor.getString(1));
            manager.setUsername(cursor.getString(2));
            manager.setCompany(cursor.getString(3));
            manager.setEmail(cursor.getString(4));
            manager.setPassword(cursor.getString(5));
            Log.d("getManager(" + username + ")", manager.toString());
            return manager;
        }
        else {
            Log.d("getManager(" + username + ")", "null");
            return null;
        }
        // 5. return user
    }

    // Get All users
    public List<Manager> getAllManagers() {
        List<Manager> managers = new LinkedList<Manager>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MANAGER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Manager manager = null;
        if (cursor.moveToFirst()) {
            do {
                manager = new Manager();
                manager.setId(Integer.parseInt(cursor.getString(0)));
                manager.setFullName(cursor.getString(1));
                manager.setUsername(cursor.getString(2));
                manager.setCompany(cursor.getString(3));
                manager.setEmail(cursor.getString(4));
                manager.setPassword(cursor.getString(5));

                // Add user to users
                managers.add(manager);
            } while (cursor.moveToNext());
        }

        Log.d("getAllManagers()", managers.toString());

        // return users
        return managers;
    }

    // Updating single user
    public int updateManager(Manager manager) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("fullName", manager.getFullName()); // get title
        values.put("username", manager.getUsername()); // get author
        values.put("company", manager.getCompany());
        values.put("email", manager.getEmail());
        values.put("password", manager.getPassword());

        // 3. updating row
        int i = db.update(TABLE_MANAGER, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(manager.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single user
    public void deleteManager(Manager manager) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MANAGER,
                KEY_ID+" = ?",
                new String[] { String.valueOf(manager.getId()) });

        // 3. close
        db.close();

        Log.d("deleteManager", manager.toString());
    }

    // Job -----------------------------------------------------------------------
    private static final String TABLE_JOB = "job";

    // Job Table Columns names
    private static final String KEY_ID_JOB = "id";
    private static final String KEY_COMPANYID_JOB = "companyId";
    private static final String KEY_NAME_JOB = "name";
    private static final String KEY_LOCATION_JOB = "location";
    private static final String KEY_DESCRIPTION_JOB = "description";
    private static final String KEY_STARTDATE_JOB = "startDate";
    private static final String KEY_ENDDATE_JOB = "endDate";

    private static final String[] JOB_COLUMNS = {KEY_ID_JOB,KEY_COMPANYID_JOB,KEY_NAME_JOB,KEY_LOCATION_JOB,KEY_DESCRIPTION_JOB,KEY_STARTDATE_JOB,KEY_ENDDATE_JOB};

    public void addJob(Job job){
        Log.d("adJob", job.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID_JOB, job.getId());
        values.put(KEY_COMPANYID_JOB, job.getCompanyId());
        values.put(KEY_NAME_JOB, job.getName());
        values.put(KEY_LOCATION_JOB, job.getLocation());
        values.put(KEY_DESCRIPTION_JOB, job.getDescription());
        values.put(KEY_STARTDATE_JOB, job.getStartDate());
        values.put(KEY_ENDDATE_JOB, job.getEndDate());

        // 3. insert
        db.insert(TABLE_JOB, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Job getJob(String name){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_JOB, // a. table
                        JOB_COLUMNS, // b. column names
                        " name = ?", // c. selections
                        new String[] { String.valueOf(name) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build user object

        if (cursor.getCount() > 0) {
            Job job = new Job();
            job.setId(Integer.parseInt(cursor.getString(0)));
            job.setCompanyId(Integer.parseInt(cursor.getString(1)));
            job.setName(cursor.getString(2));
            job.setLocation(cursor.getString(3));
            job.setDescription(cursor.getString(4));
            job.setStartDate(cursor.getString(5));
            job.setEndDate(cursor.getString(6));
            Log.d("getJob(" + name + ")", job.toString());
            return job;
        }
        else {
            Log.d("getJob(" + name + ")", "null");
            return null;
        }
        // 5. return user
    }

    // Get All users
    public List<Job> getAllJobs() {
        List<Job> jobs = new LinkedList<Job>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_JOB;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Job job = null;
        if (cursor.moveToFirst()) {
            do {
                job = new Job();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setCompanyId(Integer.parseInt(cursor.getString(1)));
                job.setName(cursor.getString(2));
                job.setLocation(cursor.getString(3));
                job.setDescription(cursor.getString(4));
                job.setStartDate(cursor.getString(5));
                job.setEndDate(cursor.getString(6));

                // Add user to users
                jobs.add(job);
            } while (cursor.moveToNext());
        }

        Log.d("getAllJobs()", jobs.toString());

        // return users
        return jobs;
    }

    // Updating single user
    public int updateJob(Job job) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("companyId", job.getCompanyId()); // get title
        values.put("name", job.getName()); // get author
        values.put("location", job.getLocation());
        values.put("description", job.getDescription());
        values.put("startDate", job.getStartDate());
        values.put("endDate", job.getEndDate());

        // 3. updating row
        int i = db.update(TABLE_JOB, //table
                values, // column/value
                KEY_ID_JOB+" = ?", // selections
                new String[] { String.valueOf(job.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single user
    public void deleteJob(Job job) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_JOB,
                KEY_ID_JOB+" = ?",
                new String[] { String.valueOf(job.getId()) });

        // 3. close
        db.close();

        Log.d("deleteJob", job.toString());
    }

}