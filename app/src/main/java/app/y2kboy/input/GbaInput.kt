package app.y2kboy.input

enum class GbaButton(val mask: Int) {
    A(1 shl 0),
    B(1 shl 1),
    Select(1 shl 2),
    Start(1 shl 3),
    Right(1 shl 4),
    Left(1 shl 5),
    Up(1 shl 6),
    Down(1 shl 7),
    R(1 shl 8),
    L(1 shl 9),
}

fun Set<GbaButton>.toInputMask(): Int = fold(0) { mask, button -> mask or button.mask }
