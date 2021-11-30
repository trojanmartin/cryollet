package sk.fei.beskydky.cryollet.home.qrcode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentQrCodeBinding


class QrCodeFragment : Fragment() {

    private lateinit var binding : FragmentQrCodeBinding

    private lateinit var viewModel : QrCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qr_code, container, false)
        viewModel = ViewModelProvider(this)[QrCodeViewModel::class.java]
        val args = QrCodeFragmentArgs.fromBundle(requireArguments())

        generateQRCode(args.requestPaymentInfo)

        binding.viewModel = viewModel
        return binding.root
    }

    private fun generateQRCode(data: String) {
        val multiFormatWriter = MultiFormatWriter()

        try{
            val bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 350, 350)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            binding.imageViewQRCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

}