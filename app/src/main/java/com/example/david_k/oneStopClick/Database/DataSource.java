package com.example.david_k.oneStopClick.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.david_k.oneStopClick.ModelLayers.Database.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David_K on 20/10/2017.
 */

public class DataSource {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Product createProduct(Product product) {
        ContentValues values = product.toValues();
        mDatabase.insert(ProductTable.TABLE_PRODUCT, null, values);
        return product;
    }

    public long getProductsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ProductTable.TABLE_PRODUCT);
    }

    public void seedDatabase(List<Product> productList) {
        long numItems = getProductsCount();
        if (numItems == 0) {
            for (Product item :
                    productList) {
                try {
                    createProduct(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Product> getAllItems() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = mDatabase.query(ProductTable.TABLE_PRODUCT, ProductTable.ALL_COLUMNS,
                null, null, null, null, ProductTable.COLUMN_NAME);

        while (cursor.moveToNext()) {
            Product product = new Product();
            product.setId(cursor.getInt(
                    cursor.getColumnIndex(ProductTable.COLUMN_ID)));
            product.setName(cursor.getString(
                    cursor.getColumnIndex(ProductTable.COLUMN_NAME)));
            product.setPrice(cursor.getInt(
                    cursor.getColumnIndex(ProductTable.COLUMN_PRICE)));
            product.setImageName(cursor.getString(
                    cursor.getColumnIndex(ProductTable.COLUMN_IMAGE_NAME)));
            product.setImageId(cursor.getInt(
                    cursor.getColumnIndex(ProductTable.COLUMN_IMAGE_ID)));

            products.add(product);
        }
        cursor.close();
        return products;
    }
}
