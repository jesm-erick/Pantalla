package pe.edu.upeu.proyectovcmjc.ui.presentation.screens.persona

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.k0shk0sh.compose.easyforms.*
import com.google.gson.Gson
import pe.edu.upeu.proyectovcmjc.modelo.Persona
import pe.edu.upeu.proyectovcmjc.ui.navigation.Destinations
import pe.edu.upeu.proyectovcmjc.ui.presentation.screens.persona.custom.MyEasyFormsCustomStringState

enum class MyFormKeys {
    EMAIL, PASSWORD, SALUTATION, NAME, URL, CUSTOM_FOCUS,
    PHONE, CARD, CHECKBOX, LIST_CHECKBOX, TRI_CHECKBOX, RADIO_BUTTON,
    SWITCH, SLIDER, RANGE_SLIDER,DNI, APE_PAT, APE_MAT
}

@Composable
fun PersonaForm(
    text: String,
    darkMode: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: PersonaFormViewModel= hiltViewModel()
) {
    val personD:Persona
    if (text!="0"){
        personD = Gson().fromJson(text, Persona::class.java)
    }else{
        personD=Persona(0,"","", "","","","","")
    }
    val isLoading by viewModel.isLoading.observeAsState(false)
    formulario(personD.id,
        darkMode,
        navController,
        personD,
        viewModel
    )
}

@Composable
fun formulario(id:Int,
               darkMode: MutableState<Boolean>,
               navController: NavHostController,
               persona:Persona,
               viewModel: PersonaFormViewModel){
    Log.i("VERRR", "d: "+persona?.dni!!)
    Scaffold(modifier = Modifier.padding(8.dp)){
        BuildEasyForms { easyForm ->
            Column {
// your Composables
                MyTextField(easyForms = easyForm, text = persona?.dni!!)
                NameTextField(easyForms = easyForm, text =persona?.nombre!!,"Nombre:", MyFormKeys.NAME )
                NameTextField(easyForms = easyForm, text =persona?.apellido_paterno!!, "Apellido Paterno:", MyFormKeys.APE_PAT )
                NameTextField(easyForms = easyForm, text =persona?.apellido_materno!! , "Apellido Paterno:", MyFormKeys.APE_MAT )
                PhoneTextField(easyForms = easyForm, persona?.telefono!!)
                Salutation(easyForm = easyForm, "Genero:", persona?.genero!!)
                EmailTextField(easyForms = easyForm, persona?.correo!!)
                Row(Modifier.align(Alignment.CenterHorizontally)){
                    GuardarButton(easyForms = easyForm){
                        val lista=easyForm.formData()
//lista.forEach{ println((it as EasyFormsResult.StringResult).value) }
                        val person=Persona(0,"","", "","","","","")

                        person.dni=(lista.get(0) as EasyFormsResult.StringResult).value
                        person.nombre=(lista.get(1) as EasyFormsResult.StringResult).value
                        person.apellido_paterno=(lista.get(2) as EasyFormsResult.StringResult).value
                        person.apellido_materno=(lista.get(3) as EasyFormsResult.StringResult).value
                        person.telefono=(lista.get(4) as EasyFormsResult.StringResult).value
                        person.correo=(lista.get(6) as EasyFormsResult.StringResult).value
                        person.genero=(lista.get(5) as EasyFormsResult.GenericStateResult<String>).value
                        if (id==0){
                            viewModel.addPersona(person)
                        }else{
                            person.id=id
                            Log.i("MODIFICAR", "M:"+person)
                            viewModel.editPersona(person)
                        }
                        navController.navigate(Destinations.PersonaUI.route)
                    }
                    Spacer()
                    CancelarButton(easyForms = easyForm){
                        navController.navigate(Destinations.PersonaUI.route)
                    }
                }
            }
        }
    }
}








@Composable
fun Salutation(
    easyForm: EasyForms,
    label:String,
    textv: String
) {
    val list = listOf("M", "F")
    val state = easyForm.addAndGetCustomState(
        MyFormKeys.SALUTATION, MyEasyFormsCustomStringState(
            validData = list
        )
    )
    val text = state.state
    val isOpen = state.isOpen
    if (textv != "") {
        text.value = textv
    }
    Box{
        Column {
            TextField(
                value = text.value,
                onValueChange = {},
                label = { Text(text = label) },
                placeholder = { Text(text = label) },
                modifier = Modifier.fillMaxWidth(),
            )
            DropDownList(
                state = state,
                requestToOpen = isOpen.value,
                list = list,
            )
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable (onClick = state.onClick)
        )
    }
}

@Composable
private fun DropDownList(
    state: MyEasyFormsCustomStringState,
    requestToOpen: Boolean = false,
    list: List<String>,
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        expanded = requestToOpen,
        onDismissRequest = state.onDismissed,
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = { state.onValueChangedCallback(it) },
            ) {
                Text(
                    text = it,
                    modifier = Modifier.wrapContentWidth(),
                )
            }
        }
    }
}

@Composable
fun EmailTextField(easyForms: EasyForms,text: String) {
    val textFieldState = easyForms.getTextFieldState(
        key = MyFormKeys.EMAIL,
        easyFormsValidationType = EmailValidationType,
        defaultValue = text,
    )
    val state = textFieldState.state
    TextField(
        value = state.value,
        onValueChange = textFieldState.onValueChangedCallback,
        label = { Text(text = "Correo:") },
        modifier = Modifier.fillMaxWidth(),
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
    )
}

object MyCustomLengthValidationType : EasyFormsValidationType(
    minLength = 8,
    maxLength = 8,
)
@Composable
fun MyTextField(easyForms: EasyForms, text: String) {
    val textFieldState = easyForms.getTextFieldState(MyFormKeys.DNI,
        MyCustomLengthValidationType,
        defaultValue = text,
    )
    val state = textFieldState.state
    TextField(
        value = state.value,
        onValueChange = textFieldState.onValueChangedCallback,
        label = { Text(text = "DNI:") },
        modifier = Modifier.fillMaxWidth(),
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
    )
}

@Composable
fun PhoneTextField(easyForms: EasyForms, text: String) {
    val textFieldState = easyForms.getTextFieldState(
        key = MyFormKeys.PHONE,
        easyFormsValidationType = PhoneNumberValidationType,
        defaultValue = text,
    )
    val state = textFieldState.state
    TextField(
        value = state.value,
        onValueChange = textFieldState.onValueChangedCallback,
        label = { Text(text = "Telefono:") },
        modifier = Modifier.fillMaxWidth(),
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
    )
}

@Composable
fun NameTextField(easyForms: EasyForms, text: String, label:String, key:MyFormKeys) {
    val textFieldState = easyForms.getTextFieldState(
        key = key,
        easyFormsValidationType = NameValidationType,
        defaultValue = text,
    )
    val state = textFieldState.state
    TextField(
        value = state.value,
        onValueChange = textFieldState.onValueChangedCallback,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
    )
}


@Composable
fun GuardarButton(
    easyForms: EasyForms,
    onClick: () -> Unit,
) {
    val errorStates = easyForms.observeFormStates()
    Button(
        onClick = onClick,
        modifier = Modifier.wrapContentWidth(),
        enabled = errorStates.value.all {
            it.value == EasyFormsErrorState.VALID
        }
    ) {
        Text("Guardar")
    }
}

@Composable
fun CancelarButton(
    easyForms: EasyForms,
    onClick: () -> Unit,
) {
    val errorStates = easyForms.observeFormStates()
    Button(
        onClick = onClick,
        modifier = Modifier.wrapContentWidth(),
    ) {
        Text("Cancelar")
    }
}
