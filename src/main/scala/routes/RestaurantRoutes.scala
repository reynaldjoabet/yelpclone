package routes

import org.http4s.dsl.Http4sDsl
import org.http4s._

import cats.effect.kernel.Async
import services.RestaurantService
import cats.implicits._
import org.http4s.implicits._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.CirceEntityDecoder._
import api._
//import error._
import services._
import domain.Restaurant
import db.Doobie._
import org.http4s.ResponseCookie
import java.util.UUID

final case class RestaurantRoutes[F[_]: Async](
  restaurantService: RestaurantService[F]
) extends Http4sDsl[F] {

  import org.http4s.server.Router

  private val prefix = "api/v1"

  private val routes = HttpRoutes.of[F] {

    case req @ POST -> Root / "restaurants" =>
      req
        .as[CreateRestaurant]
        .flatMap { restaurant =>
          restaurantService
            .saveRestaurant(
              restaurant.name,
              restaurant.location,
              restaurant.priceRange,
            )
            .flatMap(Ok(_))
        }
        .handleErrorWith(e => InternalServerError(e.toString()))

    case GET -> Root / "restaurants" =>
      restaurantService
        .getRestaurants
        .flatMap(Ok(_))
        .handleErrorWith(e => InternalServerError(e.toString()))
    case GET -> Root / "restaurants" / IntVar(restaurantId) =>
      restaurantService
        .getRestaurantById(restaurantId)
        .flatMap {
          case Some(restaurant) => Ok(restaurant)
          case None             => NotFound()
        }
        .handleErrorWith(e => InternalServerError(e.toString()))

    case req @ PUT -> Root / "restaurants" / IntVar(restaurantId) =>
      req
        .as[UpdateRestaurant]
        .flatMap { restaurant =>
          restaurantService
            .updateRestaurant(
              restaurant.restaurantId,
              restaurant.name,
              restaurant.location,
              restaurant.priceRange,
            )
            .flatMap(Ok(_))
        }
        .handleErrorWith(e => InternalServerError(e.toString()))
    case DELETE -> Root / "restaurants" / IntVar(restaurantId) =>
      restaurantService
        .getRestaurantById(restaurantId)
        .flatMap {
          case Some(restaurant) =>
            restaurantService
              .deleteRestaurant(restaurantId)
              .flatMap(_ => Ok())
          case None => NoContent()
        }
        .handleErrorWith(e => InternalServerError(e.toString()))

  }

  val router: HttpRoutes[F] = Router(prefix -> routes)
}

object RestaurantRoutes {

  def make[F[_]: Async]() = RestaurantRoutes[F](RestaurantService(xa))
}
