package pe.edu.upeu.proyectovcmjc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.edu.upeu.proyectovcmjc.repository.PersonaRepository
import pe.edu.upeu.proyectovcmjc.repository.PersonaRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun personaRepository(perRepos: PersonaRepositoryImp): PersonaRepository
}