package signalmaster.com.smmobile.notuse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class SmFileReader {



    /*//FileInputStream
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream("C:\\Users\\signalmaster\\Downloads\\mst\\MRKT.cod");
            byte[] data      = new byte[134];
            int    bytesRead = inputstream.read(data);
            while (bytesRead != -1) {
                // doSomethingWithData(data, bytesRead);

                bytesRead = inputstream.read(data);
                String value;
                value = Arrays.toString(data);
                System.out.print(value);
            }

            inputstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    public void Reader() {

        File file = new File("C:\\Users\\signalmaster\\Downloads\\mst\\MRKT.cod");
        FileInputStream fis = null;
        BufferedInputStream bufis = null;
        int data = 0;

        if (file.exists() && file.canRead()) {
            try {
                // open file.
                fis = new FileInputStream(file);
                bufis = new BufferedInputStream(fis);

                // read data from bufis's buffer.
                while ((data = bufis.read()) != -1) {
                    // TODO : use data
                    System.out.println("data : " + data);

                }

                // close file.
                bufis.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

