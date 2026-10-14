// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ContactUsActivity_ViewBinding implements Unbinder {
  private ContactUsActivity target;

  @UiThread
  public ContactUsActivity_ViewBinding(ContactUsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ContactUsActivity_ViewBinding(ContactUsActivity target, View source) {
    this.target = target;

    target.adminEmail = Utils.findRequiredViewAsType(source, R.id.adminEmail, "field 'adminEmail'", TextView.class);
    target.adminMobile = Utils.findRequiredViewAsType(source, R.id.adminMobile, "field 'adminMobile'", TextView.class);
    target.technicalEmail = Utils.findRequiredViewAsType(source, R.id.technicalEmail, "field 'technicalEmail'", TextView.class);
    target.technicalMobile = Utils.findRequiredViewAsType(source, R.id.technicalMobile, "field 'technicalMobile'", TextView.class);
    target.adminLayout = Utils.findRequiredViewAsType(source, R.id.adminLayout, "field 'adminLayout'", CardView.class);
    target.technicalLayout = Utils.findRequiredViewAsType(source, R.id.technicalLayout, "field 'technicalLayout'", CardView.class);
    target.adminEmailLayout = Utils.findRequiredViewAsType(source, R.id.adminEmailLayout, "field 'adminEmailLayout'", LinearLayout.class);
    target.adminMobileLayout = Utils.findRequiredViewAsType(source, R.id.adminMobileLayout, "field 'adminMobileLayout'", LinearLayout.class);
    target.technicalEmailLayout = Utils.findRequiredViewAsType(source, R.id.technicalEmailLayout, "field 'technicalEmailLayout'", LinearLayout.class);
    target.technicalMobileLayout = Utils.findRequiredViewAsType(source, R.id.technicalMobileLayout, "field 'technicalMobileLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactUsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.adminEmail = null;
    target.adminMobile = null;
    target.technicalEmail = null;
    target.technicalMobile = null;
    target.adminLayout = null;
    target.technicalLayout = null;
    target.adminEmailLayout = null;
    target.adminMobileLayout = null;
    target.technicalEmailLayout = null;
    target.technicalMobileLayout = null;
  }
}
