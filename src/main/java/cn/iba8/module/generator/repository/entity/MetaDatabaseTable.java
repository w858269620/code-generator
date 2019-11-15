package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "meta_database_table")
public class MetaDatabaseTable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "meta_database_id")
	private Long metaDatabaseId;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "table_comment")
	private String tableComment;

	@Column(name = "api_table")
	private Integer apiTable = 1;

	@Column(name = "id_strategy")
	private String idStrategy;

	@Column(name = "table_ddl")
	private String tableDdl;

	@Column(name = "table_triggers")
	private String tableTriggers;

}
