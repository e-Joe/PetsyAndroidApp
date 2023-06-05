package com.bytecode.petsy.presentation.ui.screens.loginflow.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.data.model.dto.color.ColorDto
import com.bytecode.petsy.data.model.dto.dog.DogDto
import com.bytecode.petsy.data.model.dto.user.UserDto
import com.bytecode.petsy.domain.usecase.color.SaveColorsUseCase
import com.bytecode.petsy.domain.usecase.dog.GetDogsUseCase
import com.bytecode.petsy.domain.usecase.dog.SaveDogsUseCase
import com.bytecode.petsy.domain.usecase.user.GetLoggedInUserByCredentialsUseCase
import com.bytecode.petsy.domain.usecase.user.GetUserByEmailUseCase
import com.bytecode.petsy.domain.usecase.user.GetUsersUseCase
import com.bytecode.petsy.domain.usecase.user.SaveUserUseCase
import com.bytecode.petsy.domain.usecase.user.SaveUsersUseCase
import com.bytecode.petsy.domain.usecase.validation.ValidateCountry
import com.bytecode.petsy.domain.usecase.validation.ValidateEmail
import com.bytecode.petsy.domain.usecase.validation.ValidateFirstName
import com.bytecode.petsy.domain.usecase.validation.ValidatePassword
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordDigit
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordLength
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordLowerCase
import com.bytecode.petsy.domain.usecase.validation.ValidatePasswordUpperCase
import com.bytecode.petsy.domain.usecase.validation.ValidatePhoneNumber
import com.bytecode.petsy.domain.usecase.validation.ValidateSecondName
import com.bytecode.petsy.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validatePasswordDigit: ValidatePasswordDigit,
    private val validatePasswordLowerCase: ValidatePasswordLowerCase,
    private val validatePasswordLength: ValidatePasswordLength,
    private val validatePasswordUpperCase: ValidatePasswordUpperCase,
    private val validateFirstName: ValidateFirstName,
    private val validateSecondName: ValidateSecondName,
    private val validateCountry: ValidateCountry,
    private val validatePhoneNumber: ValidatePhoneNumber,
    private val saveUserUserCase: SaveUserUseCase,
    private val saveUsersUserCase: SaveUsersUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getLoggedInUserByCredentialsUseCase: GetLoggedInUserByCredentialsUseCase,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val saveDogsUserCase: SaveDogsUseCase,
    private val saveColorsUseCase: SaveColorsUseCase,
    private val getDogsUseCase: GetDogsUseCase,
) : MvvmViewModel() {

    var state by mutableStateOf(RegisterFormState())

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    private val countryModalChannel = Channel<ModalEvent>()
    val countryModalEvents = countryModalChannel.receiveAsFlow()

    private val countryChangeChannel = Channel<CountryEvent>()
    val countryChangeEvents = countryChangeChannel.receiveAsFlow()

    private var dogsList = mutableStateListOf(DogDto())
    private val _dogsListFlow = MutableStateFlow(dogsList)
    val dogsListFlow: StateFlow<List<DogDto>> get() = _dogsListFlow
    lateinit var user: UserDto

    private fun saveUser() = safeLaunch {
        val user = UserDto(
            0,
            state.email,
            state.password,
            state.firstName,
            state.lastName,
            state.country,
            false
        )
        val params = SaveUserUseCase.Params(user)
        call(saveUserUserCase(params))
        getLoggedInUserForNextStep()
    }

    private fun saveUsers(users: List<UserDto>) = safeLaunch {
        var loggedOutUsers = users.map { user -> user.copy(isLoggedIn = false) }
        val params = SaveUsersUseCase.Params(loggedOutUsers)
        call(saveUsersUserCase(params))
        saveUser()
    }

    private fun logOutAllUsersAndSaveLastOne() = safeLaunch {
        call(getUsersUseCase(Unit)) {
            if (it.isEmpty()) {
                saveUser()
            } else {
                saveUsers(it)
            }
        }
    }

    private fun checkIfUserExist() = safeLaunch {
        call(getUserByEmailUseCase(state.email)) {
            if (it.id > -1) {
                viewModelScope.launch {
                    validationChannel.send(ValidationEvent.UserExist)
                }
            } else {
                viewModelScope.launch {
                    validationChannel.send(ValidationEvent.Success)
                }
            }
        }
    }

    private fun getLoggedInUserForNextStep() = safeLaunch {
        call(getLoggedInUserByCredentialsUseCase(Pair(state.email, state.password))) {
            user = it
            viewModelScope.launch {
                validationChannel.send(ValidationEvent.Success)
            }
        }
    }

    private fun saveColors(colors: List<ColorDto>) = safeLaunch {
        Log.d("Dogs", "saveDogs ")
        call(saveColorsUseCase(SaveColorsUseCase.Params(colors)))
        Log.d("Dogs", "saveDogs after")
        resetState()
        validationChannel.send(ValidationEvent.Success)
    }

    private fun getColorForDog(index: Int): String {
        Log.d("Dogs", "Index is $index")
        return Constants.colors[index]
    }

    private fun saveDogs() = safeLaunch {
        Log.d("Dogs", "saveDogs 1")
        var dogs = arrayListOf<DogDto>()
        dogsList.forEachIndexed { index, dog ->
            dogs.add(DogDto(ownerId = user.id, color = getColorForDog(index), name = dog.name))
        }

        Log.d("Dogs", "saveDogs 2 $dogs")
        val params = SaveDogsUseCase.Params(dogs)
        call(saveDogsUserCase(params))
        updateColorsState()
    }

    private fun updateColorsState() = safeLaunch {
        Log.d("Dogs", "getDogs ")
        call(getDogsUseCase(user.id)) {
            if (it.isNotEmpty()) {
                dogsList.clear()
                dogsList.addAll(it)

                var colors = arrayListOf<ColorDto>()

                dogsList.forEach {
                    val color = ColorDto(value = it.color, dogId = it.id, ownerId = user.id)
                    colors.add(color)
                }
                saveColors(colors)
            }
        }
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

            is RegisterFormEvent.CloseCountryModal -> {
                viewModelScope.launch {
                    countryModalChannel.send(ModalEvent.Close)
                }
            }


            is RegisterFormEvent.Submit -> {
                when (event.registrationStep) {
                    RegistrationStep.FISRT -> validateFirstStep()
                    RegistrationStep.SECOND -> validateSecondStep()
                    RegistrationStep.THIRD -> validateThirdStep()
                }
            }

            is RegisterFormEvent.AddNewDogClicked -> {
                if (!dogsList.last().name.isEmpty())
                    addDog("")
            }

            is RegisterFormEvent.OnDogNameChanged -> {
                dogsList[event.index] = dogsList[event.index].copy(name = event.name)
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

        checkIfUserExist()
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

        logOutAllUsersAndSaveLastOne()
    }

    private fun validateThirdStep() {
        var isValid = true

        dogsList.forEach { dog ->
            if (dog.name.isEmpty())
                isValid = false
        }

        if (isValid)
            viewModelScope.launch {
                saveDogs()
            }
        else
            viewModelScope.launch {
                validationChannel.send(ValidationEvent.Fail)
            }

    }

    private fun addDog(name: String) {
        dogsList.add(DogDto(name = name, ownerId = user.id))
    }


    private fun resetState() {
        state = RegisterFormState()
    }
}

enum class RegistrationStep {
    FISRT,
    SECOND,
    THIRD
}

sealed class ValidationEvent {
    object Success : ValidationEvent()

    object UserExist : ValidationEvent()

    object Fail : ValidationEvent()
}

sealed class ModalEvent {
    object Open : ModalEvent()
    object Close : ModalEvent()
}

sealed class CountryEvent {
    data class CountryChanged(val name: String) : CountryEvent()
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

    data class AddNewDogClicked(val clicked: Boolean = true) : RegisterFormEvent()
    data class OnDogNameChanged(val index: Int, val name: String) : RegisterFormEvent()
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

//data class RegisterFormState(
//    val email: String = "vu@vu.com",
//    val password: String = "Ejoe1989",
//    val firstName: String = "Ilija",
//    val lastName: String = "Vucetic",
//    val country: String = "Serbia",
//    val phoneNumber: String = "",
//    val emailError: String? = null,
//    val passwordError: String? = null,
//    val firstNameError: String? = null,
//    val lastNameError: String? = null,
//    val countryError: String? = null,
//    val phoneNumberError: String? = null,
//    val isPasswordDigitValid: Boolean = false,
//    val isPasswordLength: Boolean = false,
//    val isPasswordUpperCaseValid: Boolean = false,
//    val isPasswordLowerCaseValid: Boolean = false,
//    val countryDialogShow: Boolean = false
//)