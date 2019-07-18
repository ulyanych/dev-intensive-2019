package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        Log.d("M_MainActivity:", "onCreate $status $question")

        prepareUI()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity:", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity:", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity:", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity:", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity:", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity:", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        benderObj.saveState(outState)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            sendMessage()
        }
    }

    override fun onEditorAction(textView: TextView?, id: Int, keyEvent: KeyEvent?): Boolean {
        if (id == EditorInfo.IME_ACTION_DONE) {
            sendMessage()
            return true
        }
        return false
    }

    private fun prepareUI() {
        changeBenderColor(benderObj.status.color)

        textTxt.text = benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener(this)
    }

    private fun changeBenderColor(color: Triple<Int, Int, Int>) {
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    }

    private fun sendMessage() {
        this.hideKeyboard()
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")

        changeBenderColor(color)
        textTxt.text = phrase
    }
}
