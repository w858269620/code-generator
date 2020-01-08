package cn.iba8.module.generator.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name="resource_file_origin")
public class ResourceFileOrigin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 19)
    private Long id;

    /** 模块编号 */
    @Column(name = "module_code", length = 255)
    private String moduleCode;

    /** 处理状态：0-未处理，1-已处理，2-重复文件 */
    @Column(name = "processed")
    private String processed;

    @Column(name = "modify_ts", length = 19)
    private Long modifyTs;

    @Column(name = "create_ts", length = 19)
    private Long createTs;

    /** 文件名称 */
    @Column(name = "name", length = 255)
    private String name;

    /** 文件类型：1-后端，2-前端 */
    @Column(name = "type")
    private String type;

    @Column(name = "suffix", length = 20)
    private String suffix;

    /** 文件内容 */
    @Column(name = "content", length = 2147483647)
    private String content;

    @Column(name = "md5", length = 32)
    private String md5;


}