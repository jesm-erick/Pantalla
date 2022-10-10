package pe.edu.upeu.proyectovcmjc.data.remote

import pe.edu.upeu.proyectovcmjc.modelo.MsgModelResponse
import pe.edu.upeu.proyectovcmjc.modelo.Persona
import pe.edu.upeu.proyectovcmjc.modelo.User
import pe.edu.upeu.proyectovcmjc.modelo.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface RestDataSource {
    @GET("/apiv1/persona")
    fun reportarPersona(@Header("Authorization") token:String):Response<MsgModelResponse>

    @GET("/apiv1/persona/{id}")
    suspend fun getPersonaId(@Header("Authorization") token:String, @Query("id") id:Int):Response<MsgModelResponse>

    @DELETE("/apiv1/persona")
    suspend fun deletePersona(@Path("id") id:Int):Response<MsgModelResponse>

    @PATCH("/apiv1/persona")
    suspend fun actualizarPersona(@Body persona: Persona):Response<MsgModelResponse>

    @POST("/apiv1/persona")
    suspend fun insertarPersona(@Body persona: Persona):Response<MsgModelResponse>

    @POST("/api/auth/login")
    fun login(@Body user: User):Response<UserResponse>

}