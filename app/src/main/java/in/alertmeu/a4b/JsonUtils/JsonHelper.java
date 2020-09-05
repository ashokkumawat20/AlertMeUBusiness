package in.alertmeu.a4b.JsonUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.alertmeu.a4b.models.AdvertisementDAO;
import in.alertmeu.a4b.models.CurrentUserLocationAdvertisementDAO;
import in.alertmeu.a4b.models.FAQDAO;
import in.alertmeu.a4b.models.ImageModel;
import in.alertmeu.a4b.models.MainCatModeDAO;
import in.alertmeu.a4b.models.SubCatModeDAO;
import in.alertmeu.a4b.models.TransactionHistoryDAO;
import in.alertmeu.a4b.models.YouTubeDAO;


public class JsonHelper {



    private ArrayList<ImageModel> imageModelArrayList = new ArrayList<ImageModel>();
    private ImageModel imageModel;

    private ArrayList<MainCatModeDAO> mainCatModeDAOArrayList = new ArrayList<MainCatModeDAO>();
    private MainCatModeDAO mainCatModeDAO;

    private ArrayList<SubCatModeDAO> subCatModeDAOArrayList = new ArrayList<SubCatModeDAO>();
    private SubCatModeDAO subCatModeDAO;

    private ArrayList<AdvertisementDAO> advertisementDAOArrayList = new ArrayList<AdvertisementDAO>();
    private AdvertisementDAO advertisementDAO;

    private ArrayList<CurrentUserLocationAdvertisementDAO> locationAdvertisementDAOArrayList = new ArrayList<CurrentUserLocationAdvertisementDAO>();
    private CurrentUserLocationAdvertisementDAO locationAdvertisementDAO;
    private ArrayList<FAQDAO> faqdaoArrayList = new ArrayList<FAQDAO>();
    private FAQDAO faqdao;

    private ArrayList<YouTubeDAO> youTubeDAOArrayList = new ArrayList<YouTubeDAO>();
    private YouTubeDAO youTubeDAO;

    private ArrayList<TransactionHistoryDAO> transactionHistoryDAOArrayList = new ArrayList<TransactionHistoryDAO>();
    private TransactionHistoryDAO transactionHistoryDAO;

