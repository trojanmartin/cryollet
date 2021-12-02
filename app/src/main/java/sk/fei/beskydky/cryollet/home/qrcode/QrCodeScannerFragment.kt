package sk.fei.beskydky.cryollet.home.qrcode

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.*
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentQrCodeScannerBinding


class QrCodeScannerFragment : Fragment() {

    private lateinit var binding: FragmentQrCodeScannerBinding

    private lateinit var qrScannerCode: CodeScanner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qr_code_scanner, container, false)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }

        return binding.root
    }

    private fun startScanning() {
        val scannerView: CodeScannerView = binding.scannerView

        qrScannerCode = CodeScanner(requireContext(), scannerView)
        qrScannerCode.camera = CodeScanner.CAMERA_BACK
        qrScannerCode.formats = CodeScanner.ALL_FORMATS

        qrScannerCode.autoFocusMode = AutoFocusMode.SAFE
        qrScannerCode.scanMode = ScanMode.SINGLE
        qrScannerCode.isAutoFocusEnabled = true
        qrScannerCode.isFlashEnabled = false

        qrScannerCode.decodeCallback = DecodeCallback {
            findNavController().navigate(
                    QrCodeScannerFragmentDirections
                            .actionQrCodeScannerFragmentToSendPaymentFragment(it.text))
        }

        qrScannerCode.errorCallback = ErrorCallback {
            CrashlyticsReport.Session.Event.Log.builder()
                    .setContent("QR Code Scanner failed!").build()
        }

        scannerView.setOnClickListener {
            qrScannerCode.startPreview()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::qrScannerCode.isInitialized) {
            qrScannerCode?.startPreview()
        }
    }

    override fun onPause() {
        if (::qrScannerCode.isInitialized) {
            qrScannerCode?.releaseResources()
        }
        super.onPause()
    }
}