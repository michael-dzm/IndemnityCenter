package com.sh3h.localprovider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sh3h.localprovider.greendaoDao.BranchAcceptDao;
import com.sh3h.localprovider.greendaoDao.BudgetDao;
import com.sh3h.localprovider.greendaoDao.ConstructionAcceptDao;
import com.sh3h.localprovider.greendaoDao.DaoMaster;
import com.sh3h.localprovider.greendaoDao.MultiMediaDao;
import com.sh3h.localprovider.greendaoDao.OperateDao;
import com.sh3h.localprovider.greendaoDao.PatrolDao;
import com.sh3h.localprovider.greendaoDao.ProjectAcceptDao;
import com.sh3h.localprovider.greendaoDao.ProjectDao;

import org.greenrobot.greendao.database.Database;

/**
 * 自定义OpenHelper 以便数据库更新
 * Created by dengzhimin on 2017/3/17.
 */

public class DataOpenHelper extends DaoMaster.OpenHelper {

    public static final String TAG = DataOpenHelper.class.getName();

    public DataOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion);
        //APP多版本跨越升级时 必须执行oldVersion与newVersion之间的数据库升级代码
        while (oldVersion++ <= newVersion){
            switch (oldVersion){
                case 1:
                    break;
                case 2:
                    //add column FILE_PATH
                    String tableName = MultiMediaDao.TABLENAME;
                    String columnName = MultiMediaDao.Properties.FILE_PATH.columnName;
                    if(!checkColumnExists(db, tableName, columnName)){
                        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT;";
                        db.execSQL(sql);
                    }
                    break;
                case 3:
                    //add column FILE_TIME
                    tableName = MultiMediaDao.TABLENAME;
                    columnName = MultiMediaDao.Properties.FILE_TIME.columnName;
                    if(!checkColumnExists(db, tableName, columnName)){
                        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " INTEGER;";
                        db.execSQL(sql);
                    }
                    break;
                case 4:
                    db.beginTransaction();
                    try{
                        //新增施工方案验收表
                        ConstructionAcceptDao.createTable(db, true);
                        //删除支管验收表字段 保留原有数据
                        //先按照字段需求复制出一张TEMP表 然后删除旧表 再重命名TEMP表
                        StringBuffer createTable = new StringBuffer()
                                .append("CREATE TABLE TEMP AS SELECT ")
                                .append(BranchAcceptDao.Properties.ACCEPT_ID.columnName).append(",")
                                .append(BranchAcceptDao.Properties.SERVER_ID.columnName).append(",")
                                .append(BranchAcceptDao.Properties.PROJECT_ID.columnName).append(",")
                                .append(BranchAcceptDao.Properties.BUDGET_ID.columnName).append(",")
                                .append(BranchAcceptDao.Properties.CHECK_SITUATION.columnName).append(",")
                                .append(BranchAcceptDao.Properties.LEAK_SITUATION.columnName).append(",")
                                .append(BranchAcceptDao.Properties.LEAK_REFORM.columnName).append(",")
                                .append(BranchAcceptDao.Properties.OPERATOR.columnName).append(",")
                                .append(BranchAcceptDao.Properties.OPERATE_TIME.columnName).append(",")
                                .append(BranchAcceptDao.Properties.LONGITUDE.columnName).append(",")
                                .append(BranchAcceptDao.Properties.LATITUDE.columnName).append(",")
                                .append(BranchAcceptDao.Properties.REMARK.columnName).append(",")
                                .append(BranchAcceptDao.Properties.UPLOAD.columnName).append(",")
                                .append(BranchAcceptDao.Properties.ACCEPT_TYPE.columnName)
                                .append(" FROM ")
                                .append(BranchAcceptDao.TABLENAME);
                        db.execSQL(createTable.toString());
                        BranchAcceptDao.dropTable(db, true);
                        db.execSQL("ALTER TABLE TEMP RENAME TO " + BranchAcceptDao.TABLENAME);
                        //验收数据作废 清空预算表、支管验收表、工程验收表数据
                        //并重置表自增列
                        db.execSQL("DELETE FROM " + BudgetDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + BudgetDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + BranchAcceptDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + BranchAcceptDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + ProjectAcceptDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + ProjectAcceptDao.TABLENAME + "'");
                        db.setTransactionSuccessful();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        db.endTransaction();
                    }
                    break;
                case 5:
                    try {
                        //BUG:数据库升级到版本4导致BZ_BRANCH_ACCEPT表结构出错，验收数据保存时ACCEPT_ID为null
                        //解决办法：清空工程表、预算表、施工验收表、支管验收表、工程验收表、操作表、巡视表、多媒体表数据，删除并新建支管验收表
                        db.beginTransaction();
                        db.execSQL("DELETE FROM " + ProjectDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + ProjectDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + BudgetDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + BudgetDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + ConstructionAcceptDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + ConstructionAcceptDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + BranchAcceptDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + BranchAcceptDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + ProjectAcceptDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + ProjectAcceptDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + OperateDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + OperateDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + PatrolDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + PatrolDao.TABLENAME + "'");
                        db.execSQL("DELETE FROM " + MultiMediaDao.TABLENAME);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + MultiMediaDao.TABLENAME + "'");
                        BranchAcceptDao.dropTable(db, false);
                        BranchAcceptDao.createTable(db, false);
                        //BUG:APP多版本跨越升级导致多媒体字段缺失，删除并新建多媒体表
                        MultiMediaDao.dropTable(db, false);
                        MultiMediaDao.createTable(db, false);
                        db.setTransactionSuccessful();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        db.endTransaction();
                    }
                    break;
            }
        }
    }

    /**
     * 检测表字段存在
     * @param db
     * @param tableName
     * @param columnName
     * @return
     */
    private boolean checkColumnExists(Database db, String tableName, String columnName){
        Cursor cursor = null;
        try{
            cursor = db.rawQuery( "select * from sqlite_master where name = ? and sql like ?", new String[]{tableName , "%" + columnName + "%"});
            return cursor != null && cursor.moveToFirst();
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return false;
    }
}
