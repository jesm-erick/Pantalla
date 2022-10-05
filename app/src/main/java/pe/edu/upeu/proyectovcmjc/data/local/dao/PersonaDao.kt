package pe.edu.upeu.proyectovcmjc.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pe.edu.upeu.proyectovcmjc.modelo.Persona

@Dao
interface PersonaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPersona(persona: Persona)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPersonas(persona: List<Persona>)

    @Update
    suspend fun actualizarPersona(persona: Persona)

    @Delete
    suspend fun eleminarPersona(persona: Persona)

    @Query("select * from persona")
    fun reportarPersonas():LiveData<List<Persona>>

    @Query("select * from persona where id=:id")
    fun buscarPersona(id:Int):LiveData<Persona>
}