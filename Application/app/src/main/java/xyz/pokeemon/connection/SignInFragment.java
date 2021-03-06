package xyz.pokeemon.connection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import xyz.pokeemon.MainActivity;
import xyz.pokeemon.R;
import xyz.pokeemon.connection.home.HomeFragment;
import xyz.pokeemon.model.User;
import xyz.pokeemon.repository.UserRepository;

public class SignInFragment extends Fragment {
    private UserRepository repository = new UserRepository();
    private EditText email, password;
    private boolean pseudoIsCorrect, emailIsCorrect;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        submit(v);
        goToSignUp(v);
        return v;
    }

    public void submit(View view){
        email = (EditText) view.findViewById(R.id.et_signin_email);
        password = (EditText) view.findViewById(R.id.et_signin_password);
        Button btnSignIn = (Button) view.findViewById(R.id.btn_signin_login);
        int index = 1;
        btnSignIn.setOnClickListener(v->{
            User us = new User(email.getText().toString(), password.getText().toString());

            repository.getUserLog(us).observe(getViewLifecycleOwner(), user -> {

                if (user == null || user.getId() == 0) {
                    Toast.makeText(view.getContext(), "Error ID", Toast.LENGTH_LONG).show();
                }
                else {
                    MainActivity.user = user;
                    FragmentTransaction ft = SignInFragment.this.getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.fl_wrapper, new HomeFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        );
        });
    }

    public void goToSignUp(View view){
        Button btnGotoSignUp = (Button) view.findViewById(R.id.btn_signin_signup);
        btnGotoSignUp.setOnClickListener(v -> {
            FragmentTransaction ft = SignInFragment.this.getParentFragmentManager().beginTransaction();
            ft.replace(R.id.fl_wrapper, new SignUpFragment());
            ft.addToBackStack(null);
            ft.commit();
        });
    }
}