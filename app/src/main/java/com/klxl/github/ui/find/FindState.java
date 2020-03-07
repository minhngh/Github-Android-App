package com.klxl.github.ui.find;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class FindState {
    @NonNull
    @StringRes
    private Integer usernameError;

    private boolean isDataValid;

    @NonNull
    public Integer getUsernameError() {
        return usernameError;
    }

    public FindState(@NonNull Integer usernameError) {
        this.usernameError = usernameError;
        isDataValid = false;
    }

    public FindState(boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
