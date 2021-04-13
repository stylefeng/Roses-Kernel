package cn.stylefeng.roses.kernel.mongodb.file.entity;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author huziyang
 * @create 2021-03-26 17:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("mongo_file")
public class MongoFileEntity extends BaseRequest {
    @Id
    private String id;
    private String name;
    private Date uploadDate;
    private Long uploadUserId;
    private String suffix;
    private String description;
    private String gridfsId;

    /**
     * 分页 响应字段
     */
    private byte[] content;
}
