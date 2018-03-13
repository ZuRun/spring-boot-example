package me.zuhr.demo.jpa.cattest;

import org.springframework.data.repository.CrudRepository;

/**
 * 该接口会自动被实现，springData已经帮我们实现了基本的增删改查
 * CRUD --> Create（增）, Read（查）, Update（改）, Delete（删）
 */
public interface CatRepository extends CrudRepository<Cat, Integer> {

}
