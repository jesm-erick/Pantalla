package pe.edu.upeu.proyectovcmjc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.upeu.proyectovcmjc.modelo.Persona
import pe.edu.upeu.proyectovcmjc.data.local.dao.PersonaDao


@Database(entities = [Persona::class], version = 1)
abstract class DbDataSource:RoomDatabase() {
    abstract fun personaDao():PersonaDao
}