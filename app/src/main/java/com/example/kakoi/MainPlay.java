package com.example.kakoi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/*
THIS IS THE MAIN PLAYING SCREEN
 */

public class MainPlay extends AppCompatActivity {

    //EditText myInput; //not used now.

    TextView feedbackText; //text that shows if the answer chosen is Correct or Wrong
    MyDBHandler dbHandler; //create the database
    TextView questionText; //text that shows the question (english word)
    int n; //the position of the random question in the database
    ImageView feedbackImg;
    TextView highScoreDisp;
    TextView livesCounter;
    HighscoreDB dbHighScore;
    int highScoreValueInt;

    Button quitButton;

    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);

        //myInput = (EditText) findViewById(R.id.myInput); //not used now.

        questionText = (TextView) findViewById(R.id.questionText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHighScore = new HighscoreDB(this, null, null, 1);
        //quitButton = (Button) findViewById(R.id.quitButton);
        feedbackImg = (ImageView) findViewById(R.id.feedbackImg);
        livesCounter = (TextView) findViewById(R.id.livesCounter);

        highScoreDisp = (TextView) findViewById(R.id.highScoreDisp);

        /*
        TODO: COMMENT THESE OUT WHEN DOING THE DEMO.
         */

        addingQuestion("one", "ichi");
        addingQuestion("two", "ni");
        addingQuestion("three", "san");
        addingQuestion("four", "yong");

//        addingQuestion("five", "go");
//        addingQuestion("six", "roku");
//        addingQuestion("seven", "nana");
//        addingQuestion("eight", "hachi");
//        addingQuestion("nine", "kyuu");
//        addingQuestion("ten", "jyuu");

        setRandomQuestion();

        //printDatabaseAnswer();
    }

    //function that runs if the first button is clicked.
    public void onClick1 (View view){
        Button answer1 = (Button) findViewById(R.id.answer1);
        String buttonText = answer1.getText().toString(); //get the text of the button clicked
        isCorrectAnswer(n, buttonText); //checks if it is the correct answer
        setRandomQuestion(); //get another random question
    }

    //function that runs if the second button is clicked.
    public void onClick2 (View view){
        Button answer2 = (Button) findViewById(R.id.answer2);
        String buttonText = answer2.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRandomQuestion();
    }

    //function that runs if the third button is clicked.
    public void onClick3 (View view){
        Button answer3 = (Button) findViewById(R.id.answer3);
        String buttonText = answer3.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRandomQuestion();
    }

    //function that runs if the fourth button is clicked.
    public void onClick4 (View view) {
        Button answer4 = (Button) findViewById(R.id.answer4);
        String buttonText = answer4.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRandomQuestion();
    }

    //checks if correct answer
    public void isCorrectAnswer(int n, String answerChoice){
        if ((dbHandler.isCorrectAnswer(n, answerChoice))){
            feedbackImg.setImageResource(R.drawable.correctsign);

            //set up button sounds
            final MediaPlayer correctButtonClick = MediaPlayer.create(this, R.raw.correct);
            correctButtonClick.start();

            String highScoreValueStr = highScoreDisp.getText().toString();
            highScoreValueInt = Integer.valueOf(highScoreValueStr);
            highScoreValueInt += 5;
            highScoreValueStr = Integer.toString(highScoreValueInt);
            highScoreDisp.setText(highScoreValueStr);

        }else{
            feedbackImg.setImageResource(R.drawable.incorrectsign);

            //set up button sounds
            final MediaPlayer incorrectButtonClick = MediaPlayer.create(this, R.raw.wrong);
            incorrectButtonClick.start();

            String livesCounterStr = livesCounter.getText().toString();
            int livesCounterInt = Integer.valueOf(livesCounterStr);
            livesCounterInt -= 1;
            livesCounterStr = Integer.toString(livesCounterInt);
            livesCounter.setText(livesCounterStr);

            if(livesCounterInt==0){
                addingHighscore(highScoreValueInt);
                Intent i = new Intent(this, HighScore.class);
                startActivity(i);
            }







        }
    }

    /*
    TODO: LIFESUBSTRACTION()

     */

    //this function changes the question text to a random question in the database
    public void setRandomQuestion (){
        n = dbHandler.generateRandomQuestion(); //get the random position
        questionText.setText(dbHandler.getQuestion(n)); //get the question based on the random position
    }




    /*
    BELOW ARE FUNCTIONS FOR BACK-END/DEBUGGING PURPOSES
     */

////    function that adds items to the database (when add button is clicked)
//    public void addButtonClicked(View view){
//        Questions question = new Questions(myInput.getText().toString());
//        dbHandler.addQuestion(question);
//        printDatabase();
//    }

public void addingHighscore(int highScoreValue){
    HighScoreItem highScoreItem = new HighScoreItem(highScoreValue);
    dbHighScore.addHighScore(highScoreItem);
}


    //function that adds question to the database (add button is not clicked)
    public void addingQuestion(String englishWord, String answer){
        Questions question = new Questions(englishWord, answer);
        dbHandler.addQuestion(question);

    }

    //function that deletes items from the database
    public void deleteButtonClicked(View view){
        //String inputText = myInput.getText().toString();
        //dbHandler.deleteQuestion(inputText);
        printDatabase();
    }

    //random button to generate random question
    public void randomButtonClicked(View view){
        setRandomQuestion();
    }

    //function that prints the questions in the database (for debugging purposes)
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        feedbackText.setText(dbString);
    }

    //function that prints the answers in the database (for debugging purposes)
    public void printDatabaseAnswer(){
        String dbString = dbHandler.databaseToStringAnswer();
        feedbackText.setText(dbString);
    }

}

