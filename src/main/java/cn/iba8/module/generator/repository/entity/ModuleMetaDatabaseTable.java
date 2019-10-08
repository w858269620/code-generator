package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "module_meta_database_table")
public class ModuleMetaDatabaseTable implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "module_id")
	private Long moduleId;

	@Column(name = "meta_database_table_id")
	private Long metaDatabaseTableId;

}
