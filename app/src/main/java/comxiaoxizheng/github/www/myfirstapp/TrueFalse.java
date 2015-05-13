/**
 * Created by Xiaoxi on 5/7/2015.
 */

package comxiaoxizheng.github.www.myfirstapp;
public class TrueFalse {
    private int mQuestion;//int bc mQuestion variable will hold the resource ID(always in the form of in

    private boolean mTrueQuestion;

    public TrueFalse(int question, boolean trueQuestion){
        mQuestion = question;
        mTrueQuestion = trueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
