package com.example.medha.knowyourcountry;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.medha.knowyourcountry/databases/";

    private static String DB_NAME = "know_your_country";
    public static final String TABLE_NAME = "all_data";
    public static final String COL_ID = "_id";
    public static final String COL_COUNTRYNAME = "country_name";
    public static final String COL_CAPITAL = "capital";
    public static final String COL_LANGUAGE = "language";
    public static final String COL_DEMONYM = "demonym";
    public static final String COL_CURRENCY = "currency";
    public static final String COL_AREA = "area";
    public static final String COL_DIALCODE = "calling_code";
    public static final String COL_POPULATION = "population";
    //String[] clist;


    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String[] getCountryList() {

        String[] clist = new String[10];
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDataBase();
        Cursor c = myDataBase.rawQuery("SELECT "+COL_COUNTRYNAME+" FROM "+TABLE_NAME+"", null);
        int i=0;
        for(c.moveToFirst();c.moveToNext();c.isAfterLast()){
            String s = c.getString(c.getColumnIndex(COL_COUNTRYNAME));
            clist[i] = s;
            i++;
        }
        return clist;
    }

    public String[] getData(int id){
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDataBase();
        int i=0;
        int x = id+1;
        Cursor c = myDataBase.query(TABLE_NAME, new String[]{COL_COUNTRYNAME, COL_CAPITAL, COL_LANGUAGE, COL_DEMONYM, COL_AREA, COL_POPULATION, COL_CURRENCY, COL_DIALCODE}, COL_ID + "=" + x + "", null, null, null, null);
        c.moveToFirst();
        String[] data = new String[]{c.getString(c.getColumnIndex(COL_COUNTRYNAME)), c.getString(c.getColumnIndex(COL_CAPITAL)), c.getString(c.getColumnIndex(COL_LANGUAGE)), c.getString(c.getColumnIndex(COL_DEMONYM)), c.getString(c.getColumnIndex(COL_AREA)), c.getString(c.getColumnIndex(COL_POPULATION)), c.getString(c.getColumnIndex(COL_CURRENCY)), c.getBlob(c.getColumnIndex(COL_DIALCODE)).toString()};

        // close();
     /*   for(c.moveToFirst(); c.moveToNext();c.isAfterLast()){
            data[i] = c.getString(i);
            i+=1;
        }*/
       // c.moveToFirst();
      /*  while(c.isAfterLast())
        {
            data[i] = c.getString();
            i++;
            c.moveToNext();
        }*/
        return data;
    }


    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}