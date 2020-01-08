package cn.iba8.module.generator.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name="meta_database_resource")
public class MetaDatabaseResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 19)
    private Long id;

    /** 备注 */
    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "system", length = 500)
    private String system;

    /** 资源唯一标识 */
    @Column(name = "code", length = 255)
    private String code;

    /** 唯一标记 */
    @Column(name = "sign", length = 255)
    private String sign;

    /** 菜单图标 */
    @Column(name = "icon", length = 100)
    private String icon;

    /** 排序 */
    @Column(name = "sort", length = 5)
    private Integer sort;

    @Column(name = "meta_database_id", length = 19)
    private Long metaDatabaseId;

    /** 是否启用：0-否，1-是 */
    @Column(name = "enabled")
    private Integer enabled;

    /** 打开方式 */
    @Column(name = "target", length = 20)
    private String target;

    /** 显示背景色 */
    @Column(name = "bg_color", length = 255)
    private String bgColor;

    /** 父菜单 */
    @Column(name = "parent_id", length = 19)
    private Long parentId;

    /** 菜单名称 */
    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "resource_id", length = 19)
    private Long resourceId;

    @Column(name = "perms", length = 65535)
    private String perms;

    /** 链接地址 */
    @Column(name = "href", length = 2000)
    private String href;

    /** 资源分类 0-读写所有权限,1-菜单，2-按钮，3-接口 */
    @Column(name = "category")
    private Integer category;


}