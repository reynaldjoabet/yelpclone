package api

import io.circe.generic.semiauto.deriveCodec

final case class CreateRestaurant(
  name: String,
  location: String,
  priceRange: Int,
  // ratings
)

object CreateRestaurant {

  implicit val createRestaurantCodec = deriveCodec[CreateRestaurant]

}
