package finki.ukim.mk.cookitup.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.databinding.FragmentLoginBinding
import finki.ukim.mk.cookitup.domain.login.Account
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import io.github.muddz.styleabletoast.StyleableToast

class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel by activityViewModels<AccountViewModel>()
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            resultHandler(task)
            }
    }

    private fun resultHandler(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if(account != null){
                createCredentialsAndProceed(account)
            }
        }
        else{
            StyleableToast.makeText(requireContext(), "Something went wrong during signing in. Please try again",Toast.LENGTH_SHORT, R.style.Toast).show()
        }
    }

    private fun createCredentialsAndProceed(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credentials).addOnCompleteListener {
            if(it.isSuccessful){
                accountViewModel.setAccount(
                    Account(
                        account.displayName ?: "Hello User",
                        account.photoUrl.toString().dropLast(4).plus("400-c"),
                        account.email?:"no email provided"
                    )
                )
                findNavController().navigate(R.id.from_navigation_login_to_navigation_backup)
            }
            else{
                StyleableToast.makeText(requireContext(), "Something went wrong during signing in. Please try again",Toast.LENGTH_SHORT, R.style.Toast).show()
            }
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser?.providerData?.get(1)
                    accountViewModel.setAccount(
                        Account(
                            user?.displayName ?: "Hello User",
                            "${user?.photoUrl}?height=400&access_token=${token.token}",
                            user?.email ?: "no email provided"
                        )
                    )
                    findNavController().navigate(R.id.from_navigation_login_to_navigation_backup)

                } else {
                    StyleableToast.makeText(requireContext(), "Something went wrong during signing in. Please try again",Toast.LENGTH_SHORT, R.style.Toast).show()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val callbackManager = CallbackManager.Factory.create()

        binding.facebookSignIn.setPermissions("email", "public_profile")
        binding.facebookSignIn.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                StyleableToast.makeText(requireContext(), "Something went wrong during signing in. Please try again",Toast.LENGTH_SHORT, R.style.Toast).show()
            }
        })

        binding.googleSignIn.setOnClickListener {
            val googleSignInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(googleSignInIntent)
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.from_navigation_login_to_navigation_backup)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}