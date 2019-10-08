package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "api")
public class Api implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_id")
	private Long moduleId;

	@Column(name = "name")
	private String name;

	@Column(name = "author")
	private String author;

	@Column(name = "note")
	private String note;

}
