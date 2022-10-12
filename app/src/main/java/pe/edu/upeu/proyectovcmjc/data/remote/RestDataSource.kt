package pe.edu.upeu.proyectovcmjc.data.remote

import pe.edu.upeu.proyectovcmjc.modelo.MsgModelResponse
import pe.edu.upeu.proyectovcmjc.modelo.Persona
import pe.edu.upeu.proyectovcmjc.modelo.User
import pe.edu.upeu.proyectovcmjc.modelo.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface RestDataSource {
    @GET("/api/persona")
    suspend fun reportarPersona(@Header("Authorization") token:String):Response<MsgModelResponse>

    @GET("/api/persona/{id}")
    suspend fun getPersonaId(@Header("Authorization") token:String, @Query("id") id:Int):Response<MsgModelResponse>

    @DELETE("/api/persona")
    suspend fun deletePersona(@Path("id") id:Int):Response<MsgModelResponse>

    @PATCH("/api/persona")
    suspend fun actualizarPersona(@Body persona: Persona):Response<MsgModelResponse>

    @POST("/api/persona")
    suspend fun insertarPersona(@Body persona: Persona):Response<MsgModelResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body user: User):UserResponse

}