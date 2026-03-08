package com.abacatogames.view

import kotlinx.css.Align
import kotlinx.css.BackgroundRepeat
import kotlinx.css.Border
import kotlinx.css.BorderStyle
import kotlinx.css.Color
import kotlinx.css.CssBuilder
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.FontStyle
import kotlinx.css.FontWeight
import kotlinx.css.Image
import kotlinx.css.JustifyContent
import kotlinx.css.Margin
import kotlinx.css.Padding
import kotlinx.css.Position
import kotlinx.css.RelativePosition
import kotlinx.css.TextAlign
import kotlinx.css.TextTransform
import kotlinx.css.alignItems
import kotlinx.css.animationDelay
import kotlinx.css.animationDuration
import kotlinx.css.animationFillMode
import kotlinx.css.animationIterationCount
import kotlinx.css.animationName
import kotlinx.css.animationTimingFunction
import kotlinx.css.backgroundColor
import kotlinx.css.backgroundImage
import kotlinx.css.backgroundPosition
import kotlinx.css.backgroundRepeat
import kotlinx.css.backgroundSize
import kotlinx.css.border
import kotlinx.css.borderRadius
import kotlinx.css.bottom
import kotlinx.css.boxShadow
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.flexGrow
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.fontStyle
import kotlinx.css.fontWeight
import kotlinx.css.footer
import kotlinx.css.gap
import kotlinx.css.h1
import kotlinx.css.h2
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.keyframes
import kotlinx.css.left
import kotlinx.css.letterSpacing
import kotlinx.css.margin
import kotlinx.css.marginBottom
import kotlinx.css.maxWidth
import kotlinx.css.padding
import kotlinx.css.paddingBottom
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.BoxShadow
import kotlinx.css.properties.FillMode
import kotlinx.css.properties.Timing
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.deg
import kotlinx.css.properties.linearGradient
import kotlinx.css.properties.s
import kotlinx.css.properties.translateX
import kotlinx.css.properties.translateY
import kotlinx.css.px
import kotlinx.css.rem
import kotlinx.css.textAlign
import kotlinx.css.textTransform
import kotlinx.css.transform
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
        maxWidth = 1280.px
        width = 100.pct
        margin = Margin(0.px)
        textAlign = TextAlign.center
    }
    rule(".with-image") {
        backgroundImage = Image("url(/wordov3.webp)")
        backgroundSize = "contain"
        backgroundPosition = RelativePosition.rightTop(yOffset = 0.px, xOffset = clamp(100.px, 8.5.vw, 120.px))
        backgroundRepeat = BackgroundRepeat.noRepeat
        padding = Padding(0.5.vw)
    }
    h1 {
        fontSize = 3.2.em
        fontWeight = FontWeight.bold
        marginBottom = 16.px
        color = Color.blackBean
        put("-webkit-text-stroke", "0.3px #c19a6b")
    }
    h2 {
        fontSize = 1.6.em
        fontWeight = FontWeight.bold
        marginBottom = 24.px
        color = Color.blackBean
        put("-webkit-text-stroke", "0.1px #c19a6b")
    }
    rule(".won") {
        color = Color.darkOliveGreen
    }
    rule(".lost") {
        color = Color.darkRed
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
        paddingBottom = 15.px
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
        border = Border(2.px, BorderStyle.solid, Color.darkSlateBlue)
        backgroundColor = Color.cafeNoir
        color = Color("#d7dadc")
        textAlign = TextAlign.center
        textTransform = TextTransform.uppercase
    }
    rule(".board") {
        display = Display.flex
        flexDirection = FlexDirection.column
        gap = 0.3.vw
        width = 100.pct
        maxWidth = 1280.px
    }
    rule(".row") {
        display = Display.flex
        gap = 0.3.vw
        width = 100.pct
        justifyContent = JustifyContent.center
        alignItems = Align.center
        paddingBottom = 0.1.vw
    }
    rule(".row.slide") {
        animationDuration = 0.5.s
        animationName = "slide-in"
        animationTimingFunction = Timing.easeInOut
    }
    rule(".tile") {
        display = Display.flex
        justifyContent = JustifyContent.center
        alignItems = Align.center
        width = clamp(58.px, 4.vw, 68.px)
        height = clamp(60.px, 4.8.vw, 70.px)
        fontWeight = FontWeight.bold
        fontSize = clamp(13.px, 2.3.vw, 23.px)
        borderRadius = clamp(6.px, 0.5.vw, 8.px)
        backgroundColor = Color.cafeNoir
        color = Color.white
        textTransform = TextTransform.uppercase

        boxShadow += BoxShadow(
            offsetX = 0.px,
            offsetY = 3.px,
            blurRadius = 6.px,
            color = Color.blackBean
        )
    }
    rule(".tile.slide") {
        animationName = "slide-in"
        animationDuration = 0.4.s
        animationTimingFunction = Timing.easeInOut
        animationFillMode = FillMode.forwards
    }
    (1..100).forEach { i ->
        rule(".tile:nth-child($i)") {
            animationDelay = (i * 0.03).s
        }
    }
    rule(".tile.hint") {
        backgroundColor = Color.indianYellow
        fontSize = clamp(12.px, 2.2.vw, 14.px)
        backgroundImage = linearGradient(0.deg) {
            colorStop(Color("#ffd399"), 0.pct)
            colorStop(Color.transparent, 10.pct)
        }
    }
    rule(".tile.correct") {
        backgroundColor = Color.correctGuess
        backgroundImage = linearGradient(0.deg) {
            colorStop(Color("#8cbf88"), 0.pct)
            colorStop(Color.transparent, 10.pct)
        }
    }
    rule(".tile.absent") {
        backgroundColor = Color.cafeNoir
        backgroundImage = linearGradient(0.deg) {
            colorStop(Color("#735c45"), 0.pct)
            colorStop(Color.transparent, 10.pct)
        }
    }
    rule(".tile.tiny") {
        width = clamp(28.px, 2.vw, 38.px)
        height = clamp(30.px, 2.8.vw, 40.px)
    }
    keyframes("color-animation") {
        0 { color = Color.blackBean }
        20  { color = Color.darkRed }
        40  { color = Color.cafeNoir }
        60  { color = Color.darkOliveGreen }
        80  { color = Color.darkSlateBlue }
        100 { color = Color.blackBean }
    }
    keyframes("slide-in") {
        from {
            val tsf = Transforms()
            tsf.translateX(100.pct)
            tsf.translateY((-10).px)
            transform = tsf
        }
        to {
            val tsf = Transforms()
            tsf.translateX(0.px)
            tsf.translateY(0.px)
            transform = tsf
        }
    }
    rule(".color-gif") {
        color = Color.blackBean
        fontWeight = FontWeight.bold
        fontSize = 24.px
        animationDuration = 3.5.s
        animationTimingFunction = Timing.linear
        animationIterationCount = Int.MAX_VALUE
        animationName = "color-animation"
    }
    rule(".how-to") {
        padding = Padding(12.px)
        fontSize = 1.3.em
        color = Color.blackBean
        textAlign = TextAlign.justify
    }
    rule(".how-to.notes") {
        fontSize = 1.em
        fontStyle = FontStyle.italic
    }
    rule(".how-to.title") {
        fontSize = 1.5.em
        textAlign = TextAlign.center
    }
    footer {
        fontSize = 1.em
        color = Color.blackBean
        textAlign = TextAlign.center
        position = Position.fixed
        bottom = 0.px
        left = 0.px
        width = 100.pct
        height = 2.rem
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
