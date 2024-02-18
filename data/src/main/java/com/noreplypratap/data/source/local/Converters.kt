package com.noreplypratap.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noreplypratap.data.model.remote.SourceDTO

class Converters {
    private val gson = Gson()
    @TypeConverter
    fun fromSourceDTO(source : SourceDTO) : String {
        return gson.toJson(source)
    }
    @TypeConverter
    fun toSourceDTO(data : String) : SourceDTO {
        val obj = object : TypeToken<SourceDTO>() {}.type
        return gson.fromJson(data, obj)
    }
}