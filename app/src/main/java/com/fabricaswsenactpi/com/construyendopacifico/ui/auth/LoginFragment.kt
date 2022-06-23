package com.fabricaswsenactpi.com.construyendopacifico.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fabricaswsenactpi.com.construyendopacifico.MainActivity
import com.fabricaswsenactpi.com.construyendopacifico.R
import com.fabricaswsenactpi.com.construyendopacifico.core.AppConstants
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.AppDatabase
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.user.UserDataSource
import com.fabricaswsenactpi.com.construyendopacifico.databinding.FragmentLoginBinding
import com.fabricaswsenactpi.com.construyendopacifico.domain.user.UserRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.LoginViewModel
import com.fabricaswsenactpi.com.construyendopacifico.presentation.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.fabricaswsenactpi.com.construyendopacifico.core.Result
import com.fabricaswsenactpi.com.construyendopacifico.core.hide
import com.fabricaswsenactpi.com.construyendopacifico.core.show
import com.fabricaswsenactpi.com.construyendopacifico.data.models.`interface`.RetrofitClient
import com.fabricaswsenactpi.com.construyendopacifico.data.models.local.auth.AuthDataSource
import com.fabricaswsenactpi.com.construyendopacifico.data.models.room.UsersEntity
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthBody
import com.fabricaswsenactpi.com.construyendopacifico.data.models.web.authentication.AuthResponse
import com.fabricaswsenactpi.com.construyendopacifico.domain.auth.AuthRepoImpl
import com.fabricaswsenactpi.com.construyendopacifico.presentation.AuthViewModel
import com.fabricaswsenactpi.com.construyendopacifico.presentation.AuthViewModelFactory


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(
            UserRepoImpl(
                UserDataSource(AppDatabase.getUsersDatabase(requireContext()).UserDao())
            )
        )
    }
    private val viewModelAuth by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource(RetrofitClient.webService)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
//        safeUser()
        validate()
    }

    private fun validate() {
        binding.btnLogin.setOnClickListener {
            val results = arrayOf(validateNickName(), validatePassword())
            if (false in results) {
                return@setOnClickListener
            }
            sendUser()
        }
    }

    private fun safeUser() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveUser(
                    UsersEntity(
                        1, 1, "santiago", "12345",
                        "David Santiago", "Rodriguez Cruz", "313 8124613",
                        "santi@gmail.com","1000589765"
                    )
                ).collect {
                    when (it) {
                        is Result.Loading -> {

                        }
                        is Result.Success -> {
                            Snackbar.make(
                                binding.root,
                                "Se registro correctamente",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        is Result.Failure -> {
                            Snackbar.make(
                                binding.root,
                                "Error al registrarse",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            Log.e("Error", "sendUser: ${it.exception}")
                        }
                    }
                }
            }
        }
    }

    private fun sendUser() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModelAuth.signIn(AuthBody(
                    binding.edtPassword.text.toString(),
                    binding.edtNickName.text.toString(),
                )).collect {
                    when (it) {
                        is Result.Loading -> {
                            binding.btnLogin.hide()
                            binding.progressBar.show()
                        }
                        is Result.Success -> {
                            binding.btnLogin.show()
                            binding.progressBar.hide()
                            if (it.data.results.isNullOrEmpty()) {
                                Snackbar.make(
                                    binding.root,
                                    "Usuario o contraseña incorrectos",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            } else {
                                if (it.data.results[0].profile_id == 1) {
                                    saveIdUser(it.data.results[0])
                                    val intentMain =
                                        Intent(requireContext(), MainActivity::class.java)
                                    startActivity(intentMain)
                                    requireActivity().finish()
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        "No tiene permisos para iniciar sesion",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        is Result.Failure -> {
                            binding.btnLogin.show()
                            binding.progressBar.hide()
                            Snackbar.make(
                                binding.root,
                                "Error al iniciar sesion",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun saveIdUser(authResponse: AuthResponse) {
        val shared = requireActivity().getSharedPreferences(AppConstants.SHARED_USER,Context.MODE_PRIVATE)
        shared.edit().apply {
            putInt("idUser",authResponse.id_user)
            putInt("profileId",authResponse.profile_id)
            putString("userNick",authResponse.user_nick)
            putString("userNames",authResponse.usernames)
            putString("userLastNames",authResponse.user_last_names)
            putString("phone",authResponse.phone_number)
            putString("email",authResponse.email)
            putString("identification",authResponse.identification)
        }.apply()
    }

    private fun validateNickName(): Boolean {
        return if (binding.edtNickName.text.toString().isNullOrEmpty()) {
            binding.idLayoutLoginName.error = "Este campo es obligatorio"
            false
        } else {
            binding.idLayoutLoginName.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (binding.edtPassword.text.toString().isNullOrEmpty()) {
            binding.txtILPassword.error = "Este campo es obligatorio"
            false
        } else {
            binding.txtILPassword.error = null
            true
        }
    }
}