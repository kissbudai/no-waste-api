package edu.ubb.micro.nowaste.proxy.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.security.Principal

class UserDetailsWithId(
	val userId: String,
	userName: String,
	password: String?,
	authorities: Collection<GrantedAuthority>
) : User(userName, password, authorities), Principal {

	override fun getName(): String = super.getUsername()

	companion object {
		private const val serialVersionUID = -1L
	}
}
