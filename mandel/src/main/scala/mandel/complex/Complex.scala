package mandel.complex

case class Complex(real: Double, imag: Double) {
  def + (rhs: Complex) = Complex(real + rhs.real, imag + rhs.imag)
  def - (rhs: Complex) = Complex(real - rhs.real, imag - rhs.imag)
  def * (rhs: Complex) = Complex(real * rhs.real - imag * rhs.imag, real * rhs.imag + imag * rhs.real)
  lazy val abs: Double = math.hypot(real, imag)
}
