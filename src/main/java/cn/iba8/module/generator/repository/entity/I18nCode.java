package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "i18n_code")
public class I18nCode implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_code")
	private String moduleCode;

	@Column(name = "int_code")
	private Long intCode;

	@Column(name = "code")
	private String code;

	@Column(name = "message")
	private String message;

	@Column(name = "note")
	private String note;

}
