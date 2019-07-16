package nothing.impossible.com.nothing;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import nothing.impossible.com.nothing.Model.MindSetQuiz;
import nothing.impossible.com.nothing.Model.Que;
import nothing.impossible.com.nothing.Model.Quote;
import nothing.impossible.com.nothing.Model.Story;

public class Databasehelper extends SQLiteOpenHelper

{
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final String DATABASE_NAME = "favourite.db";
    public final static String DATABASE_PATH ="/data/data/nothing.impossible.com.nothing/databases/";
    public static final int DATABASE_VERSION = 1;

    public static final String ID="id";
    public static final String TITLE="title";
    public static final String STORY_DETAIL="storyDetail";
    public static final String IMAGE="image";
    public static final String DATE="date";
    Story model;
    //Constructor

    public Databasehelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    //Create a empty database on the system
    public void createDatabase() throws IOException
    {
        boolean dbExist = checkDataBase();

        if(dbExist)
        {
//            Log.v("DB Exists", "db exists");
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }

        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }
    }

    //Check database already exist or not
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException
    {
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //delete database
    public void db_delete()
    {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if(file.exists())
        {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    //Open database
    public void openDatabase() throws SQLException
    {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void onCreate(SQLiteDatabase db)
    {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion > oldVersion)
        {
          //  Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }
    }

    //add your public methods for insert, get, delete and update data in database.
    // Getting All Stories
    public ArrayList<Story> getAllStories(Context context) {
    ArrayList<Story> storyList = new ArrayList<Story>();
    // Select All Query
    String selectQuery = "select * from favourite;";
        Databasehelper dbHelper = new Databasehelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Story story = new Story();
            story.setId(cursor.getString(0));
            story.setTitle(cursor.getString(1));
            story.setStoryDetail(cursor.getString(2));
            story.setImage(cursor.getString(3));
            story.setType(cursor.getInt(4));
            story.setTitleEng(cursor.getString(5));
            story.setStoryDetailEng(cursor.getString(6));
            // Adding story to list
            storyList.add(story);
        } while (cursor.moveToNext());
    }

    cursor.close();
        db.close();

        // return student list
    return storyList;
}


public void insertStory(String id, String title,String titleEng,String storyDetail,String storyDetailEng,String image,
                        int type, Context context){
    String insertQuery ="insert into favourite(id,title,storyDetail,image,type,titleEng,storyDetailEng) values('" +id+ "','" +title+ "','"+storyDetail+"','"+image+"','"+type+"','"+titleEng.replace("'","''")+"','"+storyDetailEng.replace("'","''")+"')";
    Databasehelper dbHelper = new Databasehelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    db.execSQL(insertQuery);
    db.close();
}

    public void deleteStory( String id, Context context){
        String deleteQuery = "delete from favourite  where id = '" +id+ "'";
        Databasehelper dbHelper = new Databasehelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();

    }
// Database Operation for Quotes

//add your public methods for insert, get, delete and update data in database.
// Getting All Quotes
public List<Quote> getAllQuote(Context context) {
    List<Quote> quoteList = new ArrayList<Quote>();
    // Select All Query
    String selectQuery = "select * from quotes;";
    Databasehelper dbHelper = new Databasehelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Quote quote = new Quote();
            quote.setId(cursor.getString(0));
            quote.setDetail(cursor.getString(1));
            quote.setAuthor(cursor.getString(2));
            quote.setDetailEng(cursor.getString(3));
            quote.setImage(cursor.getString(4));
            // Adding Quote to list
            quoteList.add(quote);
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    // return Quote list
    return quoteList;
}


    public void insertQuote( String id,String detail,String detailEng,String author,String image, Context context){
        String detailEng1 = detailEng.replaceAll("'","''");
        String insertQuery ="insert into quotes(id,detail,author,detailEng,image) values('"+id+"','" +detail+ "','"+author+"','"+detailEng1+"','"+image+"')";
        Databasehelper dbHelper = new Databasehelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(insertQuery);
        db.close();

    }

    public void deleteQuote( String id, Context context){
        String deleteQuery = "delete from quotes  where id = '"+id+"'";
        Databasehelper dbHelper = new Databasehelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();

    }
    // Getting All Questions
    public List<Que> getAllQuestions(Context context) {
        List<Que> queList = new ArrayList<Que>();
        // Select All Query
        String selectQuery = "select * from ProvokingThoughtsQues;";
        Databasehelper dbHelper = new Databasehelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Que que = new Que();
                que.setId(cursor.getInt(0));
                que.setQue(cursor.getString(1));
                // Adding Question to list
                queList.add(que);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return student list
        return queList;
    }
    // Getting All Questions For Quiz Game
    public List<MindSetQuiz> getAllQuizQuestions(Context context) {
        List<MindSetQuiz> queList = new ArrayList<MindSetQuiz>();
        // Select All Query
        String selectQuery = "select *from MindSetQuiz;";
        Databasehelper dbHelper = new Databasehelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int strAgree,agree,mostAgree;
                int mostDisgree,disagree,strDisagree;

                MindSetQuiz que = new MindSetQuiz();
                que.setId(cursor.getInt(0));
                que.setQuestions(cursor.getString(1));
                que.setStrAgree(cursor.getInt(2));
                que.setAgree(cursor.getInt(3));
                que.setMostAgree(cursor.getInt(4));
                que.setMostDisgree(cursor.getInt(5));
                que.setDisagree(cursor.getInt(6));
                que.setStrDisagree(cursor.getInt(7));

                // Adding Question to list
                queList.add(que);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return student list
        return queList;
    }
}