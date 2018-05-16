package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.Operate;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BZ_OPERATE".
*/
public class OperateDao extends AbstractDao<Operate, Long> {

    public static final String TABLENAME = "BZ_OPERATE";

    /**
     * Properties of entity Operate.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property OPERATE_ID = new Property(0, Long.class, "OPERATE_ID", true, "OPERATE_ID");
        public final static Property SERVER_ID = new Property(1, Long.class, "SERVER_ID", false, "SERVER_ID");
        public final static Property PROJECT_ID = new Property(2, long.class, "PROJECT_ID", false, "PROJECT_ID");
        public final static Property OPERATE_TYPE = new Property(3, int.class, "OPERATE_TYPE", false, "OPERATE_TYPE");
        public final static Property START_TIME = new Property(4, long.class, "START_TIME", false, "START_TIME");
        public final static Property STOP_TIME = new Property(5, long.class, "STOP_TIME", false, "STOP_TIME");
        public final static Property RESTART_TIME = new Property(6, long.class, "RESTART_TIME", false, "RESTART_TIME");
        public final static Property END_TIME = new Property(7, long.class, "END_TIME", false, "END_TIME");
        public final static Property OPERATOR = new Property(8, String.class, "OPERATOR", false, "OPERATOR");
        public final static Property OPERATE_TIME = new Property(9, long.class, "OPERATE_TIME", false, "OPERATE_TIME");
        public final static Property LONGITUDE = new Property(10, double.class, "LONGITUDE", false, "LONGITUDE");
        public final static Property LATITUDE = new Property(11, double.class, "LATITUDE", false, "LATITUDE");
        public final static Property REMARK = new Property(12, String.class, "REMARK", false, "REMARK");
        public final static Property UPLOAD = new Property(13, int.class, "UPLOAD", false, "UPLOAD");
    }


    public OperateDao(DaoConfig config) {
        super(config);
    }
    
    public OperateDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BZ_OPERATE\" (" + //
                "\"OPERATE_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: OPERATE_ID
                "\"SERVER_ID\" INTEGER," + // 1: SERVER_ID
                "\"PROJECT_ID\" INTEGER NOT NULL ," + // 2: PROJECT_ID
                "\"OPERATE_TYPE\" INTEGER NOT NULL ," + // 3: OPERATE_TYPE
                "\"START_TIME\" INTEGER NOT NULL ," + // 4: START_TIME
                "\"STOP_TIME\" INTEGER NOT NULL ," + // 5: STOP_TIME
                "\"RESTART_TIME\" INTEGER NOT NULL ," + // 6: RESTART_TIME
                "\"END_TIME\" INTEGER NOT NULL ," + // 7: END_TIME
                "\"OPERATOR\" TEXT," + // 8: OPERATOR
                "\"OPERATE_TIME\" INTEGER NOT NULL ," + // 9: OPERATE_TIME
                "\"LONGITUDE\" REAL NOT NULL ," + // 10: LONGITUDE
                "\"LATITUDE\" REAL NOT NULL ," + // 11: LATITUDE
                "\"REMARK\" TEXT," + // 12: REMARK
                "\"UPLOAD\" INTEGER NOT NULL );"); // 13: UPLOAD
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BZ_OPERATE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Operate entity) {
        stmt.clearBindings();
 
        Long OPERATE_ID = entity.getOPERATE_ID();
        if (OPERATE_ID != null) {
            stmt.bindLong(1, OPERATE_ID);
        }
 
        Long SERVER_ID = entity.getSERVER_ID();
        if (SERVER_ID != null) {
            stmt.bindLong(2, SERVER_ID);
        }
        stmt.bindLong(3, entity.getPROJECT_ID());
        stmt.bindLong(4, entity.getOPERATE_TYPE());
        stmt.bindLong(5, entity.getSTART_TIME());
        stmt.bindLong(6, entity.getSTOP_TIME());
        stmt.bindLong(7, entity.getRESTART_TIME());
        stmt.bindLong(8, entity.getEND_TIME());
 
        String OPERATOR = entity.getOPERATOR();
        if (OPERATOR != null) {
            stmt.bindString(9, OPERATOR);
        }
        stmt.bindLong(10, entity.getOPERATE_TIME());
        stmt.bindDouble(11, entity.getLONGITUDE());
        stmt.bindDouble(12, entity.getLATITUDE());
 
        String REMARK = entity.getREMARK();
        if (REMARK != null) {
            stmt.bindString(13, REMARK);
        }
        stmt.bindLong(14, entity.getUPLOAD());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Operate entity) {
        stmt.clearBindings();
 
        Long OPERATE_ID = entity.getOPERATE_ID();
        if (OPERATE_ID != null) {
            stmt.bindLong(1, OPERATE_ID);
        }
 
        Long SERVER_ID = entity.getSERVER_ID();
        if (SERVER_ID != null) {
            stmt.bindLong(2, SERVER_ID);
        }
        stmt.bindLong(3, entity.getPROJECT_ID());
        stmt.bindLong(4, entity.getOPERATE_TYPE());
        stmt.bindLong(5, entity.getSTART_TIME());
        stmt.bindLong(6, entity.getSTOP_TIME());
        stmt.bindLong(7, entity.getRESTART_TIME());
        stmt.bindLong(8, entity.getEND_TIME());
 
        String OPERATOR = entity.getOPERATOR();
        if (OPERATOR != null) {
            stmt.bindString(9, OPERATOR);
        }
        stmt.bindLong(10, entity.getOPERATE_TIME());
        stmt.bindDouble(11, entity.getLONGITUDE());
        stmt.bindDouble(12, entity.getLATITUDE());
 
        String REMARK = entity.getREMARK();
        if (REMARK != null) {
            stmt.bindString(13, REMARK);
        }
        stmt.bindLong(14, entity.getUPLOAD());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Operate readEntity(Cursor cursor, int offset) {
        Operate entity = new Operate( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // OPERATE_ID
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // SERVER_ID
            cursor.getLong(offset + 2), // PROJECT_ID
            cursor.getInt(offset + 3), // OPERATE_TYPE
            cursor.getLong(offset + 4), // START_TIME
            cursor.getLong(offset + 5), // STOP_TIME
            cursor.getLong(offset + 6), // RESTART_TIME
            cursor.getLong(offset + 7), // END_TIME
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // OPERATOR
            cursor.getLong(offset + 9), // OPERATE_TIME
            cursor.getDouble(offset + 10), // LONGITUDE
            cursor.getDouble(offset + 11), // LATITUDE
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // REMARK
            cursor.getInt(offset + 13) // UPLOAD
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Operate entity, int offset) {
        entity.setOPERATE_ID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSERVER_ID(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setPROJECT_ID(cursor.getLong(offset + 2));
        entity.setOPERATE_TYPE(cursor.getInt(offset + 3));
        entity.setSTART_TIME(cursor.getLong(offset + 4));
        entity.setSTOP_TIME(cursor.getLong(offset + 5));
        entity.setRESTART_TIME(cursor.getLong(offset + 6));
        entity.setEND_TIME(cursor.getLong(offset + 7));
        entity.setOPERATOR(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setOPERATE_TIME(cursor.getLong(offset + 9));
        entity.setLONGITUDE(cursor.getDouble(offset + 10));
        entity.setLATITUDE(cursor.getDouble(offset + 11));
        entity.setREMARK(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUPLOAD(cursor.getInt(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Operate entity, long rowId) {
        entity.setOPERATE_ID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Operate entity) {
        if(entity != null) {
            return entity.getOPERATE_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Operate entity) {
        return entity.getOPERATE_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
