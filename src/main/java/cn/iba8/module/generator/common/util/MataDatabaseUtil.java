package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
                String tableDdl = tableDdl(connection, tableName);
                String tableTriggers = tableTriggers(connection, tableName);
                List<MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn> columns = new ArrayList<>();
                MetaDatabaseTableDefinition response = new MetaDatabaseTableDefinition();
                response.setTableName(tableName);
                response.setTableDdl(tableDdl);
                response.setTableComment(tableComment);
                response.setTableTriggers(tableTriggers);
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

    private static String tableDdl(Connection connection, String tableName) throws Exception {
        String sql = "show create table " + tableName;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (null != resultSet && resultSet.next()) {
                String tddl = resultSet.getString(2);
                return tddl;
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

    private static String tableTriggers(Connection connection, String tableName) throws Exception {
        String sql = "select TRIGGER_NAME, EVENT_OBJECT_SCHEMA, EVENT_OBJECT_TABLE, ACTION_STATEMENT, EVENT_MANIPULATION, ACTION_TIMING, ACTION_ORIENTATION, ACTION_ORDER " +
                "from information_schema.triggers " +
                "where EVENT_OBJECT_SCHEMA = (select database()) and EVENT_OBJECT_TABLE='" + tableName + "'" +
                " order by ACTION_ORDER asc";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            StringBuffer sb = new StringBuffer();
            if (null != resultSet && resultSet.next()) {
                String s = "CREATE TRIGGER '" + resultSet.getString("TRIGGER_NAME") + "' "
                        + resultSet.getString("ACTION_TIMING")
                        + " " + resultSet.getString("EVENT_MANIPULATION")
                        + " ON " + resultSet.getString("EVENT_OBJECT_TABLE")
                        + " FOR EACH ROW "
                        + resultSet.getString("ACTION_STATEMENT")
                        + ";\n"
                        ;
                sb.append(s);
            }
            String ret = sb.toString();
            return StringUtils.isNotBlank(ret) ? ret : null;
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

    public static int doExec(MetaDatabase metaDatabase, String createTables) {
        Statement statement = null;
        try {
            Connection connection = MataDatabaseUtil.getConnection(metaDatabase);
            statement = connection.createStatement();
            int i = statement.executeUpdate(createTables);
            return i;
        } catch (Exception e) {
            log.error("执行建表语句失败 e {}", e);
            throw BaseException.of(ResponseCode.META_DATABASE_CONNECT_FAIL);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (Exception e) {
                    log.error("失败 e {}", e);
                }
            }
        }
    }

}
