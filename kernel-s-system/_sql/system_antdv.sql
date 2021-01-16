-- 分离版本唯一区别是这个配置的值是false
UPDATE `sys_config`
SET `config_name`  = '会话信息是否增加保存在cookie中',
    `config_code`  = 'SYS_SESSION_ADD_TO_COOKIE',
    `config_value` = 'false',
    `sys_flag`     = 'Y',
    `remark`       = NULL,
    `status_flag`  = 1,
    `group_code`   = 'auth_config',
    `del_flag`     = 'N',
    `create_time`  = NULL,
    `create_user`  = NULL,
    `update_time`  = NULL,
    `update_user`  = NULL
WHERE `config_id` = 13;
