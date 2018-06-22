package cn.lu.web.mvc;

import cn.lu.web.vo.ListResultVO;

/**
 * @author lu
 * @date 2018/6/22
 */
public class ListResponseData<T> extends ResponseData<ListResultVO<T>> {

    public ListResponseData(ListResultVO<T> listResultVO) {
        super(ResponseCode.SUCCESS.code, ResponseData.DEFAULT_SUCCESS_MESSAGE, listResultVO);
    }
}