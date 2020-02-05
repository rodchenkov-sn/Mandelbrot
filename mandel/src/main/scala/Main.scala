import javax.swing.ImageIcon
import mandel.Mandelbrot

import scala.swing._
import scala.swing.event._

object Main extends SimpleSwingApplication {
  def top: MainFrame = new MainFrame {
    private val width = 600
    private val height = 600
    private val maxIter = 500
    private var minX: Double = -2
    private var maxX: Double = 2
    private var minY: Double = -2
    private var maxY: Double = 2

    private val label = new Label {
      icon = new ImageIcon(Mandelbrot.compute(width, height, minX, maxX, minY, maxY, maxIter).img)
    }

    private def dx() = (maxX - minX) / 10.0
    private def dy() = (maxY - minY) / 10.0

    private def move(dx: Double, dy: Double) = {
      minX += dx
      maxX += dx
      minY -= dy
      maxY -= dy
      new ImageIcon(Mandelbrot.compute(width, height, minX, maxX, minY, maxY, maxIter).img)
    }

    def scale(dx: Double, dy: Double): ImageIcon = {
      maxX -= dx
      minX += dx
      maxY -= dy
      minY += dy
      new ImageIcon(Mandelbrot.compute(width, height, minX, maxX, minY, maxY, maxIter).img)
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += label
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Right, _, _) => label.icon = move(dx(), 0)
        case KeyPressed(_, Key.Left , _, _) => label.icon = move(-dx(), 0)
        case KeyPressed(_, Key.Up   , _, _) => label.icon = move(0, dy())
        case KeyPressed(_, Key.Down , _, _) => label.icon = move(0, -dy())
        case KeyPressed(_, Key.Enter, _, _) => label.icon = scale(dx(), dy())
        case KeyPressed(_, Key.Space, _, _) => label.icon = scale(-dx(), -dy())
      }
      focusable = true
      requestFocus
    }
  }
}
