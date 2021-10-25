//package DataManager;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class NewEntryManager {
//    private List<Manager_Reader.Entry> dataList =new ArrayList<>();
//
//    public void addEntry(Manager_Reader.Entry entry){
//        dataList.add(entry);
//    }
//    public List<Manager_Reader.Entry> getEntries(){
//        return dataList;
//    }
//
//
//
//}
//    public List<Entry> getDataFromDB(){
//
//        try {
//            stmt = connection.createStatement();
//            ResultSet res = stmt.executeQuery("select * from firma");
//
//            List<Entry> data = new ArrayList<>();
//            while (res.next()) {
//                Entry entry = new Entry(res.getString("ico"),
//                        res.getString("com_name"),
//                        res.getString("com_addres"),
//                        res.getString("epm_mail"),
//                        res.getString("epm_name"),
//                        res.getString("epm_surn"),
//                        res.getString("date_update")
//                        );
//                data.add(entry);
//            }
//            stmt.close();
//            return data;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }