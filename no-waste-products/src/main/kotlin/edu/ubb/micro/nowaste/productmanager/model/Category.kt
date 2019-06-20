package edu.ubb.micro.nowaste.productmanager.model


import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "categories")
data class Category(
	val name: String,
	val description: String?
) {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	var id: String = ""

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	var items: List<Product> = listOf()
}