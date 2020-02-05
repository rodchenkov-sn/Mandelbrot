package mandel

import java.awt.Color

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import complex._
import bitmap._

package object Mandelbrot {
  def compute(width: Int, height: Int, xMin: Double, xMax: Double, yMin: Double, yMax: Double, maxIter: Int): Bitmap = {
    val bm = new Bitmap(width, height)

    val cx = (xMax - xMin) / width
    val cy = (yMax - yMin) / height

    val futures = {
      for (y <- 0 until bm.height) yield Future {
        for (x <- 0 until bm.width) {
          val c = Complex(xMin + x * cx, yMin + y * cy)
          val iter = itMandel(c, maxIter, 4)
          bm.setColor(x, y, getColor(iter, maxIter))
        }
      }
    }.map(Await.result(_, 10.second))
    bm
  }

  private def itMandel(c: Complex, iMax: Int, bailout: Int): Int = {
    @scala.annotation.tailrec
    def inner(z: Complex, i: Int): Int = i match {
      case x if x == iMax => iMax
      case x if z.abs > bailout => x
      case x => inner(z * z + c, x + 1)
    }
    inner(Complex(0, 0), 0)
  }

  private def getColor(iter: Int, max: Int): Color = (iter, 3 * math.log(iter) / math.log(max - 1.0)) match {
    case (itr, _) if itr == max => Color.BLACK
    case (_, col) if col < 1    => new Color(0, 0, (255 * col).toInt)
    case (_, col) if col < 2    => new Color(0, (255 * (col - 1)).toInt, 255)
    case (_, col)               => new Color((255 * (col - 2)).toInt, 255, 255)
  }
}
