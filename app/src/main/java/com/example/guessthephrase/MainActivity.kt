package com.example.guessthephrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var phrase:String
    lateinit var displayphrase: TextView
    lateinit var userinput:EditText
    lateinit var codedphrase:CharArray
    lateinit var myRV:RecyclerView
    lateinit var guesses:ArrayList<String>
    lateinit var  button:Button
    private var counter = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        phrase = "CodingDojo"
        codedphrase = "*".repeat(phrase.length ).toCharArray()
        displayphrase = findViewById(R.id.tvInstructions)
        userinput = findViewById(R.id.editTextTextPersonName)
        guesses = arrayListOf()
         button = findViewById(R.id.bttn_click)
        myRV=findViewById(R.id.rvGuesses)
        var secretphrase="Phrase: ${String(codedphrase)}"
        displayphrase.text = secretphrase

        button.setOnClickListener {

            var length= userinput.text.length
            if( length==1)
            {checkLetter()}
            else{checkPhrase()}
        }
        myRV.adapter=RecyclerViewAdapter(guesses)
        myRV.layoutManager=LinearLayoutManager(this)
    }
    fun checkLetter(){
        var i=0
        val userletter=userinput.text.trim()[0]
        val numberofocuurance=phrase.filter{it==userletter}.count()
        if(userletter in phrase){
            while(i<phrase.length){

                if (userletter==phrase[i]) {
                    codedphrase[i]= phrase[i]
                }
                i++
            }
            guesses.add("Found $numberofocuurance $userletter (s)")
            userinput.text = null  }
        else{
            guesses.add(" wrong Guess ")
            guesses.add("${--counter} attempts left")
            userinput.text = null
        }
        if(counter==0){guesses.add("GAME OVER ")
        userinput.isEnabled = false
            userinput.text = null
            button.isEnabled = false
        }
        var secretphrase="Phrase: ${String(codedphrase)} Guessed Letter: $userletter"
        displayphrase.text = secretphrase
        myRV.adapter?.notifyDataSetChanged()
        userinput.hint="Enter The Phrase "
    }
    fun checkPhrase(){
        var c=userinput.text.toString().trim()
        if (c.equals(phrase))
        { guesses.add( ("Thats Right!!! Its :" + phrase))
            userinput.text = null}
        else{guesses.add( ("Wrong Guess:I don't Know"))
            userinput.text = null}
        myRV.adapter?.notifyDataSetChanged()
        userinput.hint="Enter The Letter"
    }
}
