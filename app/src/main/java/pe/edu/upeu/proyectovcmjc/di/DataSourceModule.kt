package pe.edu.upeu.proyectovcmjc.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.edu.upeu.proyectovcmjc.data.local.DbDataSource
import pe.edu.upeu.proyectovcmjc.data.local.dao.PersonaDao
import pe.edu.upeu.proyectovcmjc.data.remote.RestDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl()="http://172.23.11.45:8000/"

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl:String):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).build()
    }
    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit):RestDataSource = retrofit.create(RestDataSource::class.java)

    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context:Context):DbDataSource{
        return Room.databaseBuilder(context, DbDataSource::class.java, "db_persona")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun personaDao(db:DbDataSource):PersonaDao=db.personaDao()

}



