/**
 * @author qiweiwei
 *
 */
package com.sh3h.localprovider;

import android.content.Context;

import com.sh3h.localprovider.greendaoDao.DaoMaster;
import com.sh3h.localprovider.greendaoDao.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/**
 * 数据库管理 单例模式
 * 提供数据访问对象{@link #dataProvider}
 */
public class DBManager {

	/** 是否加密数据库 **/
	public static final boolean ISENCRYPTED = false;
	/** 数据库加密密码 **/
	private final static String PASSWORD = "sh3h";

	private static DBManager instance;
	private static IDataProvider dataProvider;

	private static DataOpenHelper openHelper;
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	private static Database db;

	private DBManager(Context context, String path){
		if(openHelper == null){//创建数据库
			openHelper = new DataOpenHelper(context, path, null);
		}
	}

	public static DBManager getInstance(Context context, String path){
		if (instance == null) {
			synchronized (IDataProvider.class){
				if(instance == null){
					instance = new DBManager(context, path);
				}
			}
		}
		return instance;
	}

	public static IDataProvider getDataProvider(Context context, String path){
		if (dataProvider == null) {
			synchronized (IDataProvider.class){
				if(dataProvider == null){
					dataProvider = new DataProviderImpl(initDaoSession(context, path));
				}
			}
		}
		return dataProvider;
	}

	/**
	 * init DataOpenHelper
	 * @param context
	 * @param path
	 * @return
	 */
	public static DataOpenHelper initDataOpenHelper(Context context, String path){
		if(openHelper == null){
			getInstance(context, path);
		}
		return openHelper;
	}

	/**
	 * init Database
	 * @param context
	 * @param path
	 * @return
	 */
	public static Database initDatabase(Context context, String path){
		if(db == null){
			//数据库加密
			if(ISENCRYPTED){
				db = initDataOpenHelper(context, path).getEncryptedWritableDb(PASSWORD);
			}else{
				db = initDataOpenHelper(context, path).getWritableDb();
			}
		}
		return db;
	}

	/**
	 * init DaoMaster
	 * @param context
	 * @param path
	 * @return
	 */
	public static DaoMaster initDaoMaster(Context context, String path){
		if(daoMaster == null){
			daoMaster = new DaoMaster(initDatabase(context, path));
		}
		return daoMaster;
	}

	/**
	 * init DaoSession
	 * @param context
	 * @param path
     * @return
     */
	public static DaoSession initDaoSession(Context context, String path){
		if(daoSession == null){
			daoSession = initDaoMaster(context, path).newSession();
		}
		return daoSession;
	}

	/**
	 * close DB
	 */
	public void closeDB(){
		if (db != null) {
			db.close();
			db = null;
		}
		if(daoSession != null){
			daoSession.clear();
			daoSession = null;
		}
		if(openHelper != null){
			openHelper.close();
			openHelper = null;
		}
	}

	/**
	 * clear all table data
	 */
	public void clearDB(){
		if(daoSession != null){
			for(AbstractDao dao : daoSession.getAllDaos()){
				dao.deleteAll();
			}
		}
	}

}
