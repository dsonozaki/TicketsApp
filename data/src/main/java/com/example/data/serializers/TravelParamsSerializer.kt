package com.example.data.serializers

import androidx.datastore.core.Serializer
import com.example.data.dto.TravelParamsDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class TravelParamsSerializer @Inject constructor() : Serializer<TravelParamsDto> {
    override val defaultValue: TravelParamsDto
        get() = TravelParamsDto()

    override suspend fun readFrom(input: InputStream): TravelParamsDto {
        return try {
            Json.decodeFromString(
                deserializer = TravelParamsDto.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: TravelParamsDto, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = TravelParamsDto.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}