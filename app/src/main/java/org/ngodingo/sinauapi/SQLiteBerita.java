package org.ngodingo.sinauapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.ngodingo.sinauapi.model.BeritaModel;

import android.database.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteBerita extends SQLiteOpenHelper {

    private static final String TAG = SQLiteBerita.class.getSimpleName();

    //Versi Database
    private static final int DATABASE_VERSION = 1;

    //Nama Database
    private static final String DATABASE_NAME = "berita_db";

    //Nama Tabel
    private static final String TABLE_BERITA = "table_berita";

    //Kolom - kolom tabel
    public static final String KEY_ID = "_id";
    public static final String KEY_JUDUL = "judul";
    public static final String KEY_DESC = "deskripsi";

    public SQLiteBerita(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BERITA_TABLE = "CREATE TABLE " + TABLE_BERITA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_JUDUL + " TEXT,"
                + KEY_DESC + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_BERITA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BERITA);
        onCreate(sqLiteDatabase);
    }

    public void addData(String judul, String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();

        //ngecek datanya udah ada atau belum
        if (!CheckData(judul)) {
            // kalau belum ada berarti ngeadd data baru
            // ketika databasenya kosong

            ContentValues values = new ContentValues();
            values.put(KEY_JUDUL, judul);
            values.put(KEY_DESC, deskripsi);

            //id tidak perlu ditambahkan disini
            //karena akan menambahkan secara otomatis

            Log.d(TAG, "BERHASIL MEMASUKKAN KE LOCAL DATA");

            db.insert(TABLE_BERITA, null, values);


        } else {
            // kalau udah ada datanya delete yang udah ada
            // abis itu di add lagi
            deleteAll(db);
            ContentValues values = new ContentValues();
            values.put(KEY_JUDUL, judul);
            values.put(KEY_DESC, deskripsi);

            Log.d(TAG, "BERHASIL UPDATE DATA DI LOCAL");

            db.insert(TABLE_BERITA, null, values);

        }

    }

    private void deleteAll(SQLiteDatabase db) {
        db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE from " + TABLE_BERITA);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "GAGAL DELETE");
        } finally {
            db.endTransaction();
        }
    }

    public List<BeritaModel> getData() {

        // ngambil data untuk nantinya di set di list

        List<BeritaModel> beritaList = new ArrayList<>();
        String BERITA_SELECT_QUERY = "SELECT * FROM " + TABLE_BERITA;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(BERITA_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    BeritaModel beritaModel = new BeritaModel();
                    beritaModel.judul = cursor.getString(cursor.getColumnIndex(KEY_JUDUL));
                    beritaModel.deskripsi = cursor.getString(cursor.getColumnIndex(KEY_DESC));
//                    beritaModel.idBerita = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                    beritaList.add(beritaModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error kie pas nge get e");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return beritaList;

    }

    private boolean CheckData(String judul) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BERITA + " WHERE judul ='" + judul + "'";
        Cursor cursor = sqldb.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }
}
