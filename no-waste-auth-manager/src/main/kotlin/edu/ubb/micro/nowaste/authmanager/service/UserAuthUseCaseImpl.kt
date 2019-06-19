package edu.ubb.micro.nowaste.authmanager.service

import edu.ubb.micro.nowaste.authmanager.model.User
import edu.ubb.micro.nowaste.authmanager.service.user.UserService
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class UserAuthUseCaseImpl(private val authService: AuthService, private val userService: UserService) :
	UserAuthUseCase {

	override fun getSessionUser(request: HttpServletRequest): User {

		val sessionUserId = authService.whoAmI(request)

		return userService.getUserById(sessionUserId)
	}

}