package me.zuhr.demo.shiro;

/**
 * shiro身份校验核心类
 *
 * @author zurun
 * @date 2018/2/20 00:41:10
 */
//public class MyShiroRealm extends AuthorizingRealm {
//    private static final Logger LOGGER = Logger.getLogger(MyShiroRealm.class);
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
//        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
//        String userId = user.getId();
//        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
//        //根据用户ID查询角色（role），放入到Authorization里。
//
//		/*Map<String, Object> map = new HashMap<String, Object>();
//
//		map.put("user_id", userId);
//
//		List<SysRole> roleList = sysRoleService.selectByMap(map);
//
//		Set<String> roleSet = new HashSet<String>();
//
//		for(SysRole role : roleList){
//
//			roleSet.add(role.getType());
//
//		}*/
//        Set<String> roleSet = new HashSet<String>();
//        roleSet.add("100002");
//        info.setRoles(roleSet);
//        //根据用户ID查询权限（permission），放入到Authorization里。
//
//		/*List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
//
//		Set<String> permissionSet = new HashSet<String>();
//
//		for(SysPermission Permission : permissionList){
//
//			permissionSet.add(Permission.getName());
//
//		}*/
//        Set<String> permissionSet = new HashSet<String>();
//        permissionSet.add("权限添加");
//        permissionSet.add("权限删除");
//        info.setStringPermissions(permissionSet);
//        return info;
//    }
//
//    /**
//     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
//     *
//     * @param authenticationToken
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        String name = token.getUsername();
//        String password = String.valueOf(token.getPassword());
//        //访问一次，计数一次
////
////        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
////        opsForValue.increment(SHIRO_LOGIN_COUNT+name, 1);
////        //计数大于5时，设置用户被锁定一小时
////
////        if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+name))>=5){
////            opsForValue.set(SHIRO_IS_LOCK+name, "LOCK");
////            stringRedisTemplate.expire(SHIRO_IS_LOCK+name, 1, TimeUnit.HOURS);
////        }
////        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+name))){
////            throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
////        }
////        Map<String, Object> map = new HashMap<String, Object>();
////        map.put("nickname", name);
////        //密码进行加密处理  明文为  password+name
////
////        String paw = password+name;
////        String pawDES = MyDES.encryptBasedDes(paw);
////        map.put("pswd", pawDES);
////        SysUser user = null;
////        // 从数据库获取对应用户名密码的用户
////
////        List<SysUser> userList = sysUserService.selectByMap(map);
////        if(userList.size()!=0){
////            user = userList.get(0);
////        }
////        if (null == user) {
////            throw new AccountException("帐号或密码不正确！");
////        }else if("0".equals(user.getStatus())){
////            /**
////
////             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
////
////             */
////            throw new DisabledAccountException("此帐号已经设置为禁止登录！");
////        }else{
////            //登录成功
////
////            //更新登录时间 last login time
////
////            user.setLastLoginTime(new Date());
////            sysUserService.updateById(user);
////            //清空登录计数
////
////            opsForValue.set(SHIRO_LOGIN_COUNT+name, "0");
////        }
//        LOGGER.info("身份认证成功，登录用户："+name);
//        return new SimpleAuthenticationInfo(user, password, getName());
//    }
//}
