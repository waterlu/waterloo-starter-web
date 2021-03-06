package cn.lu.web.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

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

    /**
     * 根据主键字段和逻辑删除标记进行更新，如果已经被逻辑删除，则不更新
     *
     * @param var1
     * @return
     */
    @UpdateProvider(type = MybatisProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelectiveFlag(T var1);

//    /**
//     * 批量写入
//     *
//     * @param recordList
//     * @return
//     */
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
//    int insertList(List<T> recordList);
}