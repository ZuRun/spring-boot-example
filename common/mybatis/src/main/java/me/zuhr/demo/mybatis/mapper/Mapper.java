package me.zuhr.demo.mybatis.mapper;

/**
 * @param <T> 实体类
 * @author zurun
 * @date 2018/4/9 12:46:59
 */
public interface Mapper<T> {
    /**
     * 插入数据
     *
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 选择性的插入(不为null的字段才插入)
     *
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据主键获取实体
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id);

    /**
     * 只更新不为空的字段
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新所有字段
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);
}
