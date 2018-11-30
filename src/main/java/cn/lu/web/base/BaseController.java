package cn.lu.web.base;

import cn.lu.web.mvc.*;
import cn.lu.web.vo.*;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Controller基类
 * T 实体类名
 * P 创建和更新的参数类名
 * Q 查询的参数类名
 *
 * @author lutiehua
 * @date 2018/5/11
 */
public abstract class BaseController<T extends BaseEntity, P extends ParamDTO, Q extends QueryParam> {

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取服务类
     *
     * @return
     */
    public abstract BaseService<T, Q> getService();

    /**
     * 更新前需要设置主键，底层不知道哪个字段是主键
     * TODO 生成实体类时自动根据@Id识别主键生成get/set方法（有难度）
     *
     * @param entity
     * @param id
     */
    public abstract void setEntityId(T entity, Object id);

    /**
     * 创建-C
     *
     * @return
     * @throws BizException
     */
    public ResponseResult createResource(P param) throws BizException {
        // 将入参转换为实体类对象，方便Mapper操作
        T entity = paramToEntity(param);

        // 设置默认参数
        Date now = new Date();
        entity.setDeleteFlag(0);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        // 持久化到数据库
        int row = getService().save(entity);
        if (row == 0) {
            // 写入失败，抛出异常
            throw new DBException();
        }

        // 写入成功，返回实体对象；自增ID在save()时回写；
        ResponseData responseData = entityToVo(entity);
        return new ResponseResult(responseData);
    }

    /**
     * 详情-R
     *
     * @return
     * @throws BizException
     */
    public ResponseResult getResource(Object id) throws BizException {
        // 根据主键读取数据
        T entity = getService().get(id);

        if(null == entity) {
            // 读取失败，抛出异常
//            throw new DBException();
            return new ResponseResult(ResponseCode.SUCCESS.code, "");
        }

        ResponseData responseData = entityToVo(entity);
        return new ResponseResult(responseData);
    }

    /**
     * 查询
     *
     * @return
     * @throws BizException
     */
    public ResponseResult queryResource(Q param) throws BizException {
        // 根据条件查询数据
        List<T> list = getService().query(param);

        PageInfo pageInfo = new PageInfo(list);
        ListResultVO<T> resultVO = new ListResultVO();
        resultVO.setCount(pageInfo.getTotal());
        resultVO.setPageCount(pageInfo.getPages());
        resultVO.setPageNum(pageInfo.getPageNum());
        resultVO.setPageSize(pageInfo.getPageSize());
        resultVO.setList(list);
        ListResponseData responseData = new ListResponseData(resultVO);
        return new ResponseResult(responseData);
    }

    /**
     * 更新-U
     *
     * @return 数据库更新的行数
     * @throws BizException
     */
    public ResponseResult updateResource(Object id, P param)
            throws BizException {
        // 将入参转换为实体类对象，方便Mapper操作
        T entity = paramToEntity(param);

        // 设置ID字段值
        setEntityId(entity, id);

        // 设置更新时间
        Date now = new Date();
        entity.setUpdateTime(now);

        // 更新数据库
        int row = getService().update(entity);

        // 返回更新行数，row=0不抛异常
        SimpleResponseData responseData = new SimpleResponseData(row);
        return new ResponseResult(responseData);
    }

    /**
     * 删除-D（逻辑删除）
     *
     * @param id
     * @return 数据库更新的行数
     * @throws BizException
     */
    public ResponseResult deleteResource(Object id) throws BizException {
        // 逻辑删除
        int row = getService().delete(id);

        // 返回更新行数，row=0不抛异常
        SimpleResponseData responseData = new SimpleResponseData(row);
        return new ResponseResult(responseData);
    }

    /**
     * 将入参对象转换为与数据库对应的实体类对象，默认实现是DTO类和Entity类字段一对一转换，如果不满足要求请覆盖此方法。
     * 此方法在基类的create()方法中调用，如果覆盖了create()方法请忽略此方法。
     *
     * @param param
     * @return
     */
    protected T paramToEntity(P param) {
        String jsonString = JSON.toJSONString(param);
        return (T) JSON.parseObject(jsonString, getEntityType(0));
    }

    /**
     * 封装返回结果，默认直接返回实体类对象。
     * 如果需要进行处理，请将Entity类对象转换为VO对象，并放入ResponseData中返回。
     *
     * @param entity
     * @return
     */
    protected ResponseData entityToVo(T entity) {
        return new ResponseData<T>(entity);
    }

    /**
     * 获取实体类
     *
     * @return
     */
    protected Type getEntityType(int index) {
        // 读取泛型参数
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            // 第一个泛型是实体类，所以读取[0]
            return ((ParameterizedType) superType).getActualTypeArguments()[index];
        } else {
            throw new RuntimeException("Unknown entity class type");
        }
    }
}
