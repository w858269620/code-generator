package cn.iba8.module.generator.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name="resource_file_target")
public class ResourceFileTarget implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 19)
    private Long id;

    /** 应用编号 */
    @Column(name = "module_code", length = 64)
    private String moduleCode;

    @Column(name = "note", length = 65535)
    private String note;

    @Column(name = "create_ts", length = 19)
    private Long createTs;

    /** 文件名称 */
    @Column(name = "name", length = 255)
    private String name;

    /** 内容类型：1-全量，2-增量 */
    @Column(name = "type")
    private String type;

    @Column(name = "suffix", length = 20)
    private String suffix;

    /** 版本 */
    @Column(name = "version", length = 255)
    private String version;

    /** 文件内容 */
    @Column(name = "content", length = 2147483647)
    private String content;

    @Column(name = "md5", length = 32)
    private String md5;


}