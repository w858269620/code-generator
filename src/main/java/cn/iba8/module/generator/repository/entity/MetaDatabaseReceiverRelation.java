package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "meta_database_receiver_relation")
public class MetaDatabaseReceiverRelation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "meta_database_id")
	private Long metaDatabaseId;

	@Column(name = "meta_database_receiver_id")
	private Long metaDatabaseReceiverId;

}
