package com.creatah.automobile.dealer.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.framework.utils.Constants;
import com.android.framework.utils.ServiceConstants;
import com.android.framework.utils.Utils;
import com.android.network.beans.HttpVerb;
import com.android.network.beans.ServiceRequest;
import com.android.network.listener.ResponseListener;
import com.android.network.listener.ServiceResponseListener;
import com.android.network.manager.NetworkManager;
import com.creatah.automobile.dealer.listener.UiUpdateListener;
import com.creatah.automobile.dealer.modal.AcknowledgementBookingResponse;
import com.creatah.automobile.dealer.modal.AddBookingResponse;
import com.creatah.automobile.dealer.modal.AddEmployeeResponse;
import com.creatah.automobile.dealer.modal.AddImageResponse;
import com.creatah.automobile.dealer.modal.AddRemainderResponse;
import com.creatah.automobile.dealer.modal.QuickSearchRespose;
import com.creatah.automobile.dealer.modal.DeliveryListResponse;
import com.creatah.automobile.dealer.modal.HomeResponse;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.BookingDetailsResponse;
import com.creatah.automobile.dealer.modal.BookingImageListResponse;
import com.creatah.automobile.dealer.modal.CheckListResponse;
import com.creatah.automobile.dealer.modal.CompletedListResponse;
import com.creatah.automobile.dealer.modal.CustomerEnquiryListResponse;
import com.creatah.automobile.dealer.modal.CustomerListResponse;
import com.creatah.automobile.dealer.modal.CustomerSearchResponse;
import com.creatah.automobile.dealer.modal.DealerBookingListResponse;
import com.creatah.automobile.dealer.modal.DeleteBookingResponse;
import com.creatah.automobile.dealer.modal.DeleteResponse;
import com.creatah.automobile.dealer.modal.DescriptionListResponse;
import com.creatah.automobile.dealer.modal.EditBookingResponse;
import com.creatah.automobile.dealer.modal.EmployeeListResponse;
import com.creatah.automobile.dealer.modal.ForgotPasswordResponse;
import com.creatah.automobile.dealer.modal.GCMIdBaseResponse;
import com.creatah.automobile.dealer.modal.BookingListResponse;
import com.creatah.automobile.dealer.modal.InstantServiceListResponse;
import com.creatah.automobile.dealer.modal.LoginResponse;
import com.creatah.automobile.dealer.modal.ReminderListResponse;
import com.creatah.automobile.dealer.modal.SpareLabourResponse;
import com.creatah.automobile.dealer.modal.UpdateRemainderResponse;
import com.creatah.automobile.dealer.modal.UpdateStatusResponse;
import com.loopj.android.http.RequestParams;


public class ServiceManager {


    private static boolean isDummyService = false;

    //Get AppInfo
    public static void getAppInfo(Context context, RequestParams requestParams, final ServiceResponseListener<AppInfo> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_APP_INFO);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AppInfo.class, new ResponseListener<AppInfo>() {

                @Override
                public void onSuccess(AppInfo response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AppInfo> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }

    }

