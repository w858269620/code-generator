package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "api_method")
public class ApiMethod implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "api_id")
	private Long apiId;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "author")
	private String author;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "uri")
	private String uri;

	@Column(name = "http_method")
	private String httpMethod;

}
