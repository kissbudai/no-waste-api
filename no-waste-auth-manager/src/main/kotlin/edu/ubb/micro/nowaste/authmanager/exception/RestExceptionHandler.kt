package edu.ubb.micro.nowaste.authmanager.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class RestExceptionHandler {

	@ExceptionHandler(HttpMessageNotReadableException::class)
	@Throws(IOException::class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	fun handleWrongMessageException(response: HttpServletResponse, exception: HttpMessageNotReadableException): NoWasteErrorResponse {

		@Suppress("MoveVariableDeclarationIntoWhen")
		val errorCause = exception.cause

		val errorMsg = when (errorCause) {
			is MissingKotlinParameterException -> "Invalid or missing parameter: ${errorCause.parameter.name}"
			else -> exception.localizedMessage
		}

		return NoWasteErrorResponse(null, errorMsg, null)
	}

	@ExceptionHandler(InvalidCredentialsException::class)
	@Throws(IOException::class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	fun handleNotAllowedException(response: HttpServletResponse, exception: InvalidCredentialsException): NoWasteErrorResponse =
		NoWasteErrorResponse(101, "Invalid username and password combination", exception.message)

	@ExceptionHandler(ApiException::class)
	@Throws(IOException::class)
	fun handleApiException(response: HttpServletResponse, exception: ApiException): NoWasteErrorResponse {
		response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()

		return NoWasteErrorResponse(null, exception.message.orEmpty(), exception.cause?.message)
	}

	@ExceptionHandler(UserAlreadyExistsException::class)
	@Throws(IOException::class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	fun handleNoWasteApiException(response: HttpServletResponse, exception: UserAlreadyExistsException): NoWasteErrorResponse =
		NoWasteErrorResponse(102, "Username already exists, choose another one", null)
}