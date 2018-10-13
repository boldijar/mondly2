package io.github.boldijar.cosasapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class Question implements Parcelable {
    @SerializedName("id")
    public int mId;
    @SerializedName("type")
    public QuestionType mQuestionType;
    @SerializedName("text")
    public String mText;
    @SerializedName("possible_answers")
    public List<String> mOptions;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mQuestionType == null ? -1 : this.mQuestionType.ordinal());
        dest.writeString(this.mText);
        dest.writeStringList(this.mOptions);
    }

    public Question() {
    }

    protected Question(Parcel in) {
        this.mId = in.readInt();
        int tmpMQuestionType = in.readInt();
        this.mQuestionType = tmpMQuestionType == -1 ? null : QuestionType.values()[tmpMQuestionType];
        this.mText = in.readString();
        this.mOptions = in.createStringArrayList();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
