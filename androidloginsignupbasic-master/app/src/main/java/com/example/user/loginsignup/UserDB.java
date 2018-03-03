package com.example.user.loginsignup;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "UserDB";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create user table
        String CREATE_USER_TABLE = "CREATE TABLE user ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fullName TEXT, "+
                "username TEXT, "+
                "company TEXT, "+
                "email TEXT, "+
                "password TEXT )";

        // create userss table
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS user");

        // create fresh users table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) user + get all users + delete all users
     */

    // users table name
    private static final String TABLE_USER = "user";

    // users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static final String[] USER_COLUMNS = {KEY_ID,KEY_FULLNAME,KEY_USERNAME,KEY_COMPANY,KEY_EMAIL,KEY_PASSWORD};

    public void addUser(User user){
        Log.d("addUser", user.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, user.getFullName()); // get title
        values.put(KEY_USERNAME, user.getUsername()); // get title
        values.put(KEY_COMPANY, user.getCompany()); // get title
        values.put(KEY_EMAIL, user.getEmail()); // get title
        values.put(KEY_PASSWORD, user.getPassword()); // get title

        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public User getUser(String username){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        USER_COLUMNS, // b. column names
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
            User user = new User();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setFullName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setCompany(cursor.getString(3));
            user.setEmail(cursor.getString(4));
            user.setPassword(cursor.getString(5));
            Log.d("getUser(" + username + ")", user.toString());
            return user;
        }
        else {
            Log.d("getUser(" + username + ")", "null");
            return null;
        }
        // 5. return user
    }

    // Get All users
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFullName(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setCompany(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                user.setPassword(cursor.getString(5));

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers()", users.toString());

        // return users
        return users;
    }

    // Updating single user
    public int updateUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("fullName", user.getFullName()); // get title
        values.put("username", user.getUsername()); // get author
        values.put("company", user.getCompany());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        // 3. updating row
        int i = db.update(TABLE_USER, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single user
    public void deleteUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USER,
                KEY_ID+" = ?",
                new String[] { String.valueOf(user.getId()) });

        // 3. close
        db.close();

        Log.d("deleteUser", user.toString());
    }
}