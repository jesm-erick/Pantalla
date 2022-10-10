package pe.edu.upeu.proyectovcmjc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upeu.proyectovcmjc.repository.PersonRepository
import pe.edu.upeu.proyectovcmjc.repository.PersonRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryMovil {

    @Binds
    @Singleton
    abstract fun personaRepository(perRepos: PersonRepositoryImp): PersonRepository
}