    public static void doLogin(Context context, RequestParams requestParams, final ServiceResponseListener<LoginResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.WORKSHOP_LOGIN);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        Log.d("CUST_MSG","SUCC");
        try {
            NetworkManager.executeJsonAsync(context, request, LoginResponse.class, new ResponseListener<LoginResponse>() {


                @Override
                public void onSuccess(LoginResponse response) {

                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<LoginResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Delivery List

    public static void getDeliveryList(Context context, RequestParams requestParams, final ServiceResponseListener<DeliveryListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_CUSTOMER_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DeliveryListResponse.class, new ResponseListener<DeliveryListResponse>() {

                @Override
                public void onSuccess(DeliveryListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DeliveryListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //Dealer forgot password

    public static void forgotPassword(Context context, RequestParams requestParams, final ServiceResponseListener<ForgotPasswordResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.FORGOT_PASSWORD);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, ForgotPasswordResponse.class, new ResponseListener<ForgotPasswordResponse>() {

                @Override
                public void onSuccess(ForgotPasswordResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<ForgotPasswordResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Employee List

    public static void getEmployeeList(Context context, RequestParams requestParams, final ServiceResponseListener<EmployeeListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_EMPLOYEE_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, EmployeeListResponse.class, new ResponseListener<EmployeeListResponse>() {

                @Override
                public void onSuccess(EmployeeListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<EmployeeListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void addEmployee(Context context, RequestParams requestParams, final ServiceResponseListener<AddEmployeeResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.ADD_EMPLOYEE);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AddEmployeeResponse.class, new ResponseListener<AddEmployeeResponse>() {

                @Override
                public void onSuccess(AddEmployeeResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AddEmployeeResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void editEmployee(Context context, RequestParams requestParams, final ServiceResponseListener<AddEmployeeResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.EDIT_EMPLOYEE);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AddEmployeeResponse.class, new ResponseListener<AddEmployeeResponse>() {

                @Override
                public void onSuccess(AddEmployeeResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AddEmployeeResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //delete Employee

    public static void deleteEmployee(Context context, RequestParams requestParams, final ServiceResponseListener<DeleteResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.DELETE_EMPLOYEE);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DeleteResponse.class, new ResponseListener<DeleteResponse>() {

                @Override
                public void onSuccess(DeleteResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DeleteResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Completed List

    public static void getCompletedList(Context context, RequestParams requestParams, final ServiceResponseListener<CompletedListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_COMPLETED_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, CompletedListResponse.class, new ResponseListener<CompletedListResponse>() {

                @Override
                public void onSuccess(CompletedListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<CompletedListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get InProgress List

    public static void getBookingList(Context context, RequestParams requestParams, final ServiceResponseListener<BookingListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_BOOKING_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, BookingListResponse.class, new ResponseListener<BookingListResponse>() {

                @Override
                public void onSuccess(BookingListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<BookingListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Enquiry List

    public static void getEnquiryList(Context context, RequestParams requestParams, final ServiceResponseListener<CustomerEnquiryListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_ENQUIRY_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, CustomerEnquiryListResponse.class, new ResponseListener<CustomerEnquiryListResponse>() {

                @Override
                public void onSuccess(CustomerEnquiryListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<CustomerEnquiryListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Instant Service List

    public static void getInstantServiceList(Context context, RequestParams requestParams, final ServiceResponseListener<InstantServiceListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_INSTANT_SERVICE_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, InstantServiceListResponse.class, new ResponseListener<InstantServiceListResponse>() {

                @Override
                public void onSuccess(InstantServiceListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<InstantServiceListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void getAnalysisReport(Context context, RequestParams requestParams, final ServiceResponseListener<HomeResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_HOME_DETAILS);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, HomeResponse.class, new ResponseListener<HomeResponse>() {

                @Override
                public void onSuccess(HomeResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<HomeResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }

    //send gcm id

    public static void sendGCMId(Context context, RequestParams requestParams, final ServiceResponseListener<GCMIdBaseResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.UPDATE_GCM_ID);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, GCMIdBaseResponse.class, new ResponseListener<GCMIdBaseResponse>() {

                @Override
                public void onSuccess(GCMIdBaseResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<GCMIdBaseResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void addBooking(Context context, RequestParams requestParams, final ServiceResponseListener<AddBookingResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.ADD_BOOKING);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AddBookingResponse.class, new ResponseListener<AddBookingResponse>() {

                @Override
                public void onSuccess(AddBookingResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AddBookingResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }





    public static void deleteBooking(Context context, RequestParams requestParams, final ServiceResponseListener<DeleteBookingResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.DELETE_BOOKING);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DeleteBookingResponse.class, new ResponseListener<DeleteBookingResponse>() {

                @Override
                public void onSuccess(DeleteBookingResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DeleteBookingResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }

    public static void acknowledgementBooking(Context context, RequestParams requestParams, final ServiceResponseListener<AcknowledgementBookingResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.ACKNOWLEDGEMENT_BOOKING);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AcknowledgementBookingResponse.class, new ResponseListener<AcknowledgementBookingResponse>() {

                @Override
                public void onSuccess(AcknowledgementBookingResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AcknowledgementBookingResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void updateStatus(Context context, RequestParams requestParams, final ServiceResponseListener<UpdateStatusResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.UPDATE_FINAL_AMOUNT);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, UpdateStatusResponse.class, new ResponseListener<UpdateStatusResponse>() {

                @Override
                public void onSuccess(UpdateStatusResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<UpdateStatusResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get dealer booking history

    public static void getWorkShopJobCardList(Context context, RequestParams requestParams, final ServiceResponseListener<DealerBookingListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_JOB_CARD_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DealerBookingListResponse.class, new ResponseListener<DealerBookingListResponse>() {

                @Override
                public void onSuccess(DealerBookingListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DealerBookingListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void addImage(Context context, RequestParams requestParams, final ServiceResponseListener<AddImageResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.ADD_IMAGE);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AddImageResponse.class, new ResponseListener<AddImageResponse>() {

                @Override
                public void onSuccess(AddImageResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AddImageResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get booking image List

    public static void getBookingImageList(Context context, RequestParams requestParams, final ServiceResponseListener<BookingImageListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_BOOKING_IMAGE_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, BookingImageListResponse.class, new ResponseListener<BookingImageListResponse>() {

                @Override
                public void onSuccess(BookingImageListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<BookingImageListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void deleteImage(Context context, RequestParams requestParams, final ServiceResponseListener<AddImageResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.DELETE_IMAGE);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AddImageResponse.class, new ResponseListener<AddImageResponse>() {

                @Override
                public void onSuccess(AddImageResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AddImageResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void addRemainder(Context context, RequestParams requestParams, final ServiceResponseListener<AddRemainderResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.ADD_REMAINDER);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, AddRemainderResponse.class, new ResponseListener<AddRemainderResponse>() {

                @Override
                public void onSuccess(AddRemainderResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<AddRemainderResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    // get Reminder List
    public static void getRemainderList(Context context, RequestParams requestParams, final ServiceResponseListener<ReminderListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_REMAINDER_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, ReminderListResponse.class, new ResponseListener<ReminderListResponse>() {

                @Override
                public void onSuccess(ReminderListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<ReminderListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    // Delete Reminder
    public static void deleteRemainder(Context context, RequestParams requestParams, final ServiceResponseListener<DeleteResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.DELETE_REMAINDER);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DeleteResponse.class, new ResponseListener<DeleteResponse>() {

                @Override
                public void onSuccess(DeleteResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DeleteResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }

    //update Reminder
    public static void updateRemainder(Context context, RequestParams requestParams, final ServiceResponseListener<UpdateRemainderResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.UPDATE_REMAINDER);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, UpdateRemainderResponse.class, new ResponseListener<UpdateRemainderResponse>() {

                @Override
                public void onSuccess(UpdateRemainderResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<UpdateRemainderResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }

   // booking details
    public static void getBookingDetails(Context context, RequestParams requestParams, final ServiceResponseListener<BookingDetailsResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.BOOKING_DETAILS);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, BookingDetailsResponse.class, new ResponseListener<BookingDetailsResponse>() {

                @Override
                public void onSuccess(BookingDetailsResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<BookingDetailsResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get check List

    public static void getCheckList(Context context, RequestParams requestParams, final ServiceResponseListener<CheckListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_CHECK_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, CheckListResponse.class, new ResponseListener<CheckListResponse>() {

                @Override
                public void onSuccess(CheckListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<CheckListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Description list
    public static void getDescriptionList(Context context, RequestParams requestParams, final ServiceResponseListener<DescriptionListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_DESCRIPTION_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DescriptionListResponse.class, new ResponseListener<DescriptionListResponse>() {

                @Override
                public void onSuccess(DescriptionListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DescriptionListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get check in work list
    public static void getCheckInWorkList(Context context, RequestParams requestParams, final ServiceResponseListener<DescriptionListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_CHECK_IN_WORK_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DescriptionListResponse.class, new ResponseListener<DescriptionListResponse>() {

                @Override
                public void onSuccess(DescriptionListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DescriptionListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get Spare List
    public static void getSpareList(Context context, RequestParams requestParams, final ServiceResponseListener<SpareLabourResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_SPARE_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, SpareLabourResponse.class, new ResponseListener<SpareLabourResponse>() {

                @Override
                public void onSuccess(SpareLabourResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<SpareLabourResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }

    public static void getSpareQuickList(Context context, RequestParams requestParams, final ServiceResponseListener<SpareLabourResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_SPARE_QUICK_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, SpareLabourResponse.class, new ResponseListener<SpareLabourResponse>() {

                @Override
                public void onSuccess(SpareLabourResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<SpareLabourResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }
    //get Labour List
    public static void getLabourList(Context context, RequestParams requestParams, final ServiceResponseListener<SpareLabourResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_LABOUR_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, SpareLabourResponse.class, new ResponseListener<SpareLabourResponse>() {

                @Override
                public void onSuccess(SpareLabourResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<SpareLabourResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }
    //get Quick Search Labour List
    public static void getLabourQuickList(Context context, RequestParams requestParams, final ServiceResponseListener<SpareLabourResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_LABOUR_QUICK_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, SpareLabourResponse.class, new ResponseListener<SpareLabourResponse>() {

                @Override
                public void onSuccess(SpareLabourResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<SpareLabourResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }

    //get check in work list
    public static void getServiceSpareLabourList(Context context, RequestParams requestParams, final ServiceResponseListener<DescriptionListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_CHECK_IN_SPARE_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DescriptionListResponse.class, new ResponseListener<DescriptionListResponse>() {

                @Override
                public void onSuccess(DescriptionListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DescriptionListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //delete check in work list
    public static void deleteCheckInWorkList(Context context, RequestParams requestParams, final ServiceResponseListener<DeleteResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.DELETE_CHECK_IN_WORK_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DeleteResponse.class, new ResponseListener<DeleteResponse>() {

                @Override
                public void onSuccess(DeleteResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DeleteResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //get inspection list
    public static void getInspectionList(Context context, RequestParams requestParams, final ServiceResponseListener<DescriptionListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_BOOKING_AMOUNT);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DescriptionListResponse.class, new ResponseListener<DescriptionListResponse>() {

                @Override
                public void onSuccess(DescriptionListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DescriptionListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }




    public static void updateBookingStatus(Context context, RequestParams requestParams, final ServiceResponseListener<UpdateStatusResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.UPDATE_BOOKING_STATUS);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, UpdateStatusResponse.class, new ResponseListener<UpdateStatusResponse>() {

                @Override
                public void onSuccess(UpdateStatusResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<UpdateStatusResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void deleteWorkItem(Context context, RequestParams requestParams, final ServiceResponseListener<DeleteResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.DELETE_WORK_ITEM);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, DeleteResponse.class, new ResponseListener<DeleteResponse>() {

                @Override
                public void onSuccess(DeleteResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<DeleteResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void deleteWorkItem(final Context context, RequestParams requestParams, final UiUpdateListener uiUpdateListener) {

        ServiceManager.deleteWorkItem(context, requestParams, new ServiceResponseListener<DeleteResponse>() {
                    @Override
                    public void onSuccess(DeleteResponse response) {



                        if (!(uiUpdateListener == null)) {
                            uiUpdateListener.OnUpdateUI(response);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String errorResponse) {
                        if (!(uiUpdateListener == null)) {
                            uiUpdateListener.OnFailure(errorResponse);
                        }
                    }
                }

        );

    }


    //get Customer List

    public static void getCustomerList(Context context, RequestParams requestParams, final ServiceResponseListener<CustomerListResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_CUSTOMER_LIST);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, CustomerListResponse.class, new ResponseListener<CustomerListResponse>() {

                @Override
                public void onSuccess(CustomerListResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<CustomerListResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void getCustomerDetails(Context context, RequestParams requestParams, final ServiceResponseListener<CustomerSearchResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.CUSTOMER_DETAILS);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, CustomerSearchResponse.class, new ResponseListener<CustomerSearchResponse>() {

                @Override
                public void onSuccess(CustomerSearchResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<CustomerSearchResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    public static void editBooking(Context context, RequestParams requestParams, final ServiceResponseListener<EditBookingResponse> listener) {

        String targetUrl = getServiceAPIUrl(ServiceConstants.EDIT_BOOKING);

        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, EditBookingResponse.class, new ResponseListener<EditBookingResponse>() {

                @Override
                public void onSuccess(EditBookingResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onFailure(Throwable throwable, String errorResponse) {
                    if (listener != null) {
                        listener.onFailure(throwable, errorResponse);
                    }
                }

                @Override
                public Class<EditBookingResponse> getTargetType() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onStart() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSuccess(String arg0) {
                    // TODO Auto-generated method stub
                    listener.onSuccess(null);

                }
            });

        } catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }


    //getting service url
    public static String getServiceAPIUrl(String key) {

        if (TextUtils.isEmpty(key)) {
            return Constants.EMPTY;
        }

        return Utils.getResponse().getdata().getApiservice().get(ServiceConstants.SERVER_URL_KEY) +
                Utils.getResponse().getdata().getApiservice().get(ServiceConstants.API_URL_ENDPOINT) +
                Utils.getResponse().getdata().getApiservice().get(key);

    }


    public static void quicksearch_spare(Context context, RequestParams requestParams, final ServiceResponseListener<SpareLabourResponse> listener) {
        String targetUrl = getServiceAPIUrl(ServiceConstants.QuickSearchSpare);
        System.out.println(targetUrl);
        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, SpareLabourResponse.class, new ResponseListener<SpareLabourResponse>() {
                @Override
                public void onFailure(Throwable error, String response) {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(SpareLabourResponse response) {
                    if (listener != null) {
                        listener.onSuccess(response);
                    }
                }

                @Override
                public void onSuccess(String response) {
                    listener.onSuccess(null);
                }


                @Override
                public Class<SpareLabourResponse> getTargetType() {
                    return null;
                }
            });
        }catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }
    public static void quicksearch_labour(Context context, RequestParams requestParams, final ServiceResponseListener<SpareLabourResponse> listener) {
        String targetUrl = getServiceAPIUrl(ServiceConstants.GET_SPARE_LIST);
        System.out.println(targetUrl);
        ServiceRequest request = new ServiceRequest.Builder(targetUrl).verb(HttpVerb.POST).addRequestParams(requestParams).build();
        try {
            NetworkManager.executeJsonAsync(context, request, SpareLabourResponse.class, new ResponseListener<SpareLabourResponse>() {
                @Override
                public void onFailure(Throwable error, String response) {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(SpareLabourResponse response) {

                }



                @Override
                public void onSuccess(String response) {

                }

                @Override
                public Class<SpareLabourResponse> getTargetType() {
                    return null;
                }
            });
        }catch (Exception e) {
            listener.onFailure(e.getCause(), "");
        }
    }
}
