package me.zuhr.demo.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author zurun
 * @date 2018/3/17 23:14:46
 */
@NoRepositoryBean
public interface BaseRepository <T,PK extends Serializable> extends JpaRepository<T,PK>{
}
