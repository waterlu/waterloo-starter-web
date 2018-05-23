package cn.lu.web.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Iterator;
import java.util.Set;

/**
 * 自定义的SQLProvider
 *
 * @author lutiehua
 * @date 2018/2/1
 */
public class MybatisProvider extends MapperTemplate {

    public MybatisProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键删除
     *
     * @param ms
     */
    public String deleteFlag(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append("<set>");
        sql.append("delete_flag = 1");
        sql.append("</set>");
        sql.append("<where>");
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        Iterator var3 = columnList.iterator();
        while(var3.hasNext()) {
            EntityColumn column = (EntityColumn)var3.next();
            sql.append(" AND " + column.getColumnEqualsHolder());
        }
        sql.append(" AND delete_flag = 0");
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 批量插入（主键是UUID）
     *
     * @param ms
     */
    public String insertUuidList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        // 开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        // SqlHelper.insertColumns()第二个参数改为false，true会在拼SQL语句时跳过主键字段
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        // 获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // 当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            // 去掉主键判断 if (!column.isId() && column.isInsertable())
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }
}
