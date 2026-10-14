package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.ContactUs;
import com.creatah.automobile.dealer.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactUsActivity extends BaseActivity {


    @BindView(R.id.adminEmail)
    TextView adminEmail;

    @BindView(R.id.adminMobile)
    TextView adminMobile;

    @BindView(R.id.technicalEmail)
    TextView technicalEmail;

    @BindView(R.id.technicalMobile)
    TextView technicalMobile;

    @BindView(R.id.adminLayout)
    CardView adminLayout;

    @BindView(R.id.technicalLayout)
    CardView technicalLayout;

    @BindView(R.id.adminEmailLayout)
    LinearLayout adminEmailLayout;

    @BindView(R.id.adminMobileLayout)
    LinearLayout adminMobileLayout;

    @BindView(R.id.technicalEmailLayout)
    LinearLayout technicalEmailLayout;

    @BindView(R.id.technicalMobileLayout)
    LinearLayout technicalMobileLayout;


    private ContactUs contactUs;
    private Context context;
    private String email, adminmobile, technicalemail, techmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        setTitle("Support");
        context = this;

        contactUs = AppUtils.getAppInfo(getApplication()).getContactDetails();

        adminEmail.setText("Email : " + contactUs.getAdminEmail());
        adminMobile.setText("Cantact No : " + contactUs.getAdminMobile());
        technicalEmail.setText("Email : " + contactUs.getTechnicalEmail());
        technicalMobile.setText("Cantact No : " + contactUs.getTechnicalMobileNo());
        email = contactUs.getAdminEmail();
        adminmobile = contactUs.getAdminMobile();
        technicalemail = contactUs.getTechnicalEmail();
        techmobile = contactUs.getTechnicalMobileNo();

        if ((email == null || email.isEmpty()) && (adminmobile == null || adminmobile.isEmpty())) {
            adminLayout.setVisibility(View.GONE);
        }

        if (email == null || email.isEmpty()) {
            adminEmailLayout.setVisibility(View.GONE);
        }

        if (adminmobile == null || adminmobile.isEmpty()) {
            adminMobileLayout.setVisibility(View.GONE);
        }


        if ((technicalemail == null || technicalemail.isEmpty()) && (techmobile == null || techmobile.isEmpty())) {
            technicalLayout.setVisibility(View.GONE);
        }

        if (technicalemail == null || technicalemail.isEmpty()) {
            technicalEmailLayout.setVisibility(View.GONE);
        }

        if (techmobile == null || techmobile.isEmpty()) {
            technicalMobileLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
