
package com.ryuu.music

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val bg = Color.rgb(12, 12, 27)
    private val purple = Color.rgb(155, 93, 255)
    private val cyan = Color.rgb(39, 220, 235)
    private val pink = Color.rgb(255, 78, 170)

    private fun gradient(c1: Int, c2: Int): GradientDrawable {
        return GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(c1, c2)
        ).apply {
            cornerRadius = 28f
        }
    }

    private fun text(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
        }
    }

    private fun section(value: String): TextView {
        return text(value, 20f, Color.WHITE, true).apply {
            setPadding(0, 28, 0, 14)
        }
    }

    private fun card(
        title: String,
        subtitle: String,
        color1: Int,
        color2: Int
    ): LinearLayout {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
            background = gradient(color1, color2)
        }

        layout.addView(
            TextView(this).apply {
                text = "♫"
                textSize = 42f
                setTextColor(Color.WHITE)
                gravity = Gravity.CENTER
            },
            LinearLayout.LayoutParams(-1, 100)
        )

        layout.addView(text(title, 16f, Color.WHITE, true))
        layout.addView(text(subtitle, 12f, Color.WHITE))

        return layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 24)
        }

        val brand = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        brand.addView(
            TextView(this).apply {
                text = "◈"
                textSize = 36f
                setTextColor(cyan)
            }
        )

        val brandText = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(10, 0, 0, 0)
        }

        brandText.addView(
            text("RYUU MUSIC", 23f, Color.WHITE, true)
        )
        brandText.addView(
            text("RYTHM YOUR UNIVERSE", 10f, cyan, true)
        )
        brand.addView(brandText)
        content.addView(brand)

        content.addView(
            text(
                "MUSIC FOR YOUR UNIVERSE",
                12f, pink, true
            ).apply {
                setPadding(0, 30, 0, 8)
            }
        )

        content.addView(
            text("Temukan irama kamu.", 28f, Color.WHITE, true)
        )
        content.addView(
            text("Musik yang menemani setiap suasana.", 14f,
                Color.LTGRAY).apply {
                setPadding(0, 6, 0, 18)
            }
        )

        val search = TextView(this).apply {
            text = "⌕    Cari lagu atau artis..."
            textSize = 15f
            setTextColor(Color.LTGRAY)
            setPadding(18, 16, 18, 16)
            background = GradientDrawable().apply {
                setColor(Color.rgb(30, 29, 49))
                cornerRadius = 22f
                setStroke(1, purple)
            }
        }
        content.addView(search)

        content.addView(section("Pilihan Untukmu"))

        val featured = card(
            "YOUR DAILY MIX",
            "Temukan vibe baru setiap hari",
            Color.rgb(92, 42, 180),
            Color.rgb(25, 155, 190)
        )
        content.addView(
            featured,
            LinearLayout.LayoutParams(-1, -2).apply {
                bottomMargin = 8
            }
        )

        content.addView(section("Jelajahi Musik"))

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val albums = listOf(
            Triple("Chill Vibes", "Santai & rileks",
                Color.rgb(100, 40, 160)),
            Triple("Night Drive", "Temani perjalanan",
                Color.rgb(25, 90, 170)),
            Triple("Energy Mix", "Naikkan semangat",
                Color.rgb(180, 40, 110))
        )

        albums.forEach { album ->
            val item = card(
                album.first,
                album.second,
                album.third,
                Color.rgb(32, 28, 54)
            )
            row.addView(
                item,
                LinearLayout.LayoutParams(190, 190).apply {
                    rightMargin = 12
                }
            )
        }

        content.addView(
            HorizontalScrollViewCompat.create(this, row)
        )

        content.addView(section("Baru Diputar"))

        content.addView(
            text(
                "♫    Koleksi musikmu akan tampil di sini",
                14f, Color.LTGRAY
            ).apply {
                setPadding(0, 12, 0, 20)
            }
        )

        scroll.addView(content)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(4, 12, 4, 12)
            setBackgroundColor(Color.rgb(20, 19, 38))
        }

        listOf(
            "⌂\nBeranda",
            "⌕\nCari",
            "♫\nKoleksi"
        ).forEachIndexed { index, label ->
            nav.addView(
                text(
                    label, 12f,
                    if (index == 0) cyan else Color.LTGRAY,
                    index == 0
                ).apply {
                    gravity = Gravity.CENTER
                    setPadding(12, 4, 12, 4)
                },
                LinearLayout.LayoutParams(0, -2, 1f)
            )
        }

        root.addView(nav)
        setContentView(root)
    }
}

private object HorizontalScrollViewCompat {
    fun create(
        activity: Activity,
        child: View
    ): android.widget.HorizontalScrollView {
        return android.widget.HorizontalScrollView(activity).apply {
            isHorizontalScrollBarEnabled = false
            addView(child)
        }
    }
}
