package pe.edu.upeu.proyectovcmjc.repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.delay
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

        val token=dataSource.login(User("","jaseb@gmail.com","12345678"))
        TokenUtils.TOKEN_CONTENT=token.body()?.token_type+" "+token.body()?.access_token
        Log.i("VERX", "Token:"+TokenUtils.TOKEN_CONTENT)
        val data=dataSource.reportarPersona(TokenUtils.TOKEN_CONTENT).body()!!.data
        personaDao.insertarPersonas(data)
        return personaDao.reportarPersonas()
    }

}