package fuzzylogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * Jenis Logika : K-Nearest Neighbor
 */
public class FuzzyLogic {

    // Lokasi file (C://Progran File/test)
    private static final String DATA_SET_LOCATION = "/home/ivans/fuzzy_logic/dataset.csv";
    private static final String DATA_TEST_LOCATION = "/home/ivans/fuzzy_logic/datatest.csv";

    public static void main(String[] args) {

        System.out.println("======================================================");
        System.out.println("===================== Data Awal ======================");
        System.out.println("======================================================");
        System.out.println("| No.   | Like | Provokasi | Komentar | Emosi | Hoax |");
        System.out.println("======================================================");
        List<ObjectData> listDataDicari = readDataSet();
        System.out.println("======================================================");
        System.out.println("======================================================");
        System.out.println();
        System.out.println();

        // ===========================================================================
        // ===========================================================================
        // ===========================================================================
        System.out.println("======================================================");
        System.out.println("===================== Data Hasil =====================");
        System.out.println("======================================================");
        readDataTest(listDataDicari);
        System.out.println("======================================================");
        System.out.println("======================================================");
    }

    public static String pad(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    private static List<ObjectData> readDataSet() {
        String line = "";
        List<ObjectData> listData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_SET_LOCATION))) {
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(",");
                String nama = data[0];
                int like = Integer.parseInt(data[1]);
                int provokasi = Integer.parseInt(data[2]);
                int komentar = Integer.parseInt(data[3]);
                int emosi = Integer.parseInt(data[4]);
                Boolean hoax = Boolean.FALSE;
                if (data[5].equalsIgnoreCase("1")) {
                    hoax = Boolean.TRUE;
                }

                ObjectData d = new ObjectData(nama, like, provokasi, komentar, emosi, hoax);
                listData.add(d);
                System.out.println("| " + nama + " | " + pad(d.getLike() + "", 4) + " | " + pad(d.getProvokasi() + "", 9) + " | " + pad(d.getKomentar() + "", 8) + " | " + pad(d.getEmosi() + "", 5) + " | " + data[5] + "    |");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listData;
    }

    private static void readDataTest(List<ObjectData> listDataSet) {
        String line = "";
        List<ObjectData> listData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_TEST_LOCATION))) {
            while ((line = br.readLine()) != null) {
                List<ObjectData> listDataSetSementara = listDataSet;

                // use comma as separator
                String[] dataStr = line.split(",");
                String nama = dataStr[0];
                int like = Integer.parseInt(dataStr[1]);
                int provokasi = Integer.parseInt(dataStr[2]);
                int komentar = Integer.parseInt(dataStr[3]);
                int emosi = Integer.parseInt(dataStr[4]);

                // ==== Menghitung jarak dari data set (rumus) ====
                for (ObjectData dAwal : listDataSetSementara) {
                    int hasil = ((dAwal.getEmosi() - emosi)
                            * (dAwal.getEmosi() - emosi))
                            + ((dAwal.getProvokasi() - provokasi)
                            * (dAwal.getProvokasi() - provokasi))
                            + ((dAwal.getKomentar() - komentar)
                            * (dAwal.getKomentar() - komentar))
                            + ((dAwal.getEmosi() - emosi)
                            * (dAwal.getEmosi() - emosi));

                    dAwal.setJarak(hasil);
                }
                // ===== Order dari hasil terkecil =======
                Collections.sort(listDataSetSementara, new Comparator<ObjectData>() {
                    public int compare(ObjectData o1, ObjectData o2) {
                        return o1.getJarak().compareTo(o2.getJarak());
                    }
                });

                // Diambil 10 data dengan jarak terdekat dan di kalkulasi jumlah iya atau tidak dari 10 data tersebut
                int k = 1;
                int ya = 0;
                int tdk = 0;
                for (ObjectData dataCheck : listDataSetSementara) {
                    if (dataCheck.isIsHoax()) {
                        ya++;
                    } else {
                        tdk++;
                    }

                    // Flag, hanya 10 data terdekat yang menjadi acuan
                    if (k == 10) {
                        break;
                    }
                    k++;
                }

                //Kondisi apakah hoax atau tdk
                Boolean isHoax = Boolean.FALSE;
                if (ya >= tdk) {
                    isHoax = Boolean.TRUE;
                }

                ObjectData d = new ObjectData(nama, like, provokasi, komentar, emosi, isHoax);

                String hoax = d.isIsHoax() ? "1" : "0";
                System.out.println("| " + nama + " | " + pad(d.getLike() + "", 4) + " | " + pad(d.getProvokasi() + "", 9) + " | " + pad(d.getKomentar() + "", 8) + " | " + pad(d.getEmosi() + "", 5) + " | " + hoax + "    |");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
