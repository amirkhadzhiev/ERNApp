package gov.ukuk.ernapp.ui.fragments.ernportal

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import gov.ukuk.ernapp.databinding.FragmentErnPortalBinding


class ErnPortalFragment : Fragment() {

    private var _binding: FragmentErnPortalBinding? = null
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentErnPortalBinding.inflate(inflater, container,false)
        val view = binding.root

        binding.webView.loadUrl("http://ern.gp")

        //Enable Javascript
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.domStorageEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        binding.webView.webViewClient = WebViewController()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}