package com.bytecode.petsy.presentation.ui.screens.loginflow.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.domain.usecase.validation.*
import com.bytecode.petsy.domain.usecase.welcome.SaveOnBoardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val saveOnBoarding: SaveOnBoardingUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePasswordDigit: ValidatePasswordDigit,
    private val validatePasswordLowerCase: ValidatePasswordLowerCase,
    private val validatePasswordLength: ValidatePasswordLength,
    private val validatePasswordUpperCase: ValidatePasswordUpperCase,
    private val validateFirstName: ValidateFirstName,
    private val validateSecondName: ValidateSecondName,
    private val validateCountry: ValidateCountry,
    private val validatePhoneNumber: ValidatePhoneNumber
) : MvvmViewModel() {

    var state by mutableStateOf(RegisterFormState())

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    private val countryModalChannel = Channel<ModalEvent>()
    val countryModalEvents = countryModalChannel.receiveAsFlow()

    private val countryChangeChannel = Channel<CountryEvent>()
    val countryChangeEvents = countryChangeChannel.receiveAsFlow()

    fun saveOnBoardingState(completed: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val params = SaveOnBoardingUseCase.Params(completed)
        call(saveOnBoarding(params))
    }

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is RegisterFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                state =
                    state.copy(isPasswordDigitValid = validatePasswordDigit.execute(state.password).successful)
                state =
                    state.copy(isPasswordLength = validatePasswordLength.execute(state.password).successful)
                state =
                    state.copy(isPasswordLowerCaseValid = validatePasswordLowerCase.execute(state.password).successful)
                state =
                    state.copy(isPasswordUpperCaseValid = validatePasswordUpperCase.execute(state.password).successful)
            }

            is RegisterFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }

            is RegisterFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }

            is RegisterFormEvent.CountryChanged -> {
                state = state.copy(country = event.country)
            }

            is RegisterFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }

            is RegisterFormEvent.CountryUpdated -> {
                state = state.copy(country = event.name)
                viewModelScope.launch {
                    countryModalChannel.send(ModalEvent.Close)
                }

                viewModelScope.launch {
                    countryChangeChannel.send(CountryEvent.CountryChanged(state.country))
                }
            }

            is RegisterFormEvent.CountryFieldClicked -> {
                viewModelScope.launch {
                    countryModalChannel.send(ModalEvent.Open)
                }
            }

            is RegisterFormEvent.CloseCountryModal-> {
                viewModelScope.launch {
                    countryModalChannel.send(ModalEvent.Close)
                }
            }


            is RegisterFormEvent.Submit -> {
                when (event.registrationStep) {
                    RegistrationStep.FISRT -> validateFirstStep()
                    RegistrationStep.SECOND -> validateSecondStep()
                    RegistrationStep.THIRD -> validateSecondStep()
                }
            }
        }
    }

    private fun validateFirstStep() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)


        val hasError = listOf(
            emailResult, passwordResult
        ).any {
            it?.errorMessage != null
        }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        } else {
            state = state.copy(
                emailError = null,
                passwordError = null
            )
        }

        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }

    }

    private fun validateSecondStep() {
        val firstName = validateFirstName.execute(state.firstName)
        val lastName = validateSecondName.execute(state.lastName)
        val country = validateCountry.execute(state.country)
        val phoneNumber = validatePhoneNumber.execute(state.phoneNumber)


        val hasError = listOf(
            firstName, lastName, country, phoneNumber
        ).any {
            it?.errorMessage != null
        }

        if (hasError) {
            state = state.copy(
                firstNameError = firstName.errorMessage,
                lastNameError = lastName.errorMessage,
                countryError = country.errorMessage,
                phoneNumberError = phoneNumber.errorMessage
            )
            return
        } else {
            state = state.copy(
                firstNameError = null,
                lastNameError = null,
                countryError = null,
                phoneNumberError = null
            )
        }

        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }

    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    sealed class ModalEvent {
        object Open : ModalEvent()
        object Close : ModalEvent()
    }

    sealed class CountryEvent {
        data class CountryChanged(val name: String) : CountryEvent()
    }
}

enum class RegistrationStep {
    FISRT,
    SECOND,
    THIRD
}

sealed class RegisterFormEvent {
    data class EmailChanged(val email: String) : RegisterFormEvent()
    data class PasswordChanged(val password: String) : RegisterFormEvent()
    data class FirstNameChanged(val firstName: String) : RegisterFormEvent()
    data class LastNameChanged(val lastName: String) : RegisterFormEvent()
    data class CountryChanged(val country: String) : RegisterFormEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegisterFormEvent()
    data class CountryUpdated(val name: String) : RegisterFormEvent()
    data class CountryFieldClicked(var a: String = "") : RegisterFormEvent()
    data class CloseCountryModal(var a: String = "") : RegisterFormEvent()
    data class Submit(val registrationStep: RegistrationStep = RegistrationStep.FISRT) :
        RegisterFormEvent()
}

data class RegisterFormState(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val country: String = "",
    val phoneNumber: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val countryError: String? = null,
    val phoneNumberError: String? = null,
    val isPasswordDigitValid: Boolean = false,
    val isPasswordLength: Boolean = false,
    val isPasswordUpperCaseValid: Boolean = false,
    val isPasswordLowerCaseValid: Boolean = false,
    val countryDialogShow: Boolean = false
)