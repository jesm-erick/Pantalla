package pe.edu.upeu.proyectovcmjc.modelo

data class MsgModelResponse(
    var success:Boolean,
    var data:List<Persona>,
    var message:String
)