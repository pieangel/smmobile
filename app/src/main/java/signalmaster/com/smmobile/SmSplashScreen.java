package signalmaster.com.smmobile;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import signalmaster.com.smmobile.market.SmMarketReader;
import signalmaster.com.smmobile.network.SmServiceManager;
import signalmaster.com.smmobile.network.SmSocketManager;

public class SmSplashScreen extends Activity {

    private String mrktFile = "MRKT.cod";
    private String pmFile = "PMCODE.cod";
    private String jmFile = "JMCODE.cod";

    public void downloadPMFile() {
        new SmFileDownloader().execute(pmFile);


    }

    public void downloadJMFile() {
        new SmFileDownloader().execute(jmFile);
    }

    public void downloadMRKTFile() {
        new SmFileDownloader().execute(mrktFile);
    }

    public void startMainActivity() {
        Intent i = new Intent(SmSplashScreen.this, MainActivity.class);
        String msg = "complete";
        i.putExtra("data", msg);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        SmSocketManager sockMgr = SmSocketManager.getInstance();
        sockMgr.createWebSocket("sise_server");
        SmServiceManager svcMgr = SmServiceManager.getInstance();
        svcMgr.reqestLogin();

        startHeavyProcessing();

    }

    private void startHeavyProcessing(){
        downloadMRKTFile();
    }

    private class SmFileDownloader extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                File path = getBaseContext().getFilesDir();
                File file = new File(path, f_url[0]);
                // http://angelpie.ddns.net:9991/mst/
                String addr = "http://angelpie.ddns.net:9991/mst/";
                addr += f_url[0];
                URL url = new URL(addr);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return f_url[0];
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
            Log.e("downloading: ", progress[0]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
            Log.e("download complete: ", file_url);
            if (file_url == mrktFile) {
                downloadPMFile();
            } else if (file_url == pmFile) {
                downloadJMFile();
            } else if (file_url == jmFile) {
                startMainActivity();
            }
        }
    }
}
