package me.zuhr.demo.mybatis.base;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author zurun
 * @date 2018/5/2 00:59:28
 */
@Data
public class BaseEntity {
    protected Long id;
    /**
     * 创建时间
     */
    @TableField("create_time")
    protected Date createTime;
    /**
     * 更新时间
     */
    @TableField("modified_time")
    protected Date modifiedTime;
}
