package com.example.modoguardian.data.local

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "usuarios")
data class UsuarioEntity (
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val nombre: String ,
    val correo: String,
    val direccion: String,
    val modoEspecial: Boolean,
    val fotoPath: String?,
    val fechaRegistro: Date = Date()
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