package cn.lu.web.base;

import cn.lu.web.mvc.BizException;
import cn.lu.web.vo.QueryParam;

import java.util.List;

/**
 * CRUD服务接口
 *
 * @author lu
 * @date 2018/6/21
 */
public interface BaseService<T, V extends QueryParam> {

    /**
     * 持久化到数据库
     *
     * @param entity
     * @return
     * @throws BizException
     */
    int save(T entity) throws BizException;

    /**
     * 通过主鍵查找
     *
     * @param id
     * @return
     * @throws BizException
     */
    <T> T get(Object id) throws BizException;

    /**
     * 更新数据库（根据主键更新）
     *
     * @param entity
     * @return
     * @throws BizException
     */
    int update(T entity) throws BizException;

    /**
     * 通过主鍵进行逻辑刪除
     *
     * @param id
     * @return
     * @throws BizException
     */
    int delete(Object id) throws BizException;

    /**
     * 批量持久化到数据库
     *
     * @param list
     * @return
     * @throws BizException
     */
    int save(List<T> list) throws BizException;

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     * @throws BizException
     */
    List<T> query(V param) throws BizException;
}
