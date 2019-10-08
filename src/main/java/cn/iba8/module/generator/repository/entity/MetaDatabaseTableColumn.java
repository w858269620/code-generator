package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "meta_database_table_column")
public class MetaDatabaseTableColumn implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "meta_database_table_id")
	private Long metaDatabaseTableId;

	@Column(name = "column_name")
	private String columnName;

	@Column(name = "column_type")
	private String columnType;

	@Column(name = "column_comment")
	private String columnComment;

	@Column(name = "column_size")
	private int columnSize;

	@Column(name = "digits")
	private int digits;

	@Column(name = "nullable")
	private int nullable;

	@Column(name = "primary_key")
	private boolean primaryKey;

}
