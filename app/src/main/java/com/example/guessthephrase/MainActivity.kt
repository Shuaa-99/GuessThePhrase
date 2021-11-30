package com.example.guessthephrase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var phrase: String
    lateinit var displayphrase: TextView
    lateinit var userinput: EditText
    lateinit var codedphrase: CharArray
    lateinit var myRV: RecyclerView
    lateinit var guesses: ArrayList<String>
    lateinit var button: Button
    private var count = 10
    private var score = 0
    private var highscore = 0
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        highscore = sharedPreferences.getInt("highscore", 0)
        phrase = "CodingDojo"
        codedphrase = "*".repeat(phrase.length).toCharArray()
        displayphrase = findViewById(R.id.tvInstructions)
        userinput = findViewById(R.id.editTextTextPersonName)
        guesses = arrayListOf()
        button = findViewById(R.id.bttn_click)
        myRV = findViewById(R.id.rvGuesses)
        var secretphrase = "Phrase: ${String(codedphrase)}"
        displayphrase.text = secretphrase

        button.setOnClickListener {

            var length = userinput.text.length
            if (length == 1) {
                checkLetter()
            } else {
                checkPhrase()
            }

        }
        myRV.adapter = RecyclerViewAdapter(guesses)
        myRV.layoutManager = LinearLayoutManager(this)
    }

    fun checkLetter() {
        var i = 0
        val userletter = userinput.text.trim()[0]
        val numberofocuurance = phrase.filter { it == userletter }.count()
        if (userletter in phrase) {
            while (i < phrase.length) {

                if (userletter == phrase[i]) {
                    codedphrase[i] = phrase[i]
                }
                i++
            }
            guesses.add("Found $numberofocuurance $userletter (s)")
            checkPhrase()
            userinput.text = null
        } else {
            guesses.add(" wrong Guess :(")
            guesses.add("${--count} attempts left")
            score++
            save()
            userinput.text = null
            if (count == 0 ) {
                guesses.add("GAME OVER")
                userinput.text = null
                userinput.isEnabled = false
                button.isEnabled = false
            }
        }

        var secretphrase = "Phrase: ${String(codedphrase)} Guessed Letter: $userletter"
        displayphrase.text = secretphrase
        myRV.adapter?.notifyDataSetChanged()
        userinput.hint = "Enter The Phrase "
    }

    fun checkPhrase() {
        if (String(codedphrase) == phrase) {
            guesses.add(("Great, you win :)"))
            guesses.add("GAME OVER ")
            save()
            userinput.text = null
            userinput.isEnabled = false
            button.isEnabled = false
        }

        myRV.adapter?.notifyDataSetChanged()
        userinput.hint = "Enter The Letter"
    }

    fun save() {
        if (score < highscore) {
            with(sharedPreferences.edit()) {
                putInt("highscore", score)
                displayphrase.text = score.toString()
                apply()
            }
        }
    }
}