package com.slavafleer.nearpois.asynkTask;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AsynkTask for pois search request from Google Place API.
 */
public class SendQueryAsyncTask extends AsyncTask<URL, Void, String> {

    private Callbacks callbacks;
    private int requestKey;
    private String errorMessage;

    public SendQueryAsyncTask(Callbacks callbacks, int requestKey) {
        this.callbacks = callbacks;
        this.requestKey = requestKey;
    }

    // Precede before asyncTask.
    @Override
    protected void onPreExecute() {

        callbacks.onAboutToStart();
    }

    // Send query for results from api - done in parallel thread.
    @Override
    protected String doInBackground(URL... params) {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {

            URL url = params[0];

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int httpStatusCode = connection.getResponseCode();

            if (httpStatusCode != HttpURLConnection.HTTP_OK) {
                errorMessage = connection.getResponseMessage();
                return null;
            }

            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String result = "";

            String oneLine = bufferedReader.readLine();

            while (oneLine != null) {
                result += oneLine + "\n";
                oneLine = bufferedReader.readLine();
            }

            return result;

        } catch (Exception ex) {
            errorMessage = ex.getMessage();
            return null;

        } finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (Exception e) {
            }
        }
    }

    // When result received, send it to main thread.
    @Override
    protected void onPostExecute(String result) {

        if(errorMessage != null) {
            callbacks.onError(errorMessage, requestKey);
        }
        else {
            callbacks.onSuccess(result, requestKey);
        }
    }

    // Interface for adding relevant actions in parent Class.
    public interface Callbacks {

        void onAboutToStart();

        void onSuccess(String result, int respondKey);

        void onError(String errorMessage, int respondKey);
    }
}
