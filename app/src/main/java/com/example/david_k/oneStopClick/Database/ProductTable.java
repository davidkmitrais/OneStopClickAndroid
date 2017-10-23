package com.example.david_k.oneStopClick.Database;

/**
 * Created by David_K on 20/10/2017.
 */

public class ProductTable {
    public static final String TABLE_PRODUCT = "Products";
    public static final String COLUMN_ID = "ProductId";
    public static final String COLUMN_NAME = "ProductName";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_IMAGE_ID = "ImageId";
    public static final String COLUMN_IMAGE_NAME = "ImageName";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_NAME, COLUMN_PRICE, COLUMN_IMAGE_ID, COLUMN_IMAGE_NAME};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_PRODUCT + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PRICE + " REAL," +
                    COLUMN_IMAGE_ID + " REAL," +
                    COLUMN_IMAGE_NAME + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_PRODUCT;
}
