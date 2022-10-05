package pe.edu.upeu.proyectovcmjc.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persona")

data class Persona(
    @PrimaryKey(autoGenerate = true) var id:Int,
    @ColumnInfo(name = "dni") var dni:String,
    var nombre:String,
    @ColumnInfo(name = "apellidoPaterno") var apellidoPaterno: String,
    @ColumnInfo(name = "apellidoMaterno") var apellidoMaterno: String,
    var telefono:String,
    var genero:String,
    var correo:String,
)
