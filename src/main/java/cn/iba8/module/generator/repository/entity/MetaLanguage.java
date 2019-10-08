package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "meta_language")
public class MetaLanguage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "el")
	private String el;

	@Column(name = "name")
	private String name;

	@Column(name = "language")
	private String language;

}
