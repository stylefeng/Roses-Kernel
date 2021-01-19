# wrapper模块，一种开发思路或者开发工具

将控制器层的返回结果进行进一层包装从而灵活的进行响应参数装配

例如：
select menu_id,menu_name,create_user from sys_menu

利用Wrapper可以将返回结果的关联字段响应中文名称，例如create_user这种字段，就不用去left join连表查询

另外wrapper时可以用缓存，加快wrapper速度
