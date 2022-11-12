package api

import io.circe.generic.semiauto.deriveCodec

final case class UpdateRestaurant(
  restaurantId: Int,
  name: String,
  location: String,
  priceRange: Int,
)

object UpdateRestaurant {
  implicit val updateRestaurantCodec = deriveCodec[UpdateRestaurant]
}
