package com.sunnao.qianlian.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 键值对
 *
 * @author sunnao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue<T> {

    /**
     * 选项的键
     */
    private T key;

    /**
     * 选项的值
     */
    private String value;

}
