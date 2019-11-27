package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "i18n_file_target")
public class I18nFileTarget implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "app_code")
	private String appCode;

	@Column(name = "app_name")
	private String appName;

	@Column(name = "name")
	private String name;

	@Column(name = "language")
	private String language;

	@Column(name = "suffix")
	private String suffix;

	@Column(name = "content")
	private String content;

	@Column(name = "create_ts")
	private Long createTs;

}
