package pe.edu.upeu.proyectovcmjc.ui.presentation.screens.persona

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upeu.proyectovcmjc.modelo.Persona
import pe.edu.upeu.proyectovcmjc.repository.PersonaRepository
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val userRepo: PersonaRepository
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val users: LiveData<List<Persona>> by lazy {
        userRepo.reportarPersonas()
    }

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun addUser() {
        if (_isLoading.value == false)
            viewModelScope.launch  (Dispatchers.IO) {
                _isLoading.postValue(true)
                // userRepo._isLoading.postValue(false)

            }
    }

    fun deleteUser(toDelete: Persona) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.deletePersona(toDelete);
        }
    }
}