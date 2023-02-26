package com.example.fiveword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] wordList;
    private String selectedWord;
    private TextView wordDisplay;
    private EditText guessInput;
    private Button buttonSearch;
    private Button buttonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordDisplay = findViewById(R.id.word_display);
        guessInput = findViewById(R.id.guess_input);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonImage = findViewById(R.id.buttonImage);
        loadWordList();
        selectWord();
        updateDisplay();
        buttonSearch.setOnClickListener(this);
        buttonImage.setOnClickListener(this);

        //guessInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           // @Override
            //public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            //    if (actionId == EditorInfo.IME_ACTION_DONE) {
            //        checkGuess();
            //        return true;
            //    }
            //    return false;
           // }
       // });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == buttonSearch.getId()){
            checkGuess();
            guessInput.setText("");
        } else if (v.getId() == buttonImage.getId()) {
            loadWordList();
            selectWord();
            updateDisplay();
            guessInput.setText("");
        }
    }

    private void loadWordList() {
        try {
            InputStream is = getAssets().open("wordlist.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String words = new String(buffer, "UTF-8");
            wordList = words.split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectWord() {
        Random random = new Random();
        int index = random.nextInt(wordList.length);
        selectedWord = wordList[index].trim();
    }

    private void updateDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < selectedWord.length(); i++) {
            displayText.append("_ ");
        }
        wordDisplay.setText(displayText.toString());
    }

    private void checkGuess() {
        String guess = guessInput.getText().toString().toLowerCase();
        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < selectedWord.length(); i++) {
            char letter = selectedWord.charAt(i);
            if (guess.indexOf(letter) >= 0) {
                displayText.append(letter + " ");
            } else {
                displayText.append("_ ");
            }
        }
        wordDisplay.setText(displayText.toString());
        if (guess.equals(selectedWord)) {
            Toast toast = Toast.makeText(this, "Ты победил, загаданное слово " + selectedWord, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            selectWord();
            updateDisplay();
            guessInput.setText("");
        }
    }


}