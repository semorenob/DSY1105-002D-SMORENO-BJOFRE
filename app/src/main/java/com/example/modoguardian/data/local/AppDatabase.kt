package com.example.modoguardian.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Database(entities = [UsuarioEntity::class], version=1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao
    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(c: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(c.applicationContext, AppDatabase::class.java, "app_database").build()
                .also { INSTANCE = it }
        }
    }
}

class UsuarioRepository(private val dao: UsuarioDao){
    val usuarios: Flow<List<UsuarioEntity>> = dao.getUsuariosFlow()
    suspend fun agregar (u: UsuarioEntity)= dao.insertar(u)
}