package me.zuhr.demo.mybatisplus.mapper;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 * 此类用于生成和修改创建时间和修改时间
 *
 * @author zurun
 * @date 2018/5/3 22:35:15
 */
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {
    /**
     * 插入数据的时候写入创建时间
     * 此处没做非空判断,默认所有表都有此字段
     * 如果有不包含此字段的情况,需要先metaObject.getValue()判断是否为null,为null就不setValue了
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("modifiedTime", new Date());
    }

    /**
     * update测试无效,没找到原因
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
