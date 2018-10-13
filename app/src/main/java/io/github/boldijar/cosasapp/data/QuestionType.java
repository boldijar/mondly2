package io.github.boldijar.cosasapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Paul
 * @since 2018.10.13
 */
public enum QuestionType {

    @SerializedName("multiple")
    OPTIONS,
    @SerializedName("fix")
    NUMBER

}
