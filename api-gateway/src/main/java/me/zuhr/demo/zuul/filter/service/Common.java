package me.zuhr.demo.zuul.filter.service;

import com.netflix.zuul.context.RequestContext;

/**
 * @author zurun
 * @date 2018/4/30 22:32:47
 */
public class Common implements ServiceFilter {
    @Override
    public boolean authentication(RequestContext ctx) {
return true;
    }
}
