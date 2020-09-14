package in.alertmeu.a4b.utils;

import java.util.ArrayList;

public class Config {

    public static final String BASE_URL = "https://www.alertmeu.com/alertmeutest/api/";
    public static final String URL_AlertMeUImage ="https://www.alertmeu.com/LocationImagesTest/";
    //public static final String BASE_URL = "https://www.alertmeu.com/alertmeu/api/";
    public static final String URL_ADDUSERBYA = BASE_URL + "user/adduserByA";
    public static final String URL_MAILBUSINESSLOGIN = BASE_URL + "user/mailBusinessLogin";
    public static final String URL_ADDBUSINESSDATA = BASE_URL + "user/addBusinessData";
    public static final String URL_UPDATEBUSINESSDATA = BASE_URL + "user/updateBusinessData";
    public static final String URL_ADDREQUESTATTACHMENT = BASE_URL + "user/addRequestAttachment";
    public static final String URL_UPDATEREQUESTATTACHMENT = BASE_URL + "user/updateRequestAttachment";
    public static final String URL_GETALLLOCATIONDATA = BASE_URL + "user/getallLocationData";
    public static final String URL_UPDATELOCSTATUS = BASE_URL + "user/updateLocStatus";
    public static final String URL_GETIMAGELOCPATH = BASE_URL + "user/getImageLocPath";
    public static final String URL_GETALERTTYPES = BASE_URL + "user/getAlertTypes";
    public static final String URL_GETALERTSUBSCRIPTIONS = BASE_URL + "user/getAlertSubscriptions";
    public static final String URL_GETALLBUSINESSUSERADVERTISEMENT = BASE_URL + "user/getAllBusinessUserAdvertisement";
    public static final String URL_GETALLBUSINESSNOTACTIVEUSERADVERTISEMENT = BASE_URL + "user/getAllBusinessNotActiveUserAdvertisement";
    public static final String URL_GETALLALERTS = BASE_URL + "user/getAllAlerts";
    public static final String URL_CHECKBUSEREMAILLOGIN = BASE_URL + "user/checkBUserEmailLogin";
    public static final String URL_CHECKBUSINESSMOBILELOGIN = BASE_URL + "user/checkBusinessMobileLogin";
    public static final String URL_MOBILEBUSINESSLOGIN = BASE_URL + "user/mobileBusinessLogin";
    public static final String URL_ADDUSERBYGOOGLE = BASE_URL + "user/adduserByGoogle";
    public static final String URL_GETALLSLIDEIMAGES = BASE_URL + "user/getAllSlideImages";
    public static final String URL_UPDATEPROFILEPATH = BASE_URL + "user/updateBusinessProfilePath";
    public static final String URL_ADDUSERLIKES = BASE_URL + "user/addUserLikes";
    public static final String URL_CUCBID = BASE_URL + "user/cUCBBId";
    public static final String URL_ADDUSERPLACES = BASE_URL + "user/addUserplaces";
    public static final String URL_GETALLMAINCATEGORY = BASE_URL + "user/getAllMainCategory";
    public static final String URL_GETAVAILABLEBMOBILENUMBER = BASE_URL + "user/getAvailableBMobileNumber";
    public static final String URL_ADDBUSINESSACCOUNTDEATILS = BASE_URL + "user/addBusinessAccountDeatils";
    public static final String URL_SAVEMAINCAT = BASE_URL + "user/savemaincat";
    public static final String URL_DELETEMAINCATEGORY = BASE_URL + "user/deletemaincategory";
    public static final String URL_GETALLMAINSUBCATEGORY = BASE_URL + "user/getAllMainSubCategory";
    public static final String URL_SAVESUBCAT = BASE_URL + "user/savesubcat";
    public static final String URL_DELETESUBCATEGORY = BASE_URL + "user/deletesubcategory";
    public static final String URL_GETALLMAINCATEGORYBYBUSINESSUSER = BASE_URL + "user/getAllMainCategoryByBusinessUser";
    public static final String URL_GETALLSUBCATEGORYBUSINESSUSERBYID = BASE_URL + "user/getAllSubCategoryBusinessUserById";
    public static final String URL_SHOPPERCATSYNCDATA = BASE_URL + "user/shopperCatSyncData";
    public static final String URL_SENDBUSINESSTOUSERNOTIFICATION = BASE_URL + "user/sendBusinessToUserNotification";
    public static final String URL_GETALLUSERCOUNTFORBUSINESSLOCATIONDATA = BASE_URL + "user/getallUserCountForBusinessLocationData";
    public static final String URL_GETBUSINESSUSERDEATILS = BASE_URL + "user/getBusinessUserDeatils";
    public static final String URL_GETQRCODEFORBUSINESS = BASE_URL + "user/getQRCodeForBusiness";
    public static final String URL_GETBUSINESSUSERDETAILS = BASE_URL + "user/getBusinessUserDetails";
    public static final String URL_DELETEBUSINESSIMAGE = BASE_URL + "user/deleteBusinessImage";
    public static final String URL_UPDATEBUSINESSUSERDETAILS = BASE_URL + "user/updateBusinessUserDetails";
    public static final String URL_UPDATEBUSINESSEUPDATE = BASE_URL + "user/updateBusinessEUpdate";
    public static final String URL_UPDATEBUSINESSMUPDATE = BASE_URL + "user/updateBusinessMUpdate";
    public static final String URL_UPDATEBUSINESSUSERLOCDETAILS = BASE_URL + "user/updateBusinessUserLocDetails";
    public static final String URL_GETBALANCEAMOUNT = BASE_URL + "user/getBalanceAmount";
    public static final String URL_ADDUSERREDEEMED = BASE_URL + "user/addUserRedeemed";
    public static final String URL_ADDCONTACTUS = BASE_URL + "user/addContactUs";
    public static final String URL_INSERTUSERRQ = BASE_URL + "user/insertUserRq";
    public static final String URL_GETTREAMSCONDITION = BASE_URL + "user/getTreamsCondition";
    public static final String URL_GETALLFAQQUESTION = BASE_URL + "user/getAllFAQQuestionBusiness";
    public static final String URL_GETAVAILABLECHANGEBUSINESSMOBILENUMBER = BASE_URL + "user/getAvailableChangeBusinessMobileNumber";
    public static final String URL_GETAVAILABLECHANGEBUSINESSEMAILID = BASE_URL + "user/getAvailableChangeBusinessEmailId";
    public static final String URL_UPDATEMOBILENO = BASE_URL + "user/updateBMobileNo";
    public static final String URL_UPDATEBEMAIL = BASE_URL + "user/updateBEmail";
    public static final String URL_UPDATEBPASSWORD = BASE_URL + "user/updateBPassword";
    public static final String URL_UPDATEREPOSTADS = BASE_URL + "user/updateRePostAds";
    public static final String URL_UPDATEADAMT = BASE_URL + "user/updateAdAmt";
    public static final String URL_ADDBAMT = BASE_URL + "user/addBAmt";
    public static final String URL_ADDREBAMT = BASE_URL + "user/addReBAmt";
    public static final String URL_DELETEBUSINESSADIMAGE = BASE_URL + "user/deleteBusinessAdImage";
    public static final String URL_DELETEBUSINESSADS = BASE_URL + "user/deleteBusinessAds";
    public static final String URL_GETALLBUSINESSHISTORYADVERTISEMENT = BASE_URL + "user/getAllBusinessHistoryAdvertisement";
    public static final String URL_GETALLBUSINESSHISTORYTRANSACTION = BASE_URL + "user/getAllBusinessHistoryTransaction";
    public static final String URL_GETATLEATONECATBUSINESS = BASE_URL + "user/getAtleatOneCatBusiness";
    public static final String URL_UPDATEBUSINESSUSERPASSWORD = BASE_URL + "user/updateBusinessUserPassword";
    public static final String URL_CHECKINGREFERRALCODE = BASE_URL + "user/checkingReferralCode";
    public static final String URL_GETCOSTBYCAT = BASE_URL + "user/getCostByCat";
    public static final String URL_CHECKINGEMAILADDRESS = BASE_URL + "user/checkingEmailAddress";
    public static final String URL_GETALLYOUTUBELIST = BASE_URL + "user/getAllBusinessYouTubeList";
    public static final String URL_DEACTIVATEADBYID = BASE_URL + "user/deactivateAdById";
    public static final String URL_CHECLUSERREDEEMEDCODE = BASE_URL + "user/checlUserRedeemedCode";
    public static final String URL_BUSINESSPRECATSYNCDATA = BASE_URL + "user/businessPreCatSyncData";
   // public static final String URL_AlertMeUImage = "https://www.alertmeu.com/LocationImages/";

    // Directory name to store captured images
    public static final String IMAGE_DIRECTORY_NAME = "AlertMeUBusiness";

    public static ArrayList<String> VALUE = new ArrayList<String>();


}
