package me.zuhr.demo.jpa.base;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * MappedSuperclass注解必须加,排除此类的autoconfig
 *
 * @author zurun
 * @date 2018/3/18 00:18:02
 */
@MappedSuperclass
public abstract class AbstractHibernateEntity extends BaseEntity {
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }


}
