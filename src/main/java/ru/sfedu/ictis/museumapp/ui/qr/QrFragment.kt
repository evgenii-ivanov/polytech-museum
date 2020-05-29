package ru.sfedu.ictis.museumapp.ui.qr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_qr.*
import kotlinx.android.synthetic.main.nav_header_main.*
import ru.sfedu.ictis.museumapp.R
import ru.sfedu.ictis.museumapp.ui.exhibits.ExhibitsFragment

public class QrFragment : Fragment() {

    private lateinit var qrViewModel: QrViewModel

    private lateinit var surfaceView: SurfaceView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        qrViewModel =
            ViewModelProviders.of(this).get(QrViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_qr, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        initScan()
    }

    fun initScan() {
        val intent = IntentIntegrator.forSupportFragment(this)
        intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        intent.setPrompt("Наведите камеру на QR-код.")
        intent.setCameraId(0)
        intent.setBeepEnabled(true)
        intent.setBarcodeImageEnabled(false)
        intent.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(context, "Не корректный QR-код", Toast.LENGTH_SHORT)
            } else {
                val exhibitsFragment = ExhibitsFragment()
                if (fragmentManager == null) {
                    return
                }
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_qr, exhibitsFragment)
                    ?.commit()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /*
    fun initScan() {
        val barcodeDetector = BarcodeDetector.Builder(this.activity)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()
        val qwe = this.activity?.findViewById<SurfaceView>(R.id.camerapreview)
        val surfaceView = this.activity?.findViewById(R.id.camerapreview) as SurfaceView
        val qrText = this.activity?.findViewById(R.id.qrText) as TextView
        val cameraSource = CameraSource.Builder(this.activity, barcodeDetector)
            .setRequestedPreviewSize(640, 480).build()
        val svHolfer = surfaceView.holder
        surfaceView.holder.addCallback(object: SurfaceHolder.Callback {
            public override fun surfaceCreated(holder: SurfaceHolder) {
                cameraSource.start(holder)
            }

            public override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

            }

            public override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
        barcodeDetector.setProcessor(object: Detector.Processor<Barcode> {
            override fun release() {

            }
            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val qrCodes = detections?.detectedItems
                if (qrCodes?.size() != 0)
                {
                    textView.post(object: Runnable {
                        override fun run() {
                            val vibrator = context?.applicationContext?.getSystemService(Context.VIBRATOR_SERVICE)
                            qrText.setText(qrCodes?.valueAt(0)?.displayValue)

                        }

                    })
                }
            }

        })
    }
    */
}