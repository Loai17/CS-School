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

    // Manager ------------------------------------------------------------
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
        values.put(KEY_FULLNAME, manager.getFullName());
        values.put(KEY_USERNAME, manager.getUsername());
        values.put(KEY_COMPANY, manager.getCompany());
        values.put(KEY_EMAIL, manager.getEmail());
        values.put(KEY_PASSWORD, manager.getPassword());

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
        Log.d("addJob", job.toString());
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

    // Deleting single job
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

    // Day -----------------------------------------------------------------------
    private static final String TABLE_DAY = "day";

    // Job Table Columns names
    private static final String KEY_ID_DAY = "id";
    private static final String KEY_COMPANYID_DAY = "companyId";
    private static final String KEY_JOBID_DAY = "jobId";
    private static final String KEY_DATE_DAY = "date";
    private static final String KEY_NAME_DAY = "name";
    private static final String KEY_DESCRIPTION_DAY = "description";

    private static final String[] DAY_COLUMNS = {KEY_ID_DAY,KEY_COMPANYID_DAY,KEY_JOBID_DAY,KEY_DATE_DAY,KEY_NAME_DAY,KEY_DESCRIPTION_DAY};

    public void addDay(Day day){
        Log.d("addDay", day.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID_DAY, day.getId());
        values.put(KEY_COMPANYID_DAY, day.getCompanyId());
        values.put(KEY_JOBID_DAY, day.getJobId());
        values.put(KEY_DATE_DAY, day.getDate());
        values.put(KEY_NAME_DAY, day.getName());
        values.put(KEY_DESCRIPTION_DAY, day.getDescription());

        // 3. insert
        db.insert(TABLE_DAY, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Day getDay(String name){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_DAY, // a. table
                        DAY_COLUMNS, // b. column names
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
            Day day = new Day();
            day.setId(Integer.parseInt(cursor.getString(0)));
            day.setCompanyId(Integer.parseInt(cursor.getString(1)));
            day.setJobId(Integer.parseInt(cursor.getString(2)));
            day.setDate(cursor.getString(3));
            day.setName(cursor.getString(4));
            day.setDescription(cursor.getString(5));
            Log.d("getDay(" + name + ")", day.toString());
            return day;
        }
        else {
            Log.d("getDay(" + name + ")", "null");
            return null;
        }
        // 5. return user
    }

    // Get All users
    public List<Day> getAllDays() {
        List<Day> days = new LinkedList<Day>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_DAY;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Day day = null;
        if (cursor.moveToFirst()) {
            do {
                day = new Day();
                day.setId(Integer.parseInt(cursor.getString(0)));
                day.setCompanyId(Integer.parseInt(cursor.getString(1)));
                day.setJobId(Integer.parseInt(cursor.getString(2)));
                day.setDate(cursor.getString(3));
                day.setName(cursor.getString(4));
                day.setDescription(cursor.getString(5));

                // Add day to days
                days.add(day);
            } while (cursor.moveToNext());
        }

        Log.d("getAllDays()", days.toString());

        // return users
        return days;
    }

    // Updating single user
    public int updateDay(Day day) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("companyId", day.getCompanyId()); // get title
        values.put("jobId", day.getJobId()); // get author
        values.put("date", day.getDate());
        values.put("name", day.getName());
        values.put("description", day.getDescription());

        // 3. updating rowd
        int i = db.update(TABLE_DAY, //table
                values, // column/value
                KEY_ID_DAY+" = ?", // selections
                new String[] { String.valueOf(day.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single day
    public void deleteDay(Day day) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_DAY,
                KEY_ID_DAY+" = ?",
                new String[] { String.valueOf(day.getId()) });

        // 3. close
        db.close();

        Log.d("deleteDay", day.toString());
    }

    // Worker -----------------------------------------------------------------------
    private static final String TABLE_WORKER = "worker";

    // Job Table Columns names
    private static final String KEY_ID_WORKER = "id";
    private static final String KEY_COMPANYID_WORKER = "companyId";
    private static final String KEY_NAME_WORKER = "name";
    private static final String KEY_DOB_WORKER = "dob";
    private static final String KEY_USERNAME_WORKER = "username";
    private static final String KEY_PASSWORD_WORKER = "password";
    private static final String KEY_EXPERIENCE_WORKER = "experience";
    private static final String KEY_PAYMENT_WORKER = "payment";
    private static final String KEY_PAYMENTMETHOD_WORKER = "paymentMethod";
    private static final String KEY_PHOTO_WORKER = "photo";

    private static final String[] WORKER_COLUMNS = {KEY_ID_WORKER,KEY_COMPANYID_WORKER,KEY_NAME_WORKER,KEY_DOB_WORKER,KEY_USERNAME_WORKER,KEY_PASSWORD_WORKER,KEY_EXPERIENCE_WORKER,KEY_PAYMENT_WORKER,KEY_PAYMENTMETHOD_WORKER,KEY_PHOTO_WORKER};

    public void addWorker(Worker worker){
        Log.d("addWorker", worker.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID_WORKER, worker.getId());
        values.put(KEY_COMPANYID_WORKER, worker.getCompanyId());
        values.put(KEY_NAME_WORKER, worker.getName());
        values.put(KEY_DOB_WORKER, worker.getDob());
        values.put(KEY_USERNAME_WORKER, worker.getUsername());
        values.put(KEY_PASSWORD_WORKER, worker.getPassword());
        values.put(KEY_EXPERIENCE_WORKER, worker.getExperience());
        values.put(KEY_PAYMENT_WORKER, worker.getPayment());
        values.put(KEY_PAYMENTMETHOD_WORKER, worker.getPaymentMethod());
        values.put(KEY_PHOTO_WORKER, worker.getPhoto());

        // 3. insert
        db.insert(TABLE_WORKER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Worker getWorker(String username){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_WORKER, // a. table
                        WORKER_COLUMNS, // b. column names
                        " name = ?", // c. selections
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
            Worker worker = new Worker();
            worker.setId(Integer.parseInt(cursor.getString(0)));
            worker.setCompanyId(Integer.parseInt(cursor.getString(1)));
            worker.setName(cursor.getString(2));
            worker.setDob(cursor.getString(3));
            worker.setUsername(cursor.getString(4));
            worker.setPassword(cursor.getString(5));
            worker.setExperience(cursor.getString(6));
            worker.setPayment(Integer.parseInt(cursor.getString(7)));
            worker.setPaymentMethod(cursor.getString(8));
            worker.setPhoto(cursor.getString(9));
            Log.d("getWorker(" + username + ")", worker.toString());
            return worker;

        }
        else {
            Log.d("getWorker(" + username + ")", "null");
            return null;
        }
        // 5. return user
    }

    // Get All users
    public List<Worker> getAllWorkers() {
        List<Worker> workers = new LinkedList<Worker>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_WORKER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        Worker worker = null;
        if (cursor.moveToFirst()) {
            do {
                worker = new Worker();
                worker.setId(Integer.parseInt(cursor.getString(0)));
                worker.setCompanyId(Integer.parseInt(cursor.getString(1)));
                worker.setName(cursor.getString(2));
                worker.setDob(cursor.getString(3));
                worker.setUsername(cursor.getString(4));
                worker.setPassword(cursor.getString(5));
                worker.setExperience(cursor.getString(6));
                worker.setPayment(Integer.parseInt(cursor.getString(7)));
                worker.setPaymentMethod(cursor.getString(8));
                worker.setPhoto(cursor.getString(9));


                // Add day to days
                workers.add(worker);
            } while (cursor.moveToNext());
        }

        Log.d("getAllWorkers()", workers.toString());

        // return users
        return workers;
    }

    // Updating single user
    public int updateWorker(Worker worker) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("companyId", worker.getCompanyId()); // get title
        values.put("name", worker.getName()); // get author
        values.put("dob", worker.getDob());
        values.put("username", worker.getUsername());
        values.put("password", worker.getPassword());
        values.put("experience", worker.getExperience());
        values.put("payment", worker.getPayment());
        values.put("paymentMethod", worker.getPaymentMethod());
        values.put("photo", worker.getPhoto());


        // 3. updating rowd
        int i = db.update(TABLE_WORKER, //table
                values, // column/value
                KEY_ID_DAY+" = ?", // selections
                new String[] { String.valueOf(worker.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single day
    public void deleteWorker(Worker worker) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_WORKER,
                KEY_ID_WORKER+" = ?",
                new String[] { String.valueOf(worker.getId()) });

        // 3. close
        db.close();

        Log.d("deleteWorker", worker.toString());
    }

    // Continue DayImage
}