package com.waltz.springshirostudy.utils.table;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MrWei
 * @date 2023/1/3 14:32
 * @description 根据实体类创建表Sql工具类
 * @version 1.0.0
 */
public class CreateTable {
    public static Map<String, String> javaProperty2SqlColumnMap = new HashMap<>();

    //下边是对应的oracle的生成语句，类型都是oracle，如果是mysql还需要改。
    static {
        javaProperty2SqlColumnMap.put("Integer", "int(9)");
        javaProperty2SqlColumnMap.put("Short", "int(4)");
        javaProperty2SqlColumnMap.put("Long", "bigint(18)");
        javaProperty2SqlColumnMap.put("BigDecimal", "bigint(22,2)");
        javaProperty2SqlColumnMap.put("Double", "double(22,2)");
        javaProperty2SqlColumnMap.put("Float", "float(22,2)");
        javaProperty2SqlColumnMap.put("Boolean", "tinyint(1)");
        javaProperty2SqlColumnMap.put("Timestamp", "datetime");
        javaProperty2SqlColumnMap.put("String", "varchar(255)");
        javaProperty2SqlColumnMap.put("Date","datetime");
    }

    public static void main(String[] args) throws IOException {
//        createTable(TestClass.class, null);
    }

    public static String createTable(Class<?> clz, String tableName) throws IOException {
        // 判断类上是否有次注解
        boolean clzHasAnno = clz.isAnnotationPresent(Table.class);
        String prikey = null;
        if (clzHasAnno) {
            // 获取类上的注解
            Table annotation = (Table)clz.getAnnotation(Table.class);
            // 输出注解上的类名
            String tableNameAnno = annotation.tableName();
            if(tableNameAnno != null && !"".equals(tableNameAnno)){
                tableName = tableNameAnno;
            }else{
                throw new RuntimeException("没有类名");
            }
            String keyIdAnno = annotation.keyFields();
            if(keyIdAnno != null && !"".equals(keyIdAnno)){
                prikey = keyIdAnno;
            }else{
                throw new RuntimeException("没有设置主键");
            }
        }
        Field[] fields = null;
        fields = clz.getDeclaredFields();
        String param = null;
        String column = null;
        String columnRule = null;
        StringBuilder sb = null;
        sb = new StringBuilder(50);
        sb.append("create table ").append(tableName).append(" ( \r\n");
        boolean firstId = true;
        File file = null;
        for (Field f : fields) {
            column = f.getName();

            if (column.equals("serialVersionUID")) {
                continue;
            }
            boolean fieldHasAnno = f.isAnnotationPresent(Column.class);
            if(fieldHasAnno){
                Column fieldAnno = f.getAnnotation(Column.class);
                //输出注解属性
                String  name = fieldAnno.name();
                String rule = fieldAnno.rule();
                if(!"".equals(name)){
                    column = name;
                }
                columnRule = rule;
            }else{
                continue; //没有column注解的过滤掉
            }

            param = f.getType().getSimpleName();
            sb.append(column);//一般第一个是主键
            sb.append(" ").append(javaProperty2SqlColumnMap.get(param)).append(" ");
            if(prikey == null){
                if (firstId) {//类型转换
                    sb.append(" PRIMARY KEY ");
                    firstId = false;
                }
            }else{
                if(prikey.equals(column)){
                    sb.append(" PRIMARY KEY ");
                }
            }
            sb.append(columnRule);
            sb.append(",\n ");
        }
        String sql = null;
        sql = sb.toString();
        //去掉最后一个逗号
        int lastIndex = sql.lastIndexOf(",");
        sql = sql.substring(0, lastIndex) + sql.substring(lastIndex + 1);

        sql = sql.substring(0, sql.length() - 1) + " );\r\n";
        System.out.println("sql :");
        System.out.println(sql);
        return sql;
    }

}