    //advertisementPaser
    public ArrayList<ImageModel> parseImagePathList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {
            JSONObject jsonObject = new JSONObject(ListResponse);

            if (!jsonObject.isNull("dataList")) {
                JSONArray jsonArray = jsonObject.getJSONArray("dataList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    imageModel = new ImageModel();
                    imageModel.setImage_id(object.getString("id"));
                    imageModel.setImage_path(object.getString("image_path"));
                    imageModel.setImage_description(object.getString("image_description"));
                    imageModel.setImage_description_hindi(object.getString("image_description_hindi"));
                    imageModelArrayList.add(imageModel);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageModelArrayList;
    }

    //Main cat
    public ArrayList<MainCatModeDAO> parseMyPlaceList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {


            JSONArray leadJsonObj = new JSONArray(ListResponse);
            for (int i = 0; i < leadJsonObj.length(); i++) {
                JSONObject object = leadJsonObj.getJSONObject(i);
                mainCatModeDAO = new MainCatModeDAO();
                mainCatModeDAO.setId(object.getString("id"));
                mainCatModeDAO.setCategory_name(object.getString("category_name"));
                mainCatModeDAO.setCategory_name_hindi(object.getString("category_name_hindi"));
                mainCatModeDAO.setChecked_status(object.getString("checked_status"));
                mainCatModeDAO.setCurrency_sign(object.getString("currency_sign"));
                mainCatModeDAO.setImage_path(object.getString("image_path"));
                mainCatModeDAOArrayList.add(mainCatModeDAO);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mainCatModeDAOArrayList;
    }

    //Sub cat
    public ArrayList<SubCatModeDAO> parseSubCatList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {


            JSONArray leadJsonObj = new JSONArray(ListResponse);
            for (int i = 0; i < leadJsonObj.length(); i++) {
                JSONObject object = leadJsonObj.getJSONObject(i);
                subCatModeDAO = new SubCatModeDAO();
                subCatModeDAO.setId(object.getString("id"));
                subCatModeDAO.setBc_id(object.getString("bc_id"));
                subCatModeDAO.setSubcategory_name(object.getString("subcategory_name"));
                subCatModeDAO.setSubcategory_name_hindi(object.getString("subcategory_name_hindi"));
                subCatModeDAO.setChecked_status(object.getString("checked_status"));
                subCatModeDAO.setActiveads(object.getString("activeads"));
                subCatModeDAO.setImage_path(object.getString("image_path"));
                subCatModeDAOArrayList.add(subCatModeDAO);

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return subCatModeDAOArrayList;
    }

    //advertisementPaser
    public ArrayList<AdvertisementDAO> parseAdvertisementList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                advertisementDAO = new AdvertisementDAO();
                String sequence = String.format("%03d", i + 1);
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                advertisementDAO.setId(json_data.getString("id"));
                advertisementDAO.setBusiness_user_id(json_data.getString("business_user_id"));
                advertisementDAO.setTitle(json_data.getString("title"));
                advertisementDAO.setDescription(json_data.getString("description"));
                advertisementDAO.setDescribe_limitations(json_data.getString("describe_limitations"));
                advertisementDAO.setRq_code(json_data.getString("rq_code"));
                advertisementDAO.setOriginal_image_path(json_data.getString("original_image_path"));
                advertisementDAO.setModify_image_path(json_data.getString("modify_image_path"));
                advertisementDAO.setLikecnt(json_data.getString("likecnt"));
                advertisementDAO.setDislikecnt(json_data.getString("dislikecnt"));
                advertisementDAO.setClick_count(json_data.getString("click_count"));
                advertisementDAO.setActive_status(json_data.getString("active_status"));
                advertisementDAO.setCreate_at(json_data.getString("create_at"));
                advertisementDAO.setE_date(json_data.getString("e_date"));
                advertisementDAO.setE_time(json_data.getString("e_time"));
                advertisementDAO.setS_date(json_data.getString("s_date"));
                advertisementDAO.setS_time(json_data.getString("s_time"));
                advertisementDAO.setPaid_amount(json_data.getString("paid_amount"));
                advertisementDAO.setTotal_time(json_data.getString("total_time"));
                advertisementDAO.setTotal_views(json_data.getString("total_views"));
                advertisementDAO.setTotal_redeemed(json_data.getString("total_redeemed"));
                advertisementDAO.setBusiness_main_category(json_data.getString("business_main_category"));
                advertisementDAO.setBusiness_subcategory(json_data.getString("business_subcategory"));
                advertisementDAO.setTunit(json_data.getString("tunit"));
                advertisementDAO.setTsign(json_data.getString("tsign"));
                advertisementDAO.setActive_user_count(json_data.getString("active_user_count"));
                advertisementDAO.setNumbers("" + sequence);
                advertisementDAOArrayList.add(advertisementDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return advertisementDAOArrayList;
    }

    //Main cat
    public ArrayList<CurrentUserLocationAdvertisementDAO> parseCurrentUsersList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {


            JSONArray leadJsonObj = new JSONArray(ListResponse);
            for (int i = 0; i < leadJsonObj.length(); i++) {
                JSONObject object = leadJsonObj.getJSONObject(i);
                locationAdvertisementDAO = new CurrentUserLocationAdvertisementDAO();
                locationAdvertisementDAO.setBusiness_user_id(object.getString("business_user_id"));
                locationAdvertisementDAO.setCategory_name(object.getString("category_name"));
                locationAdvertisementDAO.setSubcategory_name(object.getString("subcategory_name"));
                locationAdvertisementDAO.setCategory_name_hindi(object.getString("category_name_hindi"));
                locationAdvertisementDAO.setSubcategory_name_hindi(object.getString("subcategory_name_hindi"));
                locationAdvertisementDAO.setUser_count(object.getString("user_count"));
                locationAdvertisementDAO.setMaincat_id(object.getString("maincat_id"));
                locationAdvertisementDAO.setSubbc_id(object.getString("subbc_id"));
                locationAdvertisementDAOArrayList.add(locationAdvertisementDAO);

            }




        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return locationAdvertisementDAOArrayList;
    }



    public ArrayList<YouTubeDAO> parseYouTubeList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {


            JSONArray leadJsonObj = new JSONArray(ListResponse);
            for (int i = 0; i < leadJsonObj.length(); i++) {
                JSONObject object = leadJsonObj.getJSONObject(i);
                youTubeDAO = new YouTubeDAO();
                youTubeDAO.setId(object.getString("id"));
                youTubeDAO.setVideo_description(object.getString("video_description"));
                youTubeDAO.setVideo_description_hindi(object.getString("video_description_hindi"));
                youTubeDAO.setVideo_link(object.getString("video_link"));
                youTubeDAOArrayList.add(youTubeDAO);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return youTubeDAOArrayList;
    }
    public ArrayList<FAQDAO> parseFAQList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {


            JSONArray leadJsonObj = new JSONArray(ListResponse);
            for (int i = 0; i < leadJsonObj.length(); i++) {
                JSONObject object = leadJsonObj.getJSONObject(i);
                faqdao = new FAQDAO();
                faqdao.setId(object.getString("id"));
                faqdao.setTitle(object.getString("title"));
                faqdao.setDescription(object.getString("description"));
                faqdao.setTitle_hindi(object.getString("title_hindi"));
                faqdao.setDescription_hindi(object.getString("description_hindi"));
                faqdaoArrayList.add(faqdao);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return faqdaoArrayList;
    }
    public ArrayList<TransactionHistoryDAO> parsetransactionhistoryList(String ListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", ListResponse);
        try {


            JSONArray leadJsonObj = new JSONArray(ListResponse);
            for (int i = 0; i < leadJsonObj.length(); i++) {
                JSONObject object = leadJsonObj.getJSONObject(i);
                transactionHistoryDAO = new TransactionHistoryDAO();
                transactionHistoryDAO.setId(object.getString("id"));
                transactionHistoryDAO.setBusiness_user_id(object.getString("business_user_id"));
                transactionHistoryDAO.setDescription(object.getString("description"));
                transactionHistoryDAO.setAmount(object.getString("amount"));
                transactionHistoryDAO.setPrevious_balance(object.getString("previous_balance"));
                transactionHistoryDAO.setCurrent_balance(object.getString("current_balance"));
                transactionHistoryDAO.setDate_time(object.getString("date_time"));
                transactionHistoryDAOArrayList.add(transactionHistoryDAO);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return transactionHistoryDAOArrayList;
    }
}
