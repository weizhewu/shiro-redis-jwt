package com.waltz.springshirostudy.utils.table;

import com.waltz.springshirostudy.entity.system.SystemMenu;

import java.io.IOException;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 根绝实体类生成建表sql
 * @createDate: 2023/4/7 9:22
 **/
public class CreateTableUtil {
    public static void main(String[] args) throws IOException {
        CreateTable.createTable(SystemMenu.class,null);
    }
}
