整个框架每个模块需要遵守的规范

每个开发人员也需要遵守的规范

## 业务异常统一用ServiceException类

## 每个模块的异常枚举要在exception.enums包下维护

## 异常枚举分三类

异常分为三类，第一类用户操作异常的枚举以UserExceptionEnum结尾

第二类是系统业务逻辑异常，枚举以BusinessExceptionEnum结尾

第三类是第三方调用异常，枚举以ThirdExceptionEnum结尾

## 每个模块设立独立的模块异常，方便区分各个模块的异常

