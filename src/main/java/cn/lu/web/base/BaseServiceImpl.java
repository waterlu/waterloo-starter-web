package cn.lu.web.base;

import cn.lu.web.mapper.CrudMapper;
import cn.lu.web.mvc.BizException;
import cn.lu.web.mvc.FrameException;
import cn.lu.web.vo.QueryParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service基类
 *
 * @author lutiehua
 * @date 2018/5/11
 */
public abstract class BaseServiceImpl<T, V extends QueryParam> implements BaseService<T, V> {

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
    @Override
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
    @Override
    public <T> T get(Object id) throws BizException {
        return (T) getMapper().selectByPrimaryKey(id);
    }

    /**
     * 更新数据库（根据主键更新）
     *
     * @param entity
     * @return
     * @throws BizException
     */
    @Override
    public int update(T entity) throws BizException {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    /**
     * 通过主鍵进行逻辑刪除
     *
     * @param id
     * @return
     * @throws BizException
     */
    @Override
    public int delete(Object id) throws BizException {
        return getMapper().deleteFlag(id);
    }

    /**
     * 批量持久化到数据库
     *
     * @param list
     * @return
     * @throws BizException
     */
    @Override
    public int save(List<T> list) throws BizException {
        //return getMapper().insertList(list);
        int row = 0;
        for (T t : list) {
            row += getMapper().insert(t);
        }
        return row;
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<T> query(V param) throws BizException {
        // 读取泛型参数
        Type entityClassType;
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            // 第一个泛型是实体类，所以读取[0]
            entityClassType = ((ParameterizedType) superType).getActualTypeArguments()[0];
        } else {
            logger.error("Unknown entity class type");
            throw new FrameException("Unknown entity class type");
        }

        // 这里需要指定实体类名
        String className = entityClassType.getTypeName();
        Class classType = null;
        try {
            classType = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw new FrameException(e.getMessage());
        }
        Example condition = new Example(classType);

        // 创建查询条件
        Example.Criteria criteria = condition.createCriteria();

        // 此处仅为示例，将对象转为Map可以完成通用的相等判断
        String paramString = JSON.toJSONString(param);
        Map<String, Object> queryMap = JSON.parseObject(paramString, new TypeReference<HashMap<String, Object>>() {});
        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            String property = entry.getKey();
            Object value = entry.getValue();
            // 跳过callSystemID和traceID
            if ("callSystemID".equalsIgnoreCase(property) || "traceID".equalsIgnoreCase(property)) {
                continue;
            }
            // 跳过startRow和pageSize
            if ("startRow".equalsIgnoreCase(property) || "pageSize".equalsIgnoreCase(property)) {
                continue;
            }
            criteria = criteria.andEqualTo(property, value);
        }

        // 分页
        // pagehelper.offset-as-page-num
        // 默认false，表示offset,limit
        // 设置true，表示pageNum,pageSize
        // pagehelper.row-bounds-with-count=true，表示先select count()，默认false，不查询count
        // pagehelper.reasonable=false，不支持不合理的页码
        int startRow = param.getStartRow();
        int pageSize = param.getPageSize();
        if (startRow < 0) {
            startRow = 0;
        }
        if (pageSize <= 0) {
            pageSize = 20;
        }
        RowBounds rowBounds = new RowBounds(startRow, pageSize);

        // 排序，使用表的字段名称
        // condition.setOrderByClause("create_time desc");

        // 分页查询
        List<T> list = getMapper().selectByExampleAndRowBounds(condition, rowBounds);
        return list;
    }

    /**
     * 分页查询所有数据
     *
     * @return
     * @throws BizException
     */
    @Override
    public List<T> queryAll(QueryParam param) throws BizException {
        // 读取泛型参数
        Type entityClassType;
        Type superType = this.getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            // 第一个泛型是实体类，所以读取[0]
            entityClassType = ((ParameterizedType) superType).getActualTypeArguments()[0];
        } else {
            logger.error("Unknown entity class type");
            throw new FrameException("Unknown entity class type");
        }

        // 这里需要指定实体类名
        String className = entityClassType.getTypeName();
        Class classType = null;
        try {
            classType = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw new FrameException(e.getMessage());
        }

        // 创建查询条件
        Example condition = new Example(classType);
        int startRow = param.getStartRow();
        int pageSize = param.getPageSize();
        if (startRow < 0) {
            startRow = 0;
        }
        if (pageSize <= 0) {
            pageSize = 20;
        }
        RowBounds rowBounds = new RowBounds(startRow, pageSize);
        // 分页查询
        List<T> list = getMapper().selectByExampleAndRowBounds(condition, rowBounds);
        return list;
    }
}
