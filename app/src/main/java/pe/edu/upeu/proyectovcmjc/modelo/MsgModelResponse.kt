package pe.edu.upeu.proyectovcmjc.modelo

data class MsgModelResponse(
    var succes: Boolean,
    var data: List<Persona>,
    var message:String,
)
