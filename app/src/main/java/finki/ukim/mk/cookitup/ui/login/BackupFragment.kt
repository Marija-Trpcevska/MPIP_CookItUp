package finki.ukim.mk.cookitup.ui.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.databinding.FragmentBackupBinding
import finki.ukim.mk.cookitup.domain.room.AppDatabase
import io.github.muddz.styleabletoast.StyleableToast
import java.io.File


class BackupFragment : Fragment() {

    private var _binding: FragmentBackupBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel by activityViewModels<AccountViewModel>()
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var storageReference: StorageReference
    private lateinit var notificationService: BackupNotificationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationService = BackupNotificationService(requireContext())
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                BackupNotificationService.BACKUP_CHANNEL_ID,
                "Backup Success",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used to inform the user of successful backup of data to the cloud"
            val notificationManager = this.context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBackupBinding.inflate(inflater, container, false)
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
        accountViewModel.getAccount().observe(viewLifecycleOwner){
            binding.profilePhoto.load(it.profilePhoto){
                transformations(CircleCropTransformation())
            }
            binding.profileName.text = it.displayName
            binding.profileEmail.text = it.email
        }
        binding.profileLogout.setOnClickListener {
            val provider: String? = auth.currentUser?.getIdToken(false)?.result?.signInProvider
            auth.signOut()
            if(provider.equals("google.com")){
                googleSignInClient.signOut().addOnCompleteListener {
                    if(it.isSuccessful){
                        findNavController().navigate(R.id.from_navigation_backup_to_navigation_login)
                    }
                    else{
                        StyleableToast.makeText(requireContext(), "Something went wrong during signing out. Please try again",Toast.LENGTH_SHORT, R.style.Toast).show()
                    }
                }
            }
            else{
                LoginManager.getInstance().logOut()
                findNavController().navigate(R.id.from_navigation_backup_to_navigation_login)
            }
        }
        storageReference = FirebaseStorage.getInstance().reference.child("room-database/user-${auth.currentUser?.uid}")
        binding.profileBackup.setOnClickListener {
            val backupDbPath = AppDatabase.getDatabase(requireContext()).openHelper.readableDatabase.path
            val backupDb = File(backupDbPath.toString())
            storageReference.putFile(backupDb.toUri()).addOnCompleteListener{
                if(it.isSuccessful){
                    notificationService.showNotification()
                }
                else{
                    StyleableToast.makeText(requireContext(), "Backup failed. Please try again",Toast.LENGTH_SHORT, R.style.Toast).show()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}