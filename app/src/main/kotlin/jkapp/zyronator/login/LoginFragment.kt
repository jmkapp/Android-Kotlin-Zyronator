package jkapp.zyronator.login

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import jkapp.zyronator.R

interface LoginCallback
{
    fun loginDetailsCallback(userName : String, password : String)
}

internal class LoginFragment : Fragment(), View.OnClickListener
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        val button = view.findViewById(R.id.login_button) as Button
        button.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View)
    {
        val editText = view.findViewById(R.id.login_username_text) as EditText
        val userName = editText.text.toString()

        val passwordText = view.findViewById(R.id.login_password_text) as EditText
        val password = passwordText.text.toString()

        val activity = activity as LoginCallback
        activity.loginDetailsCallback(userName = userName, password = password)
    }
}
