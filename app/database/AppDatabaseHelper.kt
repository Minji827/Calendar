class AppDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "UserDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)")
        db.execSQL("CREATE TABLE Schedule (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, date TEXT, time TEXT, emotion TEXT, content TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS User")
        db.execSQL("DROP TABLE IF EXISTS Schedule")
        onCreate(db)
    }
}
