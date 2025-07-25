package me.kvdpxne.dtm.data.serialization

import java.util.UUID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.kvdpxne.dtm.data.raw.RawMonumentPosition

class SerializerMonumentPosition : KSerializer<RawMonumentPosition> {

  override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
    RawMonumentPosition::class.java.name,
  ) {
    element("monument", String.serializer().descriptor)
    element<UUID>("identifier")
  }

  override fun deserialize(decoder: Decoder): RawMonumentPosition {
    TODO("Not yet implemented")
  }

  override fun serialize(encoder: Encoder, value: RawMonumentPosition) {
    TODO("Not yet implemented")
  }
}