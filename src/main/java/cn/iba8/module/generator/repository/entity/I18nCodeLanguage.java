package cn.iba8.module.generator.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "i18n_code_language")
@NoArgsConstructor
public class I18nCodeLanguage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_code")
	private String moduleCode;

	@Column(name = "code")
	private String code;

	@Column(name = "message")
	private String message;

	@Column(name = "language")
	private String language;

}
