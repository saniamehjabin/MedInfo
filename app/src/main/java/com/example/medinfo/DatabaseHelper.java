package com.example.medinfo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Test_DB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PRODUCTS = "products";
    public static final String COL_ID = "_id";
    public static final String COL_PRODUCT_NAME = "productName";
    public static final String COL_PRODUCT_PRICE = "productPrice";
    public static final String COL_PRODUCT_DOSAGE = "productDosage";
    public static final String COL_PRODUCT_IMAGE_URI = "productImageUri";
    public static final String COL_PRODUCT_DESCRIPTION = "productDescription";
    public static final String COL_PRODUCT_SIDE_EFFECTS = "productSideEffects"; // Added this line

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT, " +
                COL_PRODUCT_DESCRIPTION + " TEXT, " +
                COL_PRODUCT_PRICE + " REAL, " +
                COL_PRODUCT_DOSAGE + " INTEGER, " +
                COL_PRODUCT_SIDE_EFFECTS + " TEXT, " +
                COL_PRODUCT_IMAGE_URI + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }


    public boolean insertProduct(String name, double price, String description, int dosage, String side_effects, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_DESCRIPTION, description);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_DOSAGE, dosage);
        values.put(COL_PRODUCT_SIDE_EFFECTS, side_effects);
        values.put(COL_PRODUCT_IMAGE_URI, imageByteArray);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();

        return result != -1;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COL_PRODUCT_NAME + " = ?", new String[]{productName});
    }

    public void updateProduct(int productId, String productName, double price, String description, int dosage, String sideEffects, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_PRODUCT_NAME, productName);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_DESCRIPTION, description);
        values.put(COL_PRODUCT_DOSAGE, dosage);
        values.put(COL_PRODUCT_SIDE_EFFECTS, sideEffects);
        values.put(COL_PRODUCT_IMAGE_URI, productImageByteArray);

        db.update(TABLE_PRODUCTS, values, COL_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COL_PRODUCT_NAME + " = ?", new String[]{productName});
        db.close();
    }
}
