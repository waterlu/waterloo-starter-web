package cn.lu.web.base;

import cn.lu.web.mapper.CrudMapper;
import cn.lu.web.mvc.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service基类
 *
 * @author lutiehua
 * @date 2018/5/11
 */
public abstract class BaseService<T> {

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取Mapper，子类实现
     *
     * @return
     */
    public abstract CrudMapper<T> getMapper();

    /**
     * 持久化到数据库
     *
     * @param entity
     * @return
     * @throws BizException
     */
    public int save(T entity) throws BizException {
        return getMapper().insertSelective(entity);
    }

    /**
     * 通过主鍵查找
     *
     * @param id
     * @return
     * @throws BizException
     */
    <T> T get(Object id) throws BizException {
        return (T) getMapper().selectByPrimaryKey(id);
    }

    /**
     * 更新数据库（根据主键更新）
     *
     * @param entity
     * @return
     * @throws BizException
     */
    int update(T entity) throws BizException {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    /**
     * 通过主鍵进行逻辑刪除
     *
     * @param id
     * @return
     * @throws BizException
     */
    int delete(Object id) throws BizException {
        return getMapper().deleteFlag(id);
    }

//    /**
//     * 根据相等条件进行查询
//     *
//     * @param queryParam 查询参数
//     * @return
//     */
//    ListResponseResult<UserVO> query(UserQueryDTO queryParam);
//
//    /**
//     * 批量持久化到数据库
//     *
//     * @param userList
//     * @return
//     * @throws BusinessException
//     */
//    int save(List<User> userList) throws BusinessException;
}
