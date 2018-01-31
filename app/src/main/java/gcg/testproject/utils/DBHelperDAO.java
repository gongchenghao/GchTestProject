package gcg.testproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelperDAO {
	private DBHelper helper;

	//创建有参数的构造函数，在创建这个类的对象的时候，就会创建DbHelper的对象
	//DbHelper的构造函数中会创建一个数据库
	public DBHelperDAO(Context context) {
		helper = new  DBHelper(context);
	}

	//在调用这个方法插入数据时，必须将数据封装到ContentValues中
	public  long insert(ContentValues values,String tableName,int flag)  //插入一条
	{

		//获取SQLiteDatabase对象
		SQLiteDatabase db = helper.getReadableDatabase();
		//执行插入操作
		//第一个参数：表名
		//第二个参数：向表中插入一条完全空的记录时写的列名
		long insert = 0;
		if (flag == 1) //搜索界面插入
		{
			insert = db.insert(tableName, null, values);
		}
		if(flag == 0) //购物车插入
		{
			insert = db.insert(tableName,"productId_standerdId_productPp = ? ", values);
		}
		db.close();
		//返回插入了几行
		return insert;

	}

	//删除全部数据，notebooks是表名，String数组中的是列名
	//参数一：表名  参数二：判断是购物车的全部清除还是搜索的，0表示购物车，1表示搜索
	public  void deleteAll (int flag)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		if (flag == 0) //购物车全部清除
		{
			Cursor cursor = db.query("shoppingcar", new String[]{"productId_standerdId_productPp"}, null, null, null, null, null);

			while(cursor.moveToNext())
			{
				db.delete("shoppingcar", "productId_standerdId_productPp = ?", new String[]{cursor.getString(0)});

			}
		}
		if (flag == 1)  //搜索功能全部清除
		{
			Cursor cursor = db.query("search", new String[]{"content"}, null, null, null, null, null);
			while(cursor.moveToNext())
			{
				db.delete("search", "content = ?", new String[]{cursor.getString(0)});
			}
		}
		db.close();		
	}

	public int updata(ContentValues values,String lieMing)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		int num = db.update("shoppingcar",values,"productId_standerdId_productPp = ?",new String[]{lieMing});

		return num;
	}
	//notebooks：表名   参数二：需要查询的内容的数组  参数三：查询列  参数四：查询列的值
	public  String query(String title)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		//参数二：需要查询的列名  参数三：根据哪一列  参数四：根据的那一列的值
		String value = null;
		Cursor cursor = db.query("shoppingcar",new String[]{"productNum"},"productId_standerdId_productPp = ?",new String[]{title},null,null,null,null);

		while (cursor.moveToNext())
		{
			value = cursor.getString(0);
		}
		return value;
	}
	//根据列名删除数据
	public int delete(String productId,String standerdId,String productPp,int flag,String title)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		int  num = 0;
		if (flag == 0) //购物车
		{
			num = db.delete("shoppingcar","productId_standerdId_productPp = ? ",new String[]{productId+"_"+standerdId+"_"+productPp});

			return num;
		}

		if (flag == 1) //搜索
		{
			num = db.delete("search", "content = ? ", new String[]{title});
			return num;
		}
		else
		{
			return  0;
		}
	}

	//查询全部数据，并封装到list集合中
	//参数一：表名  参数二：判断是购物车的查询全部还是搜索的，0表示购物车，1表示搜索
	public HashMap<String,String> findAll_1(int flag)
	{
//		productId_standerdId_productPp
		HashMap<String,String> map= new HashMap<>();
		SQLiteDatabase db = helper.getWritableDatabase();
		if (flag == 0) //购物车
		{
			String[] columns = {"productId_standerdId_productPp","productNum"};
			Cursor cursor = db.query("shoppingcar", columns, null, null, null, null, null);
			if(cursor != null)
			{
				while(cursor.moveToNext())
				{

					String productId_standerdId_productPp = cursor.getString(0);
					String productNum = cursor.getString(1);
					map.put(productId_standerdId_productPp,productNum);
				}
				cursor.close();
			}
			db.close();
		}
		return map;
	}

	public List<String> findAll(int flag)
	{
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = helper.getWritableDatabase();
		if (flag == 1) //搜索
		{
			String[] columns = {"content"};
			Cursor cursor = db.query("search", columns, null, null, null, null, null);
			if(cursor != null)
			{
				while(cursor.moveToNext())
				{

					String productId = cursor.getString(0);
					list.add(productId);
				}
				cursor.close();
			}
			db.close();
		}
		return list;
	}
}







