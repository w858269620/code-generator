package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "api_method_request")
public class ApiMethodRequest implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "api_method_id")
	private Long apiMethodId;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "note")
	private String note;

	@Column(name = "meta_language_id")
	private Long metaLanguageId;

	@Column(name = "allow_null")
	private Integer allowNull;

	@Column(name = "min_length")
	private Integer minLength;

	@Column(name = "max_length")
	private Integer maxLength;

}
