package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "i18n_file_origin")
public class I18nFileOrigin implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_code")
	private String moduleCode;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "name")
	private String name;

	@Column(name = "language")
	private String language;

	@Column(name = "suffix")
	private String suffix;

	@Column(name = "content")
	private String content;

	@Column(name = "processed")
	private Integer processed;

	@Column(name = "type")
	private Integer type;

	@Column(name = "create_ts")
	private Long createTs;

	@Column(name = "modify_ts")
	private Long modifyTs;

	@Column(name = "md5")
	private String md5;

}
