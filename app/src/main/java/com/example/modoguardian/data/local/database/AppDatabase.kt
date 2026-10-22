package com.example.modoguardian.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.modoguardian.data.local.database.dao.UsuarioDao
import com.example.modoguardian.data.local.database.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [UsuarioEntity::class], version=1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao
    companion object{
        @Volatile private var INSTANCE: AppDatabase?= null
        fun getDatabase(c: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(c, AppDatabase::class.java, "coworkhub.db").build()
                .also { INSTANCE = it }
        }
    }
}

class UsuarioRepository(private val dao: UsuarioDao){
    val usuarios: Flow<List<UsuarioEntity>> = dao.getUsuariosFlow()
    suspend fun agregar (u: UsuarioEntity)= dao.insertar(u)
}