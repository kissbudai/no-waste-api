package edu.ubb.micro.nowaste.authmanager.service.auth

import edu.ubb.micro.nowaste.authmanager.exception.InvalidCredentialsException
import edu.ubb.micro.nowaste.authmanager.exception.UserAlreadyExistsException
import edu.ubb.micro.nowaste.authmanager.model.User
import edu.ubb.micro.nowaste.authmanager.security.AuthTokenProvider
import edu.ubb.micro.nowaste.authmanager.service.ServiceException
import edu.ubb.micro.nowaste.authmanager.repository.UserRepository
import org.springframework.dao.DataAccessException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class AuthServiceImpl(private val userRepository: UserRepository, private val tokenProvider: AuthTokenProvider, private val passwordEncoder: PasswordEncoder) :
	AuthService {

	override fun getTokenForUserId(id: String): String = tokenProvider.createToken(id)

	override fun whoAmI(request: HttpServletRequest): String {
		try {
			val currentToken = tokenProvider.resolveToken(request)
				?: throw ServiceException("Couldn't find a valid token", Exception("No token in header"))

			return tokenProvider.getUserIdFromToken(currentToken)
		} catch (ex: DataAccessException) {
			throw ServiceException("Unable to determine the user from session, because $ex", ex)
		}
	}

	@Throws(ServiceException::class)
	override fun authenticate(userName: String, password: String): User {

		try {
			val user = userRepository.findUserByUserName(userName)
				?: throw InvalidCredentialsException("Invalid credentials")

			if (!passwordEncoder.matches(password, user.password)) {
				throw InvalidCredentialsException("Invalid credentials")
			}

			return user
		} catch (ex: DataAccessException) {
			throw ServiceException("Unable to login the user, because ${ex.message}", ex)
		}
	}

	override fun register(user: User): User {

		try {

			if (userRepository.findUserByUserName(user.userName) != null) {
				throw UserAlreadyExistsException()
			}

			return userRepository.save(user)

		} catch (ex: DataAccessException) {
			throw ServiceException("Unable to register because ${ex.message}", ex)
		}
	}
}