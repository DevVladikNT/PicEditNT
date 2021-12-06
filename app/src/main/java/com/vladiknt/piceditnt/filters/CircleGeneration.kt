//import java.awt.Color
//import java.awt.image.BufferedImage
//
//object CircleGeneration {
//    private lateinit var curColor: Color
//    private var image = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)
//    private var current = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
//    private lateinit var result: BufferedImage
//
//    fun make(choice: Int): BufferedImage {
//        // Заполняем квадрат белым цветом
//        for (i in 0 until image.width) {
//            for (j in 0 until image.height) {
//                image.setRGB(i, j, Color(0xFFFFFF).rgb)
//            }
//        }
//        if (choice == 1) {
//            // Закрашиваем половину верхнего левого квадрата (черно-белый режим)
//            val black = Color(0)
//            for (i in 0..image.width / 2) {
//                for (j in 0..i) {
//                    if (Math.random() < Math.random()) {
//                        image.setRGB(i, j, black.rgb)
//                    }
//                }
//            }
//        } else {
//            // Закрашиваем половину верхнего левого квадрата (цветной режим)
//            for (i in 0..image.width / 2) {
//                for (j in 0..i) {
//                    image.setRGB(
//                        i, j, Color(
//                            (Math.random() * 255).toInt(),
//                            (Math.random() * 255).toInt(), (Math.random() * 255).toInt()
//                        ).rgb
//                    )
//                }
//            }
//        }
//        symmetry()
//        upscale()
//        blur()
//        redraw(choice)
//        return result
//    }
//
//    private fun symmetry() {
//        // Отображаем относительно главной диагонали, чтобы полностью закрыть первый квадрат
//        for (i in 0 until image.width / 2) {
//            for (j in 0 until i) {
//                curColor = Color(image.getRGB(i, j))
//                image.setRGB(j, i, curColor.rgb)
//            }
//        }
//        // Отображаем рисунок симетрично относительно оси ОУ
//        for (i in 0 until image.width / 2) {
//            for (j in 0 until image.height / 2) {
//                curColor = Color(image.getRGB(i, j))
//                image.setRGB(image.width - i - 1, j, curColor.rgb)
//            }
//        }
//        // Отображаем рисунок симетрично относительно оси ОХ
//        for (i in 0 until image.width) {
//            for (j in 0 until image.height / 2) {
//                curColor = Color(image.getRGB(i, j))
//                image.setRGB(i, image.height - j - 1, curColor.rgb)
//            }
//        }
//    }
//
//    private fun upscale() {
//        // Увеличиваем рисунок с 100х100 до 1000х1000
//        for (i in 0 until image.width) {
//            for (j in 0 until image.height) {
//                curColor = Color(image.getRGB(i, j))
//                for (i1 in i * 10 until (i + 1) * 10) {
//                    for (j1 in j * 10 until (j + 1) * 10) {
//                        current.setRGB(i1, j1, curColor.rgb)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun blur() {
//        // Блюрим фотку
//        // Блюрим фотку
//        println("Wait please.")
//        result = current.getSubimage(0, 0, 1000, 1000)
//        var col1: Color
//        var col2: Color
//        var col3: Color
//        var col4: Color
//        var col5: Color
//        var col6: Color
//        var col7: Color
//        var col8: Color
//        var col9: Color
//        var col10: Color
//        var col11: Color
//        var col12: Color
//        repeat(50) {
//            for (i in 2 until current.width - 2) {
//                for (j in 2 until current.height - 2) {
//                    col1 = Color(current.getRGB(i - 1, j))
//                    col2 = Color(current.getRGB(i, j - 1))
//                    col3 = Color(current.getRGB(i + 1, j))
//                    col4 = Color(current.getRGB(i, j + 1))
//                    col5 = Color(current.getRGB(i + 1, j + 1))
//                    col6 = Color(current.getRGB(i - 1, j + 1))
//                    col7 = Color(current.getRGB(i + 1, j - 1))
//                    col8 = Color(current.getRGB(i - 1, j - 1))
//                    col9 = Color(current.getRGB(i, j + 2))
//                    col10 = Color(current.getRGB(i + 2, j))
//                    col11 = Color(current.getRGB(i, j - 2))
//                    col12 = Color(current.getRGB(i - 2, j))
//                    curColor = Color(
//                        (col1.red + col2.red + col3.red + col4.red + col5.red + col6.red + col7.red + col8.red + col9.red + col10.red + col11.red + col12.red) / 12,
//                        (col1.green + col2.green + col3.green + col4.green + col5.green + col6.green + col7.green + col8.green + col9.green + col10.green + col11.green + col12.green) / 12,
//                        (col1.blue + col2.blue + col3.blue + col4.blue + col5.blue + col6.blue + col7.blue + col8.blue + col9.blue + col10.blue + col11.blue + col12.blue) / 12
//                    )
//                    result.setRGB(i, j, curColor.rgb)
//                }
//            }
//            current = result.getSubimage(0, 0, 1000, 1000)
//        }
//    }
//
//    private fun redraw(choice: Int) {
//        // Рисуем новую картинку по очертаниям
//        if (choice == 1) {
//            for (i in 2 until current.width) {
//                for (j in 2 until current.height) {
//                    this.curColor = Color(result.getRGB(i, j))
//                    this.curColor =
//                        if (this.curColor.red > 96) Color(0xFFFFFF)
//                        else Color(0)
//                    result.setRGB(i, j, this.curColor.rgb)
//                }
//            }
//        } else {
//            var currentRed: Int
//            var currentGreen: Int
//            var currentBlue: Int
//            for (i in 2 until current.width) {
//                for (j in 2 until current.height) {
//                    this.curColor = Color(result.getRGB(i, j))
//                    currentRed = this.curColor.red
//                    currentGreen = this.curColor.green
//                    currentBlue = this.curColor.blue
//                    this.curColor =
//                        if (currentRed >= currentGreen && currentRed >= currentBlue) Color(0xFF0000)
//                        else if (currentGreen >= currentRed && currentGreen >= currentBlue) Color(0x00FF00)
//                        else Color(0x0000FF)
//                    result.setRGB(i, j, this.curColor.rgb)
//                }
//            }
//        }
//    }
//}