package mandel.bitmap

import java.awt.image.BufferedImage
import java.awt.Color

class Bitmap(val width: Int, val height: Int) {
  val img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
  def getColor(x: Int, y: Int): Color = new Color(img.getRGB(x, y))
  def setColor(x: Int, y: Int, c : Color): Unit = img.setRGB(x, y, c.getRGB)
}
