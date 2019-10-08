package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "meta_error_code")
public class MetaErrorCode implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_id")
	private Long moduleId;

	@Column(name = "is_global")
	private Integer isGlobal;

	@Column(name = "code")
	private String code;

	@Column(name = "message")
	private String message;

	@Column(name = "note")
	private String note;

}
