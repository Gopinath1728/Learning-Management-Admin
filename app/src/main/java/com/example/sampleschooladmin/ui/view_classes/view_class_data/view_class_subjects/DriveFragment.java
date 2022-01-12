package com.example.sampleschooladmin.ui.view_classes.view_class_data.view_class_subjects;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.sampleschooladmin.Common.Common;
import com.example.sampleschooladmin.R;
import com.example.sampleschooladmin.databinding.FragmentDriveBinding;
import com.example.sampleschooladmin.databinding.FragmentViewClassDataBinding;

import java.util.Objects;


public class DriveFragment extends Fragment {

    String link;
    WebView web_view;
    int classPos,subPos;

    private FragmentDriveBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDriveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        assert getArguments() != null;
        link = getArguments().getString("subjectLink");
        classPos = getArguments().getInt("classPos");
        subPos = getArguments().getInt("subPos");

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(Common.classModels.getValue().get(classPos).getSubjects().get(subPos).getSubjectName()+" Study Materials");

        web_view = (WebView) root.findViewById(R.id.web_view);

        web_view.loadUrl(link);
        web_view.setWebViewClient(new WebViewClient());

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_view.clearCache(true);
        web_view.clearHistory();

        web_view.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(req);
                Toast.makeText(getContext(), "Downloading....", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    private class Client extends WebViewClient {
        // on page started load start loading the url
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        // load the url of our drive
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            // if stop loading
            try {
                webView.stopLoading();
            } catch (Exception e) {
            }

            if (webView.canGoBack()) {
                webView.goBack();
            }

            // if loaded blank then show error
            // to check internet connection using
            // alert dialog
            webView.loadUrl("about:blank");
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Check your internet connection and Try again.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            alertDialog.show();
            super.onReceivedError(webView, errorCode, description, failingUrl);
        }
    }

}