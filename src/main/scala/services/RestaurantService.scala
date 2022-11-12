package services

import domain._
import cats.effect.kernel.Async
import doobie.util.transactor.Transactor
import doobie.implicits._

final case class RestaurantService[F[_]: Async](private val xa: Transactor[F]) {

  def getRestaurantById(restaurantId: Int): F[Option[Restaurant]] =
    sql"SELECT * FROM restaurants WHERE id= $restaurantId"
      .query[Restaurant]
      .option
      .transact(xa)

  def getRestaurants: F[List[Restaurant]] = sql"SELECT * FROM restaurants"
    .query[Restaurant]
    .to[List]
    .transact(xa)

  def saveRestaurant(
    name: String,
    location: String,
    priceRange: Int,
  ): F[Restaurant] =
    sql"INSERT INTO restaurants (name,location,priceRange) VALUES($name,$location,$priceRange)"
      .update
      .withUniqueGeneratedKeys[Restaurant]("id", "name", "location", "price_range")
      .transact(xa)

  def deleteRestaurant(
    restaurantId: Int
  ): F[Int] = sql"delete from restaurants where id = $restaurantId"
    .update
    .run
    .transact(xa)

  def updateRestaurant(
    restaurantId: Int,
    name: String,
    location: String,
    priceRange: Int,
  ): F[Restaurant] =
    sql"update restaurants set name = $name,location=$location,price_range=$priceRange where id = $restaurantId"
      .update
      .withUniqueGeneratedKeys[Restaurant]("id", "name", "location", "price_range")
      .transact(xa)

}
