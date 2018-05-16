package com.sh3h.localprovider.greendao;

/**
 * Created by dengzhimin on 2016/5/23.
 */
public class GreenDaoTest {

//    public static void main(String[] args) throws Exception{
//        //Schema schema = new Schema(1,"com.example.greendao");
//        //创建用于添加实体的对象，并设置对应的bean,dao目录
//        Schema schema = new Schema(1,"com.sh3h.localprovider.greendaoEntity");
//        schema.setDefaultJavaPackageDao("com.sh3h.localprovider.greendaoDao");
//
//        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
//        // schema2.enableActiveEntitiesByDefault();
//        // schema2.enableKeepSectionsByDefault();
//
//        //添加实体
//        addNote(schema);
//        //使用 DAOGenerator 类的 generateAll() 方法自动生成代码
//        new DaoGenerator().generateAll(schema, "localprovider/src/main/java");
//
//    }
//
//    private static void addNote(Schema schema){
//        //实体关联数据库表note
//        Entity note = schema.addEntity("CnfWord");
//        //修改数据库表名
//        note.setTableName("CNF_WORD");
//        //greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
//        //设置表字段
//        note.addIdProperty().columnName("ID").primaryKey().autoincrement();
//        note.addIntProperty("PARENT_ID");
//        note.addStringProperty("WORD_GROUP_KEY");
//        note.addStringProperty("WORD_CODE");
//        note.addStringProperty("WORD_TEXT");
//        note.addStringProperty("WORD_VALUE");
//        note.addStringProperty("WORD_PYCODE");
//        note.addIntProperty("SORT_INDEX");
//        note.addByteProperty("STATE");
//        note.addByteProperty("IS_EXTERNAL_VISIBLE");
//        note.addStringProperty("TENANT_ID");
//        note.addStringProperty("REMARK");
//    }
}
