package com.bytecode.petsy.presentation.ui.screens.loginflow.landing

import com.bytecode.framework.base.MvvmViewModel
import com.bytecode.petsy.data.model.dto.language.LanguageDto
import com.bytecode.petsy.domain.usecase.language.GetLanguageUseCase
import com.bytecode.petsy.domain.usecase.language.SaveLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
) : MvvmViewModel() {

    private var englishLanguage =
        LanguageDto(
            languageName = "English",
            countryCode = "GB",
            flagCode = "GB",
            isSelected = true
        )
    private val _englishLanguage = MutableStateFlow(englishLanguage)
    val englishLanguageFlow = _englishLanguage.asStateFlow()

    private var denmarkLanguage =
        LanguageDto(
            languageName = "Denmark",
            countryCode = "DK",
            flagCode = "DK",
            isSelected = false
        )
    private val _denmarkLanguage = MutableStateFlow(denmarkLanguage)
    val denmarkLanguageFlow = _denmarkLanguage.asStateFlow()

    private var romanianLanguage =
        LanguageDto(
            languageName = "Romanian",
            countryCode = "RO",
            flagCode = "RO",
            isSelected = false
        )
    private val _romanianLanguage = MutableStateFlow(romanianLanguage)
    val romanianLanguageFlow = _romanianLanguage.asStateFlow()

    private var serbianLanguage =
        LanguageDto(
            languageName = "Serbian",
            countryCode = "RS",
            flagCode = "RS",
            isSelected = false
        )
    private val _serbianLanguage = MutableStateFlow(serbianLanguage)
    val serbianLanguageFlow = _serbianLanguage.asStateFlow()

    var selectedLanguage = "GB"
    var tempLanguage = "GB"

    private val _selectedLanguage = MutableStateFlow(selectedLanguage)
    val selectedLanguageFlow = _selectedLanguage.asStateFlow()

    init {
        readLanguageFunc()
    }

    private fun readLanguageFunc() = safeLaunch {
        call(getLanguageUseCase(Unit)) {
            englishLanguage = englishLanguage.copy(isSelected = false)
            romanianLanguage = romanianLanguage.copy(isSelected = false)
            denmarkLanguage = denmarkLanguage.copy(isSelected = false)
            serbianLanguage = serbianLanguage.copy(isSelected = false)

            tempLanguage = it.countryCode
            selectedLanguage = it.countryCode
            _selectedLanguage.value = selectedLanguage

            when (selectedLanguage) {
                "GB" -> {
                    englishLanguage = englishLanguage.copy(isSelected = true)
                }

                "DK" -> {
                    denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                }

                "RO" -> {
                    romanianLanguage = romanianLanguage.copy(isSelected = true)
                }

                "RS" -> {
                    serbianLanguage = serbianLanguage.copy(isSelected = true)
                }
            }

            _englishLanguage.value = englishLanguage
            _romanianLanguage.value = romanianLanguage
            _denmarkLanguage.value = denmarkLanguage
            _serbianLanguage.value = serbianLanguage
        }
    }

    fun onEvent(event: LandingFlowEvent) {
        when (event) {
            is LandingFlowEvent.LanguageClicked -> {
                tempLanguage = event.countryCode

                englishLanguage = englishLanguage.copy(isSelected = false)
                romanianLanguage = romanianLanguage.copy(isSelected = false)
                denmarkLanguage = denmarkLanguage.copy(isSelected = false)
                serbianLanguage = serbianLanguage.copy(isSelected = false)

                when (tempLanguage) {
                    "GB" -> englishLanguage = englishLanguage.copy(isSelected = true)
                    "DK" -> denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                    "RO" -> romanianLanguage = romanianLanguage.copy(isSelected = true)
                    "RS" -> serbianLanguage = serbianLanguage.copy(isSelected = true)
                }

                _englishLanguage.value = englishLanguage
                _romanianLanguage.value = romanianLanguage
                _denmarkLanguage.value = denmarkLanguage
                _serbianLanguage.value = serbianLanguage
            }

            is LandingFlowEvent.SaveLanguageClicked -> {
                saveLanguage(selectedLanguage)
            }

            is LandingFlowEvent.CheckLanguage -> {
                englishLanguage = englishLanguage.copy(isSelected = false)
                romanianLanguage = romanianLanguage.copy(isSelected = false)
                denmarkLanguage = denmarkLanguage.copy(isSelected = false)
                serbianLanguage = serbianLanguage.copy(isSelected = false)

                when (selectedLanguage) {
                    "GB" -> englishLanguage = englishLanguage.copy(isSelected = true)
                    "DK" -> denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                    "RO" -> romanianLanguage = romanianLanguage.copy(isSelected = true)
                    "RS" -> serbianLanguage = serbianLanguage.copy(isSelected = true)
                }

                _englishLanguage.value = englishLanguage
                _romanianLanguage.value = romanianLanguage
                _denmarkLanguage.value = denmarkLanguage
                _serbianLanguage.value = serbianLanguage
            }
        }
    }

    private fun saveLanguage(langCode: String) = safeLaunch {
        var language = LanguageDto()
        selectedLanguage = langCode

        _selectedLanguage.value = selectedLanguage

        when (tempLanguage) {
            "GB" -> {
                englishLanguage = englishLanguage.copy(isSelected = true)
                language = englishLanguage.copy()
            }

            "DK" -> {
                denmarkLanguage = denmarkLanguage.copy(isSelected = true)
                language = denmarkLanguage.copy()
            }

            "RO" -> {
                romanianLanguage = romanianLanguage.copy(isSelected = true)
                language = romanianLanguage.copy()
            }

            "RS" -> {
                serbianLanguage = serbianLanguage.copy(isSelected = true)
                language = serbianLanguage.copy()
            }
        }

        call(saveLanguageUseCase(SaveLanguageUseCase.Params(language))) {
            readLanguageFunc()
        }
    }


}

sealed class LandingFlowEvent() {
    data class LanguageClicked(val countryCode: String) : LandingFlowEvent()

    data class SaveLanguageClicked(val temp: String) : LandingFlowEvent()

    data class CheckLanguage(val temp: String) : LandingFlowEvent()
}