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

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "version")
	private String version;

	@Column(name = "note")
	private String note;

}
