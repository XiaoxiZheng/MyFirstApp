package comxiaoxizheng.github.www.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView; //java code for the generic textView of the question
    private boolean mIsCheater;

    //initializes the true/false question bank into an array with abstract type TrueFalse name: mQuestionBank
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true), //each TrueFalse abstract type takes in resource IDs from the strings.xml and a boolean regarding if the question is true or not
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_asia, true),
    };
    //end constructor for the TrueFalse array
    private int mCurrentIndex = 0; //default current index of array is 0

    private static final String TAG = "myFirstApp";
    private static final String KEY_INDEX = "index";
    //end private instance declaration

    //methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called"); //log activity event with an useful string message
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState!= null){ //before performing anything, first test if previous instanceState exist
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length; //increment current index mod the length of question bank array
                updateQuestion();
            }
        });
        updateQuestion();
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton =(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CheatActivity
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion(); //local variable to store if the answer to currentQuestion is true
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue); //a method from the Intent class, where extra info is passed into startActivity(Intent), where the OS forward the intent to the recipient activity
                //multiple putExtra methods are allowed, so if other relevant info need to be passed, it could be do so using this method
                //to receive these putExtra info: public boolean getBooleanExtra(String name, boolean defaultValue) is used in the recipient activity.(this is implemented in the CheatActivity.java)
                startActivityForResult(i,0); //Parameter: (intent,requestCode)..here 0 is a place holder = returning back from cheatActivity
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length; //increment current index mod the length of question bank array
                mIsCheater = false; //reset the cheater assumption every time the user press the next button
                updateQuestion();
            }
        });
        updateQuestion();

        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length-1;
                    updateQuestion();
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        if(data==null){
            return; //if data is not existence, step out of this method..no reuslt to be shown
        }
    }
    @Override
    //a method written to override the Activity method.
    //data is saved in the BUNDLE object---a structure that maps string keys to values of certain limited types.
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override //asks the compiler to ensure the class actually has the method attempting to override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");//log activity life-cycle event with an useful string message
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");//log activity life-cycle event with an useful string message
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");//log activity life-cycle event with an useful string message
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");//log activity life-cycle event with an useful string message
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");//log activity life-cycle event with an useful string message
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateQuestion(){ //helper method to updateQuestion when next is pressed
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);//calls android default java code from TextView class and set the text using resource IDs.
    }
    private void checkAnswer(boolean userInput){ //helper method to test if user input boolean is the same to the actual answer.
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int tempToast;
        if(mIsCheater){
            tempToast = R.string.judgment_toast;
        }

        else  if(userInput==answerIsTrue){
            tempToast = R.string.correct_toast;
        }
        else{
            tempToast = R.string.incorrect_toast;
        }
        Toast.makeText(this,tempToast,Toast.LENGTH_SHORT).show();
    }

}
