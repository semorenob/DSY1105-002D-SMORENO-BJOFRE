package com.example.modoguardian.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.modoguardian.data.local.database.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios ORDER BY fechaRegistro DESC")
    fun getUsuariosFlow(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE id =:id")
    suspend fun getPorld(id: Int): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioEntity)

    @Delete
    suspend fun eliminar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE correo LIKE :query")
    fun buscarPorCorreo(query: String): Flow<List<UsuarioEntity>>
}