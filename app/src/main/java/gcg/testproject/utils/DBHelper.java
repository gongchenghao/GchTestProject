package gcg.testproject.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by admin on 2016/12/30.
 */
public class DBHelper extends SQLiteOpenHelper {

    //参数一：上下文  参数二：数据库名称 参数三：工厂  参数四：数据库版本
    public DBHelper(Context context) {
        super(context, "ShoppingCarAndSearch3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //购物车的表
        //创建表
        //表的列名：产品ID，规格ID，产品件数
        String sql = "create table  shoppingcar (_id integer primary key autoincrement,productId_standerdId_productPp varchar(200),productNum varchar(100))";
        db.execSQL(sql);
        //搜索界面的表
        String sql_search = "create table search (_id integer primary key autoincrement,content varchar(500))";
        db.execSQL(sql_search);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
