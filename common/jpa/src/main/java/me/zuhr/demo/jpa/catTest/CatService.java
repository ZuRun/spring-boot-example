package me.zuhr.demo.jpa.catTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class CatService {
    @Resource
    private CatRepository catRepository;
    /**
     * save,update,delete方法需要绑定事物
     * 使用@Transactional进行事物绑定
     */
    //保存数据
    @Transactional
    public void save(Cat cat) {
        catRepository.save(cat);
    }
    //删除数据
    @Transactional
    public void delete(int id) {
        catRepository.delete(id);
    }
    //查询数据
    @Transactional
    public Iterable<Cat> getAll() {
        return catRepository.findAll();

    }

}
