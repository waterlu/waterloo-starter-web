package cn.lu.web.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

/**
 * 单表Mapper的基类
 * <p>提供了基础的单表CRUD方法</p>
 *
 * @author lu
 * @date 2018/01/31
 */
public interface CrudMapper<T> extends BaseMapper<T>, RowBoundsMapper<T> {

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param key
     * @return
     */
    @DeleteProvider(type = MybatisProvider.class, method = "dynamicSQL")
    int deleteFlag(Object key);


}