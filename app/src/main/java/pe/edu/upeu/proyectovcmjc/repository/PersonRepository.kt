package pe.edu.upeu.proyectovcmjc.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pe.edu.upeu.proyectovcmjc.data.local.dao.PersonaDao
import pe.edu.upeu.proyectovcmjc.data.remote.RestDataSource
import pe.edu.upeu.proyectovcmjc.modelo.Persona
import pe.edu.upeu.proyectovcmjc.modelo.User
import pe.edu.upeu.proyectovcmjc.utils.TokenUtils
import javax.inject.Inject

interface PersonRepository {

    suspend fun deletePersona (persona: Persona)

    fun reportarPersona():LiveData<List<Persona>>
}

class PersonRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val personaDao: PersonaDao,
):PersonRepository{
    override suspend fun deletePersona(persona: Persona) =personaDao.eleminarPersona(persona)

    override fun reportarPersona(): LiveData<List<Persona>> {
        //delay(3000)


        try {
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                Log.i("VERRX", "Llega aqui!!")
                val tokenx = dataSource.login(User("", "jaseb@gmail.com", "12345678"))
                Log.i("VERRX", "${tokenx.token_type} ${tokenx.access_token}")
                TokenUtils.TOKEN_CONTENT = "${tokenx.token_type} ${tokenx.access_token}"
                val data = dataSource.reportarPersona(TokenUtils.TOKEN_CONTENT).body()!!.data
                //Log.i("VERRX", "Llega aqui!!" + data.toString())
                personaDao.insertarPersonas(data)
            }
        } catch (e: Exception) {
            Log.i("ERROR", ""+e.message)
        }

        return personaDao.reportarPersonas()

    }

}