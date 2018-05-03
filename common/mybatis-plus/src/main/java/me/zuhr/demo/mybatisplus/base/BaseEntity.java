package me.zuhr.demo.mybatisplus.base;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;

import static com.baomidou.mybatisplus.enums.FieldFill.INSERT;

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
    @TableField(value = "create_time", fill = INSERT)
    protected Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "modified_time", update = "now()", fill = INSERT)
    protected Date modifiedTime;
}
