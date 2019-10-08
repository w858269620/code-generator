package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "meta_database_change_log")
public class MetaDatabaseChangeLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private Integer type;

	@Column(name = "code")
	private String code;

	@Column(name = "database_name")
	private String databaseName;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "log")
	private String log;

	@Column(name = "origin_data")
	private String originData;

	@Column(name = "now_data")
	private String nowData;

	@Column(name = "create_time")
	private java.util.Date createTime;

}
