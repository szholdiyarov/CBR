package com.zholdiyarov.rubrates;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.zholdiyarov.rubrates.objects.Rates;
import com.zholdiyarov.rubrates.utils.RatesListAdapter;
import com.zholdiyarov.rubrates.utils.XMLReaderUtil;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import dmax.dialog.SpotsDialog;


/**
 * author: szholdiyarov
 * date:17/02/2016
 * Purpose: This activity shows list of rates to the given date
 */
public class RatesActivity extends AppCompatActivity {
    private ArrayList<Rates> rates;
    private Context context;
    private String date;
    private TextView textView_title;
    private final String ERROR_TITLE = "Проблема подключения";
    private final String CBR_BASE_URL = "http://www.cbr.ru/scripts/XML_daily.asp?";
    private final String CBR_METHOD_REQ = "date_req=";
    private final String USER_AGENT = "Mozilla/5.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rates);
        context = this;

        textView_title = (TextView) findViewById(R.id.textView_title);

        date = getIntent().getStringExtra("key");
        textView_title.setText("Курс валют на :" + date);

        if (isNetworkConnected()) { // if device is connected to network
            new MyTask().execute();
        } else {
            showNetworkError();
        }

    }

    private void showNetworkError() {
        new AlertDialog.Builder(context)
                .setTitle(ERROR_TITLE)
                .setMessage("Извините, не могу подключится к интернету. Попробуйте снова")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /* Check if device is connected to network */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * Inner task to download and parse rates
     */
    private class MyTask extends AsyncTask<Void, Void, Void> {
        private StringBuffer response = new StringBuffer();
        private AlertDialog dialog;

        private void showDialog() {
            dialog = new SpotsDialog(context);
            dialog.show();
        }

        private void stopShowingDialog() {
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = CBR_BASE_URL + CBR_METHOD_REQ + date;


            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();

                if (responseCode == 200) { // check if response is OK
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;


                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    /* parse response */
                    XMLReaderUtil xmlReaderUtil = new XMLReaderUtil(new InputSource(new ByteArrayInputStream(response.toString().getBytes())));

                    /* save parse results */
                    rates = xmlReaderUtil.getRates();
                } else {
                    showNetworkError();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /* Set adapter to list view and stop showing dialog*/
            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new RatesListAdapter(context, rates));

            stopShowingDialog();
        }
    }


}
