package me.zuhr.demo.jpa.catTest;

import org.springframework.data.repository.CrudRepository;

public interface CatRepository  extends CrudRepository<Cat, Integer> {

}
