package com.example.testflag

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.forEach

class MainActivity : AppCompatActivity() {
    lateinit var listSavol: ArrayList<Savol>
    var savolNomeri = 0
    lateinit var textSavol: TextView
    lateinit var textTogri: TextView
    lateinit var textXato: TextView
    lateinit var imgBayroq: ImageView
    lateinit var linear1: LinearLayoutCompat
    lateinit var linear2: LinearLayoutCompat
    lateinit var linear3: LinearLayoutCompat
    lateinit var listBtn: ArrayList<Button>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listSavol = ArrayList(5)
        listBtn = ArrayList(18)
        textSavol = findViewById<TextView>(R.id.text_savol)
        textTogri = findViewById<TextView>(R.id.text_togri_javob)
        textXato = findViewById<TextView>(R.id.text_xato_javob)
        imgBayroq = findViewById<ImageView>(R.id.img_bayroq)
        linear1 = findViewById<LinearLayoutCompat>(R.id.linear1)
        linear2 = findViewById<LinearLayoutCompat>(R.id.linear2)
        linear3 = findViewById<LinearLayoutCompat>(R.id.linear3)
        savolQushish()

        savolBerish(savolNomeri)
    }

    private fun savolBerish(savolNomeri: Int) {
        if (savolNomeri+1 == 6) {
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Natijalar!!")
            alertDialog.setMessage("To'g'ri javoblar ${textTogri.text}\nXato javoblar ${textXato.text}")
            alertDialog.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                textTogri.text="0"
                textXato.text="0"
                this.savolNomeri=0
                tozalash()
                savolBerish(this.savolNomeri)
            }
            alertDialog.show()
        } else {
            textSavol.text = "${savolNomeri + 1} - savol"
            imgBayroq.setImageResource(listSavol[savolNomeri].image)

            var savolJavobi = listSavol[savolNomeri].name
            for (i in 1..savolJavobi.length) {
                var btn = Button(this)
                btn.layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
                )
                btn.setOnClickListener {

                    if (btn.hint != null) {
                        listBtn[btn.hint.toString().toInt()].visibility = View.VISIBLE
                        btn.text = ""
                        btn.hint = null
                    }

                }

                linear1.addView(btn)
            }

            var random = randomSavolJavobi(savolJavobi)

            for (i in 0..8) {
                var btn = Button(this)
                btn.layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
                )
                btn.id = i
                btn.setOnClickListener {
                    btn.visibility = View.INVISIBLE
                    var text = btn.text
                    javoblarniYuborish(text.toString(), btn.id)
                }
                btn.text = (random[i]).toString()
                linear2.addView(btn)
                listBtn.add(btn)

            }

            for (i in 9..17) {
                var btn = Button(this)
                btn.layoutParams = LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1F
                )
                btn.id = i
                btn.setOnClickListener {
                    btn.visibility = View.INVISIBLE
                    var text = btn.text
                    javoblarniYuborish(text.toString(), btn.id)
                }
                btn.text = (random[i]).toString()
                linear3.addView(btn)
                listBtn.add(btn)
            }
        }
    }

    private fun javoblarniYuborish(text: String, id: Int) {

        linear1.forEach {
            var btn = it as Button
            if (btn.text.isEmpty()) {
                btn.text = text
                btn.hint = id.toString()
                javoblarniTekshirish()
                return
            }
        }

    }

    private fun javoblarniTekshirish() {
        var javoblar = ""
        linear1.forEach {
            var btn = it as Button
            if (btn.text.isNotEmpty()) {
                javoblar+=it.text

            }
        }

        if (javoblar.length == listSavol[savolNomeri].name.length) {
            if (javoblar == listSavol[savolNomeri].name) {
                var a = textTogri.text.toString().toInt()
                a++
                textTogri.text =a.toString()
            } else {
                var a = textXato.text.toString().toInt()
                a++
                textXato.text =a.toString()
            }
            tozalash()
            savolNomeri++
            savolBerish(savolNomeri)
            Log.d("sardor", "${savolNomeri}")
        }
    }

    private fun tozalash() {
        linear1.removeAllViews()
        linear2.removeAllViews()
        linear3.removeAllViews()
    }


    private fun randomSavolJavobi(savolJavobi: String): String {
        var s = "zxcvbnmasdfghjklqwertyuiop"
        var savolJavobi = savolJavobi
        savolJavobi = savolJavobi + s.substring(0, 18 - savolJavobi.length)
        var char = savolJavobi.toCharArray()
        char.shuffle()
        savolJavobi = ""
        char.forEach {
            savolJavobi += it
        }

        return savolJavobi
    }

    fun savolQushish() {

        listSavol.add(Savol("germany", R.drawable.img_flag_germaniya))
        listSavol.add(Savol("russia", R.drawable.img_flag_russia))
        listSavol.add(Savol("usa", R.drawable.img_flag_usa))
        listSavol.add(Savol("china", R.drawable.img_flag_china))
        listSavol.add(Savol("uzbekistan", R.drawable.img_flag_uzbekiston))
    }
}