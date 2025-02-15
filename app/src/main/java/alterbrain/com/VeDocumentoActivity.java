package alterbrain.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import alterbrain.com.app.Constantes;

public class VeDocumentoActivity extends AppCompatActivity {

    String rutaDoc;
    PDFView pdfView;
    // url of our PDF file.
    String pdfurl = "https://"+ Constantes.LINK_FRACC+ "/Fpdf/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ve_documento);

        Bundle extras = getIntent().getExtras();
        rutaDoc = extras.getString("rutaDocumento");

        pdfurl += rutaDoc;

        Toast.makeText(VeDocumentoActivity.this,
                "Ruta al documento: " + pdfurl, Toast.LENGTH_SHORT).show();

        pdfView = findViewById(R.id.idPDFView);
        new RetrievePDFfromUrl().execute(pdfurl);
    }

    class RetrievePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }
}