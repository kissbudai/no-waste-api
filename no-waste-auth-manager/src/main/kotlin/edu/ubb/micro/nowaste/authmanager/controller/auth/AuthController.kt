package edu.ubb.micro.nowaste.authmanager.controller.auth

import edu.ubb.micro.nowaste.authmanager.dto.UserDTO
import edu.ubb.micro.nowaste.authmanager.dto.toDTO
import edu.ubb.micro.nowaste.authmanager.dto.toUser
import edu.ubb.micro.nowaste.authmanager.exception.ApiException
import edu.ubb.micro.nowaste.authmanager.service.auth.AuthService
import edu.ubb.micro.nowaste.authmanager.service.ServiceException
import edu.ubb.micro.nowaste.authmanager.service.UserAuthUseCase
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
class AuthController(private val authService: AuthService, private val passwordEncoder: PasswordEncoder, private val authUseCase: UserAuthUseCase) {

	private val logger = LoggerFactory.getLogger(AuthController::class.java)

	@PostMapping("/auth/login")
	fun login(@RequestBody loginRequestBody: LoginRequestBody): ResponseEntity<UserDTO> {
		logger.info("Login called with $loginRequestBody")

		try {
			val loggedInUser = authService.authenticate(loginRequestBody.userName, loginRequestBody.password)
			logger.debug("User for login based on credentials: $loggedInUser")

			val token = authService.getTokenForUserId(loggedInUser.id)
			logger.info("Login succeeded, created token for login: $token")

			return ResponseEntity(loggedInUser.toDTO(), HttpHeaders().apply { setBearerAuth(token) }, HttpStatus.OK)

		} catch (ex: ServiceException) {
			logger.info("Login failed for user with credentials: $loginRequestBody")
			logger.debug("Login failure reason: $ex")

			throw ApiException("Unable to perform the login", ex)
		}
	}

	@PostMapping("/auth/register")
	fun register(@Valid @RequestBody signUpRequestBody: SignUpRequestBody): ResponseEntity<UserDTO> {
		logger.info("Register endpoint called with $signUpRequestBody params")

		try {
			val mappedUser = signUpRequestBody.toUser().apply {
				password = passwordEncoder.encode(signUpRequestBody.password)
			}

			val savedUser = authService.register(mappedUser)

			val token = authService.getTokenForUserId(savedUser.id)

			return ResponseEntity(savedUser.toDTO(), HttpHeaders().apply { setBearerAuth(token) }, HttpStatus.OK)
		} catch (ex: ServiceException) {
			logger.info("Failed to register a new user because of: $ex")
			throw ApiException("Unable to register becuase ${ex.message}", ex)
		}
	}

	@GetMapping("/auth/user/me")
	fun getMe(request: HttpServletRequest): ResponseEntity<UserDTO> {
		logger.info("/auth/me endpoint called")

		try {
			val user = authUseCase.getSessionUser(request)

			return ResponseEntity.ok(user.toDTO())
		} catch (ex: ServiceException) {
			throw ApiException("Unable to determine the session user, because ${ex.message}", ex)
		}
	}
}