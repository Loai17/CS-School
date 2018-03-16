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

        // create userss table
        db.execSQL(CREATE_MANAGER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS manager");

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
}