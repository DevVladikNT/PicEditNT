//import java.awt.Color
//import java.awt.image.BufferedImage
//import java.io.File
//import javax.imageio.ImageIO
//
//object LGBTFilter {
//    private lateinit var image: BufferedImage
//    private lateinit var result: BufferedImage
//
//    fun make(path: String): BufferedImage {
//        val file = File(path)
//        image = ImageIO.read(file)
//        result = image.getSubimage(0, 0, image.width, image.height)
//        render()
//        return result
//    }
//
//    private fun render() {
//        var value: Color
//        var current: Color
//        val heightStrip = image.height / 6
//        for (i in 0 until image.width) {
//            for (j in 0 until image.height) {
//                current = Color(image.getRGB(i, j))
//                value = when {
//                        j <= heightStrip -> Color(0xf54242)
//                        j <= heightStrip * 2 -> Color(0xf58a42)
//                        j <= heightStrip * 3 -> Color(0xf5dd42)
//                        j <= heightStrip * 4 -> Color(0x51f542)
//                        j <= heightStrip * 5 -> Color(0x42d7f5)
//                        else -> Color(0x9042f5)
//                    }
//                result.setRGB(i, j, Color(
//                    (value.red * 0.3 + current.red * 0.7).toInt(),
//                    (value.green * 0.3 + current.green * 0.7).toInt(),
//                    (value.blue * 0.3 + current.blue * 0.7).toInt()
//                ).rgb)
//            }
//        }
//    }
//}