package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.repository.entity.MetaDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MataDatabaseUtil {

    /*
     * @Author sc.wan
     * @Description 获取表信息
     * @Date 14:00 2019/5/31
     **/
    public static List<MetaDatabaseTableDefinition> getTableColumns(Connection connection) throws Exception {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        List<MetaDatabaseTableDefinition> target = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = databaseMetaData.getTables(null, "%", "%", new String[]{"TABLE"});

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String tableComment = tableComment(connection, tableName);
                List<MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn> columns = new ArrayList<>();
                MetaDatabaseTableDefinition response = new MetaDatabaseTableDefinition();
                response.setTableName(tableName);
                response.setTableComment(tableComment);
                response.setColumns(columns);
                ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableName);
                String primaryKey = null;
                while (primaryKeys.next()) {
                    primaryKey = primaryKeys.getString("COLUMN_NAME");
                }
                ResultSet columnResult = databaseMetaData.getColumns(null, "%", tableName, "%");
                while (columnResult.next()) {
                    MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn column = new MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn();
                    String columnName = columnResult.getString("COLUMN_NAME");
                    String columnComment = columnResult.getString("REMARKS");
                    String typeName = columnResult.getString("TYPE_NAME");
                    int columnSize = columnResult.getInt("COLUMN_SIZE");
                    int digits = columnResult.getInt("DECIMAL_DIGITS");
                    int nullable = columnResult.getInt("NULLABLE");
                    column.setColumnName(columnName);
                    column.setColumnSize(columnSize);
                    column.setColumnType(typeName);
                    column.setDigits(digits);
                    column.setNullable(nullable);
                    column.setColumnComment(columnComment);
                    if (columnName.equals(primaryKey)) {
                        column.setPrimaryKey(true);
                    }
                    columns.add(column);
                }
                target.add(response);
            }
            return target;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }
        }
    }

     /*
      * @Author sc.wan
      * @Description 表备注
      * @Date 16:30 2019/7/3
      * @Param
      * @return
      **/
    private static String tableComment(Connection connection, String tableName) throws Exception {
        String sql = "select TABLE_COMMENT,TABLE_NAME from information_schema.tables where table_schema = (select database()) and table_name='" + tableName + "'";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (null != resultSet && resultSet.next()) {
                String tableComment = resultSet.getString("TABLE_COMMENT");
                return tableComment;
            }
            return null;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != resultSet) {
                resultSet.close();
            }
            if (null != statement) {
                statement.close();
            }
        }
    }

    /*
     * @Author sc.wan
     * @Description 连接数据库获取meta
     * @Date 14:00 2019/5/31
     **/
    public static Connection getConnection(MetaDatabase metaDatabase) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://")
                .append(metaDatabase.getHost())
                .append(":")
                .append(metaDatabase.getPort())
                .append("/")
                .append(metaDatabase.getName())
                .append("?")
                .append("user=")
                .append(metaDatabase.getUsername())
                .append("&")
                .append("password=")
                .append(metaDatabase.getPassword())
                .append("&")
                .append("useUnicode=true&characterEncoding=UTF-8&autoReconnect=true")
        ;
        Connection connection = DriverManager.getConnection(sb.toString());
        return connection;
    }

}
