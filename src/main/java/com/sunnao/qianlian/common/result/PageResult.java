package com.sunnao.qianlian.common.result;

import com.mybatisflex.core.paginate.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应结构体
 *
 * @author sunnao
 */
@Data
public class PageResult<T> implements Serializable {

    private String code;

    private String msg;

    private long total;

    private List<T> list;

    /**
     * 构建分页结果（MyBatis-Flex {@link Page}）。
     *
     * <p>data 为当前页记录列表；page 提供分页元信息。</p>
     */
    public static <T> PageResult<T> success(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());

        List<T> records =
                (page == null || page.getRecords() == null)
                        ? Collections.emptyList()
                        : page.getRecords();

        result.setList(records);
        result.setTotal(page != null ? page.getTotalRow() : 0L);
        return result;
    }

}
