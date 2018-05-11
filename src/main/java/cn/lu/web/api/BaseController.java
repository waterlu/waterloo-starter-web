package cn.lu.web.api;

import cn.lu.web.mvc.*;
import cn.lu.web.vo.InsertGroup;
import cn.lu.web.vo.ParamDTO;
import cn.lu.web.vo.UpdateGroup;
import com.alibaba.fastjson.JSON;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Controller基类
 *
 * @author lu
 * @date 2018/5/11
 */
public abstract class BaseController<T> {

    /**
     * 获取服务类
     *
     * @return
     */
    public abstract BaseService<T> getService();

    /**
     * 创建-C
     *
     * @return
     * @throws BizException
     */
    @PostMapping(value = "")
    public ResponseResult create(@RequestBody @Validated({InsertGroup.class}) ParamDTO param) throws BizException {
        // 将入参转换为实体类对象，方便Mapper操作
        T entity = paramToEntity(param);

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
    @GetMapping(value = "/{id}")
    public ResponseResult get(@PathVariable Object id) throws BizException {
        // 根据主键读取数据
        T entity = getService().get(id);

        if(null == entity) {
            // 读取失败，抛出异常
            throw new DBException();
        }

        ResponseData responseData = entityToVo(entity);
        return new ResponseResult(responseData);
    }

    /**
     * 更新-U
     *
     * @return
     * @throws BizException
     */
    @PutMapping(value = "/{id}")
    public ResponseResult update(@PathVariable Object id, @RequestBody @Validated({UpdateGroup.class}) ParamDTO param)
            throws BizException {
        // 将入参转换为实体类对象，方便Mapper操作
        T entity = paramToEntity(param);

        // 更新数据库
        int row = getService().update(entity);

        // 返回更新行数，row=0不抛异常
        SimpleResponseData responseData = new SimpleResponseData(row);
        return new ResponseResult(responseData);
    }

    /**
     * 删除-D
     *
     * @param id
     * @return
     * @throws BizException
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult delete(@PathVariable Object id) throws BizException {
        // 逻辑删除
        int row = getService().delete(id);

        // 返回更新行数，row=0不抛异常
        SimpleResponseData responseData = new SimpleResponseData(row);
        return new ResponseResult(responseData);
    }

    /**
     * 将参数对象转化为实体类对象，基类提供默认实现；如果不满足需要，请覆盖该方法自行实现
     *
     * @param param
     * @param <T>
     * @return
     */
    protected <T> T paramToEntity(ParamDTO param) {
        String jsonString = JSON.toJSONString(param);
        return (T) JSON.parseObject(jsonString, getEntityType());
    }

    /**
     * 将实体类对象转化为返回的值对象，基类默认实现为直接返回实体类对象，如果需要转换请覆盖该方法自行实现
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
    protected Type getEntityType() {
        // 读取泛型参数
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            // 只有一个泛型，所以读取[0]即可
            return ((ParameterizedType) superType).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("Unknown entity class type");
        }
    }
}
