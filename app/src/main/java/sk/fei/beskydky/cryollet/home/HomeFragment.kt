package sk.fei.beskydky.cryollet.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.HomeFragmentBinding
import sk.fei.beskydky.cryollet.home.requestpayment.RequestPaymentFragment
import sk.fei.beskydky.cryollet.setHideKeyboardOnClick

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var myContext: FragmentActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.eventRequestPaymentClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                val dialog = RequestPaymentFragment()
                dialog.show(myContext.supportFragmentManager, "requestPaymentDialog")
                viewModel.onRequestPaymentFinished()
            }
        })

        viewModel.eventSendPaymentClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                //TODO:  dokoncit navigaciu na send payment fragment
                viewModel.onSendPaymentFinished()
            }
        })



        setUpGraph(binding,  viewModel)
        binding.viewModel = viewModel
        binding.root.setHideKeyboardOnClick(this)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }

    fun setUpGraph(binding: HomeFragmentBinding, viewModel: HomeViewModel){
        var pieChart = binding.transactionGraph
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(72f, "Android"))
        dataEntries.add(PieEntry(26f, "Ios"))
        dataEntries.add(PieEntry(2f, "Other"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#373A4D"))
        colors.add(Color.parseColor("#8F92A9"))
        colors.add(Color.parseColor("#F5F5FA"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
       // pieChart.animateY(1400, Easing.EaseInOutQuad)

        pieChart.setDrawEntryLabels(false)
        pieChart.setDrawCenterText(false)
        pieChart.setDrawMarkers(false)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = false

//        pieChart.setDrawCenterText()
//        pieChart.centerText = "Mobile OS Market share"

        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER

        pieChart.invalidate()
    }
}