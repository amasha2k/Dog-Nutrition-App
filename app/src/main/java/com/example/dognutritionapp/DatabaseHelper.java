package com.example.dognutritionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CustomerDB7.db";
    public static final String TABLE_NAME = "customer";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    public static final String TABLE_PRODUCTS = "product";
    public static final String PRODUCT_COLUMN_ID = "ID";
    public static final String PRODUCT_COLUMN_NAME = "NAME";
    public static final String PRODUCT_COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String PRODUCT_COLUMN_PRICE = "PRICE";
    public static final String PRODUCT_COLUMN_IMAGE = "IMAGE";
    public static final String PRODUCT_COLUMN_CATEGORY = "CATEGORY";

    private static final String TABLE_BUY = "BuyTable";
    private static final String BUY_COLUMN_ID = "id";
    private static final String BUY_COLUMN_PRODUCT_ID = "product_id";
    private static final String BUY_COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String BUY_COLUMN_ADDRESS = "address";
    private static final String BUY_COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final String BUY_COLUMN_CARD_NUMBER = "card_number";
    private static final String BUY_COLUMN_CVV = "cvv";
    private static final String BUY_COLUMN_QUANTITY = "quantity";
    private static final String BUY_COLUMN_TOTAL_PRICE = "total_price";


    public static final String TABLE_REVIEWS = "reviews";
    public static final String REVIEW_COLUMN_ID = "id";
    public static final String REVIEW_COLUMN_CUSTOMER_EMAIL = "customer_email";
    public static final String REVIEW_COLUMN_NAME = "name";
    public static final String REVIEW_COLUMN_COMMENT = "comment";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PASSWORD TEXT)");

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + PRODUCT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PRODUCT_COLUMN_NAME + " TEXT,"
                + PRODUCT_COLUMN_DESCRIPTION + " TEXT,"
                + PRODUCT_COLUMN_PRICE + " REAL,"
                + PRODUCT_COLUMN_IMAGE + " BLOB,"
                + PRODUCT_COLUMN_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_BUY_TABLE = "CREATE TABLE " + TABLE_BUY + " (" +
                BUY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BUY_COLUMN_PRODUCT_ID + " INTEGER, " +
                BUY_COLUMN_CUSTOMER_NAME + " TEXT, " +
                BUY_COLUMN_ADDRESS + " TEXT, " +
                BUY_COLUMN_MOBILE_NUMBER + " TEXT, " +
                BUY_COLUMN_CARD_NUMBER + " TEXT, " +
                BUY_COLUMN_CVV + " TEXT, " +
                BUY_COLUMN_QUANTITY + " INTEGER, " +
                BUY_COLUMN_TOTAL_PRICE + " REAL)";
        db.execSQL(CREATE_BUY_TABLE);

        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + " (" +
                REVIEW_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REVIEW_COLUMN_CUSTOMER_EMAIL + " TEXT, " +
                REVIEW_COLUMN_NAME + " TEXT, " +
                REVIEW_COLUMN_COMMENT + " TEXT)";
        db.execSQL(CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);

        onCreate(db);
    }

    public boolean insertReview(String customerEmail, String name, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REVIEW_COLUMN_CUSTOMER_EMAIL, customerEmail);
        contentValues.put(REVIEW_COLUMN_NAME, name);
        contentValues.put(REVIEW_COLUMN_COMMENT, comment);
        long result = db.insert(TABLE_REVIEWS, null, contentValues);
        return result != -1;
    }

    // Get All Reviews
    public List<Review> getAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REVIEWS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setId(cursor.getInt(cursor.getColumnIndex(REVIEW_COLUMN_ID)));
                review.setCustomerEmail(cursor.getString(cursor.getColumnIndex(REVIEW_COLUMN_CUSTOMER_EMAIL)));
                review.setName(cursor.getString(cursor.getColumnIndex(REVIEW_COLUMN_NAME)));
                review.setComment(cursor.getString(cursor.getColumnIndex(REVIEW_COLUMN_COMMENT)));
                reviewList.add(review);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return reviewList;
    }

    public boolean insertData(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
    }




    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    /*public boolean updateData(String id, String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        int result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return result > 0;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }*/

    public boolean insertProduct(String name, String description, double price, byte[] image, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLUMN_NAME, name);
        contentValues.put(PRODUCT_COLUMN_DESCRIPTION, description);
        contentValues.put(PRODUCT_COLUMN_PRICE, price);
        contentValues.put(PRODUCT_COLUMN_IMAGE, image);
        contentValues.put(PRODUCT_COLUMN_CATEGORY, category);
        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        return result != -1;
    }

    public Product getProductByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, PRODUCT_COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndex(PRODUCT_COLUMN_PRICE));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(PRODUCT_COLUMN_IMAGE));
                String category = cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_CATEGORY));
                cursor.close();
                return new Product(id, name, description, price, image, category);
            }
            cursor.close();
        }
        return null;
    }

    public boolean updateProduct(int id, String name, String description, double price, byte[] image, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLUMN_NAME, name);
        contentValues.put(PRODUCT_COLUMN_DESCRIPTION, description);
        contentValues.put(PRODUCT_COLUMN_PRICE, price);
        contentValues.put(PRODUCT_COLUMN_IMAGE, image);
        contentValues.put(PRODUCT_COLUMN_CATEGORY, category);

        int result = db.update(TABLE_PRODUCTS, contentValues, PRODUCT_COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, PRODUCT_COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public List<Food> getFoodsByCategory(String category) {
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PRODUCTS,
                null,
                "category = ?",
                new String[]{category},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_COLUMN_ID)));
                food.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_NAME)));
                food.setDescription(cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION)));
                food.setPrice(cursor.getDouble(cursor.getColumnIndex(PRODUCT_COLUMN_PRICE)));
                food.setCategory(cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_CATEGORY)));
                food.setImage(cursor.getBlob(cursor.getColumnIndex(PRODUCT_COLUMN_IMAGE)));
                foodList.add(food);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return foodList;
    }

    public List<Nutrition> getNutritionByCategory(String category) {
        List<Nutrition> nutritionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PRODUCTS,
                null,
                "category = ?",
                new String[]{category},
                null,
                null,
                null
        );

        // Check if there are results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Nutrition nutrition = new Nutrition(
                        cursor.getInt(cursor.getColumnIndex(PRODUCT_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(PRODUCT_COLUMN_PRICE)),
                        cursor.getBlob(cursor.getColumnIndex(PRODUCT_COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_CATEGORY))
                );
                nutritionList.add(nutrition);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return nutritionList;
    }
    public List<Other> getOtherByCategory(String category) {
        List<Other> otherList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PRODUCTS,
                null,
                "category = ?",
                new String[]{category},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Other other = new Other(
                        cursor.getInt(cursor.getColumnIndex(PRODUCT_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(PRODUCT_COLUMN_PRICE)),
                        cursor.getBlob(cursor.getColumnIndex(PRODUCT_COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_CATEGORY))
                );
                otherList.add(other);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return otherList;
    }

    public List<Integer> getAllProductIds() {
        List<Integer> productIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{PRODUCT_COLUMN_ID}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                productIds.add(cursor.getInt(cursor.getColumnIndex(PRODUCT_COLUMN_ID)));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return productIds;
    }

    public double getProductPriceById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{PRODUCT_COLUMN_PRICE}, PRODUCT_COLUMN_ID + " = ?", new String[]{String.valueOf(productId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            double price = cursor.getDouble(cursor.getColumnIndex(PRODUCT_COLUMN_PRICE));
            cursor.close();
            return price;
        }

        return 0.0;
    }

    public long insertBuyRecord(int productId, String customerName, String address, String mobileNumber, String cardNumber, String cvv, int quantity, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUY_COLUMN_PRODUCT_ID, productId);
        contentValues.put(BUY_COLUMN_CUSTOMER_NAME, customerName);
        contentValues.put(BUY_COLUMN_ADDRESS, address);
        contentValues.put(BUY_COLUMN_MOBILE_NUMBER, mobileNumber);
        contentValues.put(BUY_COLUMN_CARD_NUMBER, cardNumber);
        contentValues.put(BUY_COLUMN_CVV, cvv);
        contentValues.put(BUY_COLUMN_QUANTITY, quantity);
        contentValues.put(BUY_COLUMN_TOTAL_PRICE, totalPrice);

        long result = -1;
        try {
            result = db.insert(TABLE_BUY, null, contentValues);
            if (result == -1) {
                Log.e("DatabaseHelper", "Failed to insert record into " + TABLE_BUY);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error inserting record: " + e.getMessage());
        }
        return result;
    }

    public List<Buy> getAllSales() {
        List<Buy> buyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BUY, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Buy buy = new Buy(
                        cursor.getString(cursor.getColumnIndex(BUY_COLUMN_CUSTOMER_NAME)),
                        cursor.getString(cursor.getColumnIndex(BUY_COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(BUY_COLUMN_MOBILE_NUMBER)),
                        cursor.getInt(cursor.getColumnIndex(BUY_COLUMN_PRODUCT_ID)),
                        cursor.getInt(cursor.getColumnIndex(BUY_COLUMN_QUANTITY)),
                        cursor.getDouble(cursor.getColumnIndex(BUY_COLUMN_TOTAL_PRICE))
                );
                buyList.add(buy);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return buyList;
    }



    public boolean updateDetails(String oldEmail, String name, String newEmail, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, newEmail);
        contentValues.put(COLUMN_PASSWORD, password);
        int result = db.update(TABLE_NAME, contentValues, COLUMN_EMAIL + " = ?", new String[]{oldEmail});
        return result > 0;
    }


}
