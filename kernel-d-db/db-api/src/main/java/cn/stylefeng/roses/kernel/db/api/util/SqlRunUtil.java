package cn.stylefeng.roses.kernel.db.api.util;

import cn.hutool.core.io.IoUtil;
import cn.stylefeng.roses.kernel.db.api.exception.DaoException;
import cn.stylefeng.roses.kernel.db.api.exception.enums.DatabaseExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * sql文件执行
 *
 * @author fengshuonan
 * @date 2019-06-18-17:10
 */
@Slf4j
public class SqlRunUtil {

    /**
     * 执行sql脚本文件，使用Spring工具类
     *
     * @author fengshuonan
     * @date 2021/5/19 10:52
     */
    public static void runClassPathSql(String classpathFileName, String driverClassName, String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, username, password);

            ClassPathResource classPathResource = new ClassPathResource(classpathFileName);
            EncodedResource encodedResource = new EncodedResource(classPathResource, "utf-8");
            ScriptUtils.executeSqlScript(conn, encodedResource);
        } catch (Exception e) {
            log.error("执行sql错误！", e);
            throw new DaoException(DatabaseExceptionEnum.SQL_EXEC_ERROR, e.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 执行系统路径sql的文件
     *
     * @author fengshuonan
     * @date 2021/5/19 10:52
     */
    public static void runFileSystemSql(SqlSessionFactory sqlSessionFactory, String sqlPath) {
        Connection conn = null;
        try {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            conn = sqlSession.getConnection();

            FileSystemResource classPathResource = new FileSystemResource(sqlPath);
            EncodedResource encodedResource = new EncodedResource(classPathResource, "GBK");
            ScriptUtils.executeSqlScript(conn, encodedResource);
        } catch (Exception e) {
            log.error("执行sql错误！", e);
            throw new DaoException(DatabaseExceptionEnum.SQL_EXEC_ERROR, e.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

}
