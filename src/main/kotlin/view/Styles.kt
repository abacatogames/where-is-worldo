package io.github.cbaumont.view

import kotlinx.css.Align
import kotlinx.css.Border
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.FontWeight
import kotlinx.css.JustifyContent
import kotlinx.css.Margin
import kotlinx.css.Overflow
import kotlinx.css.Padding
import kotlinx.css.TextAlign
import kotlinx.css.TextTransform
import kotlinx.css.alignItems
import kotlinx.css.backgroundColor
import kotlinx.css.border
import kotlinx.css.borderRadius
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.flexGrow
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.fontWeight
import kotlinx.css.gap
import kotlinx.css.h1
import kotlinx.css.h2
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.letterSpacing
import kotlinx.css.margin
import kotlinx.css.marginBottom
import kotlinx.css.maxWidth
import kotlinx.css.overflowX
import kotlinx.css.padding
import kotlinx.css.paddingBottom
import kotlinx.css.pct
import kotlinx.css.px
import kotlinx.css.textAlign
import kotlinx.css.textTransform
import kotlinx.css.vw
import kotlinx.css.width

val styles = CssBuilder().apply {
    root {
        backgroundColor = Color.bgBrown
        margin = Margin(0.px)
        padding = Padding(0.px)
    }
    rule("html, body") {
        height = 100.pct
        margin = Margin(0.px)
        padding = Padding(0.px)
    }
    rule("body") {
        display = Display.flex
        flexDirection = FlexDirection.column
        alignItems = Align.center
        fontFamily = "monospace"
        padding = Padding(32.px)
    }
    rule(".container") {
        maxWidth = 800.px
        width = 100.pct
        margin = Margin(0.px)
        textAlign = TextAlign.center
    }
    h1 {
        fontSize = 3.em
        fontWeight = FontWeight.bold
        marginBottom = 16.px
        color = Color.blackBean
    }
    h2 {
        fontSize = 1.6.em
        fontWeight = FontWeight.bold
        marginBottom = 24.px
        color = Color.cafeNoir
    }
    rule(".won") {
        color = Color.correctGuess
    }
    rule(".invalid") {
        color = Color.darkRed
    }
    rule("form") {
        display = Display.flex
        gap = 8.px
        marginBottom = 24.px
    }
    rule(".form-slot") {
        height = 48.px
        paddingBottom = 10.5.px
    }
    rule("input[type=text]") {
        flexGrow = 1.0
        padding = Padding(12.px)
        fontFamily = "monospace"
        fontWeight = FontWeight.bold
        fontSize = 20.px
        letterSpacing = 8.px
        borderRadius = 8.px
        width = 200.px
        border = Border(2.px, BorderStyle.solid, Color("#3a3a3c"))
        backgroundColor = Color.cafeNoir
        color = Color("#d7dadc")
        textAlign = TextAlign.center
        textTransform = TextTransform.uppercase
    }
    rule(".board") {
        display = Display.flex
        flexDirection = FlexDirection.column
        gap = 1.vw
        width = 100.pct
        maxWidth = 800.px
        overflowX = Overflow.auto
    }
    rule(".row") {
        display = Display.flex
        gap = 0.4.vw
        width = 100.pct
        justifyContent = JustifyContent.center
        alignItems = Align.center
    }
    rule(".tile") {
        display = Display.flex
        justifyContent = JustifyContent.center
        alignItems = Align.center
        width = 48.px
        height = 52.px
        fontWeight = FontWeight.bold
        fontSize = clamp(12.px, 2.2.vw, 22.px)
        borderRadius = 0.5.vw
        backgroundColor = Color.cafeNoir
        color = Color.white
        textTransform = TextTransform.uppercase
    }
    rule(".tile.hint") {
        backgroundColor = Color.indianYellow
        fontSize = clamp(12.px, 2.2.vw, 14.px)
    }
    rule(".tile.correct") {
        backgroundColor = Color.correctGuess
    }
    rule(".tile.present") {
        backgroundColor = Color("#b59f3b")
    }
    rule(".tile.absent") {
        backgroundColor = Color.cafeNoir
    }
}

private val Color.Companion.cafeNoir: Color
    get() = Color("#4b3621")

private val Color.Companion.correctGuess: Color
    get() = Color("#538d4e")

private val Color.Companion.blackBean: Color
    get() = Color("#3d0c02")

private val Color.Companion.indianYellow: Color
    get() = Color("#e3a857")

private val Color.Companion.bgBrown: Color
    get() = Color("#c19a6b")
