package domain

import io.circe.generic.semiauto.deriveCodec
import cats.effect.IO

final case class Restaurant(
  id: Int,
  name: String,
  location: String,
  priceRange: Int,
  // ratings
)

object Restaurant {

  implicit val restaurantCodec = deriveCodec[Restaurant]

}
