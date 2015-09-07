package io.poundcode.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.poundcode.geoquiz.models.Question;

public class QuizActivity extends AppCompatActivity {
    @Bind(R.id.button_true)
    Button mTrue;
    @Bind(R.id.button_false)
    Button mFalse;
    @Bind(R.id.button_next)
    ImageButton mNext;
    @Bind(R.id.button_previous)
    ImageButton mPrevious;
    @Bind(R.id.question)
    TextView mQuestion;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_mideast, false)
    };
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        updateQuestion();
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

    @OnClick(R.id.button_true)
    public void onClickTrue() {
        checkAnswer(true);
    }

    @OnClick(R.id.button_false)
    public void onClickFalse() {
        checkAnswer(false);
    }

    @OnClick(R.id.button_next)
    public void onClickNext() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
        updateQuestion();
    }

    @OnClick(R.id.button_previous)
    public void onClickPrevious() {
        if(mCurrentIndex == 0) {
            mCurrentIndex = mQuestions.length -1;
        }else {
            mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
        }
        updateQuestion();
    }

    public void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestion.setText(question);
    }

    @OnClick(R.id.question)
    public void onClickQuestion() {
        int next =  (mCurrentIndex + 1) % mQuestions.length;
        Toast.makeText(this, mQuestions[next].getTextResId(), Toast.LENGTH_SHORT).show();
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean isAnswerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if(userPressedTrue == isAnswerTrue) {
            messageResId = R.string.correct;
        }
        else {
            messageResId = R.string.incorrect;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
