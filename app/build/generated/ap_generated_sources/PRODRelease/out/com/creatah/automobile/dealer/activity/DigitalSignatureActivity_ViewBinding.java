// Generated code from Butter Knife. Do not modify!
package com.creatah.automobile.dealer.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.creatah.automobile.dealer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DigitalSignatureActivity_ViewBinding implements Unbinder {
  private DigitalSignatureActivity target;

  private View view2131296446;

  private View view2131296444;

  @UiThread
  public DigitalSignatureActivity_ViewBinding(DigitalSignatureActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DigitalSignatureActivity_ViewBinding(final DigitalSignatureActivity target, View source) {
    this.target = target;

    View view;
    target.horizontalRecyclerViewImageList = Utils.findRequiredViewAsType(source, R.id.horizontalRecyclerViewImageList, "field 'horizontalRecyclerViewImageList'", RecyclerView.class);
    target.noResultFound = Utils.findRequiredViewAsType(source, R.id.noResultFound, "field 'noResultFound'", TextView.class);
    target.checkListLayout = Utils.findRequiredViewAsType(source, R.id.checkListLayout, "field 'checkListLayout'", LinearLayout.class);
    target.imageListLayout = Utils.findRequiredViewAsType(source, R.id.imageListLayout, "field 'imageListLayout'", LinearLayout.class);
    target.checkRecyclerView = Utils.findRequiredViewAsType(source, R.id.checkRecyclerView, "field 'checkRecyclerView'", RecyclerView.class);
    target.clickSignPageButton = Utils.findRequiredViewAsType(source, R.id.clickSignPageButton, "field 'clickSignPageButton'", Button.class);
    target.kmsDriven = Utils.findRequiredViewAsType(source, R.id.kmsDriven, "field 'kmsDriven'", TextView.class);
    target.termsAndConditionCheckbox = Utils.findRequiredViewAsType(source, R.id.termsAndConditionCheckbox, "field 'termsAndConditionCheckbox'", CheckBox.class);
    target.termsAndConditionTextView = Utils.findRequiredViewAsType(source, R.id.termsAndConditionTextView, "field 'termsAndConditionTextView'", TextView.class);
    target.updateButtonLayout = Utils.findRequiredViewAsType(source, R.id.updateButtonLayout, "field 'updateButtonLayout'", Button.class);
    target.anyOthers = Utils.findRequiredViewAsType(source, R.id.anyOthers, "field 'anyOthers'", TextView.class);
    target.anyOthersText = Utils.findRequiredViewAsType(source, R.id.anyOthersText, "field 'anyOthersText'", TextView.class);
    target.signImageView = Utils.findRequiredViewAsType(source, R.id.signImageView, "field 'signImageView'", ImageView.class);
    target.noInternetConnection = Utils.findRequiredViewAsType(source, R.id.noInternetConnection, "field 'noInternetConnection'", TextView.class);
    target.nestedScrollView = Utils.findRequiredViewAsType(source, R.id.nestedScrollView, "field 'nestedScrollView'", NestedScrollView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", ScrollView.class);
    target.noCheckListFound = Utils.findRequiredViewAsType(source, R.id.noCheckListFound, "field 'noCheckListFound'", TextView.class);
    view = Utils.findRequiredView(source, R.id.checkListTimeTextView, "field 'checkListTimeTextView' and method 'onClickTime'");
    target.checkListTimeTextView = Utils.castView(view, R.id.checkListTimeTextView, "field 'checkListTimeTextView'", TextView.class);
    view2131296446 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickTime();
      }
    });
    target.digitalLayout = Utils.findRequiredViewAsType(source, R.id.digitalLayout, "field 'digitalLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.checkListDateTextView, "field 'checkListDateTextView' and method 'onClickDate'");
    target.checkListDateTextView = Utils.castView(view, R.id.checkListDateTextView, "field 'checkListDateTextView'", TextView.class);
    view2131296444 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDate();
      }
    });
    target.deleteSignature = Utils.findRequiredViewAsType(source, R.id.deleteSignature, "field 'deleteSignature'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DigitalSignatureActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.horizontalRecyclerViewImageList = null;
    target.noResultFound = null;
    target.checkListLayout = null;
    target.imageListLayout = null;
    target.checkRecyclerView = null;
    target.clickSignPageButton = null;
    target.kmsDriven = null;
    target.termsAndConditionCheckbox = null;
    target.termsAndConditionTextView = null;
    target.updateButtonLayout = null;
    target.anyOthers = null;
    target.anyOthersText = null;
    target.signImageView = null;
    target.noInternetConnection = null;
    target.nestedScrollView = null;
    target.scrollView = null;
    target.noCheckListFound = null;
    target.checkListTimeTextView = null;
    target.digitalLayout = null;
    target.checkListDateTextView = null;
    target.deleteSignature = null;

    view2131296446.setOnClickListener(null);
    view2131296446 = null;
    view2131296444.setOnClickListener(null);
    view2131296444 = null;
  }
}
