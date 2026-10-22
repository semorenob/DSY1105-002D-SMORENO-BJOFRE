package com.example.modoguardian.data.local.database.entities

import android.net.Uri
import androidx.room.TypeConverter
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.sql.Date

@Entity(tableName = "usuarios")
data class UsuarioEntity (
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val nombre: String ,
    val correo: String,
    val dirrecion: String,
    val modoEspecial: Boolean,
    val fotoPath: String?,
    val fechaRegistro: Date
)

class Converters {
    @TypeConverter fun dateToLong(v: Date?): Long? = v?.time
    @TypeConverter fun fromLong(d:Long? ): Date? = d?.let {Date(it)}
    @TypeConverter fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }
    @TypeConverter fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it)}
    }
}