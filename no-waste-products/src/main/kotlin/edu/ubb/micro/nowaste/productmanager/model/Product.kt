package edu.ubb.micro.nowaste.productmanager.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table


@Entity
@Table(name = "items")
data class Product(

	val name: String,

	val description: String,

	val pickupInformation: String,

	val type: Type,

	val price: Float,

	var status: Status,

	@OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	val sender: User,

	@ManyToOne(cascade = [CascadeType.ALL])
	@JoinColumn(name = "categoryId")
	val category: Category
) {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	var id: String = ""

	enum class Type(val value: Int) {
		FREE(0),
		DISCOUNTED(1),
		UNDEFINED(-1)
	}

	enum class Status(val value: Int) {
		OPEN(0),
		PENDING(1),
		COMPLETED(2),
		UNKNOWN(-1)
	}

	companion object {
		fun fromValueToStatus(value: Int): Status = when (value) {
			Status.OPEN.value -> Status.OPEN
			Status.PENDING.value -> Status.PENDING
			Status.COMPLETED.value -> Status.COMPLETED
			else -> Status.UNKNOWN
		}

		fun fromValueToType(value: Int): Type = when (value) {
			Type.FREE.value -> Type.FREE
			Type.DISCOUNTED.value -> Type.DISCOUNTED
			else -> Type.UNDEFINED
		}
	}
}