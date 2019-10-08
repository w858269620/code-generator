package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "module")
public class Module implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "package_name")
	private String packageName;

	@Column(name = "name")
	private String name;

	@Column(name = "version")
	private String version;

	@Column(name = "version_1")
	private Integer version1;

	@Column(name = "version_2")
	private Integer version2;

	@Column(name = "version_3")
	private Integer version3;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "note")
	private String note;

}
