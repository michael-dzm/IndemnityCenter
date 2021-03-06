package com.sh3h.localprovider.greendaoDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sh3h.localprovider.greendaoEntity.ConstructionAccept;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BZ_CONSTRUCTION_ACCEPT".
*/
public class ConstructionAcceptDao extends AbstractDao<ConstructionAccept, Long> {

    public static final String TABLENAME = "BZ_CONSTRUCTION_ACCEPT";

    /**
     * Properties of entity ConstructionAccept.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ACCEPT_ID = new Property(0, Long.class, "ACCEPT_ID", true, "ACCEPT_ID");
        public final static Property SERVER_ID = new Property(1, Long.class, "SERVER_ID", false, "SERVER_ID");
        public final static Property PROJECT_ID = new Property(2, long.class, "PROJECT_ID", false, "PROJECT_ID");
        public final static Property BUDGET_ID = new Property(3, long.class, "BUDGET_ID", false, "BUDGET_ID");
        public final static Property CONSTRUCTION_PROGRAM = new Property(4, String.class, "CONSTRUCTION_PROGRAM", false, "CONSTRUCTION_PROGRAM");
        public final static Property CONSTRUCTION_REFORM = new Property(5, String.class, "CONSTRUCTION_REFORM", false, "CONSTRUCTION_REFORM");
        public final static Property OPERATOR = new Property(6, String.class, "OPERATOR", false, "OPERATOR");
        public final static Property OPERATE_TIME = new Property(7, long.class, "OPERATE_TIME", false, "OPERATE_TIME");
        public final static Property LONGITUDE = new Property(8, double.class, "LONGITUDE", false, "LONGITUDE");
        public final static Property LATITUDE = new Property(9, double.class, "LATITUDE", false, "LATITUDE");
        public final static Property REMARK = new Property(10, String.class, "REMARK", false, "REMARK");
        public final static Property UPLOAD = new Property(11, int.class, "UPLOAD", false, "UPLOAD");
    }


    public ConstructionAcceptDao(DaoConfig config) {
        super(config);
    }
    
    public ConstructionAcceptDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BZ_CONSTRUCTION_ACCEPT\" (" + //
                "\"ACCEPT_ID\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ACCEPT_ID
                "\"SERVER_ID\" INTEGER," + // 1: SERVER_ID
                "\"PROJECT_ID\" INTEGER NOT NULL ," + // 2: PROJECT_ID
                "\"BUDGET_ID\" INTEGER NOT NULL ," + // 3: BUDGET_ID
                "\"CONSTRUCTION_PROGRAM\" TEXT," + // 4: CONSTRUCTION_PROGRAM
                "\"CONSTRUCTION_REFORM\" TEXT," + // 5: CONSTRUCTION_REFORM
                "\"OPERATOR\" TEXT," + // 6: OPERATOR
                "\"OPERATE_TIME\" INTEGER NOT NULL ," + // 7: OPERATE_TIME
                "\"LONGITUDE\" REAL NOT NULL ," + // 8: LONGITUDE
                "\"LATITUDE\" REAL NOT NULL ," + // 9: LATITUDE
                "\"REMARK\" TEXT," + // 10: REMARK
                "\"UPLOAD\" INTEGER NOT NULL );"); // 11: UPLOAD
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BZ_CONSTRUCTION_ACCEPT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ConstructionAccept entity) {
        stmt.clearBindings();
 
        Long ACCEPT_ID = entity.getACCEPT_ID();
        if (ACCEPT_ID != null) {
            stmt.bindLong(1, ACCEPT_ID);
        }
 
        Long SERVER_ID = entity.getSERVER_ID();
        if (SERVER_ID != null) {
            stmt.bindLong(2, SERVER_ID);
        }
        stmt.bindLong(3, entity.getPROJECT_ID());
        stmt.bindLong(4, entity.getBUDGET_ID());
 
        String CONSTRUCTION_PROGRAM = entity.getCONSTRUCTION_PROGRAM();
        if (CONSTRUCTION_PROGRAM != null) {
            stmt.bindString(5, CONSTRUCTION_PROGRAM);
        }
 
        String CONSTRUCTION_REFORM = entity.getCONSTRUCTION_REFORM();
        if (CONSTRUCTION_REFORM != null) {
            stmt.bindString(6, CONSTRUCTION_REFORM);
        }
 
        String OPERATOR = entity.getOPERATOR();
        if (OPERATOR != null) {
            stmt.bindString(7, OPERATOR);
        }
        stmt.bindLong(8, entity.getOPERATE_TIME());
        stmt.bindDouble(9, entity.getLONGITUDE());
        stmt.bindDouble(10, entity.getLATITUDE());
 
        String REMARK = entity.getREMARK();
        if (REMARK != null) {
            stmt.bindString(11, REMARK);
        }
        stmt.bindLong(12, entity.getUPLOAD());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ConstructionAccept entity) {
        stmt.clearBindings();
 
        Long ACCEPT_ID = entity.getACCEPT_ID();
        if (ACCEPT_ID != null) {
            stmt.bindLong(1, ACCEPT_ID);
        }
 
        Long SERVER_ID = entity.getSERVER_ID();
        if (SERVER_ID != null) {
            stmt.bindLong(2, SERVER_ID);
        }
        stmt.bindLong(3, entity.getPROJECT_ID());
        stmt.bindLong(4, entity.getBUDGET_ID());
 
        String CONSTRUCTION_PROGRAM = entity.getCONSTRUCTION_PROGRAM();
        if (CONSTRUCTION_PROGRAM != null) {
            stmt.bindString(5, CONSTRUCTION_PROGRAM);
        }
 
        String CONSTRUCTION_REFORM = entity.getCONSTRUCTION_REFORM();
        if (CONSTRUCTION_REFORM != null) {
            stmt.bindString(6, CONSTRUCTION_REFORM);
        }
 
        String OPERATOR = entity.getOPERATOR();
        if (OPERATOR != null) {
            stmt.bindString(7, OPERATOR);
        }
        stmt.bindLong(8, entity.getOPERATE_TIME());
        stmt.bindDouble(9, entity.getLONGITUDE());
        stmt.bindDouble(10, entity.getLATITUDE());
 
        String REMARK = entity.getREMARK();
        if (REMARK != null) {
            stmt.bindString(11, REMARK);
        }
        stmt.bindLong(12, entity.getUPLOAD());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ConstructionAccept readEntity(Cursor cursor, int offset) {
        ConstructionAccept entity = new ConstructionAccept( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ACCEPT_ID
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // SERVER_ID
            cursor.getLong(offset + 2), // PROJECT_ID
            cursor.getLong(offset + 3), // BUDGET_ID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // CONSTRUCTION_PROGRAM
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // CONSTRUCTION_REFORM
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // OPERATOR
            cursor.getLong(offset + 7), // OPERATE_TIME
            cursor.getDouble(offset + 8), // LONGITUDE
            cursor.getDouble(offset + 9), // LATITUDE
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // REMARK
            cursor.getInt(offset + 11) // UPLOAD
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ConstructionAccept entity, int offset) {
        entity.setACCEPT_ID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSERVER_ID(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setPROJECT_ID(cursor.getLong(offset + 2));
        entity.setBUDGET_ID(cursor.getLong(offset + 3));
        entity.setCONSTRUCTION_PROGRAM(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCONSTRUCTION_REFORM(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOPERATOR(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOPERATE_TIME(cursor.getLong(offset + 7));
        entity.setLONGITUDE(cursor.getDouble(offset + 8));
        entity.setLATITUDE(cursor.getDouble(offset + 9));
        entity.setREMARK(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUPLOAD(cursor.getInt(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ConstructionAccept entity, long rowId) {
        entity.setACCEPT_ID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ConstructionAccept entity) {
        if(entity != null) {
            return entity.getACCEPT_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ConstructionAccept entity) {
        return entity.getACCEPT_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
