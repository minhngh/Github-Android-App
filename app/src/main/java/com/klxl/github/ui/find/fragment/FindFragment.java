package com.klxl.github.ui.find.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.klxl.github.R;
import com.klxl.github.ui.find.FindResult;
import com.klxl.github.ui.main.MainActivity;
import com.klxl.github.viewmodel.ProjectViewModel;
import com.klxl.github.viewmodel.ProjectViewModelFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class FindFragment extends Fragment {

    public final static String USER_KEY = "user_find";

    private EditText usernameEditText;
    private Button findButton;
    private ProgressBar loading;
    private CompositeDisposable compositeDisposable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameEditText = view.findViewById(R.id.et_username);
        findButton = view.findViewById(R.id.btn_find);
        loading = view.findViewById(R.id.loading);

        ProjectViewModel projectViewModel = new ViewModelProvider(this, ProjectViewModelFactory.getInstance(getActivity().getApplication())).get(ProjectViewModel.class);

        projectViewModel.getFindResult().observe(this, findResult -> {
            if (findResult != null && findResult.getResult() == FindResult.SUCCESS){
                updateUiwithSuccess();
            }
            else{
                if (findResult != null)
                    updateUiwithFailure(getString(findResult.getMessage()));
                else
                    updateUiwithFailure(getString(R.string.find_error_message));
                loading.setVisibility(View.GONE);
                findButton.setEnabled(true);
            }
        });

        projectViewModel.getFindState().observe(this, loginState -> {
            if (loginState == null)
                return;
            findButton.setEnabled(loginState.isDataValid());
            if (loginState.getUsernameError() != null){
                usernameEditText.setError(getString(loginState.getUsernameError()));
            }
        });

        TextWatcher afterTextChanged = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                projectViewModel.loginDataChanged(usernameEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChanged);
        findButton.setOnClickListener(v -> {
            findButton.setEnabled(false);
            loading.setVisibility(View.VISIBLE);
            projectViewModel.findProjectList(usernameEditText.getText().toString());
        });
    }
    private void updateUiwithFailure(String message){
        findButton.setEnabled(true);
        loading.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void updateUiwithSuccess(){
        findButton.setEnabled(true);
        loading.setVisibility(View.GONE);
        Handler handler = new Handler();
        handler.post(()->{
            Intent intent = new Intent(getContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(USER_KEY, usernameEditText.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}
