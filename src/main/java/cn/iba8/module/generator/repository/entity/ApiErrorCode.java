package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "api_error_code")
public class ApiErrorCode implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "api_method_id")
	private Long apiMethodId;

	@Column(name = "meta_error_code_id")
	private Long metaErrorCodeId;

}
