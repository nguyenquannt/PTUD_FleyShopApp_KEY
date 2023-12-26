package main;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Precondition;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.core.view.QuerySpec;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Firebase_DAO {

    CollectionReference reference;
    static Firestore db;
//    static Firestore db = FirestoreClient.getFirestore();

    //Thêm
    public static boolean addDataFirebase(String collection, String document, Map<String, Object> data) {
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Add data to Firebase successfully !");
            return true;

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return false;
    }

//    Sửa
    public static boolean editDataFirebase(String collection, String documen, Map<String, Object> data) {
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(collection).document(documen);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Successfully updated data to Firebase !");
            return true;

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return false;
    }

//        Xóa
    public static boolean deletaDataFirebase(String collection, String documento) {
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(collection).document(documento);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Delete data Firebase successfully !");
            return true;

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return false;
    }

//    load dữ liệu 
    public static void loadData(JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID ứng dụng");
        model.addColumn("Key bản quyền");
        model.addColumn("Ngày bắt đầu");
        model.addColumn("Ngày kết thúc");
        model.addColumn("Mô tả");

        try {
            CollectionReference collection = ConnectFirebase.db.collection("LicenseKey");
            ApiFuture<QuerySnapshot> querySnap = collection.get();

            for (DocumentSnapshot document : querySnap.get().getDocuments()) {
                model.addRow(new Object[]{
                    document.getId(),
                    document.getString("Key"),
                    document.getString("dayStart"),
                    document.getString("dayEnd"),
                    document.getString("decription")
                });
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        table.setModel(model);
        System.out.println("Get data from Firebase successfully !");
    }

}
