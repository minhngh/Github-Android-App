package com.klxl.github.ui.find;

import androidx.annotation.Nullable;

public class FindResult {
    public final static int SUCCESS = 1;
    public final static int FAILURE = 0;
    private int result;
    @Nullable
    private Integer message;

    public FindResult(int result) {
        this.result = result;
    }

    public FindResult(int result, @Nullable Integer message) {
        this.result = result;
        this.message = message;
    }

    public int getResult() {
        return result;
    }
    @Nullable
    public Integer getMessage() {
        return message;
    }
}